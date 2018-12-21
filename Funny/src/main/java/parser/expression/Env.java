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


    //todo implementa metodi per recuperare i valori

    public Val getVal(String id) throws EvalException
    {
        Val value = frame.getValue(id);

        if(value == null)
            return enclosing.getVal(id);

        return value;
    }

    public void setVal(String id, Val value) throws EvalException
    {
        frame.addValue(id, value);
    }
}
