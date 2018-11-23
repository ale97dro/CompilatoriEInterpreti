package parser.expression;

public class SeqExpr extends Expr {

    private Expr expr;
    private Expr optAssignment; //TODO: deve essere una lista?

    public SeqExpr(Expr expr, Expr optAssignment)
    {
        this.expr = expr;
        this.optAssignment = optAssignment;
    }

    @Override
    public Val eval(Env env) {
        return null;
    }
}
