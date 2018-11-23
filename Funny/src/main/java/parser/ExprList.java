package parser;

import parser.expression.Env;
import parser.expression.Expr;
import parser.expression.Val;

import java.util.List;
import java.util.stream.Collectors;

public class ExprList {
    private List<Expr> list;

    public ExprList(List<Expr> list)
    {
        this.list = list;
    }

    public ExprList() {

    }

    public List<Val> eval(Env env)
    {
        try
        {
            return list.stream().map(expr->expr.eval(env)).collect(Collectors.toList());

        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    public List<Expr> toList()
    {
        return list;
    }

    public void add(Expr sequence) {

        list.add(sequence);
    }
}
