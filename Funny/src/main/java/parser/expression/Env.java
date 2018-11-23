package parser.expression;

public class Env
{
    private Env enclosing;
    private Frame frame;

    public Env(Frame frame, Env enclosing)
    {
        this.frame = frame;
        this.enclosing = enclosing;
    }

    //todo implementa metodi per recuperare i valori
}
