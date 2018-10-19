import java.util.HashMap;
import java.util.Map;

public class Token
{
    private Type type;
    private int int_value; //TODO: provvisorio, magari va cambiato in BigDecimal
    private String string_value;

    private static Map<String, Token> key_word;

    //crea elenco di parole chiave
    static
    {
        key_word = new HashMap<>();

        for(Type t : Type.values())
        {
            if(t.equals(Type.END_KEY_WORD))
                break;
            if(!t.equals(Type.START_KEY_WORD))
                key_word.put(t.toString().toLowerCase(), new Token(t));
        }
    }

    @Deprecated
    public static void stampa()
    {
        for(String t : key_word.keySet())
            System.out.println(key_word.get(t));
    }

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
    @Deprecated
    public String toString()
    {
        return "Type: "+type;
    }
}
