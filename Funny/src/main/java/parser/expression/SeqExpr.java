package parser.expression;

import parser.EvalException;

import java.util.ArrayList;
import java.util.List;

public class SeqExpr extends Expr {

    private List<Expr> exprList;

    public SeqExpr(Expr expr, Expr optAssignment)
    {
        //this.expr = expr;
        //this.optAssignment = optAssignment;
    }

    public SeqExpr()
    {
        this.exprList = new ArrayList<>();
    }

    public SeqExpr(List<Expr> exprList) {
        this.exprList = exprList;
    }

    public void add(Expr expression)
    {
        exprList.add(expression);
    }

    @Override
    public Val eval(Env env) throws EvalException {
        //return null;
        Val val = NilVal.instance();

        for(Expr e : exprList)
            val = e.eval(env);

        return val;
    }
}
