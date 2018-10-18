import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class Tokenizer {

    private BufferedReader reader;
    private int c;
    private StringBuilder str_builder;

    public Tokenizer(Reader reader)
    {
        this.reader = new BufferedReader(reader);
        StringBuilder str_builder = new StringBuilder();
    }

    public Token next() throws IOException {
        /*
            TODO: saltare gli spazi bianchi
            TODO: i commenti vanno saltati
         */

        Token temp = null;
        boolean token_ready = false;

        while(!token_ready)
        {
            skipSpace();

            switch (toChar(c)) {
                case '/':
                    slash();
                    break;
                case '*':
                    star();
                    break;
                case '=':
                    temp = new Token(Type.EQUAL);
                    token_ready = true;
                    break;
                    //casi semplici: un solo carattere per token
                default:
                    temp = simpleToken(toChar(c));
                    token_ready = true;
                    break;

            }
        }

        str_builder = new StringBuilder();

        return temp;
    }

    private void skipSpace() throws IOException
    {
        while((c = reader.read()) == ' ');
    }

    private void skipComment() throws IOException
    {
        boolean _continue;
        _continue = true;

        reader.reset(); //resetto il reader
        reader.read(); //mangio /
        reader.read(); //mangio *

        while(_continue) //vado alla fine del commento
        {
            while ((c = reader.read()) != '*') ;
            if(((c = reader.read()) == '/'))
                _continue = false;
        }
    }

    private void slash() throws IOException
    {
        str_builder.append(toChar(c));
        reader.mark(2);
    }

    private void star() throws IOException
    {
        if(str_builder.toString().equals("/"))
            skipComment();
        else
            str_builder.append(toChar(c));
    }

    private char toChar(int n)
    {
        return ((char)n);
    }

    private Token simpleToken(char _c)
    {
        Token temp = null;

        switch (_c)
        {
            case ';':
                temp = new Token(Type.SEMICOLON);
                break;
            case ',':
                temp = new Token(Type.COMMA);
                break;
            case '(':
                temp = new Token(Type.BRACKETOPEN);
                break;
            case ')':
                temp = new Token(Type.BRACKETCLOSE);
                break;
            case '{':
                temp = new Token(Type.BRACEOPEN);
                break;
            case '}':
                temp = new Token(Type.BRACECLOSE);
                break;
        }

        return temp;
    }
}
