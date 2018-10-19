import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


/*

    lista con parole chiave; la confronto
 */
public class Tokenizer {

    private BufferedReader reader;
    private int c;
    private StringBuilder str_builder;
    private boolean token_ready;
    private Token temp;

    private static List<String> keyWord;


    public Tokenizer(Reader reader)
    {
        this.reader = new BufferedReader(reader);
        StringBuilder str_builder = new StringBuilder();

        keyWord = new ArrayList<>();
    }

    public Token next() throws IOException {
        /*
            TODO: saltare gli spazi bianchi
            TODO: i commenti vanno saltati
         */

        temp = null;
        token_ready = false;

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
                    equal();
                    //token_ready = true;
                    break;
                default://casi semplici: un solo carattere per token
                    temp = simpleToken(toChar(c));
                    token_ready = true;
                    break;
            }
        }

        str_builder = new StringBuilder();
        token_ready = false;
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
        reader.mark(1); //cosa trovo dopo slash: = o *
    }

    private void star() throws IOException
    {
        if(str_builder.toString().equals("/"))
            skipComment();
        else
            str_builder.append(toChar(c));
    }

    private void equal()
    {
        //Caratteri prima dell'uguale
        if(str_builder.toString().equals("!"))
        {
            //str_builder.append(c);

            token_ready = true;
        }

        if(str_builder.toString().equals("<"))
        {
            str_builder.append(c);
            token_ready = true;
        }

        //if(str_builder.toString().equals(""))
    }

    private char toChar(int n)
    {
        return ((char)n);
    }

    private char bufferingLetters()
    {
        //while((c = reader.read()) !=

        return 'x';
    }

    private Token simpleToken(char _c) //token di un solo carattere che non sono prefissi o suffissi
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
