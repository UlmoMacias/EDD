import java.time.temporal.JulianFields;
import java.lang.*;

public class CalTest {

    public static String convertDate(String fecha) {
        fecha = fecha.replaceAll("-", "");
        fecha = fecha.replaceAll("/", "");
        fecha = fecha.replaceAll(" ", "");
        fecha = fecha.replaceAll("_", "");
        return fecha;
    }

    public static void main(String[] args) {
        String cadena = args[0] + args [1] + args [2];
        cadena = "13/09-1998";
        int dia = 22;
        System.out.println(dia.length());
        cadena = convertDate(cadena);
        System.out.println(cadena.substring(0,2));
        System.out.println(cadena.substring(2,4));
        System.out.println(cadena.substring(4,cadena.length()));
    }

}