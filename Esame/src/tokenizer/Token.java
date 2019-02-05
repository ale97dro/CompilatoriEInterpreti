package tokenizer;

import java.math.BigDecimal;

public class Token {
    private Type type;
    private String value;
    private BigDecimal numeric_value;

    public Token(Type type, String value)
    {
        this.type=type;
        this.value=value;
    }

    public Token(Type type)
    {
        this.type = type;
    }

    public Token(Type type, BigDecimal value)
    {
        this.type=type;
        this.numeric_value=value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BigDecimal getNumeric_value() {
        return numeric_value;
    }

    public void setNumeric_value(BigDecimal numeric_value) {
        this.numeric_value = numeric_value;
    }

    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();
        str.append("tokenizer.Type: "+type);

        if(value!=null)
            str.append(" Value: "+value);

        if(numeric_value!=null)
            str.append(" Value: "+numeric_value);

        return str.toString();
    }
}
