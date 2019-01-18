package parser.expression;

import parser.EvalException;

public class SetVarExpr extends Expr {
    private String id;
    private Expr expression;

    public SetVarExpr(String id, Expr expression)
    {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public Val eval(Env env) throws EvalException {
        Val val = expression.eval(env);
        env.setVal(id, val);
        return val;
        //return NilVal.instance();
    }
}