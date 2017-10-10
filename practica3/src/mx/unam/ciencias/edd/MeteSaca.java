package mx.unam.ciencias.edd;
import java.util.NoSuchElementException;

/**
 * Clase abtracta para estructuras lineales restringidas a operaciones
 * mete/saca/mira.
 */
public abstract class MeteSaca<T> {

    /**
     * Clase Nodo protegida para uso interno de sus clases herederas.
     */
    protected class Nodo {
        /** El elemento del nodo. */
        public T elemento;
        /** El siguiente nodo. */
        public Nodo siguiente;

        /**
         * Construye un nodo con un elemento.
         * @param elemento el elemento del nodo.
         */
        public Nodo(T elemento) {
            this.elemento=elemento;
        }
    }

    /** La cabeza de la estructura. */
    protected Nodo cabeza;
    /** El rabo de la estructura. */
    protected Nodo rabo;

    /**
     * Agrega un elemento al extremo de la estructura.
     * @param elemento el elemento a agregar.
     */
    public abstract void mete(T elemento);

    /**
     * Elimina el elemento en un extremo de la estructura y lo regresa. (CABEZA)
     * @return el elemento en un extremo de la estructura.
     * @throws NoSuchElementException si la estructura está vacía.
     */
    public T saca() {
        if (cabeza== null) {
        	throw new NoSuchElementException("");
        }
		T aux = cabeza.elemento;
        if(cabeza.siguiente == null)
			cabeza = rabo = null;
		else{
			cabeza = cabeza.siguiente;
        }
        return aux;
    }

    /**
     * Nos permite ver el elemento en un extremo de la estructura, sin sacarlo
     * de la misma.
     * @return el elemento en un extremo de la estructura.
     * @throws NoSuchElementException si la estructura está vacía.
     */
    public T mira() {
        if (esVacia()) {
            throw new NoSuchElementException ();
        }
        return cabeza.elemento;
    }

    /**
     * Nos dice si la estructura está vacía.
     * @return <tt>true</tt> si la estructura no tiene elementos,
     *         <tt>false</tt> en otro caso.
     */
    public boolean esVacia() {
        return cabeza == null;
    }

    private int getTamanio(){
    	if (cabeza == null) {
    		return 0;
    	}
        int numeroDeNodos=0;
        if (cabeza == rabo) {
        	return numeroDeNodos + 1;
        }
    	Nodo n = cabeza;
        while (n.siguiente != rabo) {
            n= n.siguiente;
            numeroDeNodos++;
        }
        return numeroDeNodos + 1;
    }

    /**
     * Compara la estructura con un objeto.
     * @param o el objeto con el que queremos comparar la estructura.
     * @return <code>true</code> si el objeto recibido es una instancia de la
     *         misma clase que la estructura, y sus elementos son iguales en el
     *         mismo orden; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") MeteSaca<T> m = (MeteSaca<T>)o;
        if (esVacia()&& m.esVacia())
            return true;
        int i = 0;
        int j=0;
        Nodo k = m.cabeza;
        Nodo l = cabeza;
        while(k!=null){
            k = k.siguiente;
            i++;
        }
        while(l!=null){
            l= l.siguiente;
            j++;
        }
        if (j != i)
            return false;
        Nodo h = m.cabeza;
        Nodo f = cabeza;
        while(h!=null && f!=null){
            if (!h.elemento.equals(f.elemento))
                return false;
            h = h.siguiente;
            f = f.siguiente;
        }
        return true;
    }
}
