package UD3;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Ignacio MR
 */

public class E0508_RndParImpar {

    static String stringArray(int[] array) {
        String arrayStr = "[";
        if (array.length == 0) {
            arrayStr += ']';
            return arrayStr;
        }
        for (int i = 0; i < array.length; i++) {
            if (i > 0 && i < array.length) {
                arrayStr += ',';
            }
            arrayStr += array[i];
            if (i == array.length - 1) {
                arrayStr += ']';
            }
        }
        return arrayStr;
    }

    static int[] arrayAleatorio(int n) {
        Random rnd = new Random();
        int random;
        int[] arrayRnd = new int[n];
        for (int i = 0; i < arrayRnd.length; i++) {
            random = rnd.nextInt(101);
            arrayRnd[i] = random;
        }
        return arrayRnd;
    }

    static int contar(int t[], boolean esPar) {
        int cant = 0;
        for (int i = 0; i < t.length; i++) {
            if (esPar ? t[i] % 2 == 0 : t[i] % 2 != 0) {
                cant++;
            }
        }
        return cant;
    }

    // Sin modificar el t principal
    static int[] getPares(int[] t) {
        int[] pares = new int[contar(t, true)];
        int cont = 0;
        for (int i = 0; i < t.length; i++) {
            if (t[i] % 2 == 0) {
                pares[cont] = t[i];
                cont++;
            }
        }
        Arrays.sort(pares);
        return pares;
    }

    // Sin modificar el t principal
    static int[] getImpares(int[] t) {
        int[] impares = new int[contar(t, false)];
        int cont = 0;
        for (int i = 0; i < t.length; i++) {
            if (t[i] % 2 != 0) {
                impares[cont] = t[i];
                cont++;
            }
        }
        Arrays.sort(impares);
        return impares;
    }

    // Modificando el t principal
    static String getImaparYParMod(int[] t) {
        int PositionSelcFromEnd = 1;
        int cantImpar = contar(t, false);
        int[] tPar = new int[t.length - cantImpar];
        int[] tImpar = new int[cantImpar];
        int intercambio;
        String respuesta;
        for (int i = 0; i < t.length - cantImpar; i++) {
            if (t[i] % 2 != 0) {
                while (t[t.length - PositionSelcFromEnd] % 2 != 0 && PositionSelcFromEnd < t.length) {
                    PositionSelcFromEnd++;
                }
                intercambio = t[i];
                t[i] = t[t.length - PositionSelcFromEnd];
                t[t.length - PositionSelcFromEnd] = intercambio;
                if (PositionSelcFromEnd != t.length)
                    PositionSelcFromEnd++;
            }
        }
        System.arraycopy(t, 0, tPar, 0, tPar.length);
        System.arraycopy(t, tPar.length, tImpar, 0, tImpar.length);
        Arrays.sort(tPar);
        Arrays.sort(tImpar);
        respuesta = "Pares: " + stringArray(tPar) + " Impares: " + stringArray(tImpar);
        return respuesta;
    }

    public static void main(String[] args) {
        final int LONGITUD = 7;
        int[] t = arrayAleatorio(LONGITUD);
        System.out.println(stringArray(t));
        System.out.println(stringArray(getPares(t)));
        System.out.println(stringArray(getImpares(t)));
        System.out.println(getImaparYParMod(t));
    }
}
