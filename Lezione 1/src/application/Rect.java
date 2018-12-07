package application;

public class Rect extends Block
{
    private int altezza;
    private int lunghezza;

    @Override
    public void drawAt(int x, int y, CharPixelMap bitmap)
    {
       bitmap.drawRect(x, y, lunghezza, altezza);
    }

    public Rect(int lunghezza, int altezza)
    {
        this.altezza=altezza;
        this.lunghezza=lunghezza;
    }

    public Rect()
    {

    }

    @Override
    public int width()
    {
        return lunghezza;
    }

    @Override
    public int height()
    {
        return altezza;
    }

    @Override
    public String toString()
    {
        return lunghezza+"*"+altezza;
    }
}
