public class CharPixelMap
{
    private final char[][] bitmap;

    //private static final char CrossUnit HorizontalUnit VerticalUnit

    private static final char CrossUnit = '+';
    private static final char HorizontalUnit = '-';
    private static final char VerticalUnit = '|';
    private static final char EmptyUnit = ' ';

    public CharPixelMap(int w, int h)
    {
        bitmap=new char[h+1][w+1];

        for(int y=0;y<=h;y++)
        {
            for(int x=0;x<=w;x++)
            {
                drawUnit(y, x, EmptyUnit);
            }
        }
    }

    public void drawUnit(int x, int y, char unit)
    {
        bitmap[x][y]=unit;
    }

    public void printMap()
    {
        for(int r=0;r< bitmap.length;r++)
        {
            for(int c=0;c<bitmap[0].length;c++)
                System.out.print(bitmap[r][c]);
            System.out.print("\n");
        }
    }

    public void drawRect(int x, int y, int width, int height)
    {
        drawUnit(y, x, CrossUnit);
        drawUnit(y, x+width, CrossUnit);
        drawUnit(y+height, x, CrossUnit);
        drawUnit(y+height, x+width, CrossUnit);

        //da completare con i giusti caratteri
    }
}