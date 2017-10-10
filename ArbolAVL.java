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
          altura = 0;
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
          return altura;
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
          return elemento + " " + altura + "/" + getBalance(this);
        }

        private boolean arbolesIguales(VerticeAVL helpA, VerticeAVL helpB){
          VerticeAVL izqA, izqB;
          VerticeAVL derA, derB;
          if(helpA == null && helpB == null)
            return true;
          if((helpA == null && helpB != null) || (helpA != null && helpB == null) || !helpA.elemento.equals(helpB.elemento))
            return false;
          if(helpA.altura != helpB.altura)
            return false;
          izqA = verticeAVL(helpA.izquierdo);
          derA = verticeAVL(helpA.derecho);
          izqB = verticeAVL(helpB.izquierdo);
          derB = verticeAVL(helpB.derecho);
          return arbolesIguales(derA, derB) && arbolesIguales(izqA, izqB);
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
              //VerticeAVL vertice = (VerticeAVL)o;
            return altura == vertice.altura && super.equals(o);


        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() {
      super();
    }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolAVL(Coleccion<T> coleccion) {

    }
    //hace el cast de todo, porque que flojera
    protected VerticeAVL verticeAVL(VerticeArbolBinario<T> vAux) {
        return (VerticeAVL)vAux;
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
      return new VerticeAVL(elemento);
    }

    private int getBalance(VerticeArbolBinario<T> aux){
      VerticeAVL help, izq, der;
      help = verticeAVL(aux);
      izq = verticeAVL(help.izquierdo);
      der = verticeAVL(help.derecho);
      return profundidadAux(izq) - profundidadAux(der);//cambie altura a profAux
    }

    //Empiezza a calcular la nueva altura de nuestro arbol AVL
    //Al terminar saca la nueva altura
    private void calculaCambia(VerticeArbolBinario<T> aux) {
      VerticeAVL help, izq, der;
       help = verticeAVL(aux);
       izq = verticeAVL(help.izquierdo);
       der = verticeAVL(help.derecho);
       help.altura = 1 + Math.max(altura(izq), altura(der));
   }
    private void avlBalanceado(VerticeAVL help){
      if(help==null)
        return;
      VerticeAVL izq = verticeAVL(help.izquierdo);
      VerticeAVL der = verticeAVL(help.derecho);
      calculaCambia(help);
      if(getBalance(help) == -2){
        if(getBalance(der) == 1){
          super.giraDerecha(der);
          calculaCambia(der);
        }
        super.giraIzquierda(help);
        calculaCambia(help);
      }else if(getBalance(help)==2){
        if(getBalance(izq)==-1){
          super.giraIzquierda(izq);
          calculaCambia(izq);
        }
        super.giraDerecha(help);
        calculaCambia(help);
      }
      avlBalanceado(verticeAVL(help.padre));
    }
    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
      super.agrega(elemento);
      VerticeAVL help = verticeAVL(ultimoAgregado);
      avlBalanceado(help);
    }
    private boolean ifHijoIzq(VerticeArbolBinario<T> help) {
        VerticeAVL aux = verticeAVL(help);
        return aux.padre.izquierdo == aux;
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
      VerticeAVL viejo = verticeAVL(super.busca(raiz, elemento));
      VerticeAVL help;
      if(viejo == null)
        return;
      if(viejo.hayIzquierdo()){
        help = viejo;
        viejo = verticeAVL(maximoEnSubarbol(viejo.izquierdo()));
        help.elemento = viejo.elemento;
      }
      if(!viejo.hayIzquierdo() && !viejo.hayDerecho()){
        if (raiz == viejo) {
          raiz = null;
        }else if(ifHijoIzq(viejo))
          viejo.padre.izquierdo = null;
        else
          viejo.padre.derecho = null;
      }else if (!viejo.hayDerecho()) {
        if(raiz == viejo){
          raiz = raiz.izquierdo;
          raiz.padre = null;
        }else{
          viejo.izquierdo.padre = viejo.padre;
          if(ifHijoIzq(viejo))
          viejo.padre.izquierdo = viejo.izquierdo;
          else
            viejo.padre.derecho = viejo.derecho;
        }
      }
      --elementos;
      avlBalanceado(verticeAVL(viejo.padre));
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
