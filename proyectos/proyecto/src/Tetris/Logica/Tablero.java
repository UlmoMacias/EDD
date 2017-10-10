package Tetris.Logica;

public class Tablero {

    public static final int X_LENGTH = 10;
    public static final int Y_LENGTH = 20;
    private int[][] matriz;

    /*
    *metodo que crea un objeto tablero
    *
    */
    public Tablero() {
        matriz = new int[Y_LENGTH][X_LENGTH];
    }

    /*
    *metodo que obtiene un valor de una matriz en cierto punto
    *
    */
    public int get(int x, int y) {
        return matriz[y][x];
    }

    /*
    *metodo que Modifica el valor en una casilla especifa de la matriz
    *
    */
    public void set(int x, int y, int value) {
        matriz[y][x] = value;
    }

    /*
    *metodo que actualiza el tablero
    *
    */
    public void actualiza() {
        if (fillRow()) {
            for (int j = Y_LENGTH - 2; j >= 0; j--) {
                System.arraycopy(matriz[j], 0, matriz[j + 1], 0, X_LENGTH);
            }
            matriz[0] = new int[X_LENGTH];
            actualiza();
        }
    }

    /*
    *metodo que compruea si una columna esta llena
    *
    */
    private boolean fillRow() {
        for (int i = 0; i < X_LENGTH; i++) {
            if (get(i, Y_LENGTH - 1) == 0) {
                return false;
            }
        }
        return true;
    }

}
