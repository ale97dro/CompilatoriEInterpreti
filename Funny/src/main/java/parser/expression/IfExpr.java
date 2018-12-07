package parser.expression;

import com.sun.deploy.security.ValidationState;
import parser.EvalException;
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
   public Val eval(Env env) throws EvalException
    {
        if(type == Type.AND || type == Type.OR)
        {
            BoolVal eval1 = condition.eval(env).checkBool();
            BoolVal eval2 = then.eval(env).checkBool();

            //TODO implementa
            switch(type)
            {
                case AND:

                case OR:
            }

        }
        else
        {
            if(type == Type.IF || type == Type.IFNOT)
            {
                BoolVal eval_condition = condition.eval(env).checkBool();

                if(eval_condition.getValue() == Type.TRUE && type == Type.IF)
                    return then.eval(env);

                if(eval_condition.getValue() == Type.FALSE && type == Type.IF)
                    if(_else != null)
                        return _else.eval(env);
                    else
                        return NilVal.instance();

                if(eval_condition.getValue() == Type.TRUE && type == Type.IFNOT)
                    if(_else != null)
                        return _else.eval(env);
                    else
                        return NilVal.instance();

                if(eval_condition.getValue() == Type.FALSE && type == Type.IFNOT)
                    return then.eval(env);
            }
        }

        throw new EvalException("If expression");
    }
}
