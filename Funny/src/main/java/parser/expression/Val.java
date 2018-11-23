package parser.expression;

import parser.EvalException;
import tokenizer.Type;

public abstract class Val extends Expr{
    public Val() { }



    public NumVal checkNum() throws EvalException {
        throw new EvalException("Not num");
    }

    public BoolVal checkBool() throws EvalException {
        throw new EvalException("Not bool");
    }

    public ClosureVal checkClosure() throws EvalException {
        throw new EvalException("Not closure");
    }

    public NilVal checkNil() throws EvalException {
        throw new EvalException("Not nil");
    }

    public StringVal checkString() throws EvalException {
        throw new EvalException("Not string");
    }


    @Override
    public Val eval(Env env) {
        return this;
    }
}
