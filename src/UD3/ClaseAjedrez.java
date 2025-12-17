package UD3;

import java.util.Arrays;

/**
 * @author Ignacio MR
 */
public class ClaseAjedrez {
    static String stringArray(int[] array) {
        String arrayStr = "[ ";
        if (array.length == 0) {
            arrayStr += ']';
            return arrayStr;
        }
        for (int i = 0; i < array.length; i++) {
            if (i > 0 && i < array.length) {
                arrayStr += " | ";
            }
            arrayStr += array[i];
            if (i == array.length - 1) {
                arrayStr += " ]";
            }
        }
        return arrayStr;
    }
    static char[][] tableroVacio() {
        char[][] t = new char[8][8];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                t[i][j] = '-'; // Casilla vacía
            }
        }
        return t;
    }
    static void mostrarTableroColoresCasillas(char[][] t) {
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                if ((i + j) % 2 == 0) {
                    System.out.print((t[i][j]='B') + "  "); // Casilla blanca
                } else {
                    System.out.print((t[i][j]='N') + "  "); // Casilla negra
                }
            }
            System.out.println();
        }
    }
    static void mostrarTablero(char[][] t) {
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                if ((i + j) % 2 == 0) {
                    System.out.print(t[i][j] + "  "); // Casilla blanca
                } else {
                    System.out.print(t[i][j] + "  "); // Casilla negra
                }
            }
            System.out.println();
        }
    }
    static char [][] inicializarTablero() {
        char[][] t = tableroVacio();
        // Colocar piezas negras
        t[0] = new char[] {'t', 'c', 'a', 'd', 'r', 'a', 'c', 't'};
        t[1] = new char[] {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'};
        // Colocar piezas blancas
        t[6] = new char[] {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'};
        t[7] = new char[] {'T', 'C', 'A', 'D', 'R', 'A', 'C', 'T'};
        return t;
    }
    static void mostrarTableroConLeyenda(char[][] t) {
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                if (j == 0) {
                    System.out.print((8 - i) + "    "); // Números de filas
                }
                System.out.print(t[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.print("     A  B  C  D  E  F  G  H"); // Letras de columnas
        System.arraycopy(t, 0, t, 0, t.length);
    }
    static int[] contarPiezasPorFila(char[][] t) {
        int[] piezasPorFila = new int[8];
        for (int i = 0; i < t.length; i++) {
            int contador = 0;
            for (int j = 0; j < t[i].length; j++) {
                if (t[i][j] != '-') {
                    contador++;
                }
            }
            piezasPorFila[i] = contador;
        }
        return piezasPorFila;
    }
    static int[] contarPiezasPorColumnas(char[][] t) {
        int[] piezasPorColumnas = new int[8];
        for (int j = 0; j < t[0].length; j++) {
            int contador = 0;
            for (int i = 0; i < t.length; i++) {
                if (t[i][j] != '-') {
                    contador++;
                }
            }
            piezasPorColumnas[j] = contador;
        }
        return piezasPorColumnas;
    }
    static int[] posicionAleatoriaVacia(char[][] t){
        int[] posVacia = new int[2];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t.length; j++) {
                if (t[i][j] != '-') {
                    posVacia[0]=i;
                    posVacia[1]=j;
                }
            }
        }
        return posVacia;
    }
    static char[][] tableroAleatorio(){
        char[] piezasNegras ={'t', 'c', 'a', 'd', 'r', 'p'};
        char[] piezasBlancas ={'T', 'c', 'a', 'd', 'r', 'p'};
        char[][] tAleatorio = tableroVacio();

        return tAleatorio;
    }
    public static void main(String[] args) {
        /*mostrarTableroColoresCasillas(tableroVacio());
        System.out.println();
        mostrarTablero(inicializarTablero());
        System.out.println();
        mostrarTableroConLeyenda(inicializarTablero());*/
        char[][] tablero = inicializarTablero();
        System.out.println(stringArray(contarPiezasPorFila(tablero)));
        System.out.println(stringArray(contarPiezasPorColumnas(tablero)));
    }       
}
