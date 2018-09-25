public class Rect extends Block
{
    private int altezza;
    private int lunghezza;

    @Override
    public void drawAt(int x, int y, CharPixelMap bitmap)
    {

    }

    public Rect(int lunghezza, int altezza)
    {
        this.altezza=altezza;
        this.lunghezza=lunghezza;
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
}
