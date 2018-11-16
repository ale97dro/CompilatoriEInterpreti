package parser;

import parser.expression.*;
import tokenizer.Tokenizer;
import tokenizer.Token;
import tokenizer.Type;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Parser
{
    private Tokenizer tokenizer;
    private Token token;

    public Parser(Reader reader)
    {
        tokenizer = new Tokenizer(reader);
    }

    private void next() throws IOException
    {
        token = tokenizer.next();
    }

    private Type tokenType()
    {
        return token.getType();
    }

    private void previous()
    {
        tokenizer.previous();
    }

    public Expr execute() throws ParserException, IOException //==program
    {
        Expr expr = function(null);
        check(Type.EOS, token.getType());

        return expr;
    }

    private void check(Type expected, Type actual) throws ParserException {
        if(expected != actual)
            throw new ParserException("Parsing error");
    }

    private void checkAndNext(Type expected, Type actual) throws ParserException, IOException
    {
        if(expected != actual)
            throw new ParserException("Parsing error");
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
    private Expr function(Scope scope) throws ParserException, IOException
    {
        checkAndNext(Type.BRACEOPEN, tokenType());

        List<String> params = optParams(scope);
        List<String> locals = optLocals(scope);
        List<String> params_and_locals = new ArrayList<>(params);
        params_and_locals.addAll(locals);

        scope = new Scope(params_and_locals);

        Expr expr = optSequence(scope);

        checkAndNext(Type.BRACECLOSE, tokenType());

        return expr;
    }

    private List<String> optParams(Scope scope) throws IOException, ParserException
    {
        checkAndNext(Type.BRACKETOPEN, tokenType());
        List<String> params = optIds(scope);
        checkAndNext(Type.BRACKETCLOSE, tokenType());

        return params;
    }

    private List<String> optLocals(Scope scope) throws ParserException, IOException
    {
        return optIds(scope);
    }

    private Expr optSequence(Scope scope) throws IOException, ParserException
    {
        if(tokenType() == Type.ARROW)
            return sequence(scope);

        return null;
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
        //TODO immagino di dover ritornare il nodo con il token id

        //Controllo se esiste nello scope: se esiste, exception
        //se non esiste, ritorno il nome del token

        checkAndNext(Type.ID, tokenType());
        return token.getStringValue();
    }

    private Expr sequence(Scope scope) throws IOException, ParserException
    {
        Expr expr = optAssignment(scope);
        while(isOptAssignment(tokenType()))
        {
            next();
            expr = new SeqExpr(expr, optAssignment(scope)); //cosa devo mettere dentro sequence? lista di assignment
        }

        return null;
    }



    private Expr optAssignment(Scope scope) throws ParserException, IOException {
        //se c'è token che può iniziare assign, ritorno questo altrimenti null (null di Java)
        if(isAssignment())
            return assignment(scope);
        else
            return null;
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
            case BRACKETOPEN:
            case BRACEOPEN:
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
        //TODO: controllo se il tipo è id o logicalOr; se uno di questi chiamo sotto parser altrimenti ritorno null
        //TODO: se non ho id, logicalOr
        //se ho id, devo controllare anche il carattere dopo: se è uno dei 5, allora poi dopo ho assignment altrimenti logicalOr
        if(isId(Type.ID))
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

    private Expr logicalAnd(Scope scope) throws IOException {
        Expr expr = equality(scope);

        if(tokenType() == Type.AND)
        {
            next();
            expr = new IfExpr(expr, Type.AND, logicalAnd(scope));
        }

        return expr;
    }

    private Expr equality(Scope scope) throws IOException {
        Expr expr = comparision(scope);

        if(token.getType() == Type.EQUAL || token.getType() == Type.NOTEQUAL)
        {
            Type operation = token.getType();
            next();
            expr = new BinaryExpr(expr, operation, comparision(scope));
        }

        return expr;
    }

    private Expr comparision(Scope scope) throws IOException
    {
        Expr expr = add(scope);

        if(token.getType() == Type.LESS || token.getType() == Type.LESSEQUAL || token.getType() == Type.GREATER || token.getType() == Type.GREATEREQUAL)
        {
            Type operation = token.getType();
            next();
            expr = new BinaryExpr(expr, operation, add(scope));
        }

        return expr;
    }

    private Expr add(Scope scope) throws IOException {
        Expr expr = mult(scope);

        while(token.getType() == Type.PLUS || token.getType() == Type.MINUS)
        {
            Type operation = token.getType();
            next();
            expr = new BinaryExpr(expr, operation, mult(scope));
        }

        return expr;
    }

    private Expr mult(Scope scope) throws IOException
    {
        Expr expr = unary(scope);

        while(token.getType() == Type.STAR || token.getType() == Type.SLASH || token.getType() == Type.PERCENTAGE)
        {
            Type operation = token.getType();
            next();
            expr = new BinaryExpr(expr, operation, unary(scope));
        }

        return expr;
    }

    private Expr unary(Scope scope) throws IOException {
        if(token.getType() == Type.PLUS || token.getType() == Type.MINUS || token.getType() == Type.NOT)
        {
            Type operation = token.getType();
            next();
            return new UnaryExpr(operation, unary(scope)); //todo: cosa ritorna?
        }
        else
        {
            return postfix(scope); //todo ritorna qualche oggetto istanzito qui?
        }
    }

    private Expr postfix(Scope scope)
    {
        Expr expr = primary(scope);

        while(token.getType() == Type.BRACKETOPEN)
        {

        }

        return expr;

    }

    private Expr primary(Scope scope) {
    }

    private Expr args(Scope scope) throws IOException, ParserException
    {
        checkAndNext(Type.BRACKETOPEN, token.getType());
        Expr expr = sequence(scope);

        while(token.getType() == Type.COMMA)
        {

        }
    }


}
