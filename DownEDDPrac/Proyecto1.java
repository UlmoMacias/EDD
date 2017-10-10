package mx.unam.ciencias.edd.proyecto1;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.IOException;
import java.io.FileNotFoundException;
public class Proyecto1{
	public static void main(String[] args)throws IOException{
		Lector leer = new Lector();
		if(args.length == 0)
			leer.lectura=null;
		for (int i= 0;i<args.length ; i++) {
			leer.leerArchivo(args[i]);
		}
	if(!leer.esVacio()){
		System.out.println(leer.lectura.mergeSort(leer.lectura).toString());
	}

	}
}