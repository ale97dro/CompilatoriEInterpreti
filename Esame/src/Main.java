import parser.Parser;
import parser.ParserException;
import parser.expression.Expr;
import tokenizer.Token;
import tokenizer.Tokenizer;
import tokenizer.Type;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

public class Main {
    public static void main(String[] args) throws IOException, ParserException {

        //String program = "# xy { nome: \"Pierre\";}";
       // String program = "{ ; nome: #nome \"Pierre\"; }";
        String program = "#a [ # xy { nome: \"Pierre\"; }, # xx{ nome: \"Heloise\" ; amante:-> xy; test:-> p; }, ->xx ]";

        Tokenizer tokenizer = new Tokenizer(new StringReader("[ # xy { nome: \"Pierre\"; amante:->xx}, # xx{ nome: \"Heloise\" ; amante: -> xy}, ]"));

        tokenizer = new Tokenizer(new StringReader(program));
        Token temp = null;

        do
        {
            temp = tokenizer.next();
            System.out.println(temp);
        }
        while(temp.getType() != Type.EOS);

        Expr parsata = new Parser(new StringReader(program)).parse();

        System.out.println("Ciao");
    }
}
