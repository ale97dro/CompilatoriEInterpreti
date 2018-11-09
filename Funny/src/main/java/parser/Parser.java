package parser;

import parser.expression.AssignmentExpr;
import parser.expression.Expr;
import parser.expression.FunExpr;
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
        //Todo implementare
    }

    public Expr execute() throws ParserException, IOException //==program
    {
        Expr expr = function(null);
        check(Type.EOS, token.getType());

        return expr;
    }

    private boolean check(Type expected, Type actual) throws ParserException {
        if(expected != actual)
            throw new ParserException("Parsing error");
        return true;
    }

    private boolean checkAndNext(Type expected, Type actual) throws ParserException, IOException
    {
        if(expected != actual)
            throw new ParserException("Parsing error");
        next();
        return true;
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
        optAssignment(scope);
        while(isOptAssignment(tokenType()))
        {
            next();
            optAssignment(scope);
        }

        //sequenceExpr

        return null;
    }



    private Expr optAssignment(Scope scope) throws ParserException
    {
        //todo: controllare il prossimo token per verificare se c'è o no
        if(isId(Type.ID) || (tokenType() == Type.ASSIGN)) //todo: la seconda condizione è sbagliata
            return assignment(scope);

        return null;
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
            if(isAssignmentOperator(tokenType()))
            {
                Type operator = token.getType();
                next(); //mangio token dell'operatore (todo magari non va qui)
                return new AssignmentExpr(id_value, operator, assignment(scope)); //todo: questa assignmentexpr deve diventare una binary: trasforma x+=5 in x = x + 5
            }
            else
                return logicalOr(scope);
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


    private void logicalOr(Scope scope) throws IOException, ParserException
    {
        Expr expr = logicalAnd(scope);

        if(tokenType() == Type.OR)
            expr = logicalOr(scope);


        //checkAndNext(Type.OR, tokenType());
    }

    private void logicalAnd(Scope scope)
    {
    }
}
