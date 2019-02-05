package parser.expression;

public class AssocExpr extends Expr{
    private String id;
    private Expr value;


    public AssocExpr(String id, Expr value) {
        this.id=id;
        this.value=value;
    }

    public String getId() {
        return id;
    }

    public Expr getValue() {
        return value;
    }

    public void setReference(LabelsContainer container) {
        value.setReference(container);
    }
}
