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
        tokenizer=new Tokenizer(); //mock
        token=tokenizer.next();
    }

    /**
     * Ask for next token to Tokenizer
     */
    private void next()
    {
        token=tokenizer.next();
    }

    /**
     * Entry point for Parser
     * @return c
     * @throws IllegalAccessException c
     */
    public Block expr() throws IllegalAccessException
    {
        Block b = vertExpr();

        check(Type.EOS);
        //next();

        return b;
    }

    private Block vertExpr() throws IllegalAccessException
    {
        Block top = horizExpr();

        while(isVert())
        {
            next();
            top = new VertBlock(top, horizExpr());
        }

        return top;
    }

    /**
     * Check if the token it's vertical
     * @return ccc
     */
    private boolean isVert()
    {
        return (token.getType()==Type.VERT);
    }

    private Block horizExpr() throws IllegalAccessException
    {
        Block left = primaryExpr();

        while(isHoriz())
        {
            next();
            left=new HorizBlock(left, primaryExpr());
        }

        return left;
    }

    /**
     * Check if the token it's horizontal
     * @return a
     */
    private boolean isHoriz()
    {

        return (token.getType()== Type.HORIZ);
    }

    private Block primaryExpr() throws IllegalAccessException
    {
        if(token.getType()==Type.OPEN)
            return parenExpr();
        else
            if(token.getType()==Type.NUM)
                return rectExpr();
            else
                throw new IllegalArgumentException("error");
    }

    private Block parenExpr() throws IllegalAccessException
    {
        check(Type.OPEN);
        next();
        Block b = vertExpr();
        check(Type.CLOSE);
        next();

        return b;
    }

    private Block rectExpr() throws IllegalAccessException
    {
        check(Type.NUM);
        int width=Integer.parseInt(token.getValue());
        next();

        check(Type.STAR);
        next();

        check(Type.NUM);
        int height=Integer.parseInt(token.getValue());
        next();

        return new Rect(width, height);
    }

    /**
     * Check if token_type is the one expected, else throw exception
     * @param expected a
     * @throws IllegalAccessException a
     */

    private void check(Type expected) throws IllegalAccessException
    {
        if(token.getType()!=expected)
            throw new IllegalAccessException("Wrong token type");
    }
}
