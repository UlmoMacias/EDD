package mx.unam.ciencias.edd.proyecto2;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
* Clase que imprime las estructuras
*
**/

public class Impresora {

    /*Entero identificador de la estructura*/
    public int tipoDeEstructura;
    /*String que contiene el codigo xml*/
    public String xml;
    /*Entero de coordenada X*/
    static int x = 30;
    /*Entero de coordenada Y*/
    static int y = 30;

    /**
    *Constructor de la clase que recibe el tipo de estructura a contruir
    *
    */
    public Impresora(int a){
        tipoDeEstructura = a;
    }

    /**
    *Clase que dibuja la estructura.
    * @return una string con el codigo xml
    **/
    public String dibuja(Lista<Integer> a){
        switch(tipoDeEstructura){
            case 1:
                    dibujaLista(a);
                break ;
            case 2:
                    //dibujaPila(a);
                break ;
            case 3:
                break ;
            case 4:
                break ;
        }
        return xml;
    }

    /**
    *Metodo que dibuja un circulo en xml
    *@return una String con el xml del circulo
    **/
    public String dibujaCirculo(){
        String circulo = "<circle cx = '"+x + "' cy='"+ y +"' "+"r='20' stroke='black' stroke-width='3' fill='white' />";
        return circulo;
    }

    /**
    *Metodo que dibuja una linea en xml
    *@return una String con el xml de la linea
    **/
    public String dibujaLinea(){
            return "<line x1= '"+ x+20 + "' y1= '"+ (y - 2) + "' x2='" + x+90 +
             "' y2='" + y+8 + "' stroke= 'black' stroke-width='3' />";
    }

    /**
    *Metodo que dibuja una linea en xml
    *@return una String con el xml de la linea
    **/
    public String dibujaSegundaLinea(){
        return "<line x1= '"+ x+19 + "' y1= '"+ y+10 + "' x2='" + x+84 +
             "' y2='" + y+10 + "' stroke= 'black' stroke-width='3' />";
    }

    /**
    *Metodo que dibuja dos lineas en xml
    *@return una String con el xml de las lineas
    **/
    public String dibujaLineaDoble(){
            return dibujaLinea()+ "\n" + dibujaSegundaLinea() + "\n";
    }

    /**
    *Metodo que escribe una el elemento en xml
    *@return una String con el xml del elemento
    **/
    public String dibujaElemento(int elemento, int a, int b){
        return "<text x=\""+"0" + "\" y=\""+ "15" + "\" fill=\"black\">" + elemento + "</text>";
    }

    /**
    *Metodo que dibuja una lista en xml
    *@return una String con el xml de la lista
    **/
    public void dibujaLista(Lista<Integer>lista){
        xml = "<?xml version='1.0' encoding='UTF-8' ?> \n" + 
        "<svg width=' " + (45 + (lista.getLongitud()*170)) + 
        " ' height='1000'>\n"+ "<g>\n";
        int ancho = 30;
        int largo = 35;
        for (int numero : lista ) {
            if (numero == lista.getUltimo()) {
                xml += dibujaCirculo() + "\n";
                xml += dibujaElemento(numero,ancho,largo)+ "\n";
                break;
            }
            xml += dibujaCirculo()+ "\n";
            xml += dibujaLineaDoble()+ "\n";
            xml += dibujaElemento(numero,ancho,largo)+ "\n";
            y+=140;
        }
        xml += "  </g>\n" + "</svg>";
    }
    
}