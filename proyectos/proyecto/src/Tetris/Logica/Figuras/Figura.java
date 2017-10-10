package Tetris.Logica.Figuras;

import Tetris.utils.Punto2D;
import java.awt.Color;

public abstract class Figura{
	int posicion =0;
    /*
    *metodo que  rota a la derecha
    *
    */
    public void rotateRigth(){ 
    	posicion=(posicion+1)%4;
    }
        /*
    *metodo que  rota a la izquiera
    *
    */
    public void rotateLeft(){
    	posicion--;
        if(posicion<0){
            posicion=3;
        }
    }
    abstract public Punto2D[] translate(Punto2D dxy); 
    /*
    *metodo que traslado un punto 
    *
    */
    abstract public Punto2D[] getPuntos();
        /*
    *metodo que  llena las figuras
    *
    */
    abstract public Color getFillColor();
        /*
    *metodo que llena los bordes
    *
    */
    abstract public Color getBorderColor();
        /*
    *metodo que restablece la posicion
    *
    */
    abstract public void restablece();

}
