package tokenizer;

import java.io.IOException;
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

    public Tokenizer(Reader reader) throws IOException {
        tokens = new ArrayList<>();


        //TODO saltare gli spazi bianchi
        //TODO il next legge il prossimo elemento e lo passa, niente array
        //TODO spostare logica attuale in TokenizerMock

        int c;
        char character;

        while((c=reader.read())!=-1)
        {
            character = (char)c;
            Token t = null;

            //rimane da gestire EOS e NUM
            switch(character)
            {
                case '*':
                    t = createToken(Type.STAR);
                    break;
                case '|':
                    t = createToken(Type.VERT);
                    break;
                case '-':
                    t = createToken(Type.HORIZ);
                    break;
                case '(':
                    t = createToken(Type.OPEN);
                    break;
                case ')':
                    t = createToken(Type.CLOSE);
                    break;
                default:
                    t = new Token(character - '0', Type.NUM);
                    break;
            }
            tokens.add(t);
        }

        reader.close();

        tokens.add(new Token(0, Type.EOS));
    }

    private Token createToken(Type type)
    {
        return new Token(0, type);
    }


    /**
     * Create mock token
     */
    public Tokenizer()
    {
        tokens= new ArrayList<>();
        index=0;

        tokens.add(new Token(7, Type.NUM));
        tokens.add(new Token(0, Type.STAR));
        tokens.add(new Token(8, Type.NUM));
        tokens.add(new Token(0, Type.VERT));
        tokens.add(new Token(0, Type.OPEN));
        tokens.add(new Token(0, Type.OPEN));
        tokens.add(new Token(2, Type.NUM));
        tokens.add(new Token(0, Type.STAR));
        tokens.add(new Token(3, Type.NUM));
        tokens.add(new Token(0, Type.HORIZ));
        tokens.add(new Token(7, Type.NUM));
        tokens.add(new Token(0, Type.STAR));
        tokens.add(new Token(5, Type.NUM));
        tokens.add(new Token(0, Type.CLOSE));
        tokens.add(new Token(0, Type.VERT));
        tokens.add(new Token(5, Type.NUM));
        tokens.add(new Token(0, Type.STAR));
        tokens.add(new Token(4, Type.NUM));
        tokens.add(new Token(0, Type.CLOSE));
        tokens.add(new Token(0, Type.EOS));

    }

    public Token next()
    {
        return tokens.get(index++);
    }
}
