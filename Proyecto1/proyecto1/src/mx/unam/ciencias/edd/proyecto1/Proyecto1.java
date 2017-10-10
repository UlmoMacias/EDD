package mx.unam.ciencias.edd.proyecto1;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
* Clase que contiene el Main. 
*
**/

public class Proyecto1{
	/**
	*Metodo Main que carga de consola en caso de pasarle parametros o lee todos los archivos que se le pasen. 
	*@throw FileNotFoundException si no encuentra el archivo.
	*@throw IOException si no logro cargar los datos del sistema.
	**/
	public static void main(String[] args)throws IOException, FileNotFoundException{
		LeerDeArchivo leer = new LeerDeArchivo();
		if(args.length == 0)
			leer.cargarDeConsola();
		for (int i= 0;i<args.length ; i++) {
			leer.leerArchivo(args[i]);
		}
		if (leer.lista == null) {
			return ;
		}
		if(leer!=null){
			ComparaStrings a = new ComparaStrings();
			Lista<String> b = leer.lista.mergeSort(a);
			for (String s : b) {
				System.out.println(s);			
			}
		}

	}
}