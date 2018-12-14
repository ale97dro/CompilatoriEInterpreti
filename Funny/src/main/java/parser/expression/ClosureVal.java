package parser.expression;

import parser.EvalException;

import java.util.List;

public class ClosureVal extends Val {

    private Env env;
    private FunExpr function;


    public ClosureVal(Env env, FunExpr function)
    {
        this.env = env;
        this.function = function;
    }

    public ClosureVal() {
        super();
    }

    @Override
    public ClosureVal checkClosure() {
        return this;
    }

    public Val apply(List<Val> args) throws EvalException
    {
        return function.code().eval(new Env(new Frame(function.params(), function.locals(), args), env));
    }
}
