package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Diccionario;

/**
* Clase para reportes de texto, sirve para poder manejar los reportes como una
* instancia separada de cada archivo.
*/
public class ReporteTexto {

	/* Nombre del archivo. */
	private String archivo;

	/* Título. */
	private String titulo;

	/* Texto. */
	private String texto;

	/* Coincidencias de palabras. */
	private Lista<Palabra> coincidencias;

	/* Total de palabras. */
	private int total;

	/* Constructor único. (Que en realidad genera el reporte) */
	public ReporteTexto(String archivo) {
		this.archivo = archivo;
		String[] a = archivo.split(".");
		this.titulo = archivo;	
		ContadorPalabras cp = new ContadorPalabras(archivo);
		this.coincidencias = cp.cuenta();
		this.texto = cp.getTexto();
		this.total = cp.getTotal(); 
	}

	/**
	* Regresa el nombre del archivo.
	* @return el nombre del archivo.
	*/
	public String getArchivo() {
		return this.archivo;
	}

	/**
	* Regresa el título del texto.
	* @return el título del texto.
	*/
	public String getTitulo() {
		return this.titulo;
	}

	/**
	* Regresa el texto original.
	* @return el texto original.
	*/
	public String getTexto() {
		return this.texto;
	}

	/**
	* Regresa la lista de palabras que coinciden.
	* @return las coincidencias.
	*/
	public Lista<Palabra> getCoincidencias() {
		return this.coincidencias;
	}

	/**
	*Regresa las palabras de tamaño mayor a 5.
	*@return Lista de String con las palabras de tamaño mayor a 5.
	*/
	public Lista<String> palabrasGrande(){
		Lista<String> nueva = new Lista<String>();
		for (Palabra palabra: this.getCoincidencias()) {
			if ((palabra.getPalabra()).length() >= 5) {
				nueva.agrega(palabra.getPalabra());
			}
		}
		return nueva;
	}

	/**
	*Metodo que verifica si dos reportes de texto son vecinos en una grafica.
	*@return <tt>true</tt> si dos reportes de texto son vecinos.
     	*         <tt>false</tt> en otro caso
	*/
	public boolean esVecino(ReporteTexto rt){
		int contador =0;
		Lista<String> aux = this.palabrasGrande();
		Lista<String> aux2 = rt.palabrasGrande();
		for (String palabra : aux ) {
			for (String palabra2 : aux2) {
				if(palabra.equals(palabra2)){
					contador ++;
					break ;
				}
			}
			if(contador >=10)
				return true;
		}
		return false;
	}

	/**
	* Regresa el total de palabras.
	* @return el total de palabras.
	*/
	public int getTotal() {
		return this.total;
	}

	/**
	* Regresa la lista de palabras que más se repiten.
	* @return una lista con las palabras que más se repiten.
	*/
	public Lista<Palabra> getTop() {
		Diccionario<Integer, Integer> valores = new Diccionario<>();
		int v = 0;
		int contador = 1;
		for (Palabra p : coincidencias) {
			if (!valores.contiene(p.getOcurrencias())) {
				v += p.getOcurrencias();
				valores.agrega(p.getOcurrencias(), p.getOcurrencias());
				contador++;
			}
		}
		int limite = v / contador;
		Lista<Palabra> top = new Lista<>();
		for (Palabra p : coincidencias) {
			if (p.getOcurrencias() >= limite)
				top.agrega(p);
		}
		return top;
	}
}