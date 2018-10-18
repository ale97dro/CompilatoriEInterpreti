public class Token
{
    private Type type;
    private int int_value; //TODO: provvisorio, magari va cambiato in BigDecimal
    private String string_value;

    public Token(Type type, String value)
    {
        this.type = type;
        this.string_value = value;
    }

    public Token(Type type, int value)
    {
        this.type = type;
        this.int_value = value;
    }

    public Token(Type type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "Type: "+type;
    }
}
