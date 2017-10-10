package mx.unam.ciencias.edd.proyecto2;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Comparator;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.MeteSaca;
import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.Color;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.Pila;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.VerticeGrafica;
import java.lang.Math;

/*
 * <p>Clase para manejar los parametros que este dado el usuario.
 * como atributos solo tiene una lista, la cual guardara los 
 * Strings</p>
 */
public class Lector{
    /* Lista con los Strings a acomodar */
    public Lista<String> lista= new Lista<String>();
    
    /*Constructor
     */
    public Lector(){}
    
    /*
     * <p>Método que llena el atributo lista con los string
     * de los parametros recividos por el usuario en 
     * consola o en el cad.</p>
     */
    public void meteLista() throws IOException{
	String aux;
	BufferedReader l = new BufferedReader(new InputStreamReader(System.in));
	try{
	    while((aux=l.readLine())!= null)
		lista.agrega(aux);
	    l.close();
	}catch(IOException e){
	    System.err.println("Mataste a la maquina, explotara en...");
	    lista=null;
	    return;
	}
    }

    /* 
     * <p>Método que llena el atributo lista con los Stringss
     * recibidos en archivos en los argumentos del main</p>
     * @param String[]a argumentos
     *
     */
    public void meteLista(String[]a) throws FileNotFoundException,IOException{
	String aux;
	BufferedReader l;
	try{
	    for(int i=0; i<a.length; i++){
		l = new BufferedReader(new FileReader(a[i]));
		while((aux=l.readLine()) != null)
		    lista.agrega(aux);
		l.close();
	    }
	}catch(FileNotFoundException e){
	    System.out.println("El archivo no existe");
	}catch(IOException erro){
	    return;
	}
    }

    /*
     * <p>Metodo que ordena la lista</p>
     * @param el comparador para ordenar la lista
     * @param un int por si encontro un -r
     */
    public void ordena(Comparator<String> c, int i){	
	lista = lista.mergeSort(c);
	if(i != -1)
	    lista = lista.reversa();
    }

    /*
     * <p>Método que imprime la lista</p>
     */
    public void imprime(){
	Iterator<String> iterador = lista.iteradorLista();
	while(iterador.hasNext())
	    System.out.println(iterador.next());
    }

    public Lista<String> getLista(){
	return lista;
    }
}
