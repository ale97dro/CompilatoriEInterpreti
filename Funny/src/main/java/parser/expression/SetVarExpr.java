package parser.expression;

import parser.EvalException;
import tokenizer.Type;

public class SetVarExpr extends Expr {
    private String id;
    private Type operation;
    private Expr expression;

    public SetVarExpr(String id, Type operation, Expr expression)
    {
        this.id = id;
        this.operation = operation;
        this.expression = expression;
    }

    @Override
    public Val eval(Env env) throws EvalException {
        env.setVal(id, expression.eval(env));
        return NilVal.instance();
    }
}