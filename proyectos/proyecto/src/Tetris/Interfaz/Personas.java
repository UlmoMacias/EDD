package Tetris.Interfaz;
import java.io.*;
	/*
    *Clase para ordenar los puntajes
    *
    */
public class Personas implements Serializable {
	private String nombre;
	private int puntos;
	/*
    *Metodo que construye Personas con nombre y puntuacion
    *
    */
	public Personas(String nombre, int puntos){
		super();
		this.nombre = nombre;
		this.puntos = puntos;
	}
	/*
    *Metodo que regresa el nombre de la persona
    *
    */
	public String getNombre(){
		return nombre;
	}
	/*
    *Metodo que regresa el puntaje de una persona
    *
    */
	public int getPuntos(){
		return puntos;
	}
}