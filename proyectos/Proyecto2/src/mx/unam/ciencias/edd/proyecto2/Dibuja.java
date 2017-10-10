package mx.unam.ciencias.edd.proyecto2;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.MeteSaca;
import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.ArbolBinarioOrdenado;
import mx.unam.ciencias.edd.Color;
import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.Pila;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.VerticeGrafica;
import java.lang.Math;

public class Dibuja{
    private String estructura;
    private Lista<Integer> elementos;

    public Dibuja(String s, Lista<Integer> e){
	estructura = s.toLowerCase();
	elementos = e;
    }
    //Método para identificar que estructura hay que dibujar
    public String dibujar(){
	CreaSVG graficador = new CreaSVG(); 
	switch (estructura){
	case "ARBOLROJINEGRO":
	    ArbolRojinegro<Integer> rojinegro = new ArbolRojinegro<>();
	    for(Integer n : elementos)
		rojinegro.agrega(n);
	    return graficador.dibujaArbolBinario(rojinegro);
	case "ARBOLAVL":
	    ArbolAVL<Integer> avl = new ArbolAVL<>();
	    for (Integer n : elementos)
		avl.agrega(n);
	    return graficador.dibujaArbolBinario(avl);
	case "ARBOLBINARIOCOMPLETO":
	    ArbolBinarioCompleto<Integer> completo = 
		new ArbolBinarioCompleto<>(elementos);
	    return graficador.dibujaArbolBinario(completo);
	case "ARBOLBINARIOORDENADO":
	    ArbolBinarioOrdenado<Integer> ordenado = 
		new ArbolBinarioOrdenado<>(elementos);
	    return graficador.dibujaArbolBinario(ordenado);
	case "COLA":
	    Cola<Integer> cola = new Cola<>();
	    for (int n : elementos)
		cola.mete(n);
	    return graficador.dibujaMetesaca(cola);
	case "PILA":
	    Pila<Integer> pila = new Pila<>();
	    for ( int n : elementos )
		pila.mete(n);
	    return graficador.dibujaMetesaca(pila);
	case "LISTA":
	    return graficador.dibujaLista(elementos); 
	default: return "Archivo inválido.";
	}
	//return "Archivo inválido";
    }
}
