package UD3;

import java.util.Random;
import java.util.Scanner;

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
        System.out.println("     A  B  C  D  E  F  G  H"); // Letras de columnas
        System.out.println();
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                if (j == 0) {
                    System.out.print((8 - i) + "    "); // Números de filas
                }
                System.out.print(t[i][j] + "  ");
            }
            System.out.print("  " + (8 - i)); // Números de filas
            System.out.println();
        }
        System.out.println();
        System.out.println("     a  b  c  d  e  f  g  h"); // Letras de columnas
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
    static Scanner sc = new Scanner(System.in);
    static int[] leerMovimiento() {
        char[] letras = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        char[] numeros = {'1', '2', '3', '4', '5', '6', '7', '8'};
        boolean letrasMal = true;
        boolean letraOrigenValida = false; 
        boolean letraDestinoValida = false; 
        int[] origen = new int[2];
        int[] destino = new int[2];
        do {
            System.out.print("Introduce el movimiento (formato a2 b3): ");
            String movimiento = sc.nextLine();
            origen[0] = movimiento.charAt(0); // Columna (letra) origen
            origen[1] = (int) movimiento.charAt(1); // Fila (numero) origen
            destino[0] = movimiento.charAt(3); // Columna (letra) destino
            destino[1] = (int) movimiento.charAt(4); // Fila (letra) destino
            for (int i = 0; i < letras.length; i++) {
                if (origen[0] == letras[i]) { origen[0] = i; letraOrigenValida = true; }
                if (destino[0] == letras[i]) { destino[0] = i; letraDestinoValida = true; }
            }
            if (letraOrigenValida && letraDestinoValida) letrasMal = false;
            for (int i = 0; i < numeros.length; i++) {
                if (origen[1] == numeros[i]) origen[1] = i+1;
                if (destino[1] == numeros[i]) destino[1] = i+1;
            }
        } while (origen[1] > 8 || origen[1] < 1 || destino[1] > 8 || destino[1] < 1 || letrasMal);
        int[] movConFormato = {origen[0], origen[1], destino[0], destino[1]};
        return movConFormato;
    }
    static boolean movimientoPeon(int[] movConFormato, char[][] tablero, boolean turnoBlancas, int[][] historial){
        boolean movEsValido = false;
        int columnaInicioMovAnterior = historial[historial.length-1][1];
        int columnaFinalMovAnterior = historial[historial.length-1][3];
        int filaMovAnterior = historial[historial.length-1][4];
        int columnaOrigen = movConFormato[0];
        int filaOrigen = movConFormato[1];
        int columnaDestino = movConFormato[2];
        int filaDestino = movConFormato[3];
        if (turnoBlancas){
            if (filaDestino == filaOrigen - 1 && tablero[filaDestino][columnaDestino] == '-') { //avanzar si no hay piezas delante
                movEsValido = true;
            } else if (filaDestino == filaOrigen - 1 && (columnaDestino == columnaOrigen + 1 || columnaDestino == columnaOrigen - 1) && (tablero[filaDestino][columnaDestino] != '-' || (tablero[filaDestino][columnaDestino] == '-' && (tablero[filaOrigen][columnaDestino-1] == 'p' || tablero[filaOrigen][columnaDestino+1] == 'p') && (columnaInicioMovAnterior == columnaDestino && columnaFinalMovAnterior == columnaDestino && filaMovAnterior == filaDestino - 1)))){ //Capturar en diagonal y Anpasant
            //Captura diagonal
            //Si el peon avanza en diagonal filaDestino == filaOrigen - 1 && (columnaDestino == columnaOrigen + 1 || columnaDestino == columnaOrigen - 1)
            //Sí si hay una ficha que capturar tablero[filaDestino][columnaDestino] != '-'
            //Anpasant
            //O si hay un peon al lado (tablero[filaDestino][columnaDestino] == '-' && (tablero[filaOrigen][columnaDestino-1] == 'p' || tablero[filaOrigen][columnaDestino+1] == 'p')
            //Y el movimiento del rival fue avanzar recto el peon de en frente a al lado (columnaInicioMovAnterior == columnaDestino && columnaMovAnterior == columnaDestino && filaMovAnterior == filaDestino + 1)
                movEsValido = true;
            } else if (filaOrigen==tablero.length-2 && filaDestino == filaOrigen - 2 && tablero[filaDestino][columnaDestino] == '-'){ //Doble avance inicial
                movEsValido = true;
            }
        }else{
            if (filaDestino == filaOrigen + 1 && tablero[filaDestino][columnaDestino] == '-') { //avanzar si no hay piezas delante
                movEsValido = true;
            } else if (filaDestino == filaOrigen + 1 && (columnaDestino == columnaOrigen + 1 || columnaDestino == columnaOrigen - 1) && (tablero[filaDestino][columnaDestino] != '-' || (tablero[filaDestino][columnaDestino] == '-' && (tablero[filaOrigen][columnaDestino-1] == 'p' || tablero[filaOrigen][columnaDestino+1] == 'p') && (columnaInicioMovAnterior == columnaDestino && columnaFinalMovAnterior == columnaDestino && filaMovAnterior == filaDestino + 1)))){ //Capturar en diagonal y Anpasant
                movEsValido = true;
            } else if (filaOrigen==tablero.length-2 && filaDestino == filaOrigen + 2 && tablero[filaDestino][columnaDestino] == '-'){ //Doble avance inicial
                movEsValido = true;
            }
        }
        
        return movEsValido;
    }
    static boolean movimientoRei(int[] movConFormato, char[][] tablero,boolean turnoBlancas, int[][] historial){
        boolean movEsValido = false;
        int[] movimientoRey = {5,1, 5,8};//Rey blanco, Rey negro
        int[] movimientoTorre = {1,1 ,8,1, 1,8 ,8,8};//Torres
        int columnaOrigen = movConFormato[0];
        int filaOrigen = movConFormato[1];
        int columnaDestino = movConFormato[2];
        int filaDestino = movConFormato[3];
        boolean reyYTorreSinMover = true;
        for (int i = 0; i < historial.length; i++) {
            for (int j = 0; j < historial[i].length; j++) {
                if(historial[i]==){
                reyYTorreSinMover=false;
                break;
            }
            }
            
        }
        if (filaDestino == filaOrigen - 1 || filaDestino == filaOrigen + 1 || columnaDestino == columnaOrigen + 1 || columnaDestino == columnaOrigen - 1) {
            movEsValido = true;
        } else if(columnaDestino == columnaOrigen + 2 || columnaDestino == columnaOrigen - 3){

        }

        return movEsValido;
    }


    static boolean validarMovimiento(int[] movConFormato, char[][] tablero, boolean turnoBlancas) {
        boolean esValido = false;
        if (movConFormato[0] == movConFormato[2] && movConFormato[1] == movConFormato[3]) {
            return esValido; // Misma posición
        }
        if (turnoBlancas) {
            switch (tablero[movConFormato[0]][movConFormato[1]]) {
            case'P':
                
                break;
        
            default:
                break;
            }
        } else {
            switch (tablero[movConFormato[0]][movConFormato[1]]) {
            case'P':
                
                break;
        
            default:
                break;
            }
        }//TODO Historial
        return esValido;
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
        mostrarTableroConLeyenda(tablero);
        //boolean turnoBlancas = true;
        /*char[][] t = tableroAleatorio();

        mostrarTableroConLeyenda(tablero);
        System.out.println("Piezas por fila: " + stringArray(contarPiezasPorFila(tablero)));
        System.out.println("Piezas por columna: " + stringArray(contarPiezasPorColumnas(tablero)));
        System.out.println();
        mostrarTableroConLeyenda(t);
        System.out.println("Piezas por fila: " + stringArray(contarPiezasPorFila(t)));
        System.out.println("Piezas por columna: " + stringArray(contarPiezasPorColumnas(t)));
        */
       
    }
}
