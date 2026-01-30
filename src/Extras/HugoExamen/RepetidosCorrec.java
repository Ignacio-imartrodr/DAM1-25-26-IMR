package Extras.HugoExamen;

import java.util.Arrays;

/**
 * @author Hugo Romay
 */

public class RepetidosCorrec {
    public static int[] repetidos(int t[]) {
        if (t == null) {
            return null;
        }
        if (t.length == 0) {
            return new int[0];
        }

        int[] nRepetidos = new int[0];
        boolean enRespuesta = false;

        for (int i = 0; i < t.length; i++) {
            int actual = t[i];
            for (int j = 0; j < t.length; j++) {
                if (i != j) {
                    if (actual == t[j]) {
                        for (int k = 0; k < nRepetidos.length; k++) {
                            if (actual == nRepetidos[k]) {
                                enRespuesta = true;
                            }
                        }
                        if (!enRespuesta) {
                            nRepetidos = Arrays.copyOf(nRepetidos, nRepetidos.length + 1);
                            nRepetidos[nRepetidos.length - 1] = actual;
                        }  
                        enRespuesta = false;
                    }
                }
            }
        }
        //Te faltaba ordenar e invertir el array
        Arrays.sort(nRepetidos);
        int j = nRepetidos.length-1;
        int aux;
        for (int i = 0; i < nRepetidos.length/2; i++) {
            aux = nRepetidos[i];
            nRepetidos[i] = nRepetidos[j];
            nRepetidos[j] = aux;
            j--;
        }   
        return nRepetidos;
        
    }
    public static void main(String[] args) {
        int[] t = {1, 3, 5, 3, 7, 4, 2, 8, 2, 4, 6, 2, 6};
        System.out.println(Arrays.toString(repetidos(t)));
    }
}
