package parser.expression;

public class ValueExpr extends Expr
{
    private String id;
    private Expr expression;

    public ValueExpr(String optionalId, Expr expression) {
        this.id = optionalId;
        this.expression = expression;
    }
}
