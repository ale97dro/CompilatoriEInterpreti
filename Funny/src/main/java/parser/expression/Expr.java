package parser.expression;

import parser.EvalException;

public abstract class Expr {

    public abstract Val eval(Env env) throws EvalException;
}
