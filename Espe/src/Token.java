public class Token
{
    private Type type;
    private String value;

    public Token(String value, Type type)
    {
        this.value = value;
        this.type = type;
    }

    public String toString()
    {
        return value+" "+type;
    }

    public String getValue() {return value;}
    public Type getType(){return type;};
}
