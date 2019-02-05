package parser.expression;

import java.util.List;

public class VecExpr extends Expr {
    private List<Expr> expressionList;


    public VecExpr(List<Expr> expressions) {
        this.expressionList = expressions;
    }

    public void setReference(LabelsContainer container) {
        for(Expr e : expressionList)
            e.setReference(container);

    }
}
