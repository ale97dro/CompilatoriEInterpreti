import java.io.IOException;
import java.io.StringReader;
import Exception.ParserException;
import closure.Blocco;

public class Main {

    public static void main(String[] args) throws IOException, ParserException {
        Parser parser = new Parser(new StringReader("{12cm:{007:arbitrary text}my name : is \"nobody\"}"));
        Blocco blocco = parser.parse();
         parser = new Parser(new StringReader("{12cm:{007:{45: ciao}arbitrary text}my name : is \"nobody\"}"));
         blocco = parser.parse();
         //parser = new Parser(new StringReader("{0:A text containing {}")); //questo deve fallire e dare eccezione
        //blocco = parser.parse();
         parser = new Parser(new StringReader("{text:{0:::::}{1:   }label}"));
        blocco = parser.parse();
         parser = new Parser(new StringReader("{12cm:{007:arbitrary text}my name is \"nobody\"}"));
        blocco = parser.parse();
         parser = new Parser(new StringReader("{XXX: }"));
        blocco = parser.parse();


       // parser.parse();

        Tokenizer tokenizer = new Tokenizer(new StringReader("{XXX:}"));
        //Tokenizer tokenizer = new Tokenizer(new StringReader("{12cm:{007:arbitrary text}my name is \"nobody\"}"));
       // Tokenizer tokenizer = new Tokenizer(new StringReader("{text:{0:::::}{1:   }label}"));


        //Tokenizer tokenizer = new Tokenizer(new StringReader("{ifnot while n < 10 do ciao od"));

        Token temp = null;

        do
        {
            temp = tokenizer.next();
            //System.out.println(temp);
        }
        while(temp.getType() != Type.EOS);


        //Blocco blocco = parser.parse();

        System.out.println("STOP");
    }
}
