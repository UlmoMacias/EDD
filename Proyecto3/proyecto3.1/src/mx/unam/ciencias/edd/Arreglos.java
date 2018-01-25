package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
  */
public class Arreglos {
    public static <T>  void
    quickSort(T[] a, int ini, int fin, Comparator<T> comparador){
        if(ini>=fin)
            return;
        int i = ini+1;
        int j = fin;
        while(i<j){
            if (comparador.compare(a[i],a[ini])>=0 &&
                comparador.compare(a[j],a[ini])<0) {
                swap(a,i, j);
                i=i+1;
                j=j-1;
            }
            else if (comparador.compare(a[i],a[ini])<0)
                i=i+1;
            else
                j=j-1; 
        }
        if (comparador.compare(a[i],a[ini])>0)
                i=i-1;
            

        swap(a,ini,i);
        quickSort(a, ini, i-1, comparador);
        quickSort(a,i+1,fin, comparador);  
    }

    public static <T> void swap(T[] arreglo, int a, int b){
        T temp = arreglo[a];
        arreglo[a] = arreglo[b];
        arreglo[b] = temp;
    }

    /**
     * Ordena el arreglo recibido usando QuickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void
    quickSort(T[] arreglo, Comparator<T> comparador) {
        quickSort(arreglo,0,arreglo.length-1,comparador);
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
        int minimo;
        for (int i = 0; i <arreglo.length;i++ ) {
        	minimo = i;
        	for (int j = i+1;j < arreglo.length;j++ ) {
        		if (comparador.compare(arreglo[j],arreglo[minimo])<0) {
        			minimo = j;
        		}
        	}
        	swap(arreglo,i,minimo);
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
    	if (elemento == null) {
    		return -1;
    	}
        int ini = 0;
        int fin = arreglo.length-1;
        int mid = (ini+fin) / 2;
        while (ini<=fin) {
    		if (comparador.compare(arreglo[mid],elemento)== 0 )
    			return mid;
        	if (comparador.compare(arreglo[mid],elemento)< 0){
        		ini = mid + 1;
    			mid = (ini + fin)/2;
    		}
    		if (comparador.compare(arreglo[mid],elemento)>0){
    			fin = mid - 1;
    			mid = (ini + fin)/2;
    		}
        }
        if ( ini > fin )
        	return -1;
        return -1;
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
