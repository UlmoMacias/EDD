
import static javax.swing.JOptionPane.*;
import java.io.*;

public class LeerDeArchivo{
    private String array [];
    private double arreglo[];
	/**
    *Metodo que lee de archivo
    *@param nombre del archivo
    */
    public void CargarDatos(String s){

	try{
	    FileInputStream file = new FileInputStream(s);
	    InputStreamReader puente = new InputStreamReader(file, "UTF-8");
	    BufferedReader lector = new BufferedReader(puente);
	    double numero;
	    String linea;
	    while((linea=lector.readLine())!=null){
		array = linea.split("\\|");
	    }
	    lector.close();
    	}catch(IOException ex){
	    showMessageDialog(null,"Ha ocurrido un error, no se han podidocargar los datos\n Asegurate que el archivo exista y se encuentre en el directorio","Error irrecuperable", ERROR_MESSAGE);
    	}catch(NullPointerException ex){

        }   
   
    }
    /**
    *Metodo que devuelve un arreglo de double de lo leido
    *@param s: nombre del archivo
    *@return double [] arreglo de double
    */
    public double [] Cargar(String s){
        CargarDatos(s);
        arreglo = new double[array.length];
        for (int i=0;i<arreglo.length ;i++ ) {
            arreglo[i]=Double.parseDouble(array[i]);
        }
    	return arreglo;
    }
    /**
    *Metodo que cierra el sistema guardando datos en el archivo de texto 
    */
    public void cerrarSistema(){
        BufferedWriter escritor = null;
        try{
            showMessageDialog(null,"Cerrando sistema, actualizando datos a Disco Duro","Actualizando Datos", INFORMATION_MESSAGE);
            FileOutputStream fileSalida = new FileOutputStream("conectados.txt",false);
            OutputStreamWriter puente = new OutputStreamWriter(fileSalida, "UTF-8");
            escritor = new BufferedWriter(puente);        
            
        }catch (IOException ex){
            showMessageDialog(null,"Ha ocurrido un error, no se pudo actualizar los datos correctamente" +
			      "\nAsegurate de que los datos esten correctamente puestos","Error Grave",ERROR_MESSAGE);
        } finally {
            if(escritor != null){
                try{
                    escritor.close();
                }catch (IOException ex){
                    showMessageDialog(null, "Ha ocurrido un error, no se pueden actualizar los empleados correctamente" +
				      "\nAsegurate que el archivo existe","Error Grave", ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        LeerDeArchivo leer = new LeerDeArchivo();
        double [] a = leer.Cargar("Archivo.txt");
        for (int i=0;i<a.length;i++){
            System.out.print(a[i]+", ");
        }   

    }

}