package application;

public class HorizBlock extends Block
{
    private final Block left;
    private final Block right;

    public HorizBlock(Block b1, Block b2)
    {
        left=b1;
        right=b2;
    }

    @Override
    public void drawAt(int x, int y, CharPixelMap bitmap)
    {
        left.drawAt(x, y, bitmap);
        right.drawAt(x+left.width(), y, bitmap);
    }

    @Override
    public int width()
    {
        //somma delle lunghezze
        return left.width()+right.width();
    }

    @Override
    public int height()
    {
        //massimo tra i due
        return Math.max(left.height(), right.height());
    }
}
