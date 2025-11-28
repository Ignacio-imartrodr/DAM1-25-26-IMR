package UD3;

import java.util.Random;
/**
 * @author Ignacio MR
 */
public class GeneraArrayAleatorio {
    static String stringArray(int[] array) {
        String arrayStr="";
            for (int i = 0; i < array.length; i++) {
                if (i == 0) {
                    arrayStr +='[';
                } else if (i>0 && i<array.length) {
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
            random=rnd.nextInt(19) -9;
            arrayRnd[i]=random;
        }
        return arrayRnd;
    }
    public static void main(String[] args) {
        final int cantNum = 4;
        System.out.println(stringArray(arrayAleatorio(cantNum)));
    }
}
