package Tetris.Logica;
//Clase ue construye el tablero
public class Tablero {
    //Atributos
    public static final int X_LENGTH = 10;
    public static final int Y_LENGTH = 20;
    private int[][] matriz;
    //Constructor
    public Tablero() {
        matriz = new int[Y_LENGTH][X_LENGTH];
    }
    //Método para obtener el valor de una parte especifica del tablero
    public int get(int x, int y) {
        return matriz[y][x];
    }
    //Cambiar el valor de un punto especifico del tablero
    public void set(int x, int y, int value) {
        matriz[y][x] = value;
    }
    
    public void actualiza() {
        if (fillRow()) {
            for (int j = Y_LENGTH - 2; j >= 0; j--) {
                System.arraycopy(matriz[j], 0, matriz[j + 1], 0, X_LENGTH);
            }
            matriz[0] = new int[X_LENGTH];
            actualiza();
        }
    }

    private boolean fillRow() {
        for (int i = 0; i < X_LENGTH; i++) {
            if (get(i, Y_LENGTH - 1) == 0) {
                return false;
            }
        }
        return true;
    }

}
