package mx.unam.ciencias.edd.proyecto2;
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
import java.io.IOException;
import java.io.FileNotFoundException;
public class Proyecto2{
    public static void main(String[]args)throws IOException{
	Lector l = new Lector();
	String nEstructura;
	Lista<String> argumentos;
	Lista<Integer> elementos;
	if(args.length == 0)
	    l.meteLista();
	else
	    l.meteLista(args);
	argumentos = l.getLista();
	Validador v = new Validador(argumentos);
	elementos = v.getElementos();
	nEstructura = v.getEstructura();
	Dibuja d = new Dibuja(nEstructura,elementos);
	System.out.println(d.dibujar());
    }
}
