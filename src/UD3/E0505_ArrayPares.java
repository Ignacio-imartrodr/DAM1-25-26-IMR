package UD3;

import java.util.Arrays;
import java.util.Random;

/**
 *  E0505. Escribe una función que crea y devuelve una tabla ordenada de la longitud especificada rellena con 
 *  números pares aleatorios en el rango desde 2 hasta el valor de fin inclusive. 
 * 
 * @author Ignacio MR
 */

public class E0505_ArrayPares {
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
    static int[] rellenaPares(int longitud, int fin){
        Random rnd = new Random();
        int random;
        int[] pares = new int[longitud];
        for (int i = 0; i < longitud; i++) {
            do {
                random = rnd.nextInt(fin - 1) + 2;
            } while (random%2!=0);
            pares[i] = random;
        }
        Arrays.sort(pares);
        return pares;
    }
    public static void main(String[] args) {
        System.out.println(stringArray(rellenaPares(5, 20)));
    }
}
