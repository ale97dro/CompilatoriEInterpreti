package application;

public abstract class Block
{
    public abstract int width(); //piccolo interprete per il disegno
    public abstract int height(); //piccolo interprete per il disegno

    public abstract void drawAt(int x, int y, CharPixelMap bitmap); //metodo per disegnare il blocco

    public int area()
    {
        return width()*height();
    }
}
