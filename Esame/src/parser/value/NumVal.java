package parser.value;

import java.math.BigDecimal;

public class NumVal extends Val {
    private BigDecimal value;

    public NumVal(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() { return value; }
}
