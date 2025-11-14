package UD2.IMRexamenCorregido;

/**
 * @author Ignacio MR
 */

public class SumaDescendente {
    static int sumaDescendente(int n) {
        /* Versión entregada
        int res = n;
        int cantDigitos = 0;
        int quitarPosiciones = 1;
        for (int i = 1; i < n; i *= 10) {
            cantDigitos++;
            quitarPosiciones *= 10;
        }

        for (int i = 0; i < cantDigitos; i++) {
            quitarPosiciones /= 10;
            res += (n % quitarPosiciones);
        }
        */
        //Optimización propia |
        //                    V
        int res = 0;
        for (int i = 1; i < n*10; i *= 10) {
            res += (n % i);
        }
        return res;
    }

    public static void main(String[] args) {
        final int n = 0102;
        System.out.println(sumaDescendente(n));
    }
}
