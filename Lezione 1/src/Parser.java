import java.io.Reader;

public class Parser
{
    //Parser memorizza un tokenizer per ottenere i token da consumare ma non salva nessun reader al suo interno (ha bisogno di token e non di caratteri)

    //il parser mantine anche il token appena letto attraverso il metodo next() del tokenizer

    //Block expr() => parser principale (punto di entrata)

    //check controlla il token (terminale) aspettato con quello che arriva
/*
    private Block vertExpr()
    {
        Block top = horizExpr();
        while(isVertOp())
        {
            next();
            top = new VertNode(top, horiExpr())
        }

        return node;
    }

    */

    private Tokenizer tokenizer;
    private Token token;

    public Parser(Reader reader)
    {
        tokenizer=new Tokenizer(reader);
    }

    private void next()
    {
        token=tokenizer.next();
    }

    public Block expr()
    {
        vertExpr();

        next();

        check(token.type(), "EOS");
    }

    private Block vertExpr()
    {
        Block top = horizExpr();

        while()
        {
            next();
            top = new VertBlock(top, horizExpr());
        }

    }

    private Block horizExpr()
    {
        Block left = primaryExpr();

        while()
        {
            next();
            left=new HorizBlock(left, primaryExpr());
        }
    }
}
