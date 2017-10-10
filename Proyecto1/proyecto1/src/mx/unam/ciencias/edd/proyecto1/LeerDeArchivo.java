package mx.unam.ciencias.edd.proyecto1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * <p>Clase que permite leer un archivo desde la entrada estandar o mediante un archivo.</p>
 *
 * <p>La clase LeerDeArchivo utiliza las librerias de java.io para leer archivos.</p>
 * 
 */

public class LeerDeArchivo{
	/*La lista que guarda las lineas de texto*/
	public Lista<String> lista;

	/*constructor de la clase que instancia la Lista de String */
	public LeerDeArchivo(){
		lista = new Lista<String>();
	}

	/**
	*Carga linea a linea el texto y lo almacena en el atributo Lista.
	**/
	public void cargarDeConsola(){
		String linea;
		try{
			BufferedReader lineaLeida = new BufferedReader(new InputStreamReader(System.in));
			while((linea=lineaLeida.readLine())!=null){
				lista.agrega(linea);
			}
			lineaLeida.close();
		}
		catch(IOException ex){
			System.err.println("No logramos leer los datos.");
			lista=null;
			return;
		}
	}

	/**
	* Carga linea a linea el texto desde un archivo y lo almacena en el atributo de clase lista.
	*@param String el nombre del archivo a leer.
	*@throw FileNotFoundException si no encuentra el archivo
	*@throw IOException si no logro cargar los datos del sistema.
	**/
	public void leerArchivo(String archivo)throws FileNotFoundException, IOException {
		String lineas; //union de lineas
		try{
			FileReader linea = new FileReader(archivo);
			BufferedReader linea2 = new BufferedReader(linea);
			while((lineas=linea2.readLine())!=null){
				lista.agrega(lineas);
			}
			linea2.close();
		}
		catch(FileNotFoundException ex){
			System.err.println(archivo+" no es un arcivo existente, por favor verifique el nombre del archivo");
			lista=null;
			return;
		}
		catch(IOException ex){
			System.err.println("Ha ocurrido un error, no se han podidocargar los datos" +"\n" +
				"Asegurate que el archivo exista y se encuentre en el directorio");
			lista=null;
			return;
		}
	}
}