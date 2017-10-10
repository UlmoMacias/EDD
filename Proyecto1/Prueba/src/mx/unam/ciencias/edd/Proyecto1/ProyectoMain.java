package mx.unam.ciencias.edd;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.IOException;
import java.io.FileNotFoundException;

public class ProyectoMain{
	public static void main(String[] args) {
		LectorDeArchivo leer = new LectorDeArchivo(args.length);
		if(args.length == 0)
			leer.lista=null;
		for (int i= 0;i<args.length ; i++) {
			leer.Cargar(args[i]);
		}
		if(!leer.lista.esVacio()){
			System.out.println(leer.lista.mergeSort(leer.lista).toString());
		}
	}
}