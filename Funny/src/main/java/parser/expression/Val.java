package parser.expression;

import tokenizer.Type;

public abstract class Val extends Expr{
    private Type tokenType;

    public Type getTokenType()
    {
        return tokenType;
    }

    public Val(Type tokenType)
    {
        this.tokenType = tokenType;
    }
}
