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
        //throw new EvalException("Plus");
        return new StringVal(this.toString()+arg.checkString().getValue());
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

    public Val greater(Val args) throws EvalException {
        throw new EvalException("Greater");
    }

    public Val greaterEquals(Val args) throws EvalException {
        throw new EvalException("GreaterEquals");
    }

    public Val less(Val args) throws EvalException {
        throw new EvalException("Less");
    }

    public Val lessEquals(Val args) throws EvalException {
        throw new EvalException("LessEquals");
    }

    public Val equalsEquals(Val args) throws EvalException {
        return this.equals(args) ? new BoolVal(Type.TRUE) : new BoolVal(Type.FALSE);
    }

    @Override
    public Val eval(Env env) {
        return this;
    }
}
