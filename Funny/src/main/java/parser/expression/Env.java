package parser.expression;

import parser.EvalException;

public class Env
{
    private Env enclosing;
    private Frame frame;

    public Env(Frame frame, Env enclosing)
    {
        this.frame = frame;
        this.enclosing = enclosing;
    }



    public Val getVal(String id) throws EvalException
    {
        Val value = frame.getValue(id);

        if(value == null)
            return enclosing.getVal(id);

        return value;
    }

    public void setVal(String id, Val value)
    {
        try {
            frame.addValue(id, value);
        }
        catch (EvalException ex)
        {
            enclosing.setVal(id, value);
        }
    }
}
