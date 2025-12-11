package UD3;
import java.util.Random;

/**
 * EP0512_Desordenar. Escribe una función que cambia de forma aleatoria los elementos contenidos en la tabla t.
 *  Si la tabla estuviera ordenada, dejaría de estarlo.
 * 
 * @author Ignacio MR
 * 
 */
public class EP0512_Desordenar {
    static String stringArray(int[] array) {
        String arrayStr = "[";
        if (array.length == 0) {
            arrayStr += ']';
            return arrayStr;
        }
        for (int i = 0; i < array.length; i++) {
            if (i > 0 && i < array.length) {
                arrayStr += '|';
            }
            arrayStr += array[i];
            if (i == array.length - 1) {
                arrayStr += ']';
            }
        }
        return arrayStr;
    }
    static void desordenar(int t[]){
        Random rnd = new Random();
        int PosRandom = rnd.nextInt(t.length);
        int intercambio;
        for (int i = 0; i < t.length; i++) {
            intercambio = t[i];
            t[i] = t[PosRandom];
            t[PosRandom] = intercambio;
            PosRandom = rnd.nextInt(t.length);
        }
    }
    public static void main(String[] args) {
        int[] t ={1, 2, 3, 4, 5};
        desordenar(t);
        System.out.println(stringArray(t));
    }
}
