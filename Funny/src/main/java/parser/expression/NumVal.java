package parser.expression;

import parser.EvalException;
import tokenizer.Type;

import java.math.BigDecimal;

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
        return new NumVal(number.divide(arg.checkNum().number));
    }

    @Override
    public Val module(Val arg) throws EvalException {
        return new NumVal(number.remainder(arg.checkNum().number));
    }
}
