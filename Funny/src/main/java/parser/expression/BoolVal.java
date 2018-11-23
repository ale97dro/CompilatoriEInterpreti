package parser.expression;

import parser.EvalException;
import tokenizer.Type;

public class BoolVal extends Val {
    private Type boolType;

    public BoolVal(Type boolType) {
        this.boolType = boolType;
    }

    @Override
    public BoolVal checkBool() throws EvalException {
        return this;
    }
}
