	package mx.unam.ciencias.edd.proyecto3;

	import mx.unam.ciencias.edd.Lista;
	import mx.unam.ciencias.edd.ArbolBinario;
	import mx.unam.ciencias.edd.Color;
	import mx.unam.ciencias.edd.VerticeArbolBinario;
	import mx.unam.ciencias.edd.ArbolRojinegro;
	import mx.unam.ciencias.edd.ArbolAVL;
	import mx.unam.ciencias.edd.Grafica;
	import mx.unam.ciencias.edd.VerticeGrafica;
	import java.lang.StringBuffer;
	import java.lang.Math;

	/**
	* Clase pública Graficador que nos provee métodos para dibujar las estructuras 
	* de datos en código SVG.
	* Posee cadenas constantes para cada campo del archivo SVG.
	*/
	public class Graficador {

		/* La cadena del código SVG. */
		private String dibujo;

		/* Abre etiqueta svg. */
		private static final String INICIO_SVG = "<?xml version=\'1.0\' encoding=\'"+	"UTF-8\' ?> \n\t<svg  width='%d' height='%d'>\n";

		/* Abre etiqueta svg de gráfica. */
		private static final String INICIO_SVG_G = "<?xml version=\'1.0\' encoding=\'"+"UTF-8\' ?> \n\t<svg width='%f' height='%f'>\n";

		/* Cierra etiqueta svg. */
		private static final String CIERRA_SVG = "</svg>\n";

		/* Abre etiqueta g. */
		private static final String INICIO_G = "\t<g>\n";

		/* Cierra etiqueta g. */
		private static final String CIERRA_G = "\t</g>\n";

		/* Etiqueta para rectángulos. */
		private static final String RECT = "\t<rect x='%d' y='%d' width='%d' heigh"+ "t='%d' stroke='black' fill='white'/>\n";

	    	/* Etiqueta para texto. */
	    	private static final String TEXT = "\t<text font-family='sans"+"-serif' font-size='12' x='%d' y='%d' t"+"ext-anchor='middle' fill='%s'>%s</text"+">\n";

	    	 /* Etiqueta para texto en ǵráficas. */
	    	private static final String TEXT_G = "\t<text font-family='sans"+"-serif' font-size='12' x='%f' y='%f' t"+"ext-anchor='middle' fill='%s'>%s</text"+">\n";

	    	/* Etiqueta para conectar las estructuras lineales. */
	    	private static final String CONECTOR_LISTA = "\t<text fill='black' font-fa"+"mily='sans-serif' font-size="+"'%d' x='%d' y='%d' text-anch"+"or='middle'>%s</text>\n";

		/* Etiqueta para círculo. */
		private static final String CIRCLE = "\t<circle cx='%d' cy='%d' r='20' "+"stroke='%s' fill='%s' /"+
											 ">\n";
		/* Etiqueta para círculo de gráfica. */
		private static final String CIRCLE_G = "\t<circle cx='%f' cy='%f' r='15' "+"stroke='%s' fill='%s' />\n";

		/* Etiqueta para línea. */
		private static final String LINE ="\t<line x1='%d' y1='%d' x2='%d' y2='%d'"+" stroke='black'/>\n";

		/* Etiqueta para línea de gráfica. */
		private static final String L_G = "\t<line x1='%f' y1='%f' x2='%f' y2='%f'"+" stroke='black' stroke-width='1'/>\n";

		/* Etiqueta para círculo de gráficas de pastel.*/
		private static final String CIRCLE_P = "\t<circle cx='%f' cy='%f' r='60' s"+"troke='%s' fill='%s' />\n";

	    	/* Etiqueta para rectángulo de gráfica de barras. */
	    	private static final String TEXT_B = "\t<text font-family='sans"+"-serif' font-size='12' x='%d' y='%d' t"+"ext-anchor='start' fill='%s'>%s</text"+">\n";

	    	/* Lista auxiiliar de líneas. */
	    	private Lista<String> lineas;
	    	/* Atributos para la gŕafica.*/
	    	private double x;
	    	/* Atributos para la gráfica. */
	    	private double y;
	    	/* Atributo para circulos con radio personalizado. */
	    	private String c;

	    	/* Atributos para la gŕafica.*/
	    	private double x1;
	    	/* Atributos para la gŕafica.*/
	    	private double y1;
	    
	    	/**
	    	* Dibuja una gráfica de cualquier tipo.
	    	* @param g la gráfica a dibujar.
	    	* @return el código SVG para la gráfica dada.
	    	*/
	    	public String dibujaGrafica(Grafica<?> g) {
	    		final double angulo = 360 / g.getElementos();
	    		double radio = 40 * g.getElementos();
	    		double centro = radio + 25;
	    		dibujo = String.format(INICIO_SVG_G,(radio*2)+40,(radio*2)+30);
	    		dibujo += INICIO_G;
	    		Lista<Object> lista = new Lista<>();
	    		final double[] ang = {angulo};
	    		g.paraCadaVertice((v) -> lista.agrega(v.get()));
	    		g.paraCadaVertice((v) ->  {
	    		/* Calcula las coordenadas de v. */
				x = calculaCoordenadaX(ang[0],radio);
				y = calculaCoordenadaY(ang[0],radio);

				/* Verifica los vecinos de v. */
				for(VerticeGrafica<?> vecino : v.vecinos()){
					if(vecino.getColor() != Color.ROJO){
						int i = lista.indiceDe(vecino.get());
						double auxX = calculaCoordenadaX((i+1)*angulo,radio);
						double auxY = calculaCoordenadaY((i+1)*angulo,radio);
						/* Conecta las cordenadas del vertice v con las del vecino.*/
						conectar(centro+x,centro-y,centro+auxX, centro-auxY);
					}
				}
				if(v.getGrado() != 0)
					dibujaVerticeGrafica(v,centro+x,centro-y,"black");
				else dibujaVerticeGrafica(v,centro+x,centro-y,"red");
				double auxAng = ang[0];
				auxAng += angulo;
				ang[0] = auxAng;
		    	});
	    		dibujo += CIERRA_G;
	    		dibujo += CIERRA_SVG;
	    		return dibujo;
	    	}

	   	 /**
	    	* Método auxiliar que calcula las coordenadas de los vértices basándose en 
	    	* el ángulo que reciba. Se necesita mover en el eje X usando coseno.
	    	* @return la coordenada X.
		*/
		private double calculaCoordenadaX(double angulo, double radio){
			return Math.cos(Math.toRadians(angulo)) * radio ;
		}

		/**
		* Método auxiliar que calcula las coordenadas de los vértices basándose en 
		* el ángulo que reciba. Se necesita mover en el eje Y usando seno.
		* @return la coordenada Y.
		*/
		private double calculaCoordenadaY(double angulo, double radio){
			return Math.sin(Math.toRadians(angulo)) * radio;
		}

		/**
		* Método auxiliar para conectar dos vértices en una gráfica.
		*/
		private void conectar(double x1, double y1, double x2, double y2){
		  	dibujo += String.format(L_G,x1,y1,x2,y2);
		}

		/**
		* Método auxiliar para dibujar un vértice de gráfica.
		*/
		private void dibujaVerticeGrafica(VerticeGrafica<?> v, double x, double y,String color){
			dibujo += String.format(CIRCLE_G,x,y,"black",color);
			dibujo += String.format(TEXT_G,x,y+5,"GRAY",v.get().toString());
		}

		/**
		* Dibuja un árbol binario de cualquier tipo.
		* return el código SVG del árbol binario.
		*/
		public String dibujaArbolBinario(ArbolBinario<?> arbol) {
			String cadena = "";
			dibujo = "";
			lineas = new Lista<String>();
			int profundidad = arbol.getElementos()* 23;
			int anchura = arbol.getElementos() * 80;
			cadena += String.format(INICIO_SVG,anchura,profundidad);
			cadena += INICIO_G;
			String tipo;
			if (arbol instanceof ArbolRojinegro<?>)
				// Entonces el arbol es rojinegro. 
				tipo = "ROJINEGRO";
			else if (arbol instanceof ArbolAVL<?>)
				// Entonces es AVL..
				tipo = "AVL";
				// Da igual el tipo, va a dibujarlo sin color y sin alturas. 
				else tipo = "F";
			VerticeArbolBinario<?> raiz = arbol.raiz();
			dibujaArbolBinario(raiz,0,0,anchura/2,tipo);
			for (String linea : lineas )
				cadena += linea;
			cadena += dibujo;
			cadena += CIERRA_G;
			cadena += CIERRA_SVG;
			return cadena;
		}

		/**
		* Método auxiliar para dibujar árboles binarios.
		*/
	    	private void dibujaArbolBinario(VerticeArbolBinario<?> raiz, int ini, int y,int m, String tipo) {
	        	if (tipo.equals("ROJINEGRO"))
				dibujo += dibujaVerticeRojinegro(raiz,m,y+20);
			else if (tipo.equals("AVL"))
				dibujo += dibujaVerticeAVL(raiz,m,y+20);
			else dibujo += dibujaVertice(raiz,m,y+20);

			if (raiz.hayIzquierdo()) {
				int mi = (m - ini) / 2; 
				lineas.agrega(dibujaLinea(m,y+20,m-mi,y+50));
				dibujaArbolBinario(raiz.izquierdo(),ini,y+40,m-mi,tipo);
			}
			if (raiz.hayDerecho()) {
				int md = (m - ini) / 2;
				lineas.agrega(dibujaLinea(m,y+20,m+md,y+50));
				dibujaArbolBinario(raiz.derecho(),m,y+40,m+md,tipo);
			}
		}

		/**
		* Método auxiliar que dibuja un vértice rojinegro.
		* @return el código SVG para el vértice rojinegro.
		*/
		private String dibujaVerticeRojinegro(VerticeArbolBinario<?> v, int x, int y) {
			String vertice = v.toString();
			String color;
			color = vertice.contains("R") ? "red" : "black";
			vertice = String.format(CIRCLE,x,y,"black",color);
			vertice += String.format(TEXT,x,y+3,"white",v.get().toString());
			return vertice;
		}

		/**
		* Método auxiliar que dibuja un vértice normal.
		* @return el código SVG para el vértice.
		*/
		private String dibujaVertice(VerticeArbolBinario<?> v, int x, int y) {
			String vertice = String.format(CIRCLE,x,y,"black","white");
			vertice += String.format(TEXT,x,y+3,"black",v.get().toString());
			return vertice;
		}
		
		/**
		* Método auxiliar que dibuja un vértice AVL.
		* @return el código SVG para el vértice AVL.
		*/
		private String dibujaVerticeAVL(VerticeArbolBinario<?> v, int x, int y) {
			String vertice = v.toString();
			String[] aVertice = vertice.split("\\s+");
			vertice = String.format(CIRCLE,x,y,"black","white");
			vertice += String.format(TEXT,x,y+3,"black",v.get().toString());
			vertice += String.format(TEXT,x+30,y+2,"#0000ff",aVertice[1]);
			return vertice;
		}


		/**
		* Método auxiliar que dibuja una rama.
		* @return el código SVG para una rama.
		*/
		private String dibujaLinea(int x1, int y1, int x2, int y2) {
			return String.format(LINE,x1,y1,x2,y2);
		}

		/**
     		*Método para sacar las coordenadas dado un porcentaje de la grafica
     		*/
    		private void coorPedazo(double porcentaje){
        			double rad = rad(angulo(porcentaje));
			double x2 = 130+100* Math.cos(rad);
        			double y2 = 120+100* Math.sin(rad);
        			x1=x2;
        			y1 = y2;
		}

    		/**
     		*Método para obtener el angulo del ciculo dado un porcentaje
     		*@retunr angulo double angulo en grados de la grafica.
     		*/
    		private double angulo(double porcentaje){
        			return (360*porcentaje)/100;
    		}

    		/**	
     		*Método para transformar los grados del angulo a Radianes.
     		*@param angulo double angulo en grados.
     		*@return angulo double en radianes.
     		*/
    		private double rad(double angulo){
        			return (Math.PI /180)*angulo;
    		}

    		/**
     		*Método para definir los un pezado de la grafica
     		*@param l1 double dado por la coordenada x de L
     		*@param l2 double dado por la coordenada y de L
     		*@param z1 double dado por la coordenada x de Z
     		*@param z2 double dado por la coordenada y de Z
     		*@param color String color del pezado de la grafica
     		*@return pedazo grafica String codigo svg del pedazo de grafica.
     		*/
    		private String pedazoGrafica(double l1, double l2, double z1, double z2, String color){
      			return  "<path d=\"M 130, 120 L "+l1+","+l2+" A 100,100 0 0, 1 "+z1+","+z2+" z\" fill=\""+color+"\" stroke=\"#fff\" stroke-width=\"2\"></path>\n";
    		}

    		/**
     		*Método para calcular un porcentaje dado el numero de repeticiones de la palabra.
     		*@param n int numero de repeticiones de la palabra
     		*@return porcentaje double porcentaje dado el numero de repeticiones de una palabra.
     		*/
    		private double porcentaje(int elementos,int n){
        			return (n*100)/elementos;
    		}

    		public String pastel(Lista<Palabra> palabras){
    			return graficaPastel(palabras,palabras.getElementos());
    		}
		/**
		* Dibuja una gráfica de pastel.
		* @return la gráfica de pastel en SVG.
		*/
		private String graficaPastel(Lista<Palabra> a, int n){
			String svg = "";
			Lista<Palabra> b = a;
        			String[] colores = {"#0140CA", "#7FFF00", "#16A6FE", "#DD1812"};
        			int veces = a.getElementos()-5;
        			double aux =0; 
        			x1 = 230;
        			y1 = 120;
        			Palabra auxiliar2 =null;
        			double porcentajeUsado=0; 
        			svg+="<center>\n";
			svg+="<svg width=\"260\" height=\"250\">\n";
        			svg+="<g>\n";
        			svg+="<CIRCLE cx=\"130\" cy=\"120\" r=\"100\" style=\"fill:#90EE90;\"/>\n";
        			switch(veces){
            			case -4:
            				break;
            			case -3:
                			auxiliar2 = a.eliminaPrimero();
                			aux = porcentaje(b.getElementos(),auxiliar2.getOcurrencias());
                			if(aux > 50){
                    				auxiliar2 = a.eliminaPrimero();
                    				aux = porcentaje(b.getElementos(),auxiliar2.getOcurrencias());
                			}
                			porcentajeUsado+=aux;
                			System.out.println(aux);
                			coorPedazo(porcentajeUsado);
                			svg+=pedazoGrafica(x1,y1,x1,x1,colores[0]);
            				break;
            			case -2:
            				for (int i =0;i<2 ; i++) {
                				auxiliar2 = a.eliminaPrimero();
                				aux = porcentaje(b.getElementos(),auxiliar2.getOcurrencias());
                				if(aux > 50){
                    					auxiliar2 = a.eliminaPrimero();
                    					aux = porcentaje(b.getElementos(),auxiliar2.getOcurrencias());
                				}
                				porcentajeUsado+=aux;
                				coorPedazo(porcentajeUsado);
                				svg+=pedazoGrafica(x1,y1,x1,x1,colores[i]);                
            				}
            				break;
            			case -1:
            				for (int i=0;i<3 ;i++ ) {
                				auxiliar2 = a.eliminaPrimero();
                				aux = porcentaje(b.getElementos(),auxiliar2.getOcurrencias());
                				if(aux > 50){
                    					auxiliar2 = a.eliminaPrimero();
                    					aux = porcentaje(b.getElementos(),auxiliar2.getOcurrencias());
                				}
                				porcentajeUsado+=aux;
                				x1 = x1;
                				y1 = x1;
                				coorPedazo(porcentajeUsado);
                				svg+=pedazoGrafica(x1,y1,x1,x1,colores[i]);
            				}
            				break;
            			default:
                			for(int i=0;i<4;i++){
                				auxiliar2 = a.eliminaPrimero();
                				aux = porcentaje(b.getElementos(),auxiliar2.getOcurrencias());
                				if(aux > 50){
                    					auxiliar2 = a.eliminaPrimero();
                    					aux = porcentaje(b.getElementos(),auxiliar2.getOcurrencias());
                				}
                				porcentajeUsado+=aux;
                				x1 = x1;
                				y1 = y1;
                				coorPedazo(porcentajeUsado);
                				svg+=pedazoGrafica(x1,y1,x1,y1,colores[i]);
                			}
                			break;
                		}
        			svg+="</g>\n";
        			svg+="</svg>\n";
        			svg+="</center>\n";
        			return svg ;
    		}   

		/**
		* Dibuja una gráfica de barras.
		* @return la gráfica de barras en SVG.
		*/
		public String barras(Lista<Palabra> palabras) {
			StringBuffer sb = new StringBuffer();
			int h = ((40 * palabras.getElementos()) 
					+ (2 * (palabras.getElementos())) + 10);
			int w = 1005;
			sb.append(String.format(INICIO_SVG,w,h));
			StringBuffer barras = new StringBuffer();
			int x = 5;
			int y = 0;
			for (Palabra p : palabras) {
				int width = (int)p.getPorcentaje() * 10 ;
				barras.append(String.format(RECT, x, y, width, 40));
				String cadenaPalabra = String.format("%s %2.1f%%",p.getPalabra(),p.getPorcentaje());
				barras.append(String.format(TEXT_B,width + 10,(y + 42) - 22 ,"black",cadenaPalabra));
				y += 42;
			}
			sb.append(barras.toString());
			for (int i = 0; i < w; i += 20) {
				if (i > 80)
					sb.append(String.format(TEXT_B,((i * 10) - 15),h - 1,"black",new Integer(i).toString()));
				else sb.append(String.format(TEXT_B,(i * 10),h - 1,"black",new Integer(i).toString()));
			}
			sb.append(String.format(LINE,0, h - 1, w, h - 1));
			sb.append("\t" + CIERRA_SVG);
			return sb.toString();
		}
	}