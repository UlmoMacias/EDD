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
	int r = 0, i = 0;
	while (l >= 4) {
	    r ^= (llave[i] << 24) | (llave[i+1] << 16) | (llave[i+2] << 8) | llave[i+3];
	    i += 4;
	    l -= 4;
	}
	int t = 0;
	switch (l) {
	case 3: t |= llave[i+2] << 8;
	case 2: t |= llave[i+1] << 16;
	case 1: t |= llave[i]   << 24;
	}
	r ^= t;
	return r;
    }
    /**
     * Función de dispersión de Bob Jenkins.
     * @param llave la llave a dispersar.
     * @return la dispersión de Bob Jenkins de la llave.
     */
    public static int dispersaBJ(byte[] llave) {
        int n = llave.length;
	
	int[] r = { 0x9e3779b9, 0x9e3779b9, 0xffffffff };
	
	int i = 0;
	while (n >= 12) {
	    r[0] = suma(r[0], llave[i] + (llave[i+1] <<  8) + (llave[i+2] << 16) + (llave[i+3] << 24));
	    r[1] = suma(r[1], llave[i+4] + (llave[i+5] <<  8) + (llave[i+6] << 16) + (llave[i+7] << 24));
	    r[2] = suma(r[2], llave[i+8] + (llave[i+9] << 8) + (llave[i+10] << 16) + (llave[i+11] << 24));
	    mezclaBJ(r);
	    i += 12;
	}
	    
	r[2] += n;
	switch (n - i) {
	case 11: r[2] = suma(r[2], llave[i+10] << 24);
	case 10: r[2] = suma(r[2], llave[i+9] << 16);
	case  9: r[2] = suma(r[2], llave[i+8] <<  8);
	case  8: r[1] = suma(r[1], llave[i+7] << 24);
	case  7: r[1] = suma(r[1], llave[i+6] << 16);
	case  6: r[1] = suma(r[1], llave[i+5] <<  8);
	case  5: r[1] = suma(r[1], llave[i+4]);
	case  4: r[0] = suma(r[0], llave[i+3] << 24);
	case  3: r[0] = suma(r[0], llave[i+2] << 16);
	case  2: r[0] = suma(r[0], llave[i+1] <<  8);
	case  1: r[0] = suma(r[0], llave[i]);
	}
	
	mezclaBJ(r);
	return r[2];
    }

    private static int suma(int a, int b) {
        long la = a;
        long lb = b;
        long r = (la + lb) & 0xffffffff;
        return (int)r;
    }
    
    private static byte suma(byte a, byte b) {
        int ia = a;
        int ib = b;
        int r = (ia + ib) & 0xff;
        return (byte)r;
    }

    private static void mezclaBJ(int[] r) {
        int a = r[0], b = r[1], c = r[2];
        a = suma(a, -b); a = suma(a, -c); a ^= (c >>> 13);
        b = suma(b, -c); b = suma(b, -a); b ^= (a << 8);
        c = suma(c, -a); c = suma(c, -b); c ^= (b >>> 13);
        a = suma(a, -b); a = suma(a, -c); a ^= (c >>> 12);
        b = suma(b, -c); b = suma(b, -a); b ^= (a << 16);
        c = suma(c, -a); c = suma(c, -b); c ^= (b >>> 5);
        a = suma(a, -b); a = suma(a, -c); a ^= (c >>> 3);
        b = suma(b, -c); b = suma(b, -a); b ^= (a << 10);
        c = suma(c, -a); c = suma(c, -b); c ^= (b >>> 15);
        r[0] = a; r[1] = b; r[2] = c;
    }


    
    /**
     * Función de dispersión Daniel J. Bernstein.
     * @param llave la llave a dispersar.
     * @return la dispersión de Daniel Bernstein de la llave.
     */
    public static int dispersaDJB(byte[] llave) {
	int h = 5381;
	for (int i = 0; i < llave.length; i++)
	    h = suma(multiplica(h, 33), llave[i]);
	return h;
    }

    private static int multiplica(int a, int b) {
        long la = a;
        long lb = b;
        long r = (la * lb) & 0xffffffff;
        return (int)r;
    }
}
