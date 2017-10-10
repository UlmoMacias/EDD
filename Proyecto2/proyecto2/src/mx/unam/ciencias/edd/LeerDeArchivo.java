package mx.unam.ciencias.edd.proyecto2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
* <p>Clase que permite leer un archivo desde la entrada estandar o mediante un archivo.</p>
*
* <p>La clase LeerDeArchivo utiliza las librerias de java.io para leer archivos.</p>
* 
*/
public class LeerDeArchivo{
/*La lista que guarda las lineas de texto*/
public Lista<String> lista;

/*La lista que guarda los datos con separacion*/
public Lista<Integer> listaMod= new Lista<Integer>();

/*La lista que guarda los comentarios*/
public Lista<String> comentarios = new Lista<String>();

      /*constructor de la clase que instancia la Lista de String */
      public LeerDeArchivo(){
            lista = new Lista<String>();
      }

         /**
         * Carga linea a linea el texto desde un archivo y lo almacena en el atributo de clase lista.
         *@param String el nombre del archivo a leer.
         *@throw FileNotFoundException si no encuentra el archivo
         *@throw IOException si no logro cargar los datos del sistema.
         **/
              public void leerArchivo(String archivo)throws FileNotFoundException, IOException {
                    String lineas; //union de lineas
                    try{
                        FileReader linea = new FileReader(archivo);
                        BufferedReader linea2 = new BufferedReader(linea);
                        while((lineas=linea2.readLine())!=null){
                              //System.out.println(lineas.charAt(0)=='#');
                            if (lineas.charAt(0)=='#') {
                              comentarios.agrega(lineas);
                            }
                            else{
                                lista.agrega(lineas);
                            }
                        }
                        linea2.close();
                    }
                    catch(FileNotFoundException ex){
                        System.err.println(archivo+" no es un arcivo existente, por favor verifique el nombre del archivo");
                        lista=null;
                        return;
                    }
                    catch(IOException ex){
                        System.err.println("Ha ocurrido un error, no se han podidocargar los datos" +"\n" +
                            "Asegurate que el archivo exista y se encuentre en el directorio");
                        lista=null;
                        return;
                    }
                }

                /**
                *Metodo que regresa el tipo de la estructura a dibujar.
                * @throw FileNotFounException
                * @throw IOException
                * @return una string con el nombre de la estructura.
                **/
                public String getTipoEstructura()throws FileNotFoundException, IOException{
                        String a = lista.eliminaPrimero();
                        int aux = a.indexOf(' ');
                        String b = a.substring(0,aux);
                        lista.agregaInicio(a.substring(aux+1,a.length()));
                        return b;
                }

                /**
                *Metodo que regresa una lista de los enteros a agregar en la estructura.
                * @return una Lista de enteros.
                **/
                public Lista<Integer> getListaMod(){
                    return listaMod;
                }

                /**
                *Metodo que separa los numeros de las lineas leidas y los agrega a una lista de enteros.
                **/
                public void getNumeros() {
                    String[] palabrasSeparadas;
                        for(String s: lista){
                              palabrasSeparadas = s.split(" ");
                              for(int i=0; i<palabrasSeparadas.length;i++){
                                    if(palabrasSeparadas[i]!= null){
                                          listaMod.agrega(Integer.parseInt(palabrasSeparadas[i]));
                                    }
                                    else if (i!=0){
                                        return;
                                    }
                              }
                        }
                  }
}
