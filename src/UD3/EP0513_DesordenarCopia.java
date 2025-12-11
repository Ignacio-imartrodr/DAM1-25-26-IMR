package UD3;
import java.util.Random;

/**
 * EP0513. Implementa una variante de la función de la aplicación EP0512, de nombre 
 * copiaDesordenada(), que no modifique la tabla que se pasa como parámetro y, en su lugar, cree y 
 * devuelva una copia de la tabla donde se han desordenado los valores de los elementos. 
 * 
 * @author Ignacio MR
 */
public class EP0513_DesordenarCopia {
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
    static int[] copiaDesordenada(int[] t){
        int[] tDesord= t.clone();
        Random rnd = new Random();
        int PosRandom = rnd.nextInt(tDesord.length);
        int intercambio;
        for (int i = 0; i < tDesord.length; i++) {
            intercambio = tDesord[i];
            tDesord[i] = tDesord[PosRandom];
            tDesord[PosRandom] = intercambio;
            PosRandom = rnd.nextInt(tDesord.length);
        }
        return tDesord != t ? tDesord : copiaDesordenada(t);
    }
    public static void main(String[] args) {
        int[] t ={1, 2, 3, 4, 5};
        System.out.println("Original: " + stringArray(t) + " Copia desordenada: " + stringArray(copiaDesordenada(t)));
    }
}
