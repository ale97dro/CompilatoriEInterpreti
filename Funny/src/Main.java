import java.io.IOException;
import java.io.StringReader;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello Funny!");

        Tokenizer tokenizer = new Tokenizer(new StringReader("{/*ciao c*/ (ciao = 4)*}"));

        System.out.println(tokenizer.next());
        System.out.println(tokenizer.next());
        System.out.println(tokenizer.next());
        System.out.println(tokenizer.next());
        System.out.println(tokenizer.next());
        System.out.println(tokenizer.next());
        System.out.println(tokenizer.next());
        System.out.println(tokenizer.next());
    }
}
