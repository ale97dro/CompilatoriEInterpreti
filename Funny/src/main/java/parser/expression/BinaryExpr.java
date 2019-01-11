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
                return lval.greater(rval);
            case GREATEREQUAL:
                return lval.greaterEquals(rval);
            case LESS:
                return lval.less(rval);
            case LESSEQUAL:
                return lval.lessEquals(rval);
            case EQUAL:
                return lval.equals(rval);
            default:
                throw new EvalException("Binary");
        }
    }
}
