import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * At this moment, this is a mockup
 */
public class Tokenizer
{
    private List<Token> tokens;
    private int index;

    /**
     * Use to create new token
     * @param reader
     */
    public static void createTokenizer(Reader reader)
    {
        //Skip space
        //controlla tutti i caratteri con switch e memorizza solo il tipo
    }

    private Tokenizer(Reader reader)
    {
        tokens = new ArrayList<>();
    }

    /**
     * Create mock token
     */
    public Tokenizer()
    {
        tokens= new ArrayList<>();
        index=0;

        tokens.add(new Token("7", Type.NUM));
        tokens.add(new Token("*", Type.STAR));
        tokens.add(new Token("8", Type.NUM));
        tokens.add(new Token("|", Type.VERT));
        tokens.add(new Token("(", Type.OPEN));
        tokens.add(new Token("(", Type.OPEN));
        tokens.add(new Token("2", Type.NUM));
        tokens.add(new Token("*", Type.STAR));
        tokens.add(new Token("3", Type.NUM));
        tokens.add(new Token("-", Type.HORIZ));
        tokens.add(new Token("7", Type.NUM));
        tokens.add(new Token("*", Type.STAR));
        tokens.add(new Token("5", Type.NUM));
        tokens.add(new Token(")", Type.CLOSE));
        tokens.add(new Token("|", Type.VERT));
        tokens.add(new Token("5", Type.NUM));
        tokens.add(new Token("*", Type.STAR));
        tokens.add(new Token("4", Type.NUM));
        tokens.add(new Token(")", Type.CLOSE));
        tokens.add(new Token("EOS", Type.EOS));

    }

    public Token next()
    {
        return tokens.get(index++);
    }
}
