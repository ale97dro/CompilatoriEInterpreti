import java.io.IOException;
import java.io.StringReader;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello Funny!");

       // Tokenizer tokenizer = new Tokenizer(new StringReader("{/*ciao c*/(ciao = 4)}"));
        //Tokenizer tokenizer = new Tokenizer(new StringReader("{  \n\rprint(\"bella!\");    (   _ciao var=4.0; while if fi\"ciao\"  ; ) }         "));
        //Tokenizer tokenizer = new Tokenizer(new StringReader("{print(\"Hello World\") if(ciao==5); variabile=10.4;}"));
        Tokenizer tokenizer = new Tokenizer(new StringReader("{variabile*=5; var=5*6; ciao+=ok ciao+ciao ok-ok x-=1 ok->{print(\"ciao\")};}"));


        System.out.println(tokenizer.next());
        System.out.println(tokenizer.next());
        System.out.println(tokenizer.next());
        System.out.println(tokenizer.next());
        System.out.println(tokenizer.next());
        System.out.println(tokenizer.next());
        System.out.println(tokenizer.next());
        System.out.println(tokenizer.next());
        System.out.println(tokenizer.next());
        System.out.println(tokenizer.next());
        System.out.println(tokenizer.next());
        System.out.println(tokenizer.next());
        System.out.println(tokenizer.next());
        System.out.println(tokenizer.next());

        for(int i = 0; i<20;i++)
        {
            System.out.println(tokenizer.next());
            //System.out.println("character"+tokenizer.getC());
        }
    }
}
