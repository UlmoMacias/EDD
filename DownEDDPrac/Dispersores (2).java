package mx.unam.ciencias.edd;

/**
 * Clase para métodos estáticos con dispersores de bytes.
 */
public class Dispersores {

    /**
     * Función de dispersión XOR.
     * @param llave la llave a dispersar.
     * @return la dispersión de XOR de la llave.
     */
    public static int dispersaXOR(byte[] llave) {
        int l = llave.length;
	int i = 0;
	int r = 0;
	int t = 0;
	while(l >= 4){
	    r^= ((llave[i]& 0xFF)<<24) | ((llave[i+1] & 0xFF)<<16) | ((llave[i+2] & 0xFF)<<8) | (llave[i+3] & 0xFF);
	    l -= 4;
	    i += 4;
        }
	switch (l){
	case 3: t|=((llave[i+2] & 0xFF)<<8);
	case 2: t|=((llave[i+1] & 0xFF)<<16);
	case 1: t|=((llave[i] & 0xFF)<<24);
	}
	return r^t;
    }

    /**
     * Función de dispersión de Bob Jenkins.
     * @param llave la llave a dispersar.
     * @return la dispersión de Bob Jenkins de la llave.
     */
    public static int dispersaBJ(byte[] llave) {
	int n = llave.length;
	int l = llave.length;
	int i = 0;
	int a = 0x9e3779b9;
	int b = 0x9e3779b9;
	int c = 0xFFFFFFFF;
	while(l >= 12){
	    a+= (llave[i] & 0xFF) + ((llave[i+1] & 0xFF)<<8) + ((llave[i+2] & 0xFF)<<16) + ((llave[i+3] & 0xFF)<<24);
	    b+=(llave[i+4] & 0xFF) + ((llave[i+5] & 0xFF)<<8) + ((llave[i+6] & 0xFF)<<16) + ((llave[i+7] & 0xFF)<< 24);
	    c+=(llave[i+8] & 0xFF) + ((llave[i+9] & 0xFF)<<8) + ((llave[i+10] & 0xFF)<<16) + ((llave[i+11] & 0xFF)<<24);
	    a -=b;
	    a -=c;
	    a ^= (c>>>13);
	    b -= c;
	    b -= a;
	    b ^= (a<<8);
	    c -= a;
	    c -= b;
	    c ^= (b>>>13);
	    a -=b;
	    a -=c;
	    a ^= (c>>>12);
	    b -= c;
	    b -= a;
	    b ^= (a<<16);
	    c -= a;
	    c -= b;
	    c ^= (b>>>5);
	    a -=b;
	    a -=c;
	    a ^= (c>>>3);
	    b -= c;
	    b -= a;
	    b ^= (a<<10);
	    c -= a;
	    c -= b;
	    c ^= (b>>>15);	    	    	    
	    l-= 12;
	    i+= 12;
        }
	c+= n;
	switch (l){
	case 11: c+=((llave[i+10] & 0xFF)<<24);
	case 10: c+=((llave[i+9] & 0xFF)<<16);
	case 9:  c+=((llave[i+8] & 0xFF)<<8);
	case 8:  b+=((llave[i+7] & 0xFF)<<24);	    
	case 7:  b+=((llave[i+6] & 0xFF)<<16);
	case 6:  b+=((llave[i+5] & 0xFF)<<8);
	case 5:  b+=(llave[i+4] & 0xFF);
	case 4:  a+=((llave[i+3] & 0xFF)<<24);    
	case 3:  a+=((llave[i+2] & 0xFF)<<16);
	case 2:  a+=((llave[i+1] & 0xFF)<<8);
	case 1:  a+=(llave[i] & 0xFF);
	}
	a -=b;
	a -=c;
	a ^= (c>>>13);
	b -= c;
	b -= a;
	b ^= (a<<8);
	c -= a;
	c -= b;
	c ^= (b>>>13);
	a -=b;
	a -=c;
	a ^= (c>>>12);
	b -= c;
	b -= a;
	b ^= (a<<16);
	c -= a;
	c -= b;
	c ^= (b>>>5);
	a -=b;
	a -=c;
	a ^= (c>>>3);
	b -= c;
	b -= a;
	b ^= (a<<10);
	c -= a;
	c -= b;
	c ^= (b>>>15);
	return (int) c;	
    }

    /**
     * Función de dispersión Daniel J. Bernstein.
     * @param llave la llave a dispersar.
     * @return la dispersión de Daniel Bernstein de la llave.
     */
    public static int dispersaDJB(byte[] llave) {	
	int h = 5381 ;
	int n = llave.length;
	for (int i =0; i<n; i++){
	    h+= (h<<5)+ (llave[i] & 0xFF);
	}
	return h;
	
    }
}
