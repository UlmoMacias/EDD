package mx.unam.ciencias.edd;

/**
 * Clase para pilas genéricas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la pila.
     * @return una representación en cadena de la pila.
     */
    @Override public String toString() {
        return "hola";
    }


    /**
     * Agrega un elemento al tope de la pila.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
    	if (elemento == null) {
    		throw new IllegalArgumentException("");
    	}
    	Nodo a = new Nodo(elemento);
    	/*
        cabeza.siguente = cabeza;
		cabeza = a;
		cabeza.siguente.anterior = a;*/
		if ( esVacia() ){
            this.cabeza=rabo=a;
            return ;
		}
		a.siguiente = cabeza;
		cabeza = a;
    }
}
