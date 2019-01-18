package parser.expression;

import parser.EvalException;
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
    public Val eval(Env env) throws EvalException
    {
        //BoolVal eval = cond.eval(env).checkBool();

        if(type == Type.WHILE)
            while(cond.eval(env).checkBool().getValue() == Type.TRUE)
                _do.eval(env);
        else
            if(type == Type.WHILENOT)
                while(cond.eval(env).checkBool().getValue() == Type.FALSE)
                    _do.eval(env);

        return NilVal.instance();
    }
}
