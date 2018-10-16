import java.io.Reader;

public class Parser
{
    private Tokenizer tokenizer;
    private Token token;

    public Parser(Reader reader)
    {
        tokenizer = new Tokenizer(reader);
    }

    public void next()
    {
        token = tokenizer.next();
    }

    public void execute()
    {
        //TODO: parser
    }
}
