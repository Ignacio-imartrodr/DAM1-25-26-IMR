package UD2.IMRexamenResuelto;

/**
 * @author Ignacio MR
 */

public class SumaDescendente {
    static int sumaDescendente(int n) {
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
        return res;
    }

    public static void main(String[] args) {
        final int n = 12;
        System.out.println(sumaDescendente(n));
    }
}
