package expression;

import parser.EvalException;
import parser.expression.*;
import tokenizer.Type;

import java.math.BigDecimal;

public class UnaryExpr extends Expr {

    private Type oper;
    private Expr expr;


    public UnaryExpr(Type type, Expr expr) {
        this.oper = type;
        this.expr = expr;
    }


    @Override
    public Val eval(Env env) throws EvalException{
        switch (oper) {
            case NOT:
                return expr.eval(env).checkBool().invert();
            case PLUS:
                return expr.eval(env).checkNum().times(new NumVal(BigDecimal.ONE));
            case MINUS:
                return expr.eval(env).checkNum().times(new NumVal(new BigDecimal(-1)));
        }
        return NilVal.instance();
    }

}