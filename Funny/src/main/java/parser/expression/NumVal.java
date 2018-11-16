package parser.expression;

import tokenizer.Type;

public class NumVal extends Val {
    public NumVal(Type tokenType) {
        super(tokenType);
    }

    @Override
    Val eval(Env env) {
        return null;
    }
}
