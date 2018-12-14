package parser.expression;

import java.util.ArrayList;
import java.util.List;

public class SeqExpr extends Expr {

    //private Expr expr;
    private Expr optAssignment; //TODO: deve essere una lista?

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
    public Val eval(Env env) {
        return null; //TODO implementare
    }
}
