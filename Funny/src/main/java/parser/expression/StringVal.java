package parser.expression;

import tokenizer.Type;

public class StringVal extends Val {
    public StringVal(Type tokenType) {
        super(tokenType);
    }

    @Override
    Val eval(Env env) {
        return null;
    }
}
