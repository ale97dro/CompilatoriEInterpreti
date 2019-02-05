package parser;

/*  GRAMMATICA

    Root = Value EOS
    Assoc = Ident ":" Value
    Ident = ID
    Value = ('#" Ident)? (Num | String | Diz | Vec | Ref | Nil)
    Num = NUM
    String = STRING
    Diz = "{" (Assoc ";")* "}"
    Ref = "->" Ident
    Vector = "[" (Value ",")* "]"




 */


import parser.expression.*;
import parser.value.NilVal;
import parser.value.NumVal;
import parser.value.StringVal;
import tokenizer.Token;
import tokenizer.Tokenizer;
import tokenizer.Type;

import java.io.IOException;
import java.io.Reader;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.List;

public class Parser
{
    private Tokenizer tokenizer;
    private Token token;

    public Parser(Reader reader)
    {
        tokenizer = new Tokenizer(reader);
    }

    private void next() throws IOException {
        token = tokenizer.next();
    }


    public Expr parse() throws IOException, ParserException {
        next();
        Expr expr = value(new LabelsContainer());
        check(Type.EOS, token.getType());

        return expr;
    }


    private Expr value(LabelsContainer container) throws IOException, ParserException {
        //checkAndNext(Type.GRAFFAAPERTA, token.getType());

        String optionalId = null;
        //Id opzionale
        if(token.getType() == Type.CANCELLETTO)
        {
            checkAndNext(Type.CANCELLETTO, token.getType());
            check(Type.ID, token.getType());
            optionalId = token.getValue();
            next();

            if(optionalId != null)
            {
                if (container.isLabelStored(optionalId))
                    throw new ParserException("Etichetta duplicata");
            }
        }

        Expr expression = null;

        switch(token.getType())
        {
            case QUADRAAPERTA: //vettore
                expression = vec(container);
                break;
            case GRAFFAAPERTA: //dizionario
                expression = diz(container);
                break;
            case STRING: //stringa
                check(Type.STRING, token.getType());
                expression = new StringVal(token.getValue());
                next();
                break;
            case NUM: //numero
                check(Type.NUM, token.getType());
                expression = new NumVal(token.getNumeric_value());
                next();
                break;
            case FRECCIA: //riferimento
                expression = freccia(container);
                break;
        }

        //Salva l'id opzionale nell'albero all'espressione a cui è assegnato
        //Expr returnExpr = new ValueExpr(optionalId, expression);
        //container.addLabel(optionalId, expression);
        //return returnExpr;

        container.addLabel(optionalId, expression);
        return expression;

    }

    public Expr vec(LabelsContainer container) throws IOException, ParserException {
        List<Expr> expressions = new ArrayList<>();
       checkAndNext(Type.QUADRAAPERTA, token.getType());

       while(token.getType() != Type.QUADRACHIUSA)
       {
           expressions.add(value(container));

            if(token.getType() == Type.VIRGOLA) //se c'è ']', non devo mandare avanti il token
                next();
       }

       checkAndNext(Type.QUADRACHIUSA, token.getType());

       return new VecExpr(expressions);
    }

    public Expr diz(LabelsContainer container) throws IOException, ParserException
    {
        List<Expr> assocList = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        checkAndNext(Type.GRAFFAAPERTA, token.getType());

        while(token.getType() != Type.GRAFFACHIUSA)
        {
            //NON VA QUI!

            if(token.getType() == Type.ID) //altrimenti è vuoto
            {
                check(Type.ID, token.getType()); //controllo id
                String id = token.getValue(); //leggo id

                for(String k : keys) //controllo le chiavi
                    if(k.equals(id))
                        throw new ParserException("Chiave duplicata");

                keys.add(id);
                assocList.add(assoc(container));
            }

            checkAndNext(Type.PUNTOVIRGOLA, token.getType());
        }

        checkAndNext(Type.GRAFFACHIUSA, token.getType());

        return new DizExpr(assocList);
    }

    public Expr assoc(LabelsContainer container) throws ParserException, IOException {
        check(Type.ID, token.getType());
        String id = token.getValue();
        next();
        checkAndNext(Type.DUEPUNTI, token.getType());
        Expr value = value(container);

        return new AssocExpr(id, value);
    }

    public Expr freccia(LabelsContainer container) throws IOException, ParserException
    {
        checkAndNext(Type.FRECCIA, token.getType());
        check(Type.ID, token.getType());
        //Con questa implementazione, non è possibile cercare
        Expr expression = container.get(token.getValue());

        next();
        if(expression == null)
            return NilVal.instance();
        else
            return expression;
    }


    private void check(Type expected, Type actual) throws ParserException {
        if(expected != actual)
            throw new ParserException("Parsing error: expected " + expected + ", find " + actual);
    }

    private void checkAndNext(Type expected, Type actual) throws ParserException, IOException {
        if(expected != actual)
            throw new ParserException("Parsing error: expected " + expected + ", find " + actual);
        next();
    }



}
