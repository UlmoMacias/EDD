package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;
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
            // Aquí va su código.
	    anterior = null;
	    siguiente = null;
	    this.elemento = elemento;
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
            // Aquí va su código.
	    anterior = null;
	    siguiente = cabeza;
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
	    return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            // Aquí va su código.
	    if(siguiente==null)
		throw new NoSuchElementException("Error");
	    anterior = siguiente;
	    siguiente= siguiente.siguiente;
	    return anterior.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            // Aquí va su código.
	    if(anterior == null)
		return false;
	    return true;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            // Aquí va su código.
	    if(this.hasPrevious()){
		siguiente = anterior;
		anterior = anterior.anterior;
		return siguiente.elemento;
	    }
	    throw new NoSuchElementException();
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            // Aquí va su código.
	    if(cabeza != null){
	    	siguiente = cabeza;
	    	anterior = null;
	    }
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            // Aquí va su código.
	    anterior = rabo;
	    siguiente = null;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud = 0;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        // Aquí va su código.
	return longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
        // Aquí va su código.
	return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacio() {
        // Aquí va su código.
	if(cabeza == null)
	    return true;
	return false;
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
	if(elemento == null)
	    throw new IllegalArgumentException("Error");
	if(this.esVacio()){
	    cabeza = new Nodo (elemento);
	    rabo = cabeza;
	    longitud = 1;
	}
	else{
	    Nodo aux = new Nodo(elemento);
	    aux.anterior= rabo;
	    rabo.siguiente = aux;
	    rabo = aux;
	    longitud ++;
	}
	
	    
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
	this.agrega(elemento);
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
	if(elemento == null)
	    throw new IllegalArgumentException("Exception");
	if(this.esVacio())
	    this.agrega(elemento);
	else{
	    Nodo aux = new Nodo(elemento);
	    aux.siguiente = cabeza;
	    cabeza.anterior = aux;
	    cabeza = aux;
	    longitud ++;
	}
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor que cero, el elemento se agrega al inicio de la
     * lista. Si el índice es mayor o igual que el número de elementos en la
     * lista, el elemento se agrega al final de la misma. En otro caso, después
     * de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al final, y si es mayor o igual que el número
     *          de elementos en la lista se agrega al inicio.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
	if(elemento == null)
	    throw new IllegalArgumentException("Exception");
        if(i <= 0){
	    this.agregaInicio(elemento);
	    return;
	}
	if(i >= longitud){
	    this.agregaFinal(elemento);
	    return;
	}	
    	Nodo aux = new Nodo(elemento);
	Nodo nodo = cabeza;
        for(int j = 0; j<i; j++){
		nodo = nodo.siguiente;
	    }
         aux.siguiente= nodo;
	 aux.anterior = nodo.anterior;
	 nodo.anterior.siguiente=aux;
	 nodo.anterior = aux;
	 longitud ++;
    }



   private Nodo buscaNodo(T elemento){
        Nodo nodo = cabeza;
        while (nodo!=null) {
            if (elemento.equals(nodo.elemento)) {
                return nodo;
            }
            nodo=nodo.siguiente;
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
        Nodo nodo = buscaNodo(elemento);
        if (nodo==null) {
            return ;
        }
        if (longitud==1) {
            this.limpia();
            return;
        }
        if (nodo==cabeza){
            this.eliminaPrimero();
            return ;
        }
        if (nodo==rabo) {
            this.eliminaUltimo();
            return ; 
        }
        nodo.siguiente.anterior=nodo.anterior;
        nodo.anterior.siguiente=nodo.siguiente;
        longitud--;
        return;
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
	if(longitud <=  0 || cabeza == null)
	    throw new NoSuchElementException("Exception");
	else if(longitud == 1){
	    T aux = cabeza.elemento;
	    cabeza=rabo=null;
	    longitud = 0;
	    return aux;
	}else{
	    T aux = cabeza.elemento;
	    cabeza.siguiente.anterior= null;
	    cabeza = cabeza.siguiente;
	    longitud --;
	    return aux;
	}
    }
    
    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
	if(longitud <= 0)
	    throw new NoSuchElementException("Exception");
	T aux = rabo.elemento;
	if(longitud == 1){
	    this.limpia();
	}
	else{
	    rabo.anterior.siguiente= null;
	    rabo = rabo.anterior;
	    longitud --;
	}
	return aux;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <tt>true</tt> si <tt>elemento</tt> está en la lista,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
	if(elemento == null || cabeza == null)
	    return false;
	Nodo nodo = cabeza;
	while(nodo != null){
	    if(elemento.equals(nodo.elemento))
		return true;
	    nodo = nodo.siguiente;
	}
	return false;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        Lista<T> lista = new Lista<T>();
	if(this.esVacio())
	    return lista;
	Nodo nodo = rabo;
	while(nodo != null){
	    lista.agrega(nodo.elemento);
	    nodo = nodo.anterior;
	}
	lista.longitud = longitud;
	return lista;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
	Lista<T> lista = new Lista<T>();
	if(cabeza == null)
	    return lista;
	Nodo nodo = cabeza;
	while(nodo != null){
	    lista.agrega(nodo.elemento);
	    nodo = nodo.siguiente;
	}
	lista.longitud = longitud;
	return lista;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
	rabo = cabeza = null;
	longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
	if(cabeza == null)
	    throw new NoSuchElementException("Excepcion");
        return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        if(rabo != null)
	    return rabo.elemento;
	throw new NoSuchElementException("Excepcion");
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
	if(cabeza == null)
	    return null;
	if(i<0 || i>=longitud)
	    throw new ExcepcionIndiceInvalido("Exception");
	Nodo nodo = cabeza;
	for(int j =1; j<=i; j++ )
	    nodo = nodo.siguiente;
	return nodo.elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si
     *         el elemento no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
	if(elemento != null && cabeza != null ){
            Nodo nodo = cabeza;
	    int i = 0;
	    while(nodo != null){
		if(elemento.equals(nodo.elemento))
		    return i;
		nodo = nodo.siguiente;
		i++;
	    }
	}
	return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
	if(this.esVacio())
	    return "[]";
	if(cabeza == rabo)
	    return "[" + cabeza.elemento + "]";
        String string = "[";
	Nodo nodo = cabeza;
	for(int i=0; i<longitud-1; i++){
	    string = string +nodo.elemento+", ";
	    nodo = nodo.siguiente;
	}
	string = string + nodo.elemento + "]";
	return string;
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
	Nodo nodo = cabeza;
	Nodo aux = lista.cabeza;
	if(lista.esVacio() && this.esVacio())
            return true;
	if((lista.esVacio() && !(this.esVacio())) || (this.esVacio() && !(lista.esVacio())))
	    return false;
	if(longitud != lista.longitud)
	    return false;
	while(nodo != null){
	    if(aux.elemento.equals(nodo.elemento)){
	    	nodo = nodo.siguiente;
	    	aux = aux.siguiente;
	    }else
		return false;
	}
	return true;
    }

    private Lista<T> mezcla(Lista<T> l1,Lista<T> l2,Comparator<T> c){
	if(l1.longitud >1)
	   l1 =  mergeSort(l1,c);
	if(l2.longitud >1)
	    l2 = mergeSort(l2,c);
	Lista<T> ordenada = new Lista<T>();
	Nodo aux1 = l1.cabeza;
	Nodo aux2 = l2.cabeza;
	while(aux1 != null && aux2 != null){
	    if(c.compare(aux1.elemento,aux2.elemento) <= 0){
		ordenada.agrega(l1.eliminaPrimero());
		aux1 = aux1.siguiente;
	    }else{
		ordenada.agrega(l2.eliminaPrimero());
		aux2 = aux2.siguiente;
	    }
	}
	if(l1.esVacio()){
	    ordenada.rabo.siguiente = l2.cabeza;
	    l2.cabeza.anterior = ordenada.rabo;
	    ordenada.rabo = l2.rabo;
	    ordenada.longitud = ordenada.longitud + l2.longitud;
	}else{
	    ordenada.rabo.siguiente = l1.cabeza;
	    l1.cabeza.anterior = ordenada.rabo;
	    ordenada.rabo = l1.rabo;
	    ordenada.longitud = ordenada.longitud + l1.longitud;
	}
	return ordenada;
    }

    private Lista<T> mergeSort(Lista<T> lista, Comparator<T> c){
	if(lista.longitud == 0 || lista.longitud == 1)
	    return lista;
	int m = lista.longitud/2;
	Nodo nodo = lista.cabeza;
	Lista<T> l1 = new Lista<T>();
	Lista<T> l2 = new Lista<T>();
	for(int i=1; i<m+1; i++){
	    l1.agrega(nodo.elemento);
	    nodo = nodo.siguiente;
	}
	while(nodo != null){
	    l2.agrega(nodo.elemento);
	    nodo = nodo.siguiente;
	}
	return mezcla(l1,l2,c);
    }

    public Lista<T> mergeSort(Comparator<T> c){
	return this.mergeSort(this,c);
    }
    
    public static <T extends Comparable<T>> Lista<T> mergeSort(Lista<T> lista){
	return lista.mergeSort((a,b) -> a.compareTo(b));
    }
    
    private void cambia(Nodo i, Nodo j){
	T elemento = i.elemento;
	i.elemento = j.elemento;
	j.elemento = elemento;
    }

    public boolean busquedaLineal(T elemento, Comparator<T> c){
	if(elemento == null)
	    throw new NoSuchElementException("Exception");
	//mergeSort(c);
	Nodo aux = cabeza;
	while(aux != null){
	    if(c.compare(aux.elemento,elemento)==0)
		return true;
	    aux = aux.siguiente;
	}
	//if(c.compare(this.rabo.elemento,elemento)==0)
	//  return true;
	return false;
    }

    public static <T extends Comparable<T>> boolean
	busquedaLineal(Lista<T> lista, T elemento){
	return lista.busquedaLineal(elemento, (a,b) -> a.compareTo(b));
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
