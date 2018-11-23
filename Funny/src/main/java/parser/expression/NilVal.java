package parser.expression;

import parser.EvalException;
import tokenizer.Type;

public class NilVal extends Val {

    //public static final Type NIL = Type.NIL;

    private Type type;

    private static NilVal nil = null;

    public static NilVal instance()
    {
        if(nil == null)
            nil = new NilVal();

        return nil;
    }

    private NilVal()
    {
        type = Type.NIL;
    }

    @Override
    public NilVal checkNil() throws EvalException {
        return this;
    }
}
