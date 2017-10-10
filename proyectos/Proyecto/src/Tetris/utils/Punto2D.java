package Tetris.utils;

public class Punto2D implements InterfazPunto{
    //Atributos
    private int x;
    private int y;
    //Constructor
    public Punto2D(){
	x = 0;
	y = 0;
    }
    //Constructor por parámetros
    public Punto2D(int x,int y){
	this.x=x;
	this.y=y;
    }
    //Obtiene el valor del atributo x
    public int getX() {
        return this.x;
    }
    //Cambia el valor del atribito x
    public void setX(int x) {
	this.x=x;
    }
    //Obtiene el valor del atributo y
    public int getY() {
        return this.y;
    }
    //Cambia el valor del atributo x
    public void setY(int y) {
	this.y=y;
    }
    //Compara si dos puntos son iguales
    @Override
    public boolean equals(Object obj){
	if(obj instanceof Punto2D){
	    Punto2D puntoObj = (Punto2D)obj;
	    if(puntoObj.getY()==this.getY() && puntoObj.getX()==this.getX())
		return true;
	    else
		return false;
	}
	else
	    return false;
    }
    //Mueve un punto a los parametros recibidos
    public void move(int dx,int dy){
	this.setX(x+dx);
	this.setY(y+dy);
    }
    //Translada un punto a otro
    public Punto2D translate(int dx,int dy){
	this.setX(dx);
	this.setY(dy);
        return this;
    }
    //Clona un punto
    @Override
    public Punto2D clone(){
	Punto2D p = new Punto2D(this.x, this.y);
        return p;
    }
}
