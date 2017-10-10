package Tetris;
//Todas las clases necesarias para Tetris
import Tetris.Interfaz.Ventana;
import Tetris.Logica.Figuras.Figura;
import Tetris.Logica.Figuras.Tetrimino2;
import Tetris.Logica.Figuras.Tetrimino1;
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

public class Tetris {
    //atributos
    private Tablero tablero;
    private Figura[] figuras;
    private Punto2D punto_actual;
    private Figura figura_actual;
    private Random random;
    private boolean gameover;
    private int idFigura;
    public int puntuacion;
    public static final int tamPixel = 30;
    //constructor
    public Tetris() {
        random = new Random();
        gameover=false;
        tablero = new Tablero();
        figuras = new Figura[]{new Tetrimino1(),new Tetrimino2(), new Tetrimino3(), new Tetrimino4(), new Tetrimino5(), new Tetrimino6(), new Tetrimino7()};
        punto_actual = new Punto2D(4, 0);
        idFigura = random.nextInt(figuras.length);
        figura_actual = figuras[idFigura];
	puntuacion = 1;
    }

    //Método que verifica cual es la siguente etapa, ya sea que salga otra figura, que baje o bin que finalice el juego
    public void update() {
        if (!chocaAbajo()) {
            punto_actual = new Punto2D(punto_actual.getX(), punto_actual.getY() + 1);
        } else {
            if (punto_actual.getY() == 0 && translapa()) {
                Ventana.getTimer().stop();
                gameover=true;
            } else {
                ponFiguraTablero();
                tablero.actualiza();
                punto_actual = new Punto2D(4, 0);
                figura_actual.restablece();
                idFigura = random.nextInt(figuras.length);
                figura_actual = figuras[idFigura];
		puntuacion = puntuacion + 1;
            }
        }

    }
    //Métodos que reconocen que hacer cuabdo el usuario toque las teclas
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
            }
    }
    //Método que Colorea 
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
    //Checa si algo traslapa :)
    private boolean translapa() {
        Punto2D[] puntosFigura = figura_actual.translate(new Punto2D(punto_actual.getX(), punto_actual.getY()));
        for (int i = 0; i < puntosFigura.length; i++) {
            if (tablero.get(puntosFigura[i].getX(), puntosFigura[i].getY()) != 0) {
                return true;
            }
        }
        return false;
    }
    //ve que no se salga del espacio
    private boolean saleDerecha() {
        Punto2D[] puntosFigura = figura_actual.translate(new Punto2D(punto_actual.getX(), punto_actual.getY()));
        for (int i = 0; i < puntosFigura.length; i++) {
            if (puntosFigura[i].getX()>=Tablero.X_LENGTH) {
                return true;
            }
        }
        return false;
    }
    //Coloca la pieza en una nueva posicion
    private void ponFiguraTablero() {
        Punto2D[] puntosFigura = figura_actual.translate(punto_actual);
        for (int i = 0; i < puntosFigura.length; i++) {
            tablero.set(puntosFigura[i].getX(), puntosFigura[i].getY(), idFigura + 1);
        }
    }
    //Verifica si choca abajo
    private boolean chocaAbajo() {
        Punto2D[] puntosFigura = figura_actual.translate(new Punto2D(punto_actual.getX(), punto_actual.getY()));
        for (int i = 0; i < puntosFigura.length; i++) {
            if (puntosFigura[i].getY() + 1 >= Tablero.Y_LENGTH || tablero.get(puntosFigura[i].getX(), puntosFigura[i].getY() + 1) != 0) {
                return true;
            }
        }
        return false;
    }
    //verifica si choca a la derecha
    private boolean chocaDerecha() {
        Punto2D[] puntosFigura = figura_actual.translate(new Punto2D(punto_actual.getX(), punto_actual.getY()));
        for (int i = 0; i < puntosFigura.length; i++) {
            if (puntosFigura[i].getX() + 1 >= Tablero.X_LENGTH || tablero.get(puntosFigura[i].getX() + 1, puntosFigura[i].getY()) != 0) {
                return true;
            }
        }
        return false;
    }
    //Verifica si choca a la Izquierda
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
