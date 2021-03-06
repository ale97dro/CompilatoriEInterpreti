package parser.expression;

import parser.EvalException;
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
    public Val eval(Env env) throws EvalException
    {
        Val lval = lexpr.eval(env);
        Val rval = rexpr.eval(env);

        switch(operation)
        {
            case PLUS:
                return lval.plus(rval);
            case MINUS:
                return lval.minus(rval);
            case STAR:
                return lval.times(rval);
            case SLASH:
                return lval.division(rval);
            case PERCENTAGE:
                return lval.module(rval);
            case GREATER:
                return lval.greater(rval).checkBool();
            case GREATEREQUAL:
                return lval.greaterEquals(rval).checkBool();
            case LESS:
                return lval.less(rval).checkBool();
            case LESSEQUAL:
                return lval.lessEquals(rval).checkBool();
            case EQUAL:
                return lval.equalsEquals(rval).checkBool();
            case NOTEQUAL:
                return lval.equalsEquals(rval).checkBool().invert();
            default:
                throw new EvalException("Binary");
        }
    }
}
