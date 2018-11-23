package parser.expression;

public class GetVarExpr extends Expr {

    private String id;

    public GetVarExpr(String stringValue) {
        this.id = stringValue;
    }

    @Override
    public Val eval(Env env) {
        return null;
    }
}
