import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer
{
    private List<Token> tokens;
    private int index;

    public Tokenizer(Reader reader)
    {
        tokens= new ArrayList<>();
        index=-1;
        /*

            .add(new Token(7, numero)
            .add(new Token(|, terminatore)



         */
    }


    public Token next()
    {
        index++;
        return tokens.get(index);
    }
}
