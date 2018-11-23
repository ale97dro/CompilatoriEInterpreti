package parser.expression;

import tokenizer.Type;

public class BinaryExpr extends Expr {

    private Expr lexpr;
    private Expr rexpr;
    private Type operation;

    public BinaryExpr(Expr lexpr, Type operation, Expr rexpr)
    {
        this.lexpr = lexpr;
        this.operation = operation;
        this.rexpr = rexpr;
    }
    @Override
    public Val eval(Env env) {
        return null;
    }
}
