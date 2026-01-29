package Extras.SamuExamen;

import java.util.Arrays;

/**
 * @author Samuel
 */
public class Repetidos {
    static int[] repetidos(int t[]) {
        if (t == null) {
            return null;
        }

        int tamaño = 0;
        int[] repetidos = new int[t.length];
        boolean continua = true;

        for (int i = 0; i < t.length - 1; i++) {
            continua = true;
            for (int j = i + 1; j < t.length && continua; j++) {
                if (t[i] == t[j] && Arrays.binarySearch(repetidos, t[i]) < 0) {
                    tamaño++;
                    continua = false;
                    repetidos[i] = t[i];
                }
            }
        }

        Arrays.sort(repetidos);
        int posicion = 0;

        for (int i = repetidos.length - 1; i >= 0; i--) {
            repetidos[posicion] = repetidos[i];
            posicion++;
        }
        repetidos = Arrays.copyOf(repetidos, tamaño);
        return repetidos;
    }

    public static void main(String[] args) {
        int t[] = { 1, 3, 5, 3, 7, 4, 2, 8, 2, 4, 6, 2, 6 };
        t = repetidos(t);
        System.out.println(Arrays.toString(t));
    }
}
