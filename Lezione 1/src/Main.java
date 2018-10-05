public class Main
{
    public static void main(String[] args) throws IllegalAccessException
    {
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

        Parser parser = new Parser(null);

        Block x = parser.expr();

        System.out.println(x.width());
        System.out.println(x.height());

        CharPixelMap m=new CharPixelMap(x.width(), x.height());
        x.drawAt(0,0, m);
        m.printMap();
    }
}
