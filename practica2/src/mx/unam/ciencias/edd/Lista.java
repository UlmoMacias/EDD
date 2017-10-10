package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;;
import java.util.Comparator;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas implementan la interfaz {@link Iterable}, y por lo tanto se
 * pueden recorrer usando la estructura de control <em>for-each</em>. Las listas
 * no aceptan a <code>null</code> como elemento.</p>
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase Nodo privada para uso interno de la clase Lista. */
    private class Nodo {
        /* El elemento del nodo. */
        public T elemento;
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nodo con un elemento. */
        public Nodo(T elemento) {
            this.elemento=elemento;
        }
    }

    /* Clase Iterador privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nuevo iterador. */
        public Iterador() {
            siguiente = cabeza;
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            if ( !hasNext() )
                throw new NoSuchElementException();
            T aux = this.siguiente.elemento;
            anterior = siguiente;
            siguiente = siguiente.siguiente;
            return aux;
        }
        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            return anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            if ( !hasPrevious() )
                throw new NoSuchElementException();
            T aux = anterior.elemento;
            siguiente = anterior;
            anterior = anterior.anterior;
            return aux;
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            anterior = null;
            siguiente=cabeza;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            this.anterior= rabo;
            this.siguiente = null;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

//--------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------
    private Lista<Lista<T>> dividirLista (Lista<T> t){
        int n = t.longitud/2;
        Lista<T> t1 = new Lista<T>();
        Lista<T> t2 = new Lista<T>();
        for (int i =0;i<n ;i++) {
            t1.agrega(t.eliminaPrimero());
        }
        while (!(t.esVacio())) {
            t2.agrega(t.eliminaPrimero());
        }
        Lista<Lista<T>> tu = new Lista<Lista<T>>();
        tu.agrega(t1);
        tu.agrega(t2);
        return  tu;
    }
    private Lista<T> mezcla(Lista<T> t1,Lista<T> t2, Comparator<T> comp){
        Lista<T> t = new Lista<T>();
        Nodo a = t1.cabeza;
        Nodo b = t2.cabeza;
        while (a!=null && b != null) {
            if (comp.compare(a.elemento,b.elemento)<0) {
                t.agrega(a.elemento);
                a=a.siguiente;
            }else {
                t.agrega(b.elemento);
                b= b.siguiente;
            }
        }
        if (a != null) {
            t.rabo.siguiente = a;
            a.anterior = t.rabo;
            rabo = t1.rabo;
            return t;
        }
        if (b != null) {        
            t.rabo.siguiente = b;
            b.anterior = t.rabo;
            rabo = t1.rabo;
            return t;
        }
        return null;
    } 
    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
        Lista<Lista<T>> t = dividirLista(this);
        Lista<T> izq = t.eliminaPrimero();
        Lista<T> der = t.eliminaPrimero();
        if (this.longitud<2)
            return this;
        Lista<T> t3 =izq.mergeSort(comparador);
        Lista<T> t4 =der.mergeSort(comparador);
        return mezcla(t3,t4,comparador);
    }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>>
    Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }


    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <tt>true</tt> si elemento está contenido en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
        if(cabeza.elemento == elemento){
            return true;
        }
        Nodo n = cabeza;
        while(n.siguiente != null) {
            if (n.elemento == elemento)
                return true;
            n = n.siguiente;
        }          
        return false;
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <tt>true</tt> si el elemento está contenido en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public static <T extends Comparable<T>>
    boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }
//----------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------
    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        return longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
        return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacio() {
        return cabeza == null;
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
            if (elemento == null)
                throw new IllegalArgumentException("elemento es null");
			agregaFinal(elemento);
    }
    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        if(elemento==null)
            throw new IllegalArgumentException("Elemento nulo");
        Nodo a = new Nodo(elemento);
        if( cabeza == null )
            this.cabeza=rabo=a;
        else {
            rabo.siguiente= a;
            a.anterior = rabo;
            rabo = a;
        }
        longitud++;
        
    }
    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
    	if (elemento == null) {
    		throw new IllegalArgumentException();
    	}
        if ( esVacio() ){
            agregaFinal(elemento);
            return ;
        }
        Nodo nuevo = new Nodo (elemento);
		cabeza.anterior = nuevo;
		nuevo.siguiente = cabeza;
		cabeza = nuevo;
		longitud ++;
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor que cero, el elemento se agrega al inicio de la
     * lista. Si el índice es mayor o igual que el número de elementos en la
     * lista, el elemento se agrega al fina de la misma. En otro caso, después
     * de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio, y si es mayor o igual que el número
     *          de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
		if(elemento==null){
			throw new IllegalArgumentException("elemento es null");
		}
        if ( i<=0){
            agregaInicio(elemento);
            return ;
        }
        if (i>=longitud) {
            agregaFinal(elemento);
            return ;
        }
        Nodo nuevo = new Nodo (elemento);
        Nodo aux = cabeza;
        for (int j=0;j<i ;j++)
            aux = aux.siguiente;
        nuevo.siguiente = aux;
        nuevo.anterior = aux.anterior;
        aux.anterior.siguiente = nuevo;
        aux.anterior=nuevo;
        longitud ++;            
    }
    
    private Nodo buscaNodo(T elemento){
        Nodo n = cabeza;
        while (n!=null) {
            if (elemento.equals(n.elemento)) {
                return n;
            }
            n=n.siguiente;
        }
        return null;
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        if (elemento==null)
            return ;
        Nodo n = buscaNodo(elemento);
        if (n==null) {
            return ;
        }
        if (longitud==1) {
            this.limpia();
            return;
        }
        if (n==cabeza){
            this.eliminaPrimero();
            return ;
        }
        if (n==rabo) {
            this.eliminaUltimo();
            return ; 
        }
        n.siguiente.anterior=n.anterior;
        n.anterior.siguiente=n.siguiente;
        longitud--;
        return;
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        if (esVacio())
			throw new NoSuchElementException("La lista es vacía");
		T aux = cabeza.elemento;
        if(longitud ==1)
			cabeza = rabo = null;
		else{
			cabeza = cabeza.siguiente;
			cabeza.anterior = null;
        }
        longitud --;
        return aux;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
            if (esVacio()) {
                throw new NoSuchElementException("La lista esssss vacia") ;
            }
            if (longitud == 1) 
                return eliminaPrimero();
            else {
                T aux = rabo.elemento;
                rabo = rabo.anterior;
                rabo.siguiente = null;
                longitud--;
                return aux;
			}
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <tt>true</tt> si <tt>elemento</tt> está en la lista,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        return buscaNodo(elemento) != null;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        Lista<T> n = new Lista<T>();
        Nodo nodo = cabeza;
        while (nodo != null) {
            n.agregaInicio(nodo.elemento);
            nodo= nodo.siguiente;
        }
        return n;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copia de la lista.
     */
    public Lista<T> copia() {
        Lista<T> n = new Lista<T>();
        Nodo nodo = cabeza;
        while (nodo != null) {
            n.agregaFinal(nodo.elemento);
            nodo = nodo.siguiente;
        }
        return n;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
        cabeza=rabo=null;
        longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
            if (esVacio())
                throw new NoSuchElementException("La lista es vacia");
            else
                return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        if (esVacio())
                throw new NoSuchElementException("La lista es vacia");
        else
            return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        if (i<0 || i>=longitud)
            throw new ExcepcionIndiceInvalido ("Indice invalido");
        if(cabeza == null)
            return null;
        Nodo n = cabeza;
        for (int j=0;j < i ;j++ ) {
            n= n.siguiente;
        }
        return n.elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si
     *         el elemento no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        int contador = 0;
        Nodo e =cabeza;
        while(e!=null){
            if (e.elemento.equals(elemento))
                return contador;
                e = e.siguiente;
                contador++;
        }
        return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        if(esVacio())
            return "[]";
        if(cabeza == rabo){
            return "["+cabeza.elemento+"]";
        }
        String a = "[";
        Nodo n = cabeza;
        for (int i=0 ;i<longitud-1;i++) {
            a=a+n.elemento+", ";
            n = n.siguiente;
        }
        a = a + rabo.elemento +"]";
        return a;
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param o el objeto con el que hay que comparar.
     * @return <tt>true</tt> si la lista es igual al objeto recibido;
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean equals(Object o) {
        if (!(o instanceof Lista))
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)o;
        Nodo n = lista.cabeza;
        Nodo m = this.cabeza;
        if (lista.esVacio()&&this.esVacio()) {
        	return true;
        }
        if ((lista.esVacio()&&!(this.esVacio()))||(this.esVacio()&&!(lista.esVacio()))) {
        	return false;
        }
        if (longitud != lista.longitud) {
        	return false;
        }
        while ( n!= null && m!= null ){
            if ( !n.elemento.equals(m.elemento) )
                return false;
            n = n.siguiente;
            m = m.siguiente;
        }
        return true; 
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }
}