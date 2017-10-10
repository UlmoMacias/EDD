package Tetris.utils;
    
public class Punto2D implements Punto{
    int x;
    int y;

    /*
    *Metodo que construye un objeto de la clase punto 2D
    *
    */
    public Punto2D(){
        x = 0;
        y = 0;
    }
    
    /*
    *Metodo que construyo un objeto de la clase punto 2D con valores especificos
    *
    */
    public Punto2D(int x,int y){
        this.x = x;
        this.y = y;
    }

    /*
    *metodo que regresa el valor de x de un punto
    *
    */
    public int getX() {
        return x;
    }

    /*
    *metodo que modifica el valor de x de un punto
    *
    */
    public void setX(int x) {
        this.x = x; 
    }

    /*
    *metodo que regresa el valor de y de un punto
    *
    */
    public int getY() {
        return y;
    }

    /*
    *metodo que modifica el valor de y de un punto
    *
    */
    public void setY(int y) {
        this.y = y; 
    }
    /*
    *metodo que compara si dos puntos son iguales
    *
    */
    @Override
    public boolean equals(Object obj){
        if (obj instanceof Punto2D) {
            return true;
        }else {
            return false;
        }
    }

    /*
    *metodo que mueve un punto tantas unidades como se especifica
    *
    */
    public void move(int dx,int dy){
        x = x + dx;
        y = y + dy;
    }

    /*
    *metodo que traslada un punto a otro
    *
    */
    public Punto2D translate(int dx,int dy){
    	x = dx;
    	y = dy;
        return null;
    }

    /*
    *metodo que clona un punto
    *
    */
    @Override
    public Punto2D clone(){
        Punto2D aux1 = new Punto2D ();
        aux1.y=this.y;
        aux1.x=this.x;
        return aux1;
    }
}
