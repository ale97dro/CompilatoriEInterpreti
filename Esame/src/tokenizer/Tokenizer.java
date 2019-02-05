package tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;

public class Tokenizer {

    private BufferedReader reader;
    private int c;
    private StringBuilder str_builder;
    private boolean token_ready;
    private Token current = null;

    public Tokenizer(Reader reader)
    {
        this.reader = new BufferedReader(reader);
        StringBuilder str_builder = new StringBuilder();
    }

    public Token next() throws IOException {


        while(!token_ready)
        {
            skipSpace();

            if(isEOS(c))
                endOfFile(); //controllo se fine del file

            switch (toChar(c))
            {
                case '#':
                    current = new Token(Type.CANCELLETTO);
                    token_ready=true;
                    break;
                case '[':
                    current = new Token(Type.QUADRAAPERTA);
                    token_ready=true;
                    break;
                case ']':
                    current = new Token(Type.QUADRACHIUSA);
                    token_ready=true;
                    break;
                case '{':
                    current = new Token(Type.GRAFFAAPERTA);
                    token_ready=true;
                    break;
                case '}':
                    current = new Token(Type.GRAFFACHIUSA);
                    token_ready=true;
                    break;
                case '-':
                    markAndRead();
                    if(toChar(c) == '>')
                    {
                        current = new Token(Type.FRECCIA);
                        token_ready = true;
                    }
                    break;
                case ';':
                    current = new Token(Type.PUNTOVIRGOLA);
                    token_ready=true;
                    break;
                case ',':
                    current = new Token(Type.VIRGOLA);
                    token_ready=true;
                    break;
                case ':':
                    current = new Token(Type.DUEPUNTI);
                    token_ready=true;
                    break;
                case '"': //se arriva una stringa
                    current = new Token(Type.STRING, stringValue());
                    token_ready=true;
                    break;
                    default:
                        if(isLetter(toChar(c)) || isUnderscore(toChar(c))) //id di una variabile oppure una parola chiave
                        {
                            String value = idValue();
//                            if(isKeyWord(value))
//                                current = getKeyWord(value);
//                            else
                                current = new Token(Type.ID, value);
                            token_ready=true;
                        }
                        else
                        {
                            if((isDigit(toChar(c))) || isDot(toChar(c))) //numero
                            {
                                String value = numberValue();
                                current = new Token(Type.NUM, new BigDecimal(value));
                                token_ready=true;
                            }
                        }
            }

        }


        token_ready = false;
        return current;
    }

    private String numberValue() throws IOException
    {
        StringBuilder str = new StringBuilder();
        boolean dot_find = false;
        boolean dot_repeated = false;

        do
        {
            if(isDot(toChar(c)))
            {
                if(!dot_find)
                {
                    dot_find = true;
                }
                else
                {
                    dot_repeated = true;
                    reader.reset();
                    return str.toString();
                }
            }

            if(!dot_repeated)
            {
                str.append(toChar(c));
                reader.mark(2);
                c = reader.read();
                //markAndRead();
            }
        }
        while(((isDigit(toChar(c)) || isDot(toChar(c)))) && !dot_repeated);

        reset();
        return  str.toString();
    }

    private boolean isDot(char toChar)
    {
        return c == '.';
    }

    private boolean isDigit(char c)
    {
        return Character.isDigit(c);
    }

    private String idValue() throws IOException
    {
        StringBuilder str = new StringBuilder();

        do
        {
            str.append(toChar(c));
            markAndRead();
        }
        while((isLetter(toChar(c))) || isDigit(toChar(c)) || isUnderscore(toChar(c)));

        reset();
        return str.toString();
    }

    private boolean isUnderscore(char c)
    {
        return c == '_';
    }

    private boolean isLetter(char c)
    {
        return Character.isLetter(c);
    }

    private String stringValue() throws IOException
    {
        StringBuilder str = new StringBuilder();

        while((toChar(c=reader.read()))!='"')
            str.append(toChar(c));

        return str.toString();
    }

    private char toChar(int n)
    {
        return ((char)n);
    }

    private void endOfFile() {
        //if(c == -1)
        //{
        current = new Token(Type.EOS);
        token_ready = true;
        //}
    }

    private boolean isEOS(int _c) throws IOException {
        int temp = _c;
        markAndRead();

        if(_c == -1) {
            //throw new TokenizerException("EOS");
            return true;
        }
        else
        {
            _c = temp;
            this.c = temp;
            reset();
            return false;
        }
    }

    private void markAndRead() throws IOException
    {
        reader.mark(1);
        c = reader.read();
    }

    private void reset() throws IOException
    {
        reader.reset();
    }

    private void skipSpace() throws IOException
    {
        int temp_character = c;

        do
        {
            temp_character = reader.read();
        }
        while((temp_character == ' ') || (temp_character == '\n') || (temp_character == '\r'));
        //temp_character = reader.read();

        c = temp_character;
    }


    private void skipInlineComment() throws IOException
    {
        while((c = reader.read()) != '\n');
    }
}
