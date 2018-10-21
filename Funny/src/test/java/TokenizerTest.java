import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;

public class TokenizerTest {

    @Test
    public void isSymbolTest()
    {
        Tokenizer tokenizer = new Tokenizer(new StringReader("ciao"));

        Assert.assertEquals(tokenizer.isSimpleToken('c'), false);
    }
}
