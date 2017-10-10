package mx.unam.ciencias.edd.proyecto2;
import mx.unam.ciencias.edd.Lista;
import java.lang.Math;

public class Validador{
    String nEstructura;
    Lista<Integer> elementos;

    public Validador(Lista<String> l){
	l=valida(l);
	sacaNumeros(l);
	nEstructura = l.eliminaPrimero();
    }

    private Lista<String> valida(Lista<String> s){
	String comentarios = "#";
	for(String a:s){    
	    int i=0;
	    while(i!=-1){
		i = a.indexOf(comentarios);
		String texto = a.substring(i,a.length()-1);
		a.replace(texto, "");
	    }
	}
	for(String a:s){
	    if(a.equals(""))
		s.elimina(a);
	}
	return s;
    }

    private static boolean esNumero(String s){
	try{
	    Integer.parseInt(s);
	    return true;
	}catch(NumberFormatException e){
	    return false;                        
	}
    }

    private void sacaNumeros(Lista<String> l){
	String[] s;
	for(String aux: l){
	    s=aux.split(" ");
	    for(int i=0; i<s.length;i++)
		if(esNumero(s[i])){
		    int entero = Integer.parseInt(s[i]);
		    elementos.agrega(entero);
		}
	}
    }

    public Lista<Integer> getElementos(){
	return elementos;
    }

    public String getEstructura(){
	return nEstructura;
    }
}
