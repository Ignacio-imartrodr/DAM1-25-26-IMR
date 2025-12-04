package UD3;

import java.util.Arrays;
import java.util.Random;

public class E0507_RndSinRepetidos {
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

    static int buscar(int t[], int clave) {
        for (int i = 0; i < t.length; i++) {
            if (t[i]==clave) {
                return i;
            }
        }
        return -1;
    }
    static int[] arrayAleatorio(int n) {
        Random rnd = new Random();
        int random;
        int[] arrayRnd = new int[n];
        for (int i = 0; i < arrayRnd.length; i++) {
            random=rnd.nextInt(10) + 1;
            arrayRnd[i]=random;
        }
        return arrayRnd;
    }
    static  int[] sustituirRepetidos(int[] t){
        int[] sinRep = t.clone();
        Random rnd = new Random();
        int posRep;
        for (int i = 0; i < sinRep.length; i++) {
            posRep = buscar(sinRep, sinRep[i]);
            while (posRep !=i && posRep != -1 && sinRep[i]==sinRep[posRep]) {
                int random;
                random=rnd.nextInt(10) + 1;
                sinRep[i] = random;
            }
        }
        return sinRep;
    }
    static int [] sinRepetidos(int[] t){
        int[] newArray = t.clone();
        int posRep;
        for (int i = 0; i < newArray.length; i++) {
            posRep = buscar(newArray, newArray[i]);
            while (posRep !=i && posRep != -1 && newArray[i]==newArray[posRep]) {
                newArray[i] = newArray[newArray.length-1];
                System.out.println(stringArray(newArray));
                newArray = Arrays.copyOfRange(newArray,0, newArray.length-1);
                System.out.println(stringArray(newArray));
                i--;
            }
        }
        return newArray;
    }
    public static void main(String[] args) {
        int[] tabla = arrayAleatorio(5);
        System.out.println(stringArray(tabla));
        System.out.println(stringArray(sinRepetidos(tabla)));
    }
}
