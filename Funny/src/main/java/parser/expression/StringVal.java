package parser.expression;

import parser.EvalException;

public class StringVal extends Val {
    private String value;

    public StringVal(String value)
    {
        this.value = value;
    }

    public String getValue() { return value;}

    @Override
    public StringVal checkString() throws EvalException {
        return this;
    }

    @Override
    public StringVal plus(Val arg) throws EvalException {
        try
        {
            return new StringVal(value + arg.checkString().value);
        }
        catch (EvalException e)
        {
            return new StringVal(value + arg.checkNum().getNumber().toString());
        }
    }

    @Override
    public String toString()
    {
        return value;
    }
}
