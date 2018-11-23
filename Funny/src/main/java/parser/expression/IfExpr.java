package parser.expression;

import tokenizer.Type;

public class IfExpr extends Expr {
    private Expr condition;
    private Expr then;
    private Expr _else;
    private Type type;

    /**
     *
     * @param condition
     * @param logicalType || o &&
     * @param secondCondition
     */
    public IfExpr(Expr condition, Type logicalType, Expr secondCondition) {
        this.condition = condition;
        this.type = logicalType;
        this.then = secondCondition;
        this._else = null;
    }

    public IfExpr(Expr cond, Expr then, Expr anElse, Type ifType) {
        super();
        this.condition = cond;
        this.then = then;
        this._else = anElse;
        this.type = ifType;
    }


    @Override
   public Val eval(Env env) {
        return null;
    }
}
