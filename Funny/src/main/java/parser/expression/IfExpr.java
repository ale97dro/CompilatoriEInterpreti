package parser.expression;

import tokenizer.Type;

public class IfExpr extends Expr {
    public IfExpr(Expr expr, Type or, Expr logicalOr) {
        super();
    }

    @Override
    Val eval(Env env) {
        return null;
    }
}
