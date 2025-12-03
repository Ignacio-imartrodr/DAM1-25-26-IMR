package UD3;

import java.util.Arrays;
import java.util.Random;

/**
 * E0506_Primitiva. Definir una función que tome como parámetros dos tablas, la primera 
 * con los 6 números de una apuesta de la primitiva, y la segunda (ordenada) con los 6 
 * números de la combinación ganadora. La función devolverá el número de aciertos.
 * 
 * Además crea un método que devuelve una tabla de números enteros aleatorios entre dos 
 * números y de una longitud especificada.  
 * 
 * @author Ignacio MR
 */

public class E0506_Primitiva {
    static String stringArray(int[] array) {
        String arrayStr="[";
            for (int i = 0; i < array.length; i++) {
                if (i>0 && i<array.length) {
                    arrayStr +=',';
                }
                arrayStr += array[i];
                if (i == array.length - 1){
                    arrayStr +=']';
                }
            }
        return arrayStr;
    }
    static int numAciertos(int[] apuesta, int[] ganadora){
        int aciertos = 0;
        for (int i = 0; i < ganadora.length; i++) {
            if (apuesta[i]==ganadora[i]) {
                aciertos++;
            }
        }
        return aciertos;
    }
    static int[] tablaAleatoria(int numInicio, int numFin, int longitud){
        Random rnd = new Random();
        int random;
        int[] arrayRnd = new int[longitud];
        for (int i = 0; i < arrayRnd.length; i++) {
            random=rnd.nextInt(numFin-(numInicio-1)) + numInicio;
            arrayRnd[i]=random;
        }
        return arrayRnd;
    }
    public static void main(String[] args) {
        final int longPrimitiva = 6;
        final int numInicio = 0;
        final int numFin = 9;
        int[] ganadora = tablaAleatoria(numInicio, numFin, longPrimitiva);
        int[] apuesta = ganadora.clone();
        Arrays.sort(ganadora);
        System.out.println("Apuesta: " + stringArray(apuesta));
        System.out.println("Ganadora: " + stringArray(ganadora));
        System.out.println(numAciertos(apuesta, ganadora));
    }

}
