package parser.expression;

import parser.ExprList;
import tokenizer.Type;

import java.util.List;

public class PrintExpr extends Expr {

    private Type printType;
    private ExprList args;

    public PrintExpr(ExprList args, Type printType) {
        this.printType = printType;
        this.args = args;
    }

    @Override
    public Val eval(Env env)
    {
        List<Val> valued_args = args.eval(env);

        StringBuilder stringBuilder = new StringBuilder();

        for(Val v : valued_args)
            stringBuilder.append(v.eval(env).toString());

        if(printType == Type.PRINT)
            System.out.print(stringBuilder.toString());
        else
            if(printType == Type.PRINTLN)
                System.out.println(stringBuilder.toString());

        return NilVal.instance();
    }
}
