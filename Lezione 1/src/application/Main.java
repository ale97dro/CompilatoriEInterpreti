package application;

import parser.Parser;

import java.io.IOException;
import java.io.StringReader;

public class Main
{
    public static void main(String[] args) throws IllegalAccessException, IOException {
        Block b = new VertBlock(new Rect(7, 8),
                new VertBlock(
                        new HorizBlock(
                                new Rect(2,3),
                                new Rect(7, 5)),
                        new Rect(5,4)));


        System.out.println(b.width());
        System.out.println(b.height());

        CharPixelMap mappa = new CharPixelMap(b.width(), b.height());

        b.drawAt(0,0, mappa);
        mappa.printMap();

        System.out.println("__________________________________________________");

        //MOCK PARSER
        Parser parser = new Parser();

        Block x = parser.expr();

        System.out.println(x.width());
        System.out.println(x.height());

        CharPixelMap m=new CharPixelMap(x.width(), x.height());
        x.drawAt(0,0, m);
        m.printMap();

        System.out.println("__________________________________________________");

        //TRUE PARSER

        parser = new Parser(new StringReader("7*8|((2*3-7*5)|5*4)"));
        Block y = parser.expr();

        System.out.println(y.width());
        System.out.println(y.height());

        //ESPE
        parser = new Parser(new StringReader("7*8|((2*3-7*5)|5*4)")); //una parentesi di troppo  mette parentesi all'ultima operazione
        Block result = parser.expr();
        System.out.println(result.toString());
        parser = new Parser(new StringReader("7*8|(2*3|(7*5-5*4))")); //mantiene parentesi anche se non necessaria
         result = parser.expr();
        System.out.println(result.toString());
        parser = new Parser(new StringReader("7*8|5*4-4*5")); //mette le parentesi anche se non necessarie per la priorità di -
         result = parser.expr();
        System.out.println(result.toString());
        parser = new Parser(new StringReader("3*4-(4*5-5*6)")); //non mette le parentesi per albero pendente a destra
         result = parser.expr();
        System.out.println(result.toString());
        parser = new Parser(new StringReader("3*4|(4*5-5*6)")); //ok
         result = parser.expr();

        System.out.println(result.toString());
    }
}
