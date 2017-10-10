package Tetris.Logica.Figuras;
//Interface que modela el comportamiento de todas las figuras del juego
import Tetris.utils.Punto2D;
import java.awt.Color;

public interface Figura{
    //    protected int posicion;
    //Translada 
    public Punto2D[] translate(Punto2D dxy);
    //Obtener los puntos
    public Punto2D[] getPuntos();
    //Como cambian los puntos cuando la figura gire a la derecha
    public void rotateRigth();//{
    //posicion=(posicion+1)%4;
    //}
    //Como cambiaran los puntos al girar a la izquierda
    public void rotateLeft();//{
    //posicion--;
    //	if(posicion<0)
    //	    posicion=3;
    // }
    //Obtener el color
    public  Color getFillColor();
    public  Color getBorderColor();
    //Reestablecer los puntos
    public  void restablece();

}
