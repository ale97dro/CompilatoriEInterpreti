package parser.expression;

public abstract class Expr {

    abstract Val eval(Env env);
}
