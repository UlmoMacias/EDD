package mx.unam.ciencias.edd.proyecto3;
import mx.unam.ciencias.edd.*;
import java.util.Iterator;
public class GraficaPastel{
    /** String svg de la cadena de la grafica*/
    private String svg="";

    /**punto para guardar las coordenadas del segmento */
    private Punto punto1;

    /**Número de elementos  */
    private int elementos;
	public GraficaPastel(Lista<Palabra> a, int n){
        elementos=n;
        IteradorLista<Palabra> iterador = a.iteradorLista();
        iterador.end();
        String[] colores = {"#0140CA", "#7FFF00", "#16A6FE", "#DD1812"};
        int veces = a.getElementos()-5;
        double aux =0; 
        punto1 = new Punto(230,120);
        double x1 = 230;
        double y1 = 120;
        Palabra auxiliar2 =null;
        double porcentajeUsado=0; 
        svg+="<center>\n";
		svg+="<svg width=\"260\" height=\"250\">\n";
        svg+="<g>\n";
        svg+="<CIRCLE cx=\"130\" cy=\"120\" r=\"100\" style=\"fill:#90EE90;\"/>\n";
        switch(veces){
            case -4:
            break;
            case -3:
                auxiliar2 = iterador.previous();
                aux = porcentaje(auxiliar2.repeticiones);
                if(aux > 50){
                    auxiliar2 = iterador.previous();
                    aux = porcentaje(auxiliar2.repeticiones);
                }
                porcentajeUsado+=aux;
                System.out.println(aux);
                x1 = punto1.X();
                y1 = punto1.Y();
                coorPedazo(porcentajeUsado);
                svg+=pedazoGrafica(x1,y1,punto1.X(),punto1.Y(),colores[0]);
            break;
            case -2:
            for (int i =0;i<2 ; i++) {
                auxiliar2 = iterador.previous();
                aux = porcentaje(auxiliar2.repeticiones);
                if(aux > 50){
                    auxiliar2 = iterador.previous();
                    aux = porcentaje(auxiliar2.repeticiones);
                }
                porcentajeUsado+=aux;
                x1 = punto1.X();
                y1 = punto1.Y();
                coorPedazo(porcentajeUsado);
                svg+=pedazoGrafica(x1,y1,punto1.X(),punto1.Y(),colores[i]);                
            }
            break;
            case -1:
            for (int i=0;i<3 ;i++ ) {
                auxiliar2 = iterador.previous();
                aux = porcentaje(auxiliar2.repeticiones);
                if(aux > 50){
                    auxiliar2 = iterador.previous();
                    aux = porcentaje(auxiliar2.repeticiones);
                }
                porcentajeUsado+=aux;
                x1 = punto1.X();
                y1 = punto1.Y();
                coorPedazo(porcentajeUsado);
                svg+=pedazoGrafica(x1,y1,punto1.X(),punto1.Y(),colores[i]);
            }
            break;
            default:
                for(int i=0;i<4;i++){
                auxiliar2 = iterador.previous();
                aux = porcentaje(auxiliar2.repeticiones);
                if(aux > 50){
                    auxiliar2 = iterador.previous();
                    aux = porcentaje(auxiliar2.repeticiones);
                }
                porcentajeUsado+=aux;
                x1 = punto1.X();
                y1 = punto1.Y();
                coorPedazo(porcentajeUsado);
                svg+=pedazoGrafica(x1,y1,punto1.X(),punto1.Y(),colores[i]);
                }
                break;

        }
        svg+="</g>\n";
        svg+="</svg>\n";
        svg+="</center>\n";
    }   
    /**
     *Método para obtener el codigo SVG de la grafica.
     *@return svg correspondiente al codigo;
     */
    public String getSVG(){
        return svg;
    }

    /**
     *Método para sacar las coordenadas dado un porcentaje de la grafica
     */
    private void coorPedazo(double porcentaje){
        double rad = rad(angulo(porcentaje));
        double x1 = 130+100* Math.cos(rad);
        double y1 = 120+100* Math.sin(rad);
        punto1.setCoor(x1,y1);
    }

    /**
     *Método para obtener el angulo del ciculo dado un porcentaje
     *@retunr angulo double angulo en grados de la grafica.
     */
    private double angulo(double porcentaje){
        return (360*porcentaje)/100;
    }

    /**
     *Método para transformar los grados del angulo a Radianes.
     *@param angulo double angulo en grados.
     *@return angulo double en radianes.
     */
    private double rad(double angulo){
        return (Math.PI /180)*angulo;
    }

    /**
     *Método para definir los un pezado de la grafica
     *@param l1 double dado por la coordenada x de L
     *@param l2 double dado por la coordenada y de L
     *@param z1 double dado por la coordenada x de Z
     *@param z2 double dado por la coordenada y de Z
     *@param color String color del pezado de la grafica
     *@return pedazo grafica String codigo svg del pedazo de grafica.
     */
    private String pedazoGrafica(double l1, double l2, double z1, double z2, String color){
      return  "<path d=\"M 130, 120 L "+l1+","+l2+" A 100,100 0 0, 1 "+z1+","+z2+" z\" fill=\""+color+"\" stroke=\"#fff\" stroke-width=\"2\"></path>\n";
    }

    /**
     *Método para calcular un porcentaje dado el numero de repeticiones de la palabra.
     *@param n int numero de repeticiones de la palabra
     *@return porcentaje double porcentaje dado el numero de repeticiones de una palabra.
     */
    private double porcentaje(int n){
        return (n*100)/elementos;
    }
}