        /**
        *Carga linea a linea el texto y lo almacena en el atributo Lista.
        **/
        public void cargarDeConsola(){
            String linea;
            try{
                BufferedReader lineaLeida = new BufferedReader(new InputStreamReader(System.in));
                while((linea=lineaLeida.readLine())!=null){
                    if (linea.charAt(0).equals("#")) {
                        comentarios.agrega(linea);
                    }
                    else{
                        lista.agrega(linea);
                    }
                 }
                 lineaLeida.close();
            }
            catch(IOException ex){
                System.err.println("No logramos leer los datos.");
                lista=null;
                return;
            }
        }