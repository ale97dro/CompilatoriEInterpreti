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


    public Val plus(Val arg) throws EvalException {
        throw new EvalException("Plus");
    }

    public Val minus(Val arg) throws EvalException {
        throw new EvalException("Plus");
    }

    public Val times(Val arg) throws EvalException {
        throw new EvalException("Plus");
    }

    public Val division(Val arg) throws EvalException {
        throw new EvalException("Plus");
    }

    public Val module(Val arg) throws EvalException {
        throw new EvalException("Plus");
    }


    @Override
    public Val eval(Env env) {
        return this;
    }
}
