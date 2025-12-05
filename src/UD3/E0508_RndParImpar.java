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
    //Sin modificar el t principal
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
    //Sin modificar el t principal
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
    //Modificando el t principal
    static void getParesMod(int[] t){
        int cont = 1;
        int cantNegativos = 0;
        for (int i = 0; i < t.length; i++) {
            if (t[i]%2 != 0) {
                t[i] = -1;
                cantNegativos++;
            }
        }
        System.out.println(t);
        for (int i = 0; i < t.length; i++) {
            if (t[i] == -1) {
                while (t[t.length-cont] == -1 && cont < t.length) {
                    cont++;
                }
                t[i] = t[t.length-cont];
                cont++;
            }
        }
        //Solo corta el array nuevo, el dado no.(Array.CopyOfRange)

        System.arraycopy(t,0, t, 0, t.length-cantNegativos);
        Arrays.sort(t);
    }
    public static void main(String[] args) {
        final int LONGITUD = 7;
        int[] t = arrayAleatorio(LONGITUD);
        System.out.println(stringArray(t));
        System.out.println(stringArray(getPares(t)));
        System.out.println(stringArray(getImpares(t)));
        ;
        System.out.println(stringArray(t));
    }
}
