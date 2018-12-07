package application;

public class VertBlock extends Block
{
    Block top;
    Block bottom;

    public VertBlock(Block b1, Block b2)
    {
        top=b1;
        bottom=b2;
    }

    @Override
    public int width()
    {
        //massima larghezza
        return Math.max(top.width(), bottom.width());
    }

    @Override
    public void drawAt(int x, int y, CharPixelMap bitmap)
    {
        top.drawAt(x, y, bitmap);
        bottom.drawAt(x, y+top.height(), bitmap);
    }

    @Override
    public int height()
    {
        //somma
        return top.height()+bottom.height();
    }

    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();


        //return "("+top.toString() + ") | "+ bottom.toString();
        return top.toString() + " | ("+ bottom.toString()+")";
    }
}
