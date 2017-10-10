package mx.unam.ciencias.edd;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase privada para iteradores de árboles binarios completos. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Constructor que recibe la raíz del árbol. */
        public Iterador() {
        cola = new Cola<Vertice>();
            if(esVacio())
                return;
            cola.mete(raiz);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return !cola.esVacia();
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
            if(cola.esVacia())
                throw new NoSuchElementException();
            Vertice v = cola.saca();
            if(v.hayIzquierdo())
                cola.mete(v.izquierdo);
            if (v.hayDerecho())
                cola.mete(v.derecho);
            return v.elemento;
        

        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        if(elemento==null)
            throw new IllegalArgumentException();
        Vertice a = nuevoVertice(elemento);
        elementos++;
        if(esVacio())
            raiz = a;
        else{
            Vertice vertice = BFS();
            if(!vertice.hayIzquierdo())
                vertice.izquierdo = a;              
            else
		vertice.derecho = a;
	    a.padre = vertice;
        }
    }
    private Vertice BFS(){
        if(this.esVacio())
            return null;
        Cola<Vertice> cola = new Cola<Vertice>();
        cola.mete(raiz);
	Vertice vertice;
        while(cola.cabeza!=null){
	    vertice = cola.saca();
            if(vertice.hayIzquierdo())
                cola.mete(vertice.izquierdo);
            if(vertice.hayDerecho())
                cola.mete(vertice.derecho);
            if(!vertice.hayIzquierdo() || !vertice.hayDerecho())
                return vertice;
            }
            return null;
        }
    
    private Vertice ultimo(){
	Cola<Vertice>cola = new Cola<Vertice>();
	cola.mete(raiz);
	Vertice vertice = null;
	while(cola.cabeza != null){
	    vertice = cola.saca();
            if(vertice.hayIzquierdo())
                cola.mete(vertice.izquierdo);
            if(vertice.hayDerecho())
                cola.mete(vertice.derecho);
	}
	return vertice;
    }
    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        if(elemento==null)
            return;
        Vertice aux = (Vertice)busca(elemento);
        if (aux.equals(null))
            return;
        elementos--;
        if(this.elementos==0 && elemento.equals(raiz.elemento))
            this.raiz=null;
        else{
            Vertice b = ultimo();
            if(b.padre.izquierdo.equals(b)){
		intercambia(aux,b);
		b.padre.izquierdo = null;
		b.padre = null;
            }else{
                intercambia(aux,b);
                b.padre.derecho = null;
                b.padre = null;   
	    }
        }
        }


    
    private void intercambia(Vertice a, Vertice b){
        //Vertice b =BFS();
        T aux = a.elemento;
        a.elemento = b.elemento;
        b.elemento = aux;
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
        int a= (int)Math.floor(Math.log(elementos) / Math.log(2));
        return a;
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
        
}
