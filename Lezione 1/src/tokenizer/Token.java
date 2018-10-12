package tokenizer;

public class Token
{
    private Type type;
    private int value;

    public Token(int value, Type type)
    {
        this.value=value;
        this.type=type;
    }

    public int getValue()
    {
        return value;
    }

    public Type getType()
    {
        return type;
    }
}
