import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import Exception.ParserException;
import closure.Blocco;
import com.sun.deploy.security.ValidationState;

public class Parser
{
    //private Reader reader;
    private Tokenizer tokenizer;
    private Token token;

    public Parser(Reader reader) throws IOException {
        //this.reader = reader;
        tokenizer = new Tokenizer(reader);
        //next();
    }

    private void check(Type expected) throws ParserException {
        if(!token.getType().equals(expected))
            throw new ParserException();
    }

    private void checkAndNext(Type expected) throws ParserException, IOException {
        check(expected);
        next();
    }

    private void next() throws IOException {
        token = tokenizer.next();
    }

    public Blocco parse() throws IOException, ParserException {
        //checkAndNext(Type.OPEN);
        next();
        return blocco();

    }

    private Blocco blocco() throws IOException, ParserException {
        Blocco blocco = null;

        checkAndNext(Type.OPEN);
        String etichetta = etichetta();
        checkAndNext(Type.DUEPUNTI);

        List<Blocco> blocchi = new ArrayList<>();

        while(isSottoBlocco()) //se comincia con {
        {
            blocchi.add(blocco());
        }

        String testo;
        if(token.getType()!=Type.CLOSE) //se ho la parentesi chiusa, mi salta il token corrispondente a del testo vuoto
            testo = testo();
        else
            testo= ""; //lo aggiungo quindi io

        checkAndNext(Type.CLOSE);

        blocco = new Blocco(etichetta, testo, blocchi);

        return blocco;
    }

    private String testo() throws ParserException, IOException {
        //check(Type.TESTO);
       // String testo = token.getValue();

        if(token.getType() != Type.DUEPUNTI && token.getType()!=Type.TESTO)
            throw new ParserException("Errore mio");

        //TODO: controllare che non contenga { } e gestire il caso dei :
        StringBuilder testo = new StringBuilder();

        while(token.getType() != Type.CLOSE)
        {
            //check(Type.TESTO);
            for (char x : token.getValue().toCharArray())
            {
                if (x == '{' || x == '}')
                    throw new ParserException("graffa trovata");
            }

            testo.append(token.getValue());
            next();
        }

        return testo.toString();
    }

    private String etichetta() throws ParserException, IOException {
        check(Type.TESTO);
        String testo = token.getValue();
        next();

        //TODO: controllare che siano solo numeri e lettere

        if(!testo.equals("")) //no stringa vuota, continuo
        {
            for(char x : testo.toCharArray())
            {
                if(!Character.isDigit(x) && !Character.isLetter(x))
                    throw new ParserException();
            }
        }
        else
            throw new ParserException();

        return testo;
    }

    private boolean isSottoBlocco() {
        return token.getType() == Type.OPEN;
    }


}
