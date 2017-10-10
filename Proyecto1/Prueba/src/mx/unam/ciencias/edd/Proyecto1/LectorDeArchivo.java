package mx.unam.ciencias.edd;
import java.io.*;

public class LectorDeArchivo{
    private String[] archivos;
    private int textos;
    private static String[] linea = new String[15];
    private static String[] lineas;
    private static int numeroDeLineas = 0;
    public static Lista lista = new Lista<String>();

    public LectorDeArchivo(int cantidadDeArchivos){
        archivos= new String[cantidadDeArchivos];
        textos = cantidadDeArchivos;
    }

	/**
    *Metodo que lee de archivo
    *@param nombre del archivo
    */
    public void CargarDatos(String s){

	try{
	    FileInputStream file = new FileInputStream(s);
	    InputStreamReader puente = new InputStreamReader(file, "UTF-8");
	    BufferedReader lector = new BufferedReader(puente);
        int i =0;
        linea[i]=lector.readLine();
	    while(linea[i]!=null){
            i++;
            numeroDeLineas++;
            linea[i]= lector.readLine();    
        }
        lector.close();
    	}catch(IOException ex){
	    System.out.println("Ha ocurrido un error, no se han podidocargar los datos" + 
	    						"\n" + "Asegurate que el archivo exista y se encuentre en el directorio"
	    						+"Error irrecuperable");
    	}catch(NullPointerException ex){

        }   
   
    }
    /**
    *Metodo que devuelve un arreglo de Strings de lo leido
    *@param s: nombre del archivo
    *@return double [] arreglo de Strings
    */
    public String[] Cargar(String s){
        CargarDatos(s);
        lineas = new String[numeroDeLineas];
        for (int i=0;i<lineas.length ;i++ ) {
            lineas[i]=linea[i];
        }
    	return lineas;
    }

    public static void main(String[] args) {
        LectorDeArchivo leer = new LectorDeArchivo(args.length);
        String[] archivos;
        for (int i = 0;i<args.length;i++ ) {
            archivos = leer.Cargar(args[i]);
        }
        if(!leer.lista.esVacio()){
        System.out.println(leer.lista.mergeSort(leer.lista).toString());
        }
    }
}