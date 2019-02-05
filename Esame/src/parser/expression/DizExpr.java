package parser.expression;

import java.util.List;

public class DizExpr extends Expr{
    private List<Expr> assocList;

    public DizExpr(List<Expr> assocList) {
        this.assocList = assocList;
    }

    public void setReference(LabelsContainer container) {
        for(Expr e : assocList)
            e.setReference(container);
    }
}
