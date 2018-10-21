import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;


/*

    lista con parole chiave; la confronto
 */
public class Tokenizer {

    private BufferedReader reader;
    private int c;
    private StringBuilder str_builder;
    private boolean token_ready;
    private Token temp;

    public char getC(){return toChar(c);}

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
                    //c = reader.read(); //passo al prossimo carattere
                    equal();
                    break;
                case '"': //se arriva una stringa
                    temp = new Token(Type.STRING, stringValue());
                    tokenReady();
                    break;
//                case '"': //valore di stringa
//                    whileNotQuote -> builderAppend(carattere)
//                    break;
//                case 'lettera':
//                    while notSimbolo -> builderAppend(carattere)
//                        checkParoleChiave
//                                notParoleChiave -> variabile
//                                parolaChiave -> newToken(parolaChiave)
                default:
                    if(isSimpleToken(toChar(c))) //un solo carattere come token: ( ) { } ...
                    {
                        temp = simpleToken(toChar(c));
                        tokenReady();
                    }
                    else
                    {
                        if(isLetter(toChar(c)) || isUnderscore(toChar(c))) //id di una variabile oppure una parola chiave
                        {
                            String value = idValue();
                            if(checkKeyWord(value))
                                    temp = getKeyWord(value);
                                else
                                    temp = new Token(Type.ID, value);
                            tokenReady();
                        }
                        else
                        {
                            if((isDigit(toChar(c))) || isDot(toChar(c)))
                            {
                                String value = numberValue();
                                temp = new Token(Type.NUM, new BigDecimal(value));
                                tokenReady();
                            }
                        }
                    }
                    break;
            }

            isEndOfFile();
        }

        str_builder = new StringBuilder();
        token_ready = false;
        return temp;
    }

    private void isEndOfFile() {
        if(c == -1)
        {
            temp = new Token(Type.EOS);
            tokenReady();
        }
    }


    private boolean checkKeyWord(String value)
    {
        return Token.isKeyWord(value);
    }

    private Token getKeyWord(String value)
    {
        return Token.getKeyWordToken(value);
    }


    private void skipSpace() throws IOException
    {
        int temp_character;
        while((temp_character = reader.read()) == ' ');

        c = temp_character;
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
            if((c == '/'))
                _continue = false;
        }
    }

    private void slash() throws IOException
    {
        str_builder.append(toChar(c));
        reader.mark(1); //cosa trovo dopo slash: = o *

        c=reader.read();
        if(c=='*')
        {

        }
        else
        {

        }
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


        if(str_builder.toString().equals("<"))
        {
            //str_builder.append(toChar(c));
            temp = new Token(Type.LESSEQUAL);
            token_ready = true;
            return;
        }

        if(str_builder.toString().equals(">"))
        {
            //str_builder.append(toChar(c)); //TODO: oppure posso inizializzare temp
            temp = new Token(Type.GREATEREQUAL);
            token_ready = true;
            return;
        }

        if(str_builder.toString().equals("="))
        {
            temp = new Token(Type.EQUAL);
            token_ready = true;
            return;
        }

        if(str_builder.toString().equals("!"))
        {
            //str_builder.append(toChar(c));
            temp = new Token(Type.NOTEQUAL);
            token_ready = true;
            return;
        }



        str_builder.append("=");
        //if(str_builder.toString().equals(""))
    }

    private char toChar(int n)
    {
        return ((char)n);
    }

    /**
     * Trova valore di una stringa racchiuso tra " "
     * Non serve MARK/RESET perchè l'ultimo carattere che consumo dal reader è '"'
     * @return
     * @throws IOException
     */
    private String stringValue() throws IOException {
        StringBuilder str = new StringBuilder();

        while((toChar(c=reader.read()))!='"')
            str.append(toChar(c));

        return str.toString();
    }

    private String idValue() throws IOException
    {
        StringBuilder str = new StringBuilder();

        do
        {
            str.append(toChar(c));
            reader.mark(1);
            c = reader.read();
        }
        while((isLetter(toChar(c))) || isDigit(toChar(c)) || isUnderscore(toChar(c)));

        reader.reset();
        return str.toString();
    }

    private String numberValue() throws IOException {
        StringBuilder str = new StringBuilder();

        do
        {
            str.append(toChar(c));
            reader.mark(1);
            c = reader.read();
        }
        while((isDigit(toChar(c))) || isDot(toChar(c)));

        reader.reset();
        return  str.toString();
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

    private void tokenReady()
    {
        token_ready = true;
    }

    /**
     * Vero se contiene caratteri speciali (in questo caso, viene passato solo un carattere)
     * @param _c
     * @return
     */
    public boolean isSimpleToken(char _c)
    {
        //return (!Character.isDigit(_c)) && (!Character.isLetter(_c)) && (!isUnderscore(_c));

        switch (_c)
        {
            case ';':
            case ',':
            case '(':
            case ')':
            case '{':
            case '}':
                return true;
            default:
                return false;
        }
    }

    private boolean isLetter(char c)
    {
        return Character.isLetter(c);
    }

    private boolean isSpace(char c)
    {
        return Character.isSpaceChar(c);
    }

    private boolean isDigit(char c)
    {
        return Character.isDigit(c);
    }

    private boolean isUnderscore(char c)
    {
        return c == '_';
    }

    private boolean isDot(char toChar)
    {
        return c == '.';
    }
}
