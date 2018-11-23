package parser.expression;

import parser.EvalException;
import parser.ExprList;


public class InvokeExpr extends Expr {

    private Expr expr;
    private ExprList args;

    public InvokeExpr(Expr expr, ExprList args) {
        this.expr = expr;
        this.args = args;
    }

    @Override
    public Val eval(Env env) throws EvalException {
        return expr.eval(env).checkClosure().apply(args.eval(env));
    }
}
