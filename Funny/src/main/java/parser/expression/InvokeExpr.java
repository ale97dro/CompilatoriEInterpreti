package parser.expression;

import parser.ExprList;


public class InvokeExpr extends Expr {
    public InvokeExpr(Expr expr, ExprList args) {
        super();
    }

    @Override
    public Val eval(Env env) {
        return null;
    }
}
