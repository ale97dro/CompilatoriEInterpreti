package application;

import parser.EvalException;
import parser.Parser;
import parser.ParserException;
import parser.expression.Env;
import parser.expression.Expr;
import tokenizer.Tokenizer;
import tokenizer.Token;
import tokenizer.TokenizerException;
import tokenizer.Type;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, ParserException, EvalException {
        System.out.println("Hello Funny!");

       // tokenizer.tokenizer tokenizer = new tokenizer.tokenizer(new StringReader("{/*ciao c*/(ciao = 4)}"));
        //tokenizer.tokenizer tokenizer = new tokenizer.tokenizer(new StringReader("{  \n\rprint(\"bella!\");    (   _ciao var=4.0; while if fi\"ciao\"  ; ) }         "));
        //tokenizer.tokenizer tokenizer = new tokenizer.tokenizer(new StringReader("{print(\"Hello World\") if(ciao==5); variabile=10.4;}"));
        //tokenizer tokenizer = new tokenizer(new StringReader("{variabile*=5; var=5*6; //commento \n\r ciao+=ok ciao+ciao ok-ok x-=1 ok->{print(\"ciao\")};}"));
        //Tokenizer tokenizer = new Tokenizer(new StringReader("{ciao%5; ciao/2; ciao/=4.0; !ready; 5!=4; 4>=4}"));
        //Tokenizer tokenizer = new Tokenizer(new StringReader("/*ciao*/println(/*bella*/from, \" -> \", to);"));
        //Tokenizer tokenizer = new Tokenizer(new StringReader("0.4.5"));
        Tokenizer tokenizer = new Tokenizer(new StringReader("{ifnot while n < 10 do ciao od"));


        String program = "{->print(\"Hello world\"); print(\"seconda stampa\"); print(\"Hi!\", \"\\n\");}";
        program="{->print(4+5, \" ciao\");}";
        program = "{->print(\"Hello World\");}";
        program = "{-> x=5; print(x);}";

        tokenizer = new Tokenizer(new StringReader(program));
        Token temp = null;

        do
        {
            temp = tokenizer.next();
            System.out.println(temp);
        }
        while(temp.getType() != Type.EOS);




        Expr parsata = new Parser(new StringReader(program)).execute();

        //parsata.eval(null).checkClosure().apply(new ArrayList<>());
        //System.out.println("Ok");



    }
}
