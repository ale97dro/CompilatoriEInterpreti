public class Main
{
    public static void main(String[] args)
    {
        Block b = new VertBlock(new Rect(7, 8),
                                new VertBlock(
                                        new HorizBlock(
                                                new Rect(2,3),
                                                new Rect(7, 5)),
                                        new Rect(5,4)));

        //System.out.println(b.width() + b.height());

        System.out.println(b.width());
        System.out.println(b.height());

        CharPixelMap mappa = new CharPixelMap(b.width(), b.height());

        b.drawAt(0,0, mappa);
    }
}
