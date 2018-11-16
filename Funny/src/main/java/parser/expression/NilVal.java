package parser.expression;

import tokenizer.Type;

public class NilVal extends Val {
    public NilVal(Type tokenType) {
        super(tokenType);
    }

    @Override
    Val eval(Env env) {
        return null;
    }
}
