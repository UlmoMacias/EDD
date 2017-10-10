package mx.unam.ciencias.edd;
import java.io.*;

public class LeerDeArchivo{
    private String[] archivos;
    private int textos;
    private static String[] linea = new String[15];
    private static String[] lineas;
    private static int numeroDeLineas = 0;
    private static Lista<String> lista = new Lista<String>();

    public LeerDeArchivo(int cantidadDeArchivos){
        archivos= new String[cantidadDeArchivos];
        textos = cantidadDeArchivos;
    }

	/**
    *Metodo que lee de archivo
    *@param nombre del archivo
    */
    public void CargarDatos(String s,String q){

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
    public String[] Cargar(String s,String q){
        CargarDatos(s,q);
        lineas = new String[numeroDeLineas];
        for (int i=0;i<lineas.length ;i++ ) {
            lineas[i]=linea[i];
        }
    	return lineas;
    }

    public static void main(String[] args) {
        LeerDeArchivo leer = new LeerDeArchivo(args.length);
        String[] archivos;
        for (int i = 0;i<args.length;i++ ) {
            archivos = leer.Cargar(args[i]);
        }
        System.out.println("Holi");
        for (int i=0;i<numeroDeLineas;i++){
            System.out.println("Entra For de main-------------");
            System.out.println("Entra elemento "+ i +" de lineas");
            System.out.print(linea[i]+"\n");
        }   

    }







































    /**
    *Metodo que cierra el sistema guardando datos en el archivo de texto 
    */
    public void cerrarSistema(){
        BufferedWriter escritor = null;
        try{
            System.out.println("Cerrando sistema, actualizando datos a Disco Duro"+"Actualizando Datos");
            FileOutputStream fileSalida = new FileOutputStream("tugueder.txt");
            OutputStreamWriter puente = new OutputStreamWriter(fileSalida, "UTF-8");
            escritor = new BufferedWriter(puente);        
            
        }catch (IOException ex){
            System.out.println("Ha ocurrido un error, no se pudo actualizar los datos correctamente" +
			      "\nAsegurate de que los datos esten correctamente puestos"+" Error Grave");
        } finally {
            if(escritor != null){
                try{
                    escritor.close();
                }catch (IOException ex){
                    System.out.println("Ha ocurrido un error, no se pueden actualizar los empleados correctamente" +
				      "\nAsegurate que el archivo existe"+" Error Grave");
                }
            }
        }
    }
}