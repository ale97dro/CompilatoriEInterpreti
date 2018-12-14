package parser.expression;

import java.util.ArrayList;
import java.util.List;

public class FunExpr extends Expr{

    private List<String> params;
    private List<String> locals;
    private Expr root;

    public FunExpr(List<String> params, List<String> locals, Expr expr) {

        if(params != null)
            this.params = params;
        else
            this.params = new ArrayList<>();

        if(locals != null)
            this.locals = locals;
        else
            this.locals = new ArrayList<>();

        this.root = expr;
    }

    @Override
    public Val eval(Env env)
    {
        return new ClosureVal(env, this);
    }

    public Expr code()
    {
        return root;
    }

    public List<String> locals()
    {
        return locals;
    }

    public List<String> params()
    {
        return params;
    }
}
