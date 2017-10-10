package Tetris;

import Tetris.Interfaz.Ventana;
import Tetris.Logica.Figuras.Figura;
import Tetris.Logica.Figuras.Tetrimino1;
import Tetris.Logica.Figuras.Tetrimino2;
import Tetris.Logica.Figuras.Tetrimino3;
import Tetris.Logica.Figuras.Tetrimino4;
import Tetris.Logica.Figuras.Tetrimino5;
import Tetris.Logica.Figuras.Tetrimino6;
import Tetris.Logica.Figuras.Tetrimino7;
import Tetris.Logica.Tablero;
import Tetris.utils.Punto2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.util.Random;
import java.io.*;

public class Tetris {

    private Tablero tablero; 
    private Figura[] figuras;
    private Punto2D punto_actual;
    private Figura figura_actual;
    private Random random;
    private boolean gameover;
    private int idFigura;
    public static final int tamPixel = 30;
    public int puntaje=0 ;

    /*
    *Metodo que construye un objeto de tipo Tetris
    *
    */
    public Tetris() {
        random = new Random();
        gameover=false;
        tablero = new Tablero();
        figuras = new Figura[]{new Tetrimino1(), new Tetrimino2(),new Tetrimino3(),new Tetrimino4(),new Tetrimino5(),new Tetrimino6(),new Tetrimino7()};
        punto_actual = new Punto2D(4, 0);
        idFigura = random.nextInt(figuras.length);
        figura_actual = figuras[idFigura];

    }
    /*
    *Metodo que actualiza la parte del tetris
    *
    */
    public void update() {
        if (!chocaAbajo()) { //Si la pieza no choca abajo 
            punto_actual = new Punto2D(punto_actual.getX(), punto_actual.getY() + 1);
        } else { //de otra forma.
            if (punto_actual.getY() == 0 && translapa()) {
                Ventana.setEscritura(); //Llama al metodo para escribir el Archivo de puntuaciones
                Ventana.getTimer().stop();//Pausas el juego
                gameover=true; //Acaba el juego
                
            } else { //Si aun se puede poner otra pieza entonces
                ponFiguraTablero(); //pondras otra
                tablero.actualiza(); //actualizaras el tablero
                punto_actual = new Punto2D(4, 0); 
                Ventana.getTimer().setDelay(500); //El tiempo estara en 500
                Ventana.sumaPuntos(); //hacemos la suma de puntuacion
                //-----------------------------------------------------------------------
                figura_actual.restablece(); 
                idFigura = random.nextInt(figuras.length);
                figura_actual = figuras[idFigura];
            }
        }
    }
    /*
    *Metodo que interactua el programa con el teclado con cada tecla a presionar.
    *
    */
    public void keyPressed(KeyEvent key){
        if (key.getKeyCode() == KeyEvent.VK_RIGHT && !chocaDerecha()) {
                punto_actual = new Punto2D(punto_actual.getX() + 1, punto_actual.getY());
            } else if (key.getKeyCode() == KeyEvent.VK_LEFT && !chocaIzquierda()) {
                punto_actual = new Punto2D(punto_actual.getX() - 1, punto_actual.getY());
            }else if(key.getKeyCode() == KeyEvent.VK_UP){
                figura_actual.rotateRigth();
                if(saleDerecha() || translapa())
                    figura_actual.rotateLeft();
            }else if(key.getKeyCode() == KeyEvent.VK_DOWN){
                figura_actual.rotateLeft();
                if(saleDerecha() || translapa())
                    figura_actual.rotateRigth();
            }else if(key.getKeyCode() == KeyEvent.VK_P) {
                Ventana.getTimer().stop();
            }else if (key.getKeyCode() == KeyEvent.VK_C){
                Ventana.getTimer().start();
            }
    }
    /*
    *Metodo que dibuja las figuras y el GAME OVER al terminar el juego.
    *
    */
    public void draw(Graphics pinzel) {
        for (int j = 0; j < Tablero.Y_LENGTH; j++) {
            for (int i = 0; i < Tablero.X_LENGTH; i++) {
                if (tablero.get(i, j) != 0) {
                    Figura aux = figuras[tablero.get(i, j) - 1];
                    pinzel.setColor(aux.getFillColor());
                    pinzel.fillRect(i * tamPixel, j * tamPixel, tamPixel, tamPixel);
                    pinzel.setColor(aux.getBorderColor());
                    pinzel.drawRect(i * tamPixel, j * tamPixel, tamPixel, tamPixel);
                }
            }
        }
        Punto2D[] puntosFigura = figura_actual.translate(punto_actual);
        for (int i = 0; i < puntosFigura.length; i++) {
            pinzel.setColor(figura_actual.getFillColor());
            pinzel.fillRect(puntosFigura[i].getX() * tamPixel, puntosFigura[i].getY() * tamPixel, tamPixel, tamPixel);
            pinzel.setColor(figura_actual.getBorderColor());
            pinzel.drawRect(puntosFigura[i].getX() * tamPixel, puntosFigura[i].getY() * tamPixel, tamPixel, tamPixel);
        }
        if(gameover){
            pinzel.setFont(new Font("Book Antiqua",Font.BOLD,40));
            pinzel.setColor(Color.WHITE);
            pinzel.drawString("Game Over",(Tablero.X_LENGTH/4)*(tamPixel-1),(Tablero.Y_LENGTH/2)*tamPixel);
        }
    }

    /*
    *Metodo que mueve una figura
    *
    */
    private boolean translapa() {
        Punto2D[] puntosFigura = figura_actual.translate(new Punto2D(punto_actual.getX(), punto_actual.getY()));
        for (int i = 0; i < puntosFigura.length; i++) {
            if (tablero.get(puntosFigura[i].getX(), puntosFigura[i].getY()) != 0) {
                return true;
            }
        }
        return false;
    }

    /*
    *Metodo que verifira si sale por la derecha
    *
    */
    private boolean saleDerecha() {
        Punto2D[] puntosFigura = figura_actual.translate(new Punto2D(punto_actual.getX(), punto_actual.getY()));
        for (int i = 0; i < puntosFigura.length; i++) {
            if (puntosFigura[i].getX()>=Tablero.X_LENGTH) {
                return true;
            }
        }
        return false;
    }
    /*
    *Metodo que inserta una nueva figura en el tablero
    *
    */
    private void ponFiguraTablero() {
        Punto2D[] puntosFigura = figura_actual.translate(punto_actual);
        for (int i = 0; i < puntosFigura.length; i++) {
            tablero.set(puntosFigura[i].getX(), puntosFigura[i].getY(), idFigura + 1);
        }
    }
    /*
    *Metodo que verifica si una piza choca en la parte de abajo
    *
    */
    private boolean chocaAbajo() {
        Punto2D[] puntosFigura = figura_actual.translate(new Punto2D(punto_actual.getX(), punto_actual.getY()));
        for (int i = 0; i < puntosFigura.length; i++) {
            if (puntosFigura[i].getY() + 1 >= Tablero.Y_LENGTH || tablero.get(puntosFigura[i].getX(), puntosFigura[i].getY() + 1) != 0) {
                return true;
            }
        }
        return false;
    }
    /*
    *Metodo que verifica si una pieza choca a la derecha
    *
    */
    private boolean chocaDerecha() {
        Punto2D[] puntosFigura = figura_actual.translate(new Punto2D(punto_actual.getX(), punto_actual.getY()));
        for (int i = 0; i < puntosFigura.length; i++) {
            if (puntosFigura[i].getX() + 1 >= Tablero.X_LENGTH || tablero.get(puntosFigura[i].getX() + 1, puntosFigura[i].getY()) != 0) {
                return true;
            }
        }
        return false;
    }
    /*
    *Metodo que verifica si una pieza choca a la izquiera.
    *
    */
    private boolean chocaIzquierda() {
        Punto2D[] puntosFigura = figura_actual.translate(new Punto2D(punto_actual.getX(), punto_actual.getY()));
        for (int i = 0; i < puntosFigura.length; i++) {
            if (puntosFigura[i].getX() - 1 < 0 || tablero.get(puntosFigura[i].getX() - 1, puntosFigura[i].getY()) != 0) {
                return true;
            }
        }
        return false;
    }

}
