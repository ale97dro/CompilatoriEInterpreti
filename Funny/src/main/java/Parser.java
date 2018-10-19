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

    public void next() throws IOException {
        token = tokenizer.next();
    }

    public void execute()
    {
        //TODO: parser
    }
}
