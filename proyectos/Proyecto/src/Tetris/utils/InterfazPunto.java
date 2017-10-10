package Tetris.utils;
//Interfaz qur Punto2D implementa :)
public interface InterfazPunto{
    public int getX();
    public void setX(int x);
    public int getY();
    public void setY(int y);
    public boolean equals(Object obj);
    public void move(int dx, int dy);
    public Object translate(int dx, int dy);
    public Object clone();
}
