package parser.value;

import tokenizer.Type;

public class NilVal extends Val {
    private static NilVal nil = null;

    public static NilVal instance()
    {
        if(nil == null)
            nil = new NilVal();

        return nil;
    }

    private NilVal()
    {
    }
}
