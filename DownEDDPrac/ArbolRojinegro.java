package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<tt>null</tt>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices de árboles rojinegros. La única
     * diferencia con los vértices de árbol binario, es que tienen un campo para
     * el color del vértice.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            super(elemento);
            color= Color.NINGUNO;

        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        public String toString() {
            if(this==null)
                return "";
            if(this.color== Color.ROJO)
                return "R{"+this.elemento.toString()+"}";
            return "N{"+this.elemento.toString()+"}";
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked")
            VerticeRojinegro vertice = (VerticeRojinegro)o;
            return color == vertice.color && super.equals(o);
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() {
        super();
    }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeRojinegro(elemento);
    }

    protected VerticeRojinegro convertirRojiNegro(VerticeArbolBinario<T> vertice){
        return (VerticeRojinegro)vertice;
    }
    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {  
        VerticeRojinegro aux = (VerticeRojinegro)vertice;
        return aux.color;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        if (elemento!=null) {
            super.agrega(elemento);
            VerticeRojinegro v = convertirRojiNegro(ultimoAgregado);
            v.color = Color.ROJO;
            rebalancea(v);
        }

    }

    private void rebalancea(VerticeRojinegro v){
        //Caso 1
        VerticeRojinegro p = padre(v);
        if (p==null) {
            v.color = Color.NEGRO;
            return;
        }
        //caso 2
        if(esNegro(p))
            return;
        VerticeRojinegro tio = tio(v);
        VerticeRojinegro abuelo = abuelo(v);
        //caso 3
        if (esRojo(tio)) {
            p.color=Color.NEGRO;
            tio.color= Color.NEGRO;
            abuelo.color=Color.ROJO;
            rebalancea(abuelo);
            return;
        }
        //caso 4
        if(cruzadosIzq(v)|| cruzadosDer(v)){
            if (esIzquierdo(p))
                super.giraIzquierda(p);
            else
                super.giraDerecha(p);
            VerticeRojinegro aux = v;
            v = p;
            p = aux;
        }
        //Caso 5
        p.color = Color.NEGRO;
        abuelo.color = Color.ROJO;
        if (esIzquierdo(v))
            super.giraDerecha(abuelo);
        else
            super.giraIzquierda(abuelo);
    }

    private boolean tiene2hijos(VerticeRojinegro vertice){
        return vertice.izquierdo!=null && vertice.derecho!=null;
    }

    private boolean hayAbuelo(VerticeRojinegro v){
        return v.hayPadre()&&v.padre.hayPadre();
    }
    private VerticeRojinegro padre(VerticeRojinegro vertice){
        if(vertice.padre!=null)
            return convertirRojiNegro(vertice.padre);
        return null;
    }
    private VerticeRojinegro abuelo(VerticeRojinegro v){
        if (hayAbuelo(v))
            return convertirRojiNegro(padre(padre(v)));
        return null;
    }

    private boolean hayTio(VerticeRojinegro v){
        if(hayAbuelo(v)){
            if(esIzquierdo(padre(v)))
                return abuelo(v).derecho!=null;
            return abuelo(v).izquierdo != null;
        }
        return false;
    }

    private boolean esIzquierdo(VerticeRojinegro v){
        if (v.padre.izquierdo == v)
            return true;
        return false;
    }
    private boolean esDerecho(VerticeRojinegro v){
        if (v.padre.derecho == v)
            return true;
        return false;
    }

    private VerticeRojinegro Hermano(VerticeRojinegro v){
        if (hayHermano(v)) {
            if (esDerecho(v))
                return convertirRojiNegro(v.padre.izquierdo);
            return convertirRojiNegro(v.padre.derecho);
        }
        return null;
    }

    private boolean hayHermano(VerticeRojinegro v){
        return tiene2hijos(padre(v));
    }

    private VerticeRojinegro tio(VerticeRojinegro v){
        if (hayTio(v)) {
            if (esDerecho(convertirRojiNegro(v.padre)))
                return convertirRojiNegro(abuelo(v).izquierdo);
            VerticeRojinegro v1= abuelo(v);
            return convertirRojiNegro(v1.derecho);
        }
        return null;
    }

    private boolean cruzadosIzq(VerticeRojinegro v){
        if (esIzquierdo((VerticeRojinegro)v.padre) && esDerecho(v))
            return true;
        return false;
    }
    private boolean cruzadosDer(VerticeRojinegro v){
        if (esIzquierdo(v) && esDerecho((VerticeRojinegro)v.padre))
            return true;
        return false;
    }

    private boolean esRojo(VerticeRojinegro vertice){
        return vertice !=null && vertice.color == Color.ROJO;
    }
    private boolean esNegro(VerticeRojinegro vertice){
        return vertice ==null || vertice.color == Color.NEGRO;
    }
    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        VerticeRojinegro v = convertirRojiNegro(super.busca(elemento));
      if(v==null){
        return;
    }

    if(v.izquierdo!=null && v.derecho!=null){
        v=convertirRojiNegro(super.intercambiaEliminable(v));
    }
    if(v.izquierdo==null && v.derecho==null){
        VerticeRojinegro f = fantasma();
        v.izquierdo = f;
        f.padre=v;
    }
    VerticeRojinegro h = v.derecho!=null ? convertirRojiNegro(v.derecho):convertirRojiNegro(v.izquierdo);
    Color copia = v.color;
    super.eliminaVertice(v);
    super.elementos--;
    if(esRojo(h)){
        h.color=Color.NEGRO;
        return;
    }
    if(copia==Color.ROJO&&esNegro(h)){
        if(h.elemento==null){
            if(padre(h)!=null){
                VerticeRojinegro p = padre(h);
                if(esIzquierdo(h)){
                    p.izquierdo=null;
                }else{
                    p.derecho=null;
                }
            }else{
                raiz=null;
            }

        }
    }

    if(copia==Color.NEGRO&&esNegro(h)){
        rebalancea2(h);
        if(h.elemento==null){
            if(padre(h)!=null){
                VerticeRojinegro p = padre(h);
                if(esIzquierdo(h)){
                    p.izquierdo=null;
                }else{
                    p.derecho=null;
                }
            }else{
                raiz=null;
            }

        }
    }

}  

private VerticeRojinegro fantasma(){
    VerticeRojinegro fantasma = convertirRojiNegro(nuevoVertice(null));
    fantasma.color = Color.NEGRO;
    return fantasma;
}

private void rebalancea2(VerticeRojinegro v){
        //Caso 1
        if(padre(v) == null)
            return;
        VerticeRojinegro p=padre(v);
        VerticeRojinegro h = Hermano(v);
        //Caso 2
        if(esRojo(h)){
            p.color=Color.ROJO;
            h.color=Color.NEGRO;
            
            if(esIzquierdo(v)){
                super.giraIzquierda(p);
                h = Hermano(v);
                
            }else{
                super.giraDerecha(p);
                h=Hermano(v);

            }
        }
        VerticeRojinegro hijoI = convertirRojiNegro(h.izquierdo);
        VerticeRojinegro hijoD = convertirRojiNegro(h.derecho);
            //Caso 3
            if(esNegro(hijoI) && esNegro(hijoD) && esNegro(h) && esNegro(p) && esNegro(v)){
                h.color = Color.ROJO;
                rebalancea2(p);
                return;
            }
            //Caso 4
            if(esNegro(hijoI) && esNegro(hijoD) && esNegro(h) && esRojo(p) && esNegro(v)){
                p.color = Color.NEGRO;
                h.color = Color.ROJO;
                return;
            }
            //Caso 5
            if((esIzquierdo(v) && esRojo(hijoI) && esNegro(hijoD)) || (esDerecho(v) && esNegro(hijoI) && esRojo(hijoD))){
                if(esRojo(hijoI)){
                    hijoI.color=Color.NEGRO;
                }else{
                    hijoD.color=Color.NEGRO;
                }
                h.color=Color.ROJO;
                if(esIzquierdo(v)){
                    super.giraDerecha(h);
                }else{
                    super.giraIzquierda(h);
                }
                h=Hermano(v);
                hijoI=convertirRojiNegro(h.izquierdo);
                hijoD=convertirRojiNegro(h.derecho);
            }
            //Caso 6
            Color copia = p.color;
            h.color=copia;
            p.color=Color.NEGRO;
            if(esIzquierdo(v)){
                hijoD.color=Color.NEGRO;
                super.giraIzquierda(p);
            }else{
                hijoI.color=Color.NEGRO;
                super.giraDerecha(p);
            }


        }
    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
            "pueden girar a la izquierda " +
            "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
            "pueden girar a la derecha " +
            "por el usuario.");
    }
}
