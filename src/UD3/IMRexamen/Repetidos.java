package UD3.IMRexamen;

import java.util.Arrays;

/**
 * @author Ignacio MR
 */
public class Repetidos {
    public static void main(String[] args) {
        int[] array = new int[] { 1, 3, 5, 3, 7, 4, 2, 8, 2, 4, 6, 2, 6 };
        System.out.println(Arrays.toString(repetidos(array)));

    }

    static int[] repetidos(int t[]) {
        int[] respuesta = new int[0];
        if (t == null || t == new int[t.length]) {
            return respuesta;
        }
        boolean repetidos = false;
        boolean enRespuesta = false;
        int variable;
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t.length; j++) {
                if (j != i) {
                    variable = t[i];
                    if (variable == t[j]) {
                        repetidos = true;
                        for (int j2 = 0; j2 < respuesta.length; j2++) {
                            if (variable == respuesta[j2]) {
                                enRespuesta = true;
                            }
                        }
                        if (!enRespuesta) {
                            respuesta = Arrays.copyOf(respuesta, respuesta.length + 1);
                            respuesta[respuesta.length - 1] = variable;
                        }
                        enRespuesta = false;
                    }
                }

            }
        }
        if (!repetidos) {
            return new int[0];
        }
        Arrays.sort(respuesta);
        invertirArray(respuesta);
        return respuesta;
    }
    static void invertirArray(int[] array) {
        int j = array.length-1;
        int aux;
        for (int i = 0; i < array.length/2; i++) {
            aux = array[i];
            array[i] = array[j];
            array[j] = aux;
            j--;
        }   
    }
}
