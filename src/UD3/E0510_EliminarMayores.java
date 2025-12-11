package UD3;

import java.util.Arrays;

/**
 * E0510. Escribir la función: int[] eliminarMayores(int t[], int valor) que crea y devuelve una copia de la 
 * tabla t donde se han eliminado todos los elementos que son mayores que valor.  
 * 
 * @author Ignacio MR
 */
public class E0510_EliminarMayores {
    static String stringArray(int[] array) {
        String arrayStr = "[ ";
        if (array.length == 0) {
            arrayStr += ']';
            return arrayStr;
        }
        for (int i = 0; i < array.length; i++) {
            if (i > 0 && i < array.length) {
                arrayStr += " | ";
            }
            arrayStr += array[i];
            if (i == array.length - 1) {
                arrayStr += " ]";
            }
        }
        return arrayStr;
    }
    static int[] eliminarMayores(int t[], int valor){
        int PositionSiguienteMenor = 0;
        int[] tNew = t.clone();
        int intercambio;
        int cantMayores=0;
        for (int i = 0; i < tNew.length; i++) {
            if (tNew[i] > valor) cantMayores++;
        }
        for (int i = 0; i < tNew.length - cantMayores; i++) {
            if (tNew[i] > valor) {
                while (tNew[i + PositionSiguienteMenor] > valor && PositionSiguienteMenor < tNew.length) {
                    PositionSiguienteMenor++;
                }
                intercambio = tNew[i];
                tNew[i] = tNew[i + PositionSiguienteMenor];
                tNew[i + PositionSiguienteMenor] = intercambio;
                PositionSiguienteMenor = 0;
            }
        }
        tNew = Arrays.copyOfRange(tNew, 0, tNew.length-cantMayores);
        return tNew;
    }
    public static void main(String[] args) {
        final int VALOR = 5;
        int[] t ={2, 1, 8, 3, 5, 2, 3, 6, 1};
        //int[] t ={1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println("Original: " + stringArray(t) + " Copia con valores menores: " + stringArray(eliminarMayores(t, VALOR)));
    }
}
