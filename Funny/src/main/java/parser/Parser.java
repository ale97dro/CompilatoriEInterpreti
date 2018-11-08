package parser;

import tokenizer.Tokenizer;
import tokenizer.Token;
import tokenizer.Type;

import java.io.IOException;
import java.io.Reader;

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

    public void execute() throws ParserException, IOException //==program
    {
        Scope scope = new Scope();
        function(scope);
        check(Type.EOS, token.getType());
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
    private void function(Scope scope) throws ParserException, IOException
    {
        checkAndNext(Type.BRACEOPEN, tokenType());
        optParams(scope);
        optLocals(scope);
        optSequence(scope);
        checkAndNext(Type.BRACECLOSE, tokenType());

        //FunExpr

    }

    private void optParams(Scope scope) throws IOException, ParserException
    {
        checkAndNext(Type.BRACKETOPEN, tokenType());
        optIds(scope);
        checkAndNext(Type.BRACKETCLOSE, tokenType());

        //optExpr
    }

    private void optLocals(Scope scope)
    {
        optIds(scope);
        //optLocalsExpr
    }

    private void optSequence(Scope scope) throws IOException, ParserException
    {
        checkAndNext(Type.ARROW, tokenType());
        sequence(scope);

        //optSequenceExpr
    }

    private void optIds(Scope scope)
    {
        while(isId(tokenType()))
            id(scope);

        //optIdExpr
    }

    private void id(Scope scope)
    {
        //TODO immagino di dover ritornare il nodo con il token id
    }

    private void sequence(Scope scope) throws IOException, ParserException
    {
        optAssignment(scope);
        while(isOptAssignment(tokenType()))
        {
            next();
            optAssignment(scope);
        }

        //sequenceExpr
    }



    private void optAssignment(Scope scope) throws ParserException
    {
        assignment(scope);
    }

    private void assignment(Scope scope) throws ParserException
    {
        check(Type.ID, tokenType());
        //TODO continuare implementazione
    }


    private void logicalOr(Scope scope) throws IOException, ParserException {
        logicalAnd(scope);
        checkAndNext(Type.OR, tokenType());
        logicalOr(scope);
    }

    private void logicalAnd(Scope scope)
    {
    }
}
