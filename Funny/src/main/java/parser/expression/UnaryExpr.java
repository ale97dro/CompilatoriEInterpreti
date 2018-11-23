package parser.expression;

import tokenizer.Type;

public class UnaryExpr extends Expr {

    private Type operation;
    private Expr expression;

    public UnaryExpr(Type operation, Expr unary) {
        this.operation = operation;
        this.expression = unary;
    }

    @Override
    public Val eval(Env env) {
        return null;
    }
}
