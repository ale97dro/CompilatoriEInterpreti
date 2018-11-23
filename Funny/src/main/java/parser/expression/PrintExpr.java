package parser.expression;

import parser.ExprList;
import tokenizer.Type;

public class PrintExpr extends Expr {

    private Type printType;
    private ExprList args;

    public PrintExpr(ExprList args, Type printType) {
        this.printType = printType;
        this.args = args;
    }

    @Override
    public Val eval(Env env) {
        return null;
    }
}
