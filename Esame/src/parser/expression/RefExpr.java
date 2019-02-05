package parser.expression;

public class RefExpr extends Expr {
    private String label;
    private Expr ref;

    public RefExpr(String value) {
      this.label = value;
      this.ref = null;
    }

    public void setReference(LabelsContainer container) {
        ref = container.get(label);
    }
}
