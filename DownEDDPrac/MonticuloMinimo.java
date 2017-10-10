package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>>
implements Coleccion<T> {

    /* Clase privada para iteradores de montículos. */
    private class Iterador implements Iterator<T> {

        /* Índice del iterador. */
        private int indice;

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
         return this.indice<elementos?true:false;
     }

     /* Regresa el siguiente elemento. */
     @Override public T next() {
        if(indice>=elementos)
            throw new NoSuchElementException();
        T aux = arbol[indice];
        indice++;
        return aux;
    }
}

/* Clase estática privada para poder implementar HeapSort. */
private static class Adaptador<T  extends Comparable<T>>
implements ComparableIndexable<Adaptador<T>> {

    /* El elemento. */
    private T elemento;
    /* El índice. */
    private int indice;

    /* Crea un nuevo comparable indexable. */
    public Adaptador(T elemento) {
        this.elemento = elemento;
        this.indice =-1;
    }

    /* Regresa el índice. */
    @Override public int getIndice() {
        return this.indice;
    }

    /* Define el índice. */
    @Override public void setIndice(int indice) {
        this.indice = indice;
    }

    /* Compara un indexable con otro. */
    @Override public int compareTo(Adaptador<T> adaptador) {
        return this.elemento.compareTo(adaptador.elemento);
    }
}

/* El número de elementos en el arreglo. */
private int elementos;
/* Usamos un truco para poder utilizar arreglos genéricos. */
private T[] arbol;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
       @SuppressWarnings("unchecked") private T[] nuevoArreglo(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Coleccion)}, pero se ofrece este constructor por
     * completez.
     */
    public MonticuloMinimo() {
        arbol = nuevoArreglo(100);
        elementos=0;
    }

    /**
     * Constructor para montículo mínimo que recibe una lista. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param coleccion la colección a partir de la cuál queremos construir el
     *              montículo.
     */
    public MonticuloMinimo(Coleccion<T> coleccion) {
        this.arbol = nuevoArreglo(coleccion.getElementos());
        for (T aux  : coleccion) {
            agrega(aux);
            acomodoAbajo(aux);
        }
    }

    /**
     * Agrega un nuevo elemento en el montículo.
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override public void agrega(T elemento) {
        if(elemento==null)
            return;
        if (arbol.length==elementos){
            T[] aux = nuevoArreglo(elementos+15);
            for (int i = 0;i<elementos ; i++) {
                aux[i]= arbol[i];
            }
            arbol = aux;
        }
        elementos++;
        arbol[elementos-1] = elemento;
        actualizar(arbol[elementos-1], elementos-1);
        reordena(elemento);
    }
    private int padre(int indice){
        return (int)Math.floor((indice-1)/2);
    }
    private void actualizar(T elemento, int indice){
     elemento.setIndice(indice);
 }
 private boolean hayPadre(int indice){
    return indice!=0?true:false;
}
private int hDerecho(int indice){
    return (indice*2)+2;
}
private boolean hayHDerecho(int indice){
    return hDerecho(indice)<elementos?true:false;
}
private int hIzquierdo(int indice){
    return hDerecho(indice)-1;
}
private boolean hayHIzquierdo(int indice){
    return hIzquierdo(indice)<elementos?true:false;
}
private void acomodoArriba(T indexable){
    int padre = padre(indexable.getIndice());
    if(indexable.getIndice()<0 || indexable.getIndice()>=elementos || padre<0){
        return;
    }

    if(indexable.compareTo(arbol[padre])>=0){
        return;
    }
    if(indexable.compareTo(arbol[padre])<0){
        intercambia(indexable,arbol[padre]);
    }
    acomodoArriba(indexable);
}
private boolean indiceValido(int i){
    return !(i<0 || i>=this.arbol.length || arbol[i]==null);
}
private void intercambia(T i,T d){
    int aux=d.getIndice();
    arbol[i.getIndice()]=d;
    arbol[d.getIndice()]=i;
    d.setIndice(i.getIndice());
    i.setIndice(aux);
} 
private void acomodoAbajo(T indexable){
    int izq;
    int der;
    int min;
    if(indexable==null){
        return;
    }
    izq = hIzquierdo(indexable.getIndice());
    der = hDerecho(indexable.getIndice());
    if(!indiceValido(izq)&&!indiceValido(der)){
        return;
    }
    min=der;
    if(indiceValido(izq)){
        if(indiceValido(der)){
            if(arbol[izq].compareTo(arbol[der])<0){
                min=izq;
            }
        }else{
            min=izq;
        }
    }
    if(arbol[min].compareTo(indexable)<0){
        intercambia(indexable,arbol[min]);
        acomodoAbajo(indexable);
    }
}
private void intercambia(int a, int b){
    T aux = arbol[a];
    arbol[a]= arbol[b];
    arbol[b]= aux;
    arbol[a].setIndice(a);
    arbol[b].setIndice(b); 
}
    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    public T elimina() {
        if(esVacio()){
            throw new IllegalStateException();
        }
        T t = arbol[0];
        elimina(t);
        t.setIndice(-1);
        return t;
    }

    /**
     * Elimina un elemento del montículo.
     * @param elemento a eliminar del montículo.
     */
    @Override public void elimina(T elemento) {
        if (elemento==null || elemento.getIndice()<0 || elemento.getIndice()>=elementos) {
            return;
        }
        int index = elemento.getIndice();
        
        intercambia(elemento,arbol[elementos-1]);
        elementos--;
        arbol[elementos]=null;
        elemento.setIndice(-1);
        reordena(arbol[index]);
    }

    /**
     * Nos dice si un elemento está contenido en el montículo.
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        for (int i=0;i<arbol.length ;i++ ) {
            if(arbol[i].equals(elemento))
                return true;
        }
        return false;
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <tt>true</tt> si ya no hay elementos en el montículo,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean esVacio() {
        if(elementos==0)
            return true;
        return false;
    }

    /**
     * Limpia el montículo de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        for (int i=0;i<arbol.length ;i++ ) {
            this.arbol[i]=null;
        }
        this.elementos=0;
    }

   /**
     * Reordena un elemento en el árbol.
     * @param elemento el elemento que hay que reordenar.
     */
   public void reordena(T elemento) {
    int p;
    if(elemento==null){
        return;
    }
    p=(elemento.getIndice()-1);
    if(p!=-1){
        p/=2;
    }
    if(!indiceValido(p) || arbol[p].compareTo(elemento)<=0)
        acomodoAbajo(elemento);
    else{
        acomodoArriba(elemento);
    }
}

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * @return el número de elementos en el montículo mínimo.
     */
    @Override public int getElementos() {
        return this.elementos;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    public T get(int i) {
        if (i<0 || i>=getElementos())
            throw new NoSuchElementException();
        return arbol[i];
    }

    /**
     * Regresa una representación en cadena del montículo mínimo.
     * @return una representación en cadena del montículo mínimo.
     */
    @Override public String toString() {
        String s= "";
        for (int i=0;i<elementos;i++ ) {
           s += arbol[i].toString()+", ";   
       }
       return s;
   }

    /**
     * Nos dice si el montículo mínimo es igual al objeto recibido.
     * @param o el objeto con el que queremos comparar el montículo mínimo.
     * @return <code>true</code> si el objeto recibido es un montículo mínimo
     *         igual al que llama el método; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object o) {
         if (o==null||getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") MonticuloMinimo<T> monticulo = (MonticuloMinimo<T>)o;
        if(elementos!=monticulo.elementos){
            return false;
            }
            for(int i=0; i<elementos ;i++){
                if(!arbol[i].equals(monticulo.arbol[i])){
                return false;
                }
            }
            return true;
}

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Ordena la colección usando HeapSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param coleccion la colección a ordenar.
     * @return una lista ordenada con los elementos de la colección.
     */
    public static <T extends Comparable<T>>
    Lista<T> heapSort(Coleccion<T> coleccion) {
        Lista<Adaptador<T>> lAdaptador = new Lista<Adaptador<T>>();
        for (T aux : coleccion) {
            lAdaptador.agrega(new Adaptador<T>(aux));
        }
        Lista<T> l = new Lista<T>();
        MonticuloMinimo<Adaptador<T>> minimo = new MonticuloMinimo<Adaptador<T>>(lAdaptador);
        while(!minimo.esVacio()) {
         l.agrega(minimo.elimina().elemento);   
     }
     return l;
 }
}
