package Tetris;

import Tetris.Interfaz.Ventana;
import java.io.*;
import java.util.Scanner;

public class Main {
	/*
    *Metodo Main que ejecuta todo, y solicita el nombre al usuario.
    *
    */
    public static void main(String args[])throws IOException, ClassNotFoundException {
    	Scanner sc = new Scanner(System.in);
    	System.out.println("Ingresa tu nombre:");
    	String foo = sc.nextLine(); 
        java.awt.EventQueue.invokeLater(new Ventana(foo));    
    }
}
