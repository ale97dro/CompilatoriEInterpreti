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
            expr = new SeqExpr(optAssignment(scope)); //cosa devo mettere dentro sequence? lista di assignment
        }

        //sequenceExpr

        return null;
    }



    private Expr optAssignment(Scope scope) throws ParserException, IOException {
        return assignment(scope);
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
                next(); //mangio token dell'operatore (todo magari non va qui)
                return new BinaryExpr(id_value, operator, assignment(scope)); //todo: questa assignmentexpr deve diventare una binary: trasforma x+=5 in x = x + 5
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
            next(); // //todo non so se giusto il next
            //TODO: richiamo logicalOr ma cosa devo ritornare?
        }

        return expr;
    }

    private Expr logicalAnd(Scope scope) throws IOException {
        Expr expr = equality(scope);

        if(tokenType() == Type.AND)
        {
            next(); //todo non so se giusto il next
            //TODO: richiamo logicaland ma cosa devo ritornare?
        }

        return expr;
    }

    private Expr equality(Scope scope)
    {
        Expr expr = comparision(scope);

        if(token.getType() == Type.EQUAL || token.getType() == Type.NOTEQUAL)
        {
            expr = new UnaryExpr(comparision(scope));
        }

        return expr;
    }

    private Expr comparision(Scope scope) {
        Expr expr = add(scope);

        if(token.getType() == Type.LESS || token.getType() == Type.LESSEQUAL || token.getType() == Type.GREATER)
            expr = new UnaryExpr(add(scope));

        return expr;
    }

    private Expr add(Scope scope)
    {
        Expr expr = mult(scope);

        if(token.getType())
    }

    private Expr mult(Scope scope)
    {

    }
}