package mx.unam.ciencias.edd.proyecto2;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
* Clase que contiene el Main. 
*
**/

public class Proyecto2{
	/**
	*Metodo Main que lee todos los archivos que se le pasen. 
	*@throw FileNotFoundException si no encuentra el archivo.
	*@throw IOException si no logro cargar los datos del sistema.
	**/
	public static void main(String[] args)throws IOException, FileNotFoundException{
		LeerDeArchivo leer = new LeerDeArchivo();
		if(args.length == 0)
			System.err.println("Por favor ingrese un archivo.");
		for (int i= 0;i<args.length ; i++) {
			leer.leerArchivo(args[i]);
		}
		if (leer.lista == null) {
			throw new IOException();
		}
		if(leer!=null){
			String a = leer.getTipoEstructura();
			leer.getNumeros();
			switch (a.toLowerCase()) {
				case "lista" : 
					Impresora imprime = new Impresora(1);
					System.out.println(imprime.dibuja(leer.getListaMod()));
					break ;
				case "pila" :
					imprime = new Impresora(2);
					imprime.dibuja(leer.getListaMod());
					System.out.println(imprime.dibuja(leer.getListaMod()));
					break ;
									
			}
		}

	}
}