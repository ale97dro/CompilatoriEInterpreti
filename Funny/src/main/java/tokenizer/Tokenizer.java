package tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;

/*TODO: mancano commenti inline e annidati, numeri con notazione scientifica
*/
/*
    lista con parole chiave; la confronto
 */
public class Tokenizer {

    private BufferedReader reader;
    private int c;
    private StringBuilder str_builder;
    private boolean token_ready;
    private Token current = null;
    private Token previous;
    private boolean previousMode = false;
    private boolean previousToken = false;
    private Token skipped = null;

    public char getC(){return toChar(c);}

    public Tokenizer(Reader reader)
    {
        this.reader = new BufferedReader(reader);
        StringBuilder str_builder = new StringBuilder();
    }

    public void previous()
    {
        //return previous;
        previousMode = true;
        skipped = current;
       // previousToken = true;
    }

    public Token next() throws IOException {
        /*
            saltare gli spazi bianchi
            i commenti vanno saltati
         */

        if(previousMode)
        {
            previousMode = false;
            return previous;
        }

        if(skipped != null)
        {
            previous = current;
            current = skipped;
            skipped = null;
            return current;
        }

        previous = current;
        current = null;
        token_ready = false;

        while(!token_ready)
        {
            skipSpace();

            if(isEOS(c))
                endOfFile(); //controllo se fine del file


            switch (toChar(c)) {
                case '+':
                    plus();
                    tokenReady();
                    break;
                case '-':
                    minus();
                    tokenReady();
                    break;
                case '*':
                    star();
                    tokenReady();
                    break;
                case '/':
                    slash();
                    break;
                case '%':
                    percentage();
                    tokenReady();
                    break;
                case '=':
                    equal();
                    tokenReady();
                    break;
                case '!':
                    esclamation();
                    tokenReady();
                    break;
                case '&':
                    and();
                    tokenReady();
                    break;
                case '|':
                    or();
                    tokenReady();
                    break;
                case '<':
                    lessThan();
                    tokenReady();
                    break;
                case '>':
                    greaterThan();
                    tokenReady();
                    break;
                case '"': //se arriva una stringa
                    current = new Token(Type.STRING, stringValue());
                    tokenReady();
                    break;
                default:
                    if(isSimpleToken(toChar(c))) //un solo carattere come token: ( ) { } ...
                    {
                        current = simpleToken(toChar(c));
                        tokenReady();
                    }
                    else
                    {
                        if(isLetter(toChar(c)) || isUnderscore(toChar(c))) //id di una variabile oppure una parola chiave
                        {
                            String value = idValue();
                            if(isKeyWord(value))
                                    current = getKeyWord(value);
                                else
                                    current = new Token(Type.ID, value);
                            tokenReady();
                        }
                        else
                        {
                            if((isDigit(toChar(c))) || isDot(toChar(c))) //numero
                            {
                                String value = numberValue();
                                current = new Token(Type.NUM, new BigDecimal(value));
                                tokenReady();
                            }
                        }
                    }
                    break;
            }


        }

        str_builder = new StringBuilder();
        token_ready = false;
        return current;
    }


    ////////////////////////////////////////////
    ////////////METODI DI SKIP ////////////////
    ///////////////////////////////////////////

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

    private void skipBlockComment() throws IOException
    {
        boolean _continue;
        _continue = true;

        c = reader.read();

        while(_continue) //vado alla fine del commento
        {
            while ((c = reader.read()) != '*');
            c =reader.read();
            if((c == '/'))
                _continue = false;
        }
    }

    private void skipInlineComment() throws IOException
    {
        while((c = reader.read()) != '\n');
    }


    //////////////////////////////////////////////////
    ////////////METODI GENERAZIONE TOKEN /////////////
    /////////////////////////////////////////////////

    private Token getKeyWord(String value)
    {
        return Token.getKeyWordToken(value);
    }

    private void endOfFile() {
        //if(c == -1)
        //{
            current = new Token(Type.EOS);
            tokenReady();
        //}
    }

    private void plus() throws IOException
    {
        markAndRead();

        switch(toChar(c))
        {
            case '=':
                current = new Token(Type.ADDASSIGN);
                break;
                default:
                    current = new Token(Type.PLUS);
                    reset();
                    break;
        }
    }

    private void minus() throws IOException {
        markAndRead();

        switch (toChar(c))
        {
            case '=':
                current = new Token(Type.MINUSASSIGN);
                break;
            case '>':
                current = new Token(Type.ARROW);
                break;
                default:
                    current = new Token(Type.MINUS);
                    reset();
                    break;
        }
    }

    private void star() throws IOException
    {
        markAndRead();

        switch (toChar(c))
        {
            case '=':
                current = new Token(Type.MULTASSIGN);
                break;
            default:
                current = new Token(Type.STAR);
                reset();
                break;
        }
    }

    private void slash() throws IOException
    {
        markAndRead();

        switch(toChar(c))
        {
            case '=':
                current = new Token(Type.DIVASSIGN);
                tokenReady();
                break;
            case '*':
                skipBlockComment();
                break;
            case '/':
                skipInlineComment();
                break;
                default:
                    current = new Token(Type.SLASH);
                    tokenReady();
                    reset();
                    break;
        }
    }

    private void percentage() throws IOException
    {
        markAndRead();

        switch(toChar(c))
        {
            case '=':
                current = new Token(Type.PERCENTASSIGN);
                break;
                default:
                    current = new Token(Type.PERCENTAGE);
                    reset();
                    break;
        }
    }


    /**
     * Possibilità: se trovo un altro '=', significa ==
     *              se trovo un altro carattere, significa solo assegnamento
     * @throws IOException
     */
    private void equal() throws IOException {
        //Leggo il carattere dopo

        markAndRead();

        switch (toChar(c))
        {
            case '=':
                current = new Token(Type.EQUAL);
                break;
                default:
                    current = new Token(Type.ASSIGN);
                    reset();
                    break;
        }
    }

    private void esclamation() throws IOException
    {
        markAndRead();

        switch (toChar(c))
        {
            case '=':
                current = new Token(Type.NOTEQUAL);
                break;
                default:
                    current = new Token(Type.NOT);
                    reset();
                    break;
        }
    }

    private void and() throws IOException
    {
        markAndRead();

        switch (toChar(c))
        {
            case '&':
                current = new Token(Type.AND);
                break;
                default:
                    current = new Token(Type.UNKNOW);
                    reset();
                    break;
        }
    }

    private void or() throws IOException
    {
        markAndRead();

        switch (toChar(c))
        {
            case '|':
                current = new Token(Type.OR);
                break;
                default:
                    current = new Token(Type.UNKNOW);
                    reset();
                    break;
        }
    }

    private void greaterThan() throws IOException
    {
        markAndRead();

        switch (toChar(c))
        {
            case '=':
                current = new Token(Type.GREATEREQUAL);
                break;
                default:
                    current = new Token(Type.GREATER);
                    reset();
                    break;
        }
    }

    private void lessThan() throws IOException
    {
        markAndRead();

        switch (toChar(c))
        {
            case '=':
                current = new Token(Type.LESSEQUAL);
                break;
            default:
                current = new Token(Type.LESS);
                reset();
                break;
        }
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
            markAndRead();
        }
        while((isLetter(toChar(c))) || isDigit(toChar(c)) || isUnderscore(toChar(c)));

        reset();
        return str.toString();
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


    private Token simpleToken(char _c) throws IOException //token di un solo carattere che non sono prefissi o suffissi
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
                temp = new Token(Type.TONDAOPEN);
                break;
            case ')':
                temp = new Token(Type.TONDACLOSE);
                break;
            case '{':
                temp = new Token(Type.GRAFFAOPEN);
                break;
            case '}':
                temp = new Token(Type.GRAFFACLOSE);
                break;
        }

        //next();
        return temp;
    }



    ////////////////////////////////////////////
    ////////////METODI DI UTILITA' /////////////
    ///////////////////////////////////////////

    private void markAndRead() throws IOException
    {
        reader.mark(1);
        c = reader.read();
    }

    private void reset() throws IOException
    {
        reader.reset();
    }

    private char toChar(int n)
    {
        return ((char)n);
    }

    private void tokenReady()
    {
        token_ready = true;
    }

    ///////////////////////////////////////////////////
    ////////////METODI CONTROLLI BOOLEANI /////////////
    //////////////////////////////////////////////////

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

    private boolean isKeyWord(String value)
    {
        return Token.isKeyWord(value);
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
}
