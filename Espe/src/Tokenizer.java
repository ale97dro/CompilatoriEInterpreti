import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class Tokenizer {

    private BufferedReader reader;
    private int c; //valore del carattere
    private boolean token_already_ready = false;

    public Tokenizer(Reader reader)
    {
        this.reader = new BufferedReader(reader);
    }

    private char toChar()
    {
        return ((char)c);
    }

    public Token next() throws IOException {
        Token t = new Token(null, null);



        if(token_already_ready)
        {
            token_already_ready = false;
            return new Token(null, Type.DUEPUNTI);
        }

        //while(true)
        //{
            c = reader.read();

            if(c==-1)
                return new Token(null, Type.EOS);

            switch(toChar())
            {
                case '{':
                    return new Token(null, Type.OPEN);
                case '}':
                    return new Token(null, Type.CLOSE);
                case ':':
                    return new Token(":", Type.DUEPUNTI);
                default:
                    return scritte();
            }
        //}

        //return t;
    }

    private Token scritte() throws IOException {
        StringBuilder str = new StringBuilder();

        while(!isBrace() && !isDuePunti())
        {
            str.append(toChar());
            reader.mark(1);
            c = reader.read();
        }

        reader.reset();

        /*if(str.charAt(str.length()-1) == ':')
        {
            c = ':';
            str.deleteCharAt(str.length()-1);
            token_already_ready = true;
        }*/

        return new Token(str.toString(), Type.TESTO);
    }

    private boolean isDigit()
    {
        return Character.isDigit(toChar());
    }
    private boolean isLetter()
    {
        return Character.isLetter(toChar());
    }
    private boolean isSpace()
    {
        return Character.isSpaceChar(toChar());
    }

    private boolean isDuePunti()
    {
        return toChar() == ':';
    }

    private boolean isBrace()
    {
        return toChar() == '{' || toChar() == '}';

    }
}
