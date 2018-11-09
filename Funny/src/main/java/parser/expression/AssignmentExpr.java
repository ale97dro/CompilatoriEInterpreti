package parser.expression;

import tokenizer.Type;

public class AssignmentExpr extends Expr
{
    private String id;
    private Type operation;
    private Expr expression;

    public AssignmentExpr(String id, Type operation, Expr expression)
    {
        this.id = id;
        this.operation = operation;
        this.expression = expression;
    }

    @Override
    Val eval(Env env) {
        return null;
    }
}
