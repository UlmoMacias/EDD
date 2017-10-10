package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase privada para iteradores de gráficas. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
          return iterador.hasNext();
      }

      /* Regresa el siguiente elemento. */
      @Override public T next() {
       return iterador.next().get();
   }
}

/* Vértices para gráficas; implementan la interfaz VerticeGrafica */
private class Vertice implements VerticeGrafica<T> {

    /* El elemento del vértice. */
    public T elemento;
    /* El color del vértice. */
    public Color color;
    /* La lista de vecinos del vértice. */
    public Lista<Vertice> vecinos;

    /* Crea un nuevo vértice a partir de un elemento. */
    public Vertice(T elemento) {
        vecinos = new Lista<Grafica<T>.Vertice>();
        this.elemento = elemento;
        this.color = Color.NINGUNO;
    }

    /* Regresa el elemento del vértice. */
    @Override public T get() {
        return this.elemento;
    }

    /* Regresa el grado del vértice. */
    @Override public int getGrado() {
        return this.vecinos.getLongitud();
    }

    /* Regresa el color del vértice. */
    @Override public Color getColor() {
        return this.color;
    }

    /* Regresa un iterable para los vecinos. */
    @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
        return vecinos;
    }
}

/* Vértices. */
private Lista<Vertice> vertices;
/* Número de aristas. */
private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        vertices = new Lista<Vertice>();
        aristas = 0;
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        return this.vertices.getLongitud();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        int aristas = 0;
        for (Vertice v : vertices)
            aristas += v.getGrado();
        return aristas / 2;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a
     *         la gráfica.
     */
    @Override public void agrega(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException();
        for(Vertice v : vertices) 
            if (v.elemento.equals(elemento))
                throw new IllegalArgumentException();
            vertices.agrega(new Vertice(elemento));
        }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
        if (!this.contiene(a) || !this.contiene(b))
            throw new NoSuchElementException();
        Vertice va = castVertice(vertice(a));
        Vertice vb = castVertice(vertice(b));
        if (va.vecinos.contiene(vb) || a.equals(b))
            throw new IllegalArgumentException();
        va.vecinos.agrega(vb);
        vb.vecinos.agrega(va);
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
        if (!this.contiene(a) || !this.contiene(b))
            throw new NoSuchElementException();
        Vertice va = castVertice(vertice(a));
        Vertice vb = castVertice(vertice(b));
        if (!va.vecinos.contiene(vb))
            throw new IllegalArgumentException();
        va.vecinos.elimina(vb);
        vb.vecinos.elimina(va);
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <tt>true</tt> si el elemento está contenido en la gráfica,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        for (Vertice v : vertices )
            if (v.elemento.equals(elemento))
                return true;
            return false;
        }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
        if (!contiene(elemento))
            throw new NoSuchElementException("El elemento no está en" + 
                "la gráfica");
        Vertice v = castVertice(vertice(elemento));
        for (Vertice vecino : v.vecinos)
            desconecta(v.elemento,vecino.elemento);
        vertices.elimina(v);
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <tt>true</tt> si a y b son vecinos, <tt>false</tt> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        if(!this.contiene(a) || !this.contiene(b))
            throw new NoSuchElementException("Algún elemento no existe.");
        Vertice va = castVertice(this.vertice(a));
        Vertice vb = castVertice(this.vertice(b));
        return va.vecinos.contiene(vb);
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        if (!this.contiene(elemento))
            throw new NoSuchElementException("No existe el elemento.");
        for (Vertice v : vertices)
            if (v.elemento.equals(elemento))
                return v;
            return null;
        }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws NoSuchElementException si vértice no es vértice de la gráfica.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
       boolean contenido = vertices.contiene((Vertice)vertice);
       if (contenido==true) {
        Vertice aux = (Vertice)vertice;
        aux.color=color;
    }
    else
        throw new NoSuchElementException();
}
private Vertice castVertice(VerticeGrafica<T> v) {
    return (Vertice)v;
}
    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
        if (vertices.esVacio())
            return false;
        Vertice e = vertices.get(0);
        for (Vertice v : vertices)
            v.color = Color.NINGUNO;
        Cola<Vertice> c = new Cola<Vertice>();
        c.mete(e);
        while (!c.esVacia()) {
            e = c.saca();
            e.color = Color.ROJO;
            for (Vertice vv : e.vecinos) {
                if (vv.color == Color.ROJO)
                    continue;
                vv.color = Color.ROJO;
                c.mete(vv);
            }
        }
        for (Vertice vertice: this.vertices ) {
            if(vertice.color != Color.ROJO)
                return false;
        }
        for (Vertice vertice : vertices)
            vertice.color = Color.NINGUNO;
        return true;
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        for (Vertice v : vertices) 
            accion.actua(v);
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        if (vertices.esVacio())
            return;
        Vertice e = castVertice(vertice(elemento));
        for (Vertice v : vertices)
            v.color = Color.NINGUNO;
        Cola<Vertice> c = new Cola<Vertice>();
        c.mete(e);
        while (!c.esVacia()) {
            e = c.saca();
            e.color = Color.ROJO;
            accion.actua(e);
            for (Vertice vv : e.vecinos) {
                if (vv.color == Color.ROJO)
                    continue;
                vv.color = Color.ROJO;
                c.mete(vv);
            }
        }
        for (Vertice vv : vertices)
            vv.color = Color.NINGUNO;
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        if (vertices.esVacio())
            return;
        Vertice e = castVertice(vertice(elemento));
        for (Vertice v : vertices)
            v.color = Color.NINGUNO;
        Pila<Vertice> p = new Pila<Vertice>();
        p.mete(e);
        while (!p.esVacia()) {
         e = p.saca();
         e.color = Color.ROJO;
         accion.actua(e);
         for (Vertice vv : e.vecinos) {
             if (vv.color == Color.ROJO)
                continue;
            vv.color = Color.ROJO;
            p.mete(vv);
        }
    }
    for (Vertice vv : vertices)
        vv.color = Color.NINGUNO; 
}

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacio() {
        return this.vertices.esVacio();
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
        this.vertices.limpia();
        this.aristas =0;
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
    @Override public String toString() {
        Lista<String> representacion = new Lista<String>();
     String s = "";
     for (Vertice v : this.vertices) {
         for (Vertice c : v.vecinos ) {
             if(!aristasRepetidas(v,c,representacion))
                representacion.agrega("("+v.elemento+", "+c.elemento+")");
         }
     }
     s += representacion.toString();
     s = s.replace("[","");
     s = s.replace("]","");
     s += ", ";
     return s;
    }
    private boolean aristasRepetidas(Vertice a, Vertice b, Lista<String> lista){
        String aux = "("+a.elemento+", "+b.elemento+")";
        String aux2 = "("+b.elemento+", "+a.elemento+")";
        return lista.contiene(aux) || lista.contiene(aux2);
    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param o el objeto con el que hay que comparar.
     * @return <tt>true</tt> si la gráfica es igual al objeto recibido;
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") Grafica<T> grafica = (Grafica<T>)o;
        if(grafica.aristas == this.aristas){
            if(grafica.vertices.getLongitud() == this.vertices.getLongitud()){
                Iterator<Vertice> a = this.vertices.iteradorLista();
                boolean aux = true;
                boolean contenido= true;
                Vertice v = null;
                Vertice c = null;
                int indice = 0;
                while(a.hasNext()){
                    v = a.next();
                contenido = buscaLista(grafica.vertices, v);
                if(contenido== true){
                   c = buscaVertice(grafica.vertices, v);
                   if(c==null)
                    return false;
                   aux = auxEquals(v,c);
                   if(aux== false)
                    return false;
                }
                  if(contenido==false)
                    return false;
            }
            return true;
        }
        return false;
    }
    return false;

}
private boolean auxEquals(Vertice a, Vertice b){
    boolean contenido = false;
    for (Vertice vecino : a.vecinos ) {
        contenido = buscaLista(b.vecinos, vecino);
        if(contenido== false)
            return false; 
    }
    return true;
}
private Vertice buscaVertice(Lista<Vertice> lista, Vertice v){
        Iterator<Vertice> a = lista.iteradorLista();
    Vertice aux = null;
    while(a.hasNext()){
        aux = a.next();
        if(aux.elemento.equals(v.elemento))
            return aux;
    }
    return null;
}
private boolean buscaLista(Lista<Vertice> lista, Vertice v){
    Iterator<Vertice> a = lista.iteradorLista();
    Vertice aux = null;
    while(a.hasNext()){
        aux = a.next();
        if(aux.elemento.equals(v.elemento))
            return true;
    }
    return false;
}
    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
