package Tetris.utils;
/*
*Interfaz de Punto
*
*/
public interface Punto{

    public int getX(); //metodo que obtiene el valor x de un punto
	public void setX(int x); //metodo que modifica el valor x en un punto
    public int getY(); //metodo que obtiene el valor y de un punto
	public void setY(int x); //metodo que modifica el valor y de un punto
	public boolean equals(Object obj); //metodo que compara dos puntos
	public void move(int dx,int dy); //metodo que mueve un punto cierta cantidad de veces los atrivutos x y 'y'
	public Punto2D translate(int dx,int dy); //metodo que traslada un punto a otro
	public Punto2D clone();//metodo que clona un punto
}