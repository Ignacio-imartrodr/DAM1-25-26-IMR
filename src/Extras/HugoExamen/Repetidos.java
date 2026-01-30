package Extras.HugoExamen;

import java.util.Arrays;

/**
 * @author Hugo Romay
 */

public class Repetidos {
    public static int[] repetidos(int t[]) {
        if (t == null) {
            return null;
        }
        if (t.length == 0) {
            return new int[0];
        }

        int[] nRepetidos = new int[0];

        for (int i = 0; i < t.length; i++) {
            int actual = t[i];
            for (int j = 0; j < t.length; j++) {
                if (i != j) {
                    nRepetidos = Arrays.copyOf(nRepetidos, nRepetidos.length + 1);
                    nRepetidos[nRepetidos.length - 1] = actual;
                }
            }
        }
        return nRepetidos;
        
    }
    public static void main(String[] args) {
        int[] t = {1, 3, 5, 3, 7, 4, 2, 8, 2, 4, 6, 2, 6};
        System.out.println(Arrays.toString(repetidos(t)));
    }
}
