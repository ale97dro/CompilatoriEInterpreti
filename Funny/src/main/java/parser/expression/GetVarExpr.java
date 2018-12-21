package parser.expression;

import parser.EvalException;

public class GetVarExpr extends Expr {

    private String id;

    public GetVarExpr(String stringValue) {
        this.id = stringValue;
    }

    @Override
    public Val eval(Env env) throws EvalException {
        return env.getVal(id);
    }
}
