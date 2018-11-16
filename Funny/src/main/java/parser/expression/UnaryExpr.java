package parser.expression;

import tokenizer.Type;

public class UnaryExpr extends Expr {
    public UnaryExpr(Type operation, Expr unary) {
        super();
    }

    @Override
    Val eval(Env env) {
        return null;
    }
}
