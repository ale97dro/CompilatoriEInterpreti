package parser;

import parser.expression.Env;
import parser.expression.Expr;
import parser.expression.NilVal;
import parser.expression.Val;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExprList {
    private List<Expr> list;

    public ExprList(List<Expr> list)
    {
        this.list = list;
    }

    public ExprList() {

        list = new ArrayList<>();
    }

    public List<Val> eval(Env env)
    {
        return list.stream().map(expr-> {
            try {
                return expr.eval(env);
            } catch (EvalException e) {
                //e.printStackTrace();
                return NilVal.instance();
            }
        }).collect(Collectors.toList());
    }

    public List<Expr> toList()
    {
        return list;
    }

    public void add(Expr sequence) {

        list.add(sequence);
    }
}
