public class CharPixelMap
{
    private final char[][] bitmap;

    //private static final char CrossUnit HorizontalUnit VerticalUnit

    private static final char CrossUnit = '+';
    private static final char HorizontalUnit = '-';
    private static final char VerticalUnit= '|';
    private static final char EmptyUnit = ' ';

    public CharPixelMap(int w, int h)
    {
        bitmap=new char[h+1][w+1];
        for(int y=0;y<=h;y++)
        {
            for(int x=0;x<=w;x++)
            {
                drawUnit(x, y, EmptyUnit);
            }
        }
    }

    public void drawUnit(int x, int y, char unit)
    {
        bitmap[x][y]=unit;
    }
}
