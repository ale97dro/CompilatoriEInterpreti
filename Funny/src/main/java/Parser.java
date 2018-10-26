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

    private void previous()
    {
        //Todo implementare
    }

    public void execute()
    {
        //TODO: parser
    }
}
