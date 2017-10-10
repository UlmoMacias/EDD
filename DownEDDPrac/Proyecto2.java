package mx.unam.ciencias.edd.proyecto2;

import java.io.*;
import mx.unam.ciencias.edd.Lista;

public class Proyecto2{
    private static Lector lee = new Lector();
    private static String ar;
    
    public static void main(String [] args){
	Lista<String> list = new Lista<String>();
	Lista<String> aux = new Lista<String>();
	if(args.length >= 1){
	    for(int i= 0; i<args.length; i++){
		ar = args[i];
		list = lee.lectura(ar);
		for(String m : list){
		    aux.agrega(m);
		}
	    }
	}
	else{
	    InputStreamReader is = new InputStreamReader(System.in);
	    BufferedReader bf = new BufferedReader(is);
	    try{
		String cadena = bf.readLine();
		while(cadena != null){
		    aux.agrega(cadena);
		    cadena = bf.readLine();
		}
		bf.close();
	    }catch(IOException ioe){}
	}
	//DibujaLista d = new DibujaLista();
	for(String m : aux.getPrimero().split(" ")){
	    String min = m.toLowerCase();
	    System.out.println(min);
	}

	Lista<String> li = new Lista<String>();
	DibujaLista dBL = new DibujaLista();
	for(int i=0; i<5; i++){
	    li.agrega(String.valueOf(i));
	}
	//System.out.println(dBL.dibujaLista(li));

	/*String linea;
	    FileReader l = new FileReader(archivo);
	    BufferedReader l2 = new BufferedReader(l);
	    while((linea = l2.readLine())!=null){
		if(linea.charAt(0) == '#'){
		    argumento.agrega(linea);
		}else{
		    lista.agrega(linea);
		}
	    }
	    l2.close();*/

	
    }
}


    

	

	

   
	

    
