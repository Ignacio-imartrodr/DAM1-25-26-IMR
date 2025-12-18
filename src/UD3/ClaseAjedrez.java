package UD3;

import java.util.Random;

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
                    System.out.print((t[i][j] = 'B') + "  "); // Casilla blanca
                } else {
                    System.out.print((t[i][j] = 'N') + "  "); // Casilla negra
                }
            }
            System.out.println();
        }
    }

    static void mostrarTablero(char[][] t) {
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                if ((i + j) % 2 == 0) {
                    System.out.print(t[i][j] + "  ");
                } else {
                    System.out.print(t[i][j] + "  ");
                }
            }
            System.out.println();
        }
    }

    static char[][] inicializarTablero() {
        char[][] t = tableroVacio();
        // Colocar piezas negras
        t[0] = new char[] { 't', 'c', 'a', 'd', 'r', 'a', 'c', 't' };
        t[1] = new char[] { 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p' };
        // Colocar piezas blancas
        t[6] = new char[] { 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P' };
        t[7] = new char[] { 'T', 'C', 'A', 'D', 'R', 'A', 'C', 'T' };
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
        System.out.println("     A  B  C  D  E  F  G  H"); // Letras de columnas
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

    static int[] posicionAleatoriaVacia(char[][] t) {
        Random rnd = new Random();
        int posRandomF;
        int posRandomC;
        int[] posVacia = new int[2];
        do {
            posRandomF = rnd.nextInt(t.length);
            posRandomC = rnd.nextInt(t.length);
        } while (t[posRandomF][posRandomC] != '-');

        posVacia[0] = posRandomF;
        posVacia[1] = posRandomC;

        return posVacia;
    }

    static char[][] tableroAleatorio() {
        //char[] piezasNegras = {'t', 'c', 'a', 'd', 'r', 'p'};  //Todo el set de piezas
        //char[] piezasBlancas = {'T', 'C', 'A', 'D', 'R', 'P'}; //Todo el set de piezas
        char[] piezasNegras = {'r'}; //Caso del ejercio
        char[] piezasBlancas = {'R', 'P'}; //Caso del ejercio
        char[][] tAleatorio = tableroVacio();
        int[] posRndVacia;
        for (int i = 0; i < piezasNegras.length; i++) {
            if(piezasNegras[i] == 'p') {
                for (int j = 0; j < tAleatorio[2].length; j++) {
                    posRndVacia=(posicionAleatoriaVacia(tAleatorio));
                    tAleatorio[posRndVacia[0]][posRndVacia[1]]=piezasNegras[i];
                }
            } 
            else {
                posRndVacia=(posicionAleatoriaVacia(tAleatorio));
                tAleatorio[posRndVacia[0]][posRndVacia[1]]=piezasNegras[i];
            }
        }
        for (int i = 0; i < piezasBlancas.length; i++) {
            if(piezasBlancas[i] ==  'P') {
                // Caso general para todo el set de piezas
                /*for (int j = 0; j < tAleatorio[tAleatorio.length-2].length; j++) {
                    posRndVacia=(posicionAleatoriaVacia(tAleatorio));
                    tAleatorio[posRndVacia[0]][posRndVacia[1]]=piezasBlancas[i];
                }*/
                for (int j = 0; j < 4; j++) {//Caso ejercicio
                    do {
                        posRndVacia=(posicionAleatoriaVacia(tAleatorio));
                    } while (posRndVacia[0]==0 || posRndVacia[0] == tAleatorio.length-1 );
                    tAleatorio[posRndVacia[0]][posRndVacia[1]]=piezasBlancas[i];
                }
            } 
            else {
                posRndVacia=(posicionAleatoriaVacia(tAleatorio));
                tAleatorio[posRndVacia[0]][posRndVacia[1]]=piezasBlancas[i];
            }
        }
        
        return tAleatorio;
    }

    public static void main(String[] args) {
        /*
        mostrarTableroColoresCasillas(tableroVacio());
        System.out.println();
        mostrarTablero(inicializarTablero());
        System.out.println();
        mostrarTableroConLeyenda(inicializarTablero());
         */
        char[][] tablero = inicializarTablero();
        char[][] t = tableroAleatorio();

        mostrarTableroConLeyenda(tablero);
        System.out.println("Piezas por fila: " + stringArray(contarPiezasPorFila(tablero)));
        System.out.println("Piezas por columna: " + stringArray(contarPiezasPorColumnas(tablero)));
        System.out.println();
        mostrarTableroConLeyenda(t);
        System.out.println("Piezas por fila: " + stringArray(contarPiezasPorFila(t)));
        System.out.println("Piezas por columna: " + stringArray(contarPiezasPorColumnas(t)));
    }
}
