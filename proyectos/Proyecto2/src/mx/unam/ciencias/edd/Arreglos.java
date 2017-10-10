package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
  */
public class Arreglos {

    private static <T> void intercambia(T[] arreglo, int i, int j){
	T aux = arreglo[i];
	arreglo[i] = arreglo [j];
	arreglo[j] = aux;
    }

    private static <T> void
	quickSort(T[] arreglo, Comparator<T> c,int i, int j ){
	if(i==j || j<i)
	    return;
	if(j+1 == i){
	    if(c.compare(arreglo[i],arreglo[j])<=0)
		return;
	    else
		intercambia(arreglo,i,j);
	    return;
	}
	int p = i;
	int pj = j;
	i=i+1;
	while(i<j){
	    if(c.compare(arreglo[i],arreglo[p]) <= 0){
		if(c.compare(arreglo[j], arreglo[p]) <= 0)
		   i=i+1;
	       else{
		    i=i+1;
		    j=j-1;
		}
	    }else{
		if(c.compare(arreglo[j],arreglo[p])>0)
		   j=j-1;
	       else{
		    intercambia(arreglo,i,j);
		    j=j-1;
		    i=i+1;
		}
	    }
	}
	if(j==i && c.compare(arreglo[i],arreglo[p])>0)
	    j=j-1;
	intercambia(arreglo, p, j);
	quickSort(arreglo, c, p, j-1);
	quickSort(arreglo, c, j+1, pj);
	
    }
    
    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void
    quickSort(T[] arreglo, Comparator<T> comparador) {
        quickSort(arreglo,comparador, 0, arreglo.length-1);
    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void
    selectionSort(T[] arreglo, Comparator<T> comparador) {
	int m;
        for(int i=0; i<arreglo.length; i++){
	    m = i;
	    for(int j=i+1; j<arreglo.length; j++)
		if(comparador.compare(arreglo[m],arreglo[j])>0)
		    m = j;
	    intercambia(arreglo,i,m);
	}
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    public static <T> int
	busquedaBinaria(T[] arreglo, T elemento, Comparator<T> c, int i, int j){
	int inicio = i;
	int fin = j;
	if(i>j)
	    return -1;
	if(j == i){
	    if(c.compare(arreglo[i],elemento)==0)
		return i;
	    return -1;
	}
	int b = (j+1-i)/2;
	if(c.compare(arreglo[b+i], elemento) == 0)
	    return b+i;
	if(c.compare(arreglo[b+i], elemento) < 0)
	    return busquedaBinaria(arreglo,elemento, c,b+1+i, fin);
	return busquedaBinaria(arreglo,elemento,c,inicio, b-1+i);
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int
    busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
	quickSort(arreglo, comparador);
        return busquedaBinaria(arreglo, elemento, comparador, 0, arreglo.length-1);
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int
    busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }
}
