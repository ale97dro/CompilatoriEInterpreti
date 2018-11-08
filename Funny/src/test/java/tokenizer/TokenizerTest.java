package tokenizer;

import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;

public class TokenizerTest {

    //TODO: scrivere i cazzarola di test

    @Test
    public void isSymbolTest()
    {
        Tokenizer tokenizer = new Tokenizer(new StringReader("ciao"));

        Assert.assertFalse(tokenizer.isSimpleToken('c'));
    }
}
