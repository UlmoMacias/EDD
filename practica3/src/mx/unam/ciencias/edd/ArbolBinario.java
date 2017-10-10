package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;
import java.lang.Math.*;

/**
 * <p>Clase abstracta para árboles binarios genéricos.</p>
 *
 * <p>La clase proporciona las operaciones básicas para árboles binarios, pero
 * deja la implementación de varias en manos de las subclases concretas.</p>
 */
public abstract class ArbolBinario<T> implements Coleccion<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class Vertice implements VerticeArbolBinario<T> {

        /** El elemento del vértice. */
        public T elemento;
        /** El padre del vértice. */
        public Vertice padre;
        /** El izquierdo del vértice. */
        public Vertice izquierdo;
        /** El derecho del vértice. */
        public Vertice derecho;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public Vertice(T elemento) {
        	this.elemento = elemento;
        	padre = null ;
        	izquierdo = null;
            derecho = null;
        }

        /**
         * Nos dice si el vértice tiene un padre.
         * @return <tt>true</tt> si el vértice tiene padre,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayPadre() {
        	return this.padre!=null;
        }

        /**
         * Nos dice si el vértice tiene un izquierdo.
         * @return <tt>true</tt> si el vértice tiene izquierdo,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayIzquierdo() {
        	return this.izquierdo != null;
        }

        /**
         * Nos dice si el vértice tiene un derecho.
         * @return <tt>true</tt> si el vértice tiene derecho,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayDerecho() {
        	return this.derecho != null;
	    }

        /**
         * Regresa el padre del vértice.
         * @return el padre del vértice.
         * @throws NoSuchElementException si el vértice no tiene padre.
         */
        @Override public VerticeArbolBinario<T> padre() {
        	if (hayPadre()) 
                return padre;
        	throw new NoSuchElementException("El vertice no tiene padre");
        }

        /**
         * Regresa el izquierdo del vértice.
         * @return el izquierdo del vértice.
         * @throws NoSuchElementException si el vértice no tiene izquierdo.
         */
        @Override public VerticeArbolBinario<T> izquierdo() {
        	if (hayIzquierdo())
                return izquierdo;   
        	throw new NoSuchElementException("El vertice no tiene hijo izquierdo");
        }

        /**
         * Regresa el derecho del vértice.
         * @return el derecho del vértice.
         * @throws NoSuchElementException si el vértice no tiene derecho.
         */
        @Override public VerticeArbolBinario<T> derecho() {
        	if (hayDerecho())
                return derecho;
        	throw new NoSuchElementException("El vertice no tiene hijo derecho");
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            return alturaAux(this);
        }

        protected int alturaAux(Vertice vertice){
        	if (vertice==null) {
        		return -1;
        	}
        	return 1+Math.max(alturaAux(vertice.izquierdo),alturaAux(vertice.derecho));
        }

        protected int profundidadAux(Vertice vertice){
        	if(!vertice.hayPadre()){
        		return 0;
        	}
        	return 1+profundidadAux(vertice.padre);
        }

        /**
         * Regresa la profundidad del vértice.
         * @return la profundidad del vértice.
         */
        @Override public int profundidad() {
			return profundidadAux(this);
        }

        /**
         * Regresa el elemento al que apunta el vértice.
         * @return el elemento al que apunta el vértice.
         */
        @Override public T get() {
        	return this.elemento;
        }

        private boolean tiene2hijos(){
        	return hayDerecho() && hayIzquierdo();
        }

        private boolean compareAux(Vertice vertice, Vertice ver){
        	return compelem(vertice,ver);
        }
        private boolean compelem(Vertice a, Vertice b){
            if(a.elemento == null & b.elemento == null)
                return true;
            if (a.elemento.equals(b.elemento)) {
                compareAux(a.izquierdo,b.izquierdo);
                compareAux(a.derecho,b.derecho);
            }
            return false;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>. Las clases que extiendan {@link Vertice} deben
         * sobrecargar el método {@link Vertice#equals}.
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link Vertice}, su elemento es igual al elemento de éste
         *         vértice, y los descendientes de ambos son recursivamente
         *         iguales; <code>false</code> en otro caso.
         */
        @Override public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked") Vertice vertice = (Vertice)o;
            if(!this.elemento.equals(vertice.elemento))
                return false;
            return equalsAuxDer(this, vertice) && equalsAuxIzq(this, vertice);
        }
        protected boolean equalsAuxDer(Vertice a, Vertice b){
            if (a.hayDerecho() && b.hayDerecho() && b.elemento.equals(a.elemento))
                return a.derecho.equals(b.derecho);
            if(!a.hayDerecho() && !b.hayDerecho() && b.elemento.equals(a.elemento))
                return true;
            if (a.hayDerecho() && !b.hayDerecho())
                return false;
            if(!a.hayDerecho() && !b.hayDerecho())
                return false;
            return false;

        }
        protected boolean equalsAuxIzq(Vertice a, Vertice b){
            if (a.hayIzquierdo() && b.hayIzquierdo() && b.elemento.equals(a.elemento))
                return a.izquierdo.equals(b.izquierdo);
            if(!a.hayIzquierdo() && !b.hayIzquierdo() && b.elemento.equals(a.elemento))
                return true;
            if (a.hayIzquierdo() && !b.hayIzquierdo())
                return false;
            if(!a.hayIzquierdo() && !b.hayIzquierdo())
                return false;
            return false;
        }
        /**
         * Regresa una representación en cadena del vértice.
         * @return una representación en cadena del vértice.
         */
        public String toString() {
			if(this==null)
                return " ";
            return this.elemento.toString();
        }
    }

//--------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------
    //__________________________________________________________________________________________________________
    //__________________________________________________________________________________________________________
    //__________________________________________________________________________________________________________
    /** La raíz del árbol. */
    protected Vertice raiz;
    /** El número de elementos */
    protected int elementos;

    /**
     * Constructor sin parámetros. Tenemos que definirlo para no perderlo.
     */
    public ArbolBinario() {
    	//raiz = nuevoVertice(null);
    	//elementos = 0;
    }

    /**
     * Construye un árbol binario a partir de una colección. El árbol binario
     * tendrá los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario.
     */
    public ArbolBinario(Coleccion<T> coleccion) {
        for (T e : coleccion) {
        	this.agrega(e);
        	//elementos ++;
        }
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link Vertice}. Para
     * crear vértices se debe utilizar este método en lugar del operador
     * <code>new</code>, para que las clases herederas de ésta puedan
     * sobrecargarlo y permitir que cada estructura de árbol binario utilice
     * distintos tipos de vértices.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    protected Vertice nuevoVertice(T elemento) {
        return new Vertice(elemento);
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol es la altura de su
     * raíz.
     * @return la altura del árbol.
     */
    public int altura() {
        return raiz.altura();
    }



    /**
     * Regresa el número de elementos que se han agregado al árbol.
     * @return el número de elementos en el árbol.
     */
    @Override public int getElementos() {
    	return this.elementos;
    }

    /**
     * Nos dice si un elemento está en el árbol binario.
     * @param elemento el elemento que queremos comprobar si está en el árbol.
     * @return <code>true</code> si el elemento está en el árbol;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
    	if(elemento.equals(null))
    		return false;
    	return busca(elemento)!=null;
    }

    /**
     * Busca el vértice de un elemento en el árbol. Si no lo encuentra regresa
     * <tt>null</tt>.
     * @param elemento el elemento para buscar el vértice.
     * @return un vértice que contiene el elemento buscado si lo encuentra;
     *         <tt>null</tt> en otro caso.
     */
    public VerticeArbolBinario<T> busca(T elemento) {
        if(elemento == null)
            return null;
        if(this.esVacio())
            return null;
        return buscaAux(raiz,elemento);    	
    }			

    private VerticeArbolBinario<T> buscaAux(Vertice a,T elemento){
        Cola<Vertice> b = new Cola<Vertice>();
        b.mete(a);
        while(!b.esVacia()){
            Vertice aux = b.cabeza.elemento;
            if(b.saca().elemento.equals(elemento))
                return aux;
            else{
                if(aux.hayIzquierdo())
                    b.mete(aux.izquierdo);
                if(aux.hayDerecho())
                    b.mete(aux.derecho);
            }
        }
        return null;
    }

    /**
     * Regresa el vértice que contiene la raíz del árbol.
     * @return el vértice que contiene la raíz del árbol.
     * @throws NoSuchElementException si el árbol es vacío.
     */
    public VerticeArbolBinario<T> raiz() {
    	if (this.esVacio()) {
    		throw new NoSuchElementException();
    	}
    	return this.raiz;
    }

    /**
     * Nos dice si el árbol es vacío.
     * @return <code>true</code> si el árbol es vacío, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacio() {
    	if (this.raiz == null) {
    		return true;
    	}
    	return false;
    }

    /**
     * Limpia el árbol de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
    	this.raiz = null; //elimini nuevo vertice
    	this.elementos = 0;
    }

    /**
     * Compara el árbol con un objeto.
     * @param o el objeto con el que queremos comparar el árbol.
     * @return <code>true</code> si el objeto recibido es un árbol binario y los
     *         árboles son iguales; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked")
            ArbolBinario<T> arbol = (ArbolBinario<T>)o;
        if((this.esVacio() && !arbol.esVacio() )||(!this.esVacio() && arbol.esVacio() ))
            return false;
        if(this.esVacio() && arbol.esVacio())
            return true;
        return raiz.equals(arbol.raiz);
    }

    /**
     * Regresa una representación en cadena del árbol.
     * @return una representación en cadena del árbol.
     */
    /**
     * Regresa una representación en cadena del árbol.
     * @return una representación en cadena del árbol.
     */
    @Override public String toString() {
        if(esVacio()){
            return "";
        }
        int[] a = new int[this.altura()+1];
        for (int i=0;i<this.altura()+1 ;i++ ) {
            a[i]=0;
        }
        return toString(this.raiz,0,a);

    }
    private String dibujaEspacios(int l, int[] a){
        String aux = "";
        for (int i=0;i<l ;i++) {
            if(a[i]==1){
                aux+="│  ";
            }
            else{
                aux+="   ";
            }
        }
        return aux;
    }

     


    private String toString(Vertice v, int l, int[] a){
        String aux = v.toString()+"\n";
        a[l]=1;
        if(v.hayIzquierdo() && v.hayDerecho()){
            aux = aux + dibujaEspacios(l,a);
            aux = aux +"├─›";
            aux = aux +toString(v.izquierdo,l+1,a);
            aux = aux +dibujaEspacios(l,a);
            aux = aux +"└─»";
            a[l]=0;
            aux = aux+toString(v.derecho,l+1,a);
        }
        else{
            if (v.hayIzquierdo()) {
                aux = aux + dibujaEspacios(l,a);
                aux = aux + "└─›";
                a[l]=0;
                aux = aux + toString(v.izquierdo,l+1,a);
            }
            else{
                if (v.hayDerecho()) {
                aux = aux + dibujaEspacios(l,a);
                aux = aux + "└─»";
                a[l]=0;
                aux = aux + toString(v.derecho,l+1,a); 
                }
            }
        }
        return aux;
    }



    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * Vertice}). Método auxiliar para hacer esta audición en un único lugar.
     * @param vertice el vértice de árbol binario que queremos como vértice.
     * @return el vértice recibido visto como vértice.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         Vertice}.
     */
    protected Vertice vertice(VerticeArbolBinario<T> vertice) {
        if(vertice.getClass()!= getClass())
            throw new ClassCastException();
        return (Vertice)vertice;
    }
}
