package parser.expression;

import parser.EvalException;
import tokenizer.Type;

import java.math.BigDecimal;

public class NumVal extends Val {
    private BigDecimal number;

    public NumVal(BigDecimal number) {
        this.number = number;
    }

    @Override
    public NumVal checkNum() {
        return this;
    }
}
