package mx.unam.ciencias.edd.proyecto3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import mx.unam.ciencias.edd.proyecto3.Lector;
import mx.unam.ciencias.edd.Lista;


/**
*Clase que contiene el metodo Main del proyecto3 
*
*/
public class Proyecto3 {
            /**
            *Método main que ejectuta el proyecto3
            *
            */
	public static void main(String[] args) {
                        if (args.length == 0)
                            System.err.println("Por favor introduce el nombre de los archivos de texto y el directorio precedido de la bandera -o");
                        if (args.length == 1) 
                            System.err.println("Por favor introduce el nombre de los archivos de texto y el directorio precedido de la bandera -o");
                        Lista<String> textos = new Lista<String>();
                        String directorio = "";
                        for (int i =0;i<args.length;i++ ) {
                                if (args[i].equals("-o") || args[i].equals("-O") ){
                                    directorio = args[i+1];
                                    i+=2;
                                }
                                if (i<args.length)
                                  textos.agrega(args[i]);
                            }
		File dir = new File(directorio); 
		dir.mkdir();
                            Lista<ReporteTexto> listaReportes= new Lista<ReporteTexto>();
                            for (String archivo : textos ) {
                                ReporteTexto reporte = new ReporteTexto(archivo);
                                listaReportes.agrega(reporte);
                                String r = GeneradorHTML.genera(reporte);
                                String lr = GeneradorHTML.generaIndex(listaReportes);
                                Lector l = new Lector();
                                try {
                                    String nombre = archivo.split("\\.")[0];

                                    l.setSalida(new FileOutputStream(
                                    new File(directorio + "/" + nombre + ".html")));
                                    l.escribeLinea(r);

                                     l.setSalida(new FileOutputStream(
                                    new File(directorio + "/" + "index" + ".html")));
                                     l.escribeLinea(lr);

                                } catch (FileNotFoundException fnfe) {
                                        System.out.println("Algún archivo no existe.");
                                } catch (IOException ioe) {
                                    System.out.println("Ocurrió un error de entrada y/o salida.");
                                }
                          }
                           	
	}
}	