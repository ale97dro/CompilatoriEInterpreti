package parser.value;

public class StringVal extends Val {
    private String value;
    public StringVal(String value) {
        this.value=value;
    }

    public String getValue() { return value; }
}
