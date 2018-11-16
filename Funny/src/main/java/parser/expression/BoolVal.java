package parser.expression;

import tokenizer.Type;

public class BoolVal extends Val {
    public BoolVal(Type tokenType) {
        super(tokenType);
    }

    @Override
    Val eval(Env env) {
        return null;
    }
}
