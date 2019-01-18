package parser;

import parser.expression.*;
import tokenizer.Tokenizer;
import tokenizer.Token;
import tokenizer.Type;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Parser
{
    private Tokenizer tokenizer;
    private Token token;
    private NilVal nil;


    public Parser(Reader reader)
    {
        tokenizer = new Tokenizer(reader);
    }

    private void next() throws IOException {
        token = tokenizer.next();
    }

    private Type tokenType()
    {
        return token.getType();
    }

    private void previous() throws IOException {
        tokenizer.previous();
        token = tokenizer.next();

    }

    public Expr execute() throws ParserException, IOException //==program
    {
        nil = NilVal.instance();
        next();
        Expr expr = function(null);
        check(Type.EOS, token.getType());

        return expr;
    }

    private void check(Type expected, Type actual) throws ParserException {
        if(expected != actual)
            throw new ParserException("Parsing error");
    }

    private void checkAndNext(Type expected, Type actual) throws ParserException, IOException {
        if(expected != actual)
            throw new ParserException("Parsing error: expected " + expected + ", find " + actual);
        next();
    }

    private boolean isId(Type type)
    {
        return type == Type.ID;
    }

    private boolean isOptAssignment(Type tokenType)
    {
        return tokenType == Type.SEMICOLON;
    }


    /////////////////////////////////////
    //////////// GRAMMATICA ////////////
    ///////////////////////////////////
    private Expr function(Scope scope) throws ParserException, IOException {
        //checkAndNext(Type.GRAFFAOPEN, tokenType());
        checkAndNext(Type.GRAFFAOPEN, tokenType());

        List<String> params = optParams(scope);
        List<String> locals = optLocals(scope);
        List<String> params_and_locals = new ArrayList<>(params);
        params_and_locals.addAll(locals);

        scope = new Scope(scope, params_and_locals);

        Expr expr = optSequence(scope);

        checkAndNext(Type.GRAFFACLOSE, tokenType());

        return new FunExpr(params, locals, expr);
    }

    private List<String> optParams(Scope scope) throws IOException, ParserException
    {
        try {
            checkAndNext(Type.TONDAOPEN, tokenType());
            List<String> params = optIds(scope);
            checkAndNext(Type.TONDACLOSE, tokenType());

            return params;
        }
        catch (Exception ex)
        {
            return new ArrayList<>();
        }
    }

    private List<String> optLocals(Scope scope) throws ParserException, IOException
    {
        return optIds(scope);
    }

    private Expr optSequence(Scope scope) throws IOException, ParserException
    {
        if(tokenType() == Type.ARROW)
        {
            next();
            return sequence(scope);
        }

        return nil;
    }

    private List<String> optIds(Scope scope) throws ParserException, IOException {
        List<String> ids = new ArrayList<>();

        while(isId(tokenType()))
        {
            String temp = id(scope);
            if(ids.contains(temp))
                throw new ParserException("error");
            ids.add(temp);
        }

        return ids;
    }

    private String id(Scope scope) throws ParserException, IOException {

        //Controllo se esiste nello scope: se esiste, exception
        //se non esiste, ritorno il nome del token

        //checkAndNext(Type.ID, tokenType());
        check(Type.ID, tokenType());
        String value = token.getStringValue();
        next();
        return value;
    }

    private Expr sequence(Scope scope) throws IOException, ParserException
    {
        List<Expr> exprList = new ArrayList<>();

        exprList.add(optAssignment(scope));

        //Expr expr = optAssignment(scope);

        while(isOptAssignment(tokenType()))
        {
            next();
            exprList.add(optAssignment(scope));
            //expr = optAssignment(scope);

            //expr = new SeqExpr(expr, optAssignment(scope)); //cosa devo mettere dentro sequence? lista di assignment
        }

        if(exprList.size() == 0)
            return NilVal.instance();
        if(exprList.size() == 1)
            return exprList.get(0);

        return new SeqExpr(exprList);
        //return nil;
    }



    private Expr optAssignment(Scope scope) throws ParserException, IOException {
        //se c'è token che può iniziare assign, ritorno questo altrimenti null (null di Java)
        if(isAssignment())
            return assignment(scope);
        else
            return nil;
    }

    private boolean isAssignment()
    {
        switch(token.getType())
        {
            case ID:
            case PLUS:
            case MINUS:
            case NOT:
            case NUM:
            case FALSE:
            case TRUE:
            case NIL:
            case STRING:
            case TONDAOPEN:
            case GRAFFAOPEN:
            case IF:
            case IFNOT:
            case WHILE:
            case WHILENOT:
            case PRINT:
            case PRINTLN:
                return true;
            default:
                return false;
        }
    }

    private Expr assignment(Scope scope) throws ParserException, IOException {
        // controllo se il tipo è id o logicalOr; se uno di questi chiamo sotto parser altrimenti ritorno null
        // se non ho id, logicalOr
        //se ho id, devo controllare anche il carattere dopo: se è uno dei 5, allora poi dopo ho assignment altrimenti logicalOr
        if(isId(token.getType()))
        {
            String id_value = token.getStringValue(); //prendo il valore dell'id
            checkInScope(scope, id_value); //controllo che esista nello scope (altrimenti errore)
            next(); //mangio token id
            if(isAssignmentOperator(tokenType())) //se è uno dei tipi previsti, posso chiamare l'assignment; altrimenti chiamo logicalOr
            {
                Type operator = token.getType();
                next(); //mangio token dell'operatore
                return new SetVarExpr(id_value, operator, assignment(scope)); //todo: questa setvarexpr deve diventare una binary: trasforma x+=5 in x = x + 5
            }
            else
            {
                previous(); //entro in modalità previous: il tokenizer al prossimo next restituisce il precedente token
                return logicalOr(scope);
            }
        }
        else
            return logicalOr(scope);
    }

    private boolean isAssignmentOperator(Type tokenType)
    {
        switch(tokenType)
        {
            case ASSIGN:
            case ADDASSIGN:
            case MINUSASSIGN:
            case MULTASSIGN:
            case DIVASSIGN:
            case PERCENTASSIGN:
                return true;
        }

        return false;
    }

    private void checkInScope(Scope scope, String id_value) throws ParserException
    {
        if(!scope.checkInScope(id_value))
            throw new ParserException("Error");
    }


    private Expr logicalOr(Scope scope) throws IOException, ParserException
    {
        Expr expr = logicalAnd(scope);

        if(tokenType() == Type.OR) //se token è or, ho ancora un logicalor
        {
            next();
            expr = new IfExpr(expr, Type.OR, logicalOr(scope));
        }

        return expr;
    }

    private Expr logicalAnd(Scope scope) throws IOException, ParserException {
        Expr expr = equality(scope);

        if(tokenType() == Type.AND)
        {
            next();
            expr = new IfExpr(expr, Type.AND, logicalAnd(scope));
        }

        return expr;
    }

    private Expr equality(Scope scope) throws IOException, ParserException {
        Expr expr = comparision(scope);

        if(token.getType() == Type.EQUAL || token.getType() == Type.NOTEQUAL)
        {
            Type operation = token.getType();
            next();
            expr = new BinaryExpr(expr, operation, comparision(scope));
        }

        return expr;
    }

    private Expr comparision(Scope scope) throws IOException, ParserException {
        Expr expr = add(scope);

        if(token.getType() == Type.LESS || token.getType() == Type.LESSEQUAL || token.getType() == Type.GREATER || token.getType() == Type.GREATEREQUAL)
        {
            Type operation = token.getType();
            next();
            expr = new BinaryExpr(expr, operation, add(scope));
        }

        return expr;
    }

    private Expr add(Scope scope) throws IOException, ParserException {
        Expr expr = mult(scope);

        while(token.getType() == Type.PLUS || token.getType() == Type.MINUS)
        {
            Type operation = token.getType();
            next();
            expr = new BinaryExpr(expr, operation, mult(scope));
        }

        return expr;
    }

    private Expr mult(Scope scope) throws IOException, ParserException {
        Expr expr = unary(scope);

        while(token.getType() == Type.STAR || token.getType() == Type.SLASH || token.getType() == Type.PERCENTAGE)
        {
            Type operation = token.getType();
            next();
            expr = new BinaryExpr(expr, operation, unary(scope));
        }

        return expr;
    }

    private Expr unary(Scope scope) throws IOException, ParserException {
        if(token.getType() == Type.PLUS || token.getType() == Type.MINUS || token.getType() == Type.NOT)
        {
            Type operation = token.getType();
            next();
            return new expression.UnaryExpr(operation, unary(scope));
        }
        else
        {
            return postfix(scope);
        }
    }

    private Expr postfix(Scope scope) throws IOException, ParserException {
        Expr expr = primary(scope);

        while(token.getType() == Type.TONDAOPEN)
        {
            expr = new InvokeExpr(expr, args(scope));
        }

        return expr;
    }

    private ExprList args(Scope scope) throws IOException, ParserException
    {
        ExprList list = new ExprList();
        checkAndNext(Type.TONDAOPEN, token.getType());

        if(token.getType() == Type.TONDACLOSE)
        {
            next();
            return list;
        }

        list.add(sequence(scope));

        while(token.getType() == Type.COMMA)
        {
            next();
            list.add(sequence(scope));
        }
        checkAndNext(Type.TONDACLOSE, token.getType());

        return list;
    }

    private Expr primary(Scope scope) throws ParserException, IOException {
        switch(token.getType())
        {
            case NUM:
                return getNumber(scope);
            case TRUE:
            case FALSE:
                return getBoolean(scope);
            case NIL:
                return getNil(scope);
            case STRING:
                return getString(scope);
            case ID:
                return getId(scope);
            case IF:
            case IFNOT:
                return getCond(scope);
            case WHILE:
            case WHILENOT:
                return getLoop(scope);
            case PRINT:
            case PRINTLN:
                return getPrint(scope);
            case GRAFFAOPEN:
                return function(scope);
            case TONDAOPEN:
                return getSubSequence(scope);
            default:
                throw new ParserException("Error while parsing");
        }
    }

    private Val getNumber(Scope scope) throws ParserException, IOException {
        BigDecimal value = token.getDecimalValue();
        checkAndNext(Type.NUM, token.getType());
        return new NumVal(value);
    }

    private Expr getBoolean(Scope scope) throws ParserException, IOException {

        if(token.getType() == Type.TRUE || token.getType() == Type.FALSE)
        {
            Type value = token.getType();
            next();
            return new BoolVal(value);
        }
        throw new ParserException("Parsing error");
    }

    private Expr getNil(Scope scope) throws ParserException, IOException {
        checkAndNext(Type.NIL, token.getType());
        return nil;
    }

    private Expr getString(Scope scope) throws ParserException, IOException {
        String value = token.getStringValue();
        checkAndNext(Type.STRING, token.getType());
        return new StringVal(value);
    }

    private Expr getId(Scope scope) throws ParserException, IOException {
        String id = token.getStringValue();
        checkAndNext(Type.ID, token.getType());
        checkInScope(scope, id);
        return new GetVarExpr(id);
    }

    private Expr getCond(Scope scope) throws IOException, ParserException {
        if(token.getType() == Type.IF || token.getType() == Type.IFNOT)
        {
            Type ifType = token.getType();
            next();
            Expr cond = sequence(scope);
            checkAndNext(Type.THEN, token.getType());
            Expr then = sequence(scope);

            Expr _else = null;

            if(Type.ELSE == token.getType())
            {
                next();
                _else = sequence(scope);
            }

            checkAndNext(Type.FI, token.getType());
            return new IfExpr(cond, then, _else, ifType);
        }

        throw new ParserException("Parsing error");
    }

    private Expr getLoop(Scope scope) throws IOException, ParserException {
        if(token.getType() == Type.WHILENOT || token.getType() == Type.WHILE)
        {
            Type loopType = token.getType();
            next();
            Expr cond = sequence(scope);

            Expr _do = null;

            if(token.getType() == Type.DO)
            {
                next();
                _do = sequence(scope);
            }

            checkAndNext(Type.OD, token.getType());
            return new WhileExpr(cond, _do, loopType);
        }

        throw new ParserException("Parsing error");
    }

    private Expr getPrint(Scope scope) throws IOException, ParserException {
        if(token.getType() == Type.PRINT || token.getType() == Type.PRINTLN)
        {
            Type printType = token.getType();
            next();
            return new PrintExpr(args(scope), printType);
        }

        throw new ParserException("Parsing error");
    }

    private Expr getSubSequence(Scope scope) throws IOException, ParserException {
        Expr expr = null;
        checkAndNext(Type.TONDAOPEN, token.getType());
        expr = sequence(scope);
        checkAndNext(Type.TONDACLOSE, token.getType());

        return expr;
    }
}
