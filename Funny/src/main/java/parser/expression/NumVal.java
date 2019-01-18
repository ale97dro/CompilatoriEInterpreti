package parser.expression;

import parser.EvalException;
import tokenizer.Type;

import java.math.BigDecimal;
import java.math.MathContext;

public class NumVal extends Val {
    private BigDecimal number;

    public NumVal(BigDecimal number) {
        this.number = number;
    }

    public BigDecimal getNumber()
    {
        return number;
    }

    @Override
    public NumVal checkNum() {
        return this;
    }

    @Override
    public Val plus(Val arg) throws EvalException
    {
        return new NumVal(number.add(arg.checkNum().number));
    }

    @Override
    public Val minus(Val arg) throws EvalException {
        return new NumVal(number.subtract(arg.checkNum().number));
    }

    @Override
    public Val times(Val arg) throws EvalException {
        return new NumVal(number.multiply(arg.checkNum().number));
    }

    @Override
    public Val division(Val arg) throws EvalException {
        try {
            return new NumVal(number.divide(arg.checkNum().number));
        }
        catch (ArithmeticException ex)
        {
            return new NumVal(number.divide(arg.checkNum().number, MathContext.DECIMAL32));
        }
    }

    @Override
    public Val module(Val arg) throws EvalException {
        return new NumVal(number.remainder(arg.checkNum().number));
    }

    @Override
    public Val greater(Val args) throws EvalException {
        if(this.number.compareTo(args.checkNum().number) > 0)
            return new BoolVal(Type.TRUE);
        return new BoolVal(Type.FALSE);
    }

    @Override
    public Val greaterEquals(Val args) throws EvalException {
        if(this.number.compareTo(args.checkNum().number) >= 0)
            return new BoolVal(Type.TRUE);
        return new BoolVal(Type.FALSE);
    }

    @Override
    public Val less(Val args) throws EvalException {
        if(this.number.compareTo(args.checkNum().number) < 0)
            return new BoolVal(Type.TRUE);
        return new BoolVal(Type.FALSE);
    }

    @Override
    public Val lessEquals(Val args) throws EvalException {
        if(this.number.compareTo(args.checkNum().number) <= 0)
            return new BoolVal(Type.TRUE);
        return new BoolVal(Type.FALSE);
    }

    @Override
    public Val equalsEquals(Val args) throws EvalException {
        if(this.number.compareTo(args.checkNum().number) == 0)
            return new BoolVal(Type.TRUE);
        return new BoolVal(Type.FALSE);
    }

    @Override
    public String toString()
    {
        return number.toString();
    }
}
