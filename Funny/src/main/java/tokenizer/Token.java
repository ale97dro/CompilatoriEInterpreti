package tokenizer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Token
{
    private Type type;
    private BigDecimal bd_value;
    private String string_value;

    private static Map<String, Token> key_word;
    private static Map<String, Token> simple_token;

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

        simple_token = new HashMap<>();

        for(Type t : Type.values())
        {
            if(t.equals(Type.END_SIMPLE_TOKEN))
                break;
        }

        /*for(int i = Type.START_SIMPLE_TOKEN.ordinal(); i<Type.END_SIMPLE_TOKEN.ordinal(); i++)
            simple_token.put(Type.

        int i = Type.START_SIMPLE_TOKEN.ordinal()*/

    }

    public String getStringValue()
    {
        return string_value;
    }

    public BigDecimal getDecimalValue()
    {
        return bd_value;
    }

    @Deprecated
    public static void stampa()
    {
        for(String t : key_word.keySet())
            System.out.println(key_word.get(t));
    }

    public static boolean isKeyWord(String s)
    {
        return key_word.get(s) != null;
    }

    public static Token getKeyWordToken(String s)
    {
        return key_word.get(s);
    }

    public Token(Type type, String value)
    {
        this.type = type;
        this.string_value = value;
    }

    public Token(Type type, BigDecimal value)
    {
        this.type = type;
        this.bd_value = value;
    }

    public Token(Type type)
    {
        this.type = type;
    }

    public Type getType()
    {
        return type;
    }

    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();
        str.append("tokenizer.Type: "+type);

        if(string_value!=null)
            str.append(" Value: "+string_value);

        if(bd_value!=null)
            str.append(" Value: "+bd_value);

        return str.toString();
    }
}
