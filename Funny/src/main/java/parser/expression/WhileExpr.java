package parser.expression;

import tokenizer.Type;

public class WhileExpr extends Expr
{
    private Expr cond;
    private Expr _do;
    private Type type;

    public WhileExpr(Expr cond, Expr aDo, Type loopType) {
        this.cond = cond;
        this._do = aDo;
        this.type = loopType;
    }

    @Override
    public Val eval(Env env) {
        return null;
    }
}
