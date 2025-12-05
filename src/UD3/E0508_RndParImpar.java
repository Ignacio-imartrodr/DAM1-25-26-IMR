package UD3;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Ignacio MR
 */

public class E0508_RndParImpar {
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
    static int[] arrayAleatorio(int n) {
        Random rnd = new Random();
        int random;
        int[] arrayRnd = new int[n];
        for (int i = 0; i < arrayRnd.length; i++) {
            random=rnd.nextInt(101);
            arrayRnd[i]=random;
        }
        return arrayRnd;
    }
    static int contar(int t[], boolean esPar){
        int cant = 0;
        for (int i = 0; i < t.length; i++) {
            if (esPar? t[i]%2==0 : t[i]%2!=0) {
                cant++;
            }
        }
        return cant;
    }
    static int[] getPares(int[] t){
        int[] pares = new int[contar(t, true)];
        int cont = 0;
        for (int i = 0; i < t.length; i++) {
            if (t[i]%2==0) {
                pares[cont] = t[i];
                cont++;
            }
        }
        Arrays.sort(pares);
        return pares;
    }
    static int[] getImpares(int[] t){
        int[] impares = new int[contar(t, false)];
        int cont = 0;
        for (int i = 0; i < t.length; i++) {
            if (t[i]%2!=0) {
                impares[cont] = t[i];
                cont++;
            }
        }
        Arrays.sort(impares);
        return impares;
    }
    public static void main(String[] args) {
        final int LONGITUD = 7;
        int[] t = arrayAleatorio(LONGITUD);
        System.out.println(stringArray(t));
        System.out.println(stringArray(getPares(t)));
        System.out.println(stringArray(getImpares(t)));
    }
}
