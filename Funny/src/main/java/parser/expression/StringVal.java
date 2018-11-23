package parser.expression;

import parser.EvalException;
import tokenizer.Type;

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
}
