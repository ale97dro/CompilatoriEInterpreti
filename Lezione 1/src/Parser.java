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

    public Block expr() throws IllegalAccessException
    {
        Block b = vertExpr();

        next();

        check(token.getType(), Type.EOS);

        return b;
    }

    private Block vertExpr()
    {
        Block top = horizExpr();

        while(isVertOP())
        {
            next();
            top = new VertBlock(top, horizExpr());
        }

        return top;
    }

    private boolean isVertOP()
    {
        if(token.getType()==Type.VERT)
            return true;

        return false;
    }

    private Block horizExpr()
    {
        Block left = primaryExpr();

        while(isHorizOP())
        {
            next();
            left=new HorizBlock(left, primaryExpr());
        }

        return left;
    }

    private boolean isHorizOP()
    {
        if(token.getType()== Type.HORIZ)
            return true;

        return false;
    }

    private Block primaryExpr()
    {
        return null;
    }

    private Block rectExpr()
    {
        return null;
    }

    public void check(Type token_type, Type type) throws IllegalAccessException {
        if(token_type!=type)
            throw new IllegalAccessException("Wrong type");
    }
}
