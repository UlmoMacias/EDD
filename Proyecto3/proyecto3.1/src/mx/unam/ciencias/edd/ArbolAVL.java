package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices de árboles AVL. La única diferencia
     * con los vértices de árbol binario, es que tienen una variable de clase
     * para la altura del vértice.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            super(elemento);
            this.altura = this.altura();
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            if(this==null)
                return -1;
            return super.altura();
        }
        private int balance(){
            int izq = this.izquierdo!=null ? this.izquierdo.altura():-1;
            int der = this.derecho!=null ? this.derecho.altura():-1;
            return izq -der;
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
            if(this==null)
                return " "+"-1/0";
            return this.elemento.toString()+" "+this.altura+ "/"+ this.balance();

        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)o;
            if(this.altura== vertice.altura)
                return super.equals(vertice);
            return false;
        }
    }
    public ArbolAVL(Coleccion<T> coleccion) {
        super(coleccion);
    }
    public ArbolAVL() { super(); }
    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeAVL(elemento);
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        super.agrega(elemento);
        VerticeAVL agregado = convierteAvl(ultimoAgregado);
        rebalanceo(agregado);
    }

    private VerticeAVL convierteAvl(VerticeArbolBinario<T> vertice){
        return(VerticeAVL)vertice;
    }
    private void rebalanceo(VerticeAVL aux){
      if (aux==null)
        return;
    aux.altura = aux.altura();
    VerticeAVL auxIzquierdo = convierteAvl(aux.izquierdo);
    VerticeAVL auxDerecho = convierteAvl(aux.derecho);
    if (aux.balance() == -2) {
        if (auxDerecho.balance()==1) {
            super.giraDerecha((Vertice)auxDerecho);
        }
        super.giraIzquierda((Vertice)aux);
    }
    if (aux.balance()==2) {
        if (auxIzquierdo.balance()==-1) {
            super.giraIzquierda((Vertice)auxIzquierdo);
        }
        super.giraDerecha((Vertice)aux);   
    } 
    aux.altura = aux.altura(); 
    rebalanceo(padre(aux));
}
private VerticeAVL padre(VerticeAVL aux){
    if(aux.hayPadre())
        return(VerticeAVL)aux.padre;
    return null;
}


    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        if (elemento==null)
            return;
        Vertice v = (Vertice)busca(elemento);
        if(v==null)
            return;
        elementos--;
        if(elementos==0){
            raiz=null;
            return;
        }
        if(v.hayIzquierdo() && v.hayDerecho())
            v=intercambiaEliminable(v);
        eliminaVertice(v);
        rebalanceo(padre(convierteAvl(v)));
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
            "girar a la izquierda por el " +
            "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
            "girar a la derecha por el " +
            "usuario.");
    }
}
