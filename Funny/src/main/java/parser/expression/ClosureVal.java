package parser.expression;

import tokenizer.Type;

public class ClosureVal extends Val {
    public ClosureVal(Type tokenType) {
        super(tokenType);
    }

    @Override
    Val eval(Env env) {
        return null;
    }
}
