package UD3;

import java.util.Random;

/**
 * @author Ignacio MR
 */

public class E0501_Array100Random {
    static int[] arrayAleatorio(int n) {
        Random rnd = new Random();
        int random;
        int[] arrayRnd = new int[n];
        for (int i = 0; i < arrayRnd.length; i++) {
            random=rnd.nextInt(100) + 1;
            arrayRnd[i]=random;
        }
        return arrayRnd;
    }
    static int sumaArray(int t[]){
        int suma = 0;
        for (int i = 0; i < t.length; i++) {
            suma += t[i];
        }
        return suma;
    }
    public static void main(String[] args) {
        System.out.println(sumaArray(arrayAleatorio(10)));
    }
}
