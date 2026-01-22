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

    static String stringMatriz(int[][] t) {
        String Str = "";
        if (t.length == 0) {
            Str = "[]";
            return Str;
        }
        for (int i = 0; i < t.length; i++) {
            if (t[i] == null || t[i].length == 0) {
                if (i == 0) {
                    Str += "[]";
                } else {
                    Str += String.format("%n%s", "[]");
                }

            } else {
                Str += String.format("%n%s", "[ ");
                for (int j = 0; j < t[i].length; j++) {
                    if (j != t[i].length - 1) {
                        Str += t[i][j] + " | ";
                    } else {
                        Str += t[i][j] + " ]";
                    }
                }
            }
        }
        return Str;
    }

    static int[][] agregarAHistorial(int[][] historial, int[] mov) {
        if (mov == null) {
            return historial; // El jugador se rinde
        }
        int[][] historialAux = new int[historial.length + 1][];
        System.arraycopy(historial, 0, historialAux, 0, historial.length);
        historialAux[historialAux.length - 1] = mov;
        return historialAux;
    }
    static char[][] copiarTablero(char[][] tablero, char[][] tableroAux) {
        tableroAux = new char[tablero.length][];
        for (int i = 0; i < tablero.length; i++) {
            tableroAux[i] = new char[tablero[i].length];
            for (int j = 0; j < tablero[i].length; j++) {
                tableroAux[i][j] = tablero[i][j];
            }
        }
        return tableroAux;
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
        System.out.println("      a b c d e f g h"); // Letras de columnas
        System.out.println( "   .-------------------."); // Línea superior
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                if (j == 0) {
                    System.out.print((8 - i) + "  |  "); // Números de filas
                }
                System.out.print(t[i][j] + " ");
            }
            System.out.print(" |  " + (8 - i)); // Números de filas
            System.out.println();
        }
        System.out.println( "   '-------------------'"); // Línea inferior
        System.out.println("      a b c d e f g h"); // Letras de columnas
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
        // char[] piezasNegras = {'t', 'c', 'a', 'd', 'r', 'p'}; //Todo el set de piezas
        // char[] piezasBlancas = {'T', 'C', 'A', 'D', 'R', 'P'}; //Todo el set de piezas
        char[] piezasNegras = { 'r' }; // Caso del ejercio
        char[] piezasBlancas = { 'R', 'P' }; // Caso del ejercio
        char[][] tAleatorio = tableroVacio();
        int[] posRndVacia;
        for (int i = 0; i < piezasNegras.length; i++) {
            if (piezasNegras[i] == 'p') {
                for (int j = 0; j < tAleatorio[2].length; j++) {
                    posRndVacia = (posicionAleatoriaVacia(tAleatorio));
                    tAleatorio[posRndVacia[0]][posRndVacia[1]] = piezasNegras[i];
                }
            } else {
                posRndVacia = (posicionAleatoriaVacia(tAleatorio));
                tAleatorio[posRndVacia[0]][posRndVacia[1]] = piezasNegras[i];
            }
        }
        for (int i = 0; i < piezasBlancas.length; i++) {
            if (piezasBlancas[i] == 'P') {
                // Caso general para todo el set de piezas
                /* 
                for (int j = 0; j < tAleatorio[tAleatorio.length-2].length; j++) {
                posRndVacia=(posicionAleatoriaVacia(tAleatorio));
                tAleatorio[posRndVacia[0]][posRndVacia[1]]=piezasBlancas[i];
                }
                 */
                for (int j = 0; j < 4; j++) {// Caso ejercicio
                    do {
                        posRndVacia = (posicionAleatoriaVacia(tAleatorio));
                    } while (posRndVacia[0] == 0 || posRndVacia[0] == tAleatorio.length - 1);
                    tAleatorio[posRndVacia[0]][posRndVacia[1]] = piezasBlancas[i];
                }
            } else {
                posRndVacia = (posicionAleatoriaVacia(tAleatorio));
                tAleatorio[posRndVacia[0]][posRndVacia[1]] = piezasBlancas[i];
            }
        }

        return tAleatorio;
    }

    static Scanner sc = new Scanner(System.in);

    static char getPiezaMovidaPorRival(char[][] tablero, int[][] historial){
        return tablero[historial[historial.length - 1][3]][historial[historial.length - 1][2]];
    }

    static int[] leerMovimiento() {
        int[] movConFormato = null;
        char[] letras = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
        char[] numeros = { '8', '7', '6', '5', '4', '3', '2', '1' };
        boolean letrasMal = true;
        boolean letraOrigenValida = false;
        boolean letraDestinoValida = false;
        int[] origen = new int[2];
        int[] destino = new int[2];
        try {
            do {
                System.out.print("Introduce \"fin\" para rendirte o el movimiento (formato: e1 e7): ");
                String movimiento = sc.nextLine();
                if (movimiento.equalsIgnoreCase("fin")) {
                    return null; // El jugador se rinde
                }
                origen[0] = movimiento.charAt(0); // Columna (letra) origen
                origen[1] = (int) movimiento.charAt(1); // Fila (numero) origen
                destino[0] = movimiento.charAt(3); // Columna (letra) destino
                destino[1] = (int) movimiento.charAt(4); // Fila (letra) destino
                for (int i = 0; i < letras.length; i++) {
                    if (origen[0] == letras[i]) {
                        origen[0] = i;
                        letraOrigenValida = true;
                    }
                    if (destino[0] == letras[i]) {
                        destino[0] = i;
                        letraDestinoValida = true;
                    }
                }
                if (letraOrigenValida && letraDestinoValida)
                    letrasMal = false;
                for (int i = 0; i < numeros.length; i++) {
                    if (origen[1] == numeros[i])
                        origen[1] = i;
                    if (destino[1] == numeros[i])
                        destino[1] = i;
                }
            } while (origen[1] > 7 || origen[1] < 0 || destino[1] > 7 || destino[1] < 0 || letrasMal);
            movConFormato = new int[] { origen[0], origen[1], destino[0], destino[1] };
        } catch (Exception e) {
            System.err.println("Error en el formato del movimiento. Inténtalo de nuevo.");
            return leerMovimiento();
        }
        return movConFormato;
    }

    static boolean movimientoPeon(int[] movConFormato, char[][] tablero, boolean turnoBlancas, int[][] historial) {
        boolean movEsValido = false;

        // Movimiento anterior del rival
        int filaInicioMovAnterior = historial[historial.length - 1][1];
        int filaFinalMovAnterior = historial[historial.length - 1][3];

        // Movimiento actual
        int columnaOrigen = movConFormato[0];
        int filaOrigen = movConFormato[1];
        int columnaDestino = movConFormato[2];
        int filaDestino = movConFormato[3];

        // Verificación
        if (turnoBlancas) {
            if (columnaOrigen == columnaDestino && filaDestino == filaOrigen - 1 && tablero[filaDestino][columnaDestino] == '-') { // avanzar si no hay piezas delante
                movEsValido = true;
            } else if (filaDestino == filaOrigen - 1 && (columnaDestino == columnaOrigen + 1 || columnaDestino == columnaOrigen - 1) && (tablero[filaDestino][columnaDestino] != '-' || ((getPiezaMovidaPorRival(tablero, historial) == 'p' && columnaDestino > 0 ? (tablero[filaOrigen][columnaDestino -1] == 'p') : (false) || columnaDestino < 7 ? (tablero[filaOrigen][columnaDestino + 1] == 'p') : (false)) && (filaFinalMovAnterior == filaInicioMovAnterior + 2)))) { // Capturar en diagonal y Ampasant
                // Captura diagonal
                // Si el peon avanza en diagonal: filaDestino == filaOrigen - 1 && (columnaDestino == columnaOrigen + 1 || columnaDestino == columnaOrigen - 1)
                // Sí si hay una ficha que capturar: tablero[filaDestino][columnaDestino] != '-'
                // Ampasant //TODO No funciona
                // O si el rival movió un peon: getPiezaMovidaPorRival(tablero, historial) == 'p'
                // Y si hay un peon al lado: columnaDestino > 0 ? (tablero[filaOrigen][columnaDestino -1] == 'p') : (false) || columnaDestino < 7 ? (tablero[filaOrigen][columnaDestino + 1] == 'p') : (false)
                // Y el movimiento del rival fue avanzar recto el peon de en frente a al lado: (columnaFinalMovAnterior == columnaDestino && filaMovAnterior == filaInicioMovAnterior + 2)
                movEsValido = true;
            } else if (columnaOrigen == columnaDestino && filaOrigen == tablero.length - 2 && filaDestino == filaOrigen - 2 && tablero[filaDestino][columnaDestino] == '-' && tablero[filaDestino-1][columnaDestino] == '-') { // Doble avance inicial
                movEsValido = true;
            }
            
        } else {
            if (columnaOrigen == columnaDestino && filaDestino == filaOrigen + 1 && tablero[filaDestino][columnaDestino] == '-') { // avanzar si no hay piezas delante
                movEsValido = true;
            } else if (filaDestino == filaOrigen + 1 && (columnaDestino == columnaOrigen + 1 || columnaDestino == columnaOrigen - 1) && (tablero[filaDestino][columnaDestino] != '-' || ((getPiezaMovidaPorRival(tablero, historial) == 'P' && columnaDestino > 0 ? (tablero[filaOrigen][columnaDestino -1] == 'P') : (false) || columnaDestino < 7 ? (tablero[filaOrigen][columnaDestino + 1] == 'P') : (false)) && (filaFinalMovAnterior == filaInicioMovAnterior - 2)))) { // Capturar en diagonal y Ampasant
                movEsValido = true;
            } else if (columnaOrigen == columnaDestino && filaOrigen == 1 && filaDestino ==  filaOrigen + 2 && tablero[filaDestino][columnaDestino] == '-' && tablero[filaDestino-1][columnaDestino] == '-') { // Doble avance inicial
               movEsValido = true;
            }
        }

        return movEsValido;
    }
    static char escogerPiezaPromocion(boolean turnoBlancas) {
        String piezaPromocion;
        try {
            System.out.print("El peón ha llegado al final del tablero. Elige una pieza para promocionar (D, T, A, C): ");
            piezaPromocion = sc.nextLine().toUpperCase();
            while (piezaPromocion != "D" && piezaPromocion != "T" && piezaPromocion != "A" && piezaPromocion != "C") {
                System.out.print("Entrada no válida. Elige una pieza para promocionar (D, T, A, C): ");
                piezaPromocion = sc.nextLine().toUpperCase();
            }
            return turnoBlancas ? piezaPromocion.charAt(0) : Character.toLowerCase(piezaPromocion.charAt(0));
        } catch (Exception e) {
            System.out.print("Entrada no válida. Elige una pieza para promocionar (D, T, A, C): ");
            sc.nextLine();
            return escogerPiezaPromocion(turnoBlancas);
        }
    }

    static boolean movimientoRey(int[] movConFormato, char[][] tablero, boolean turnoBlancas, int[][] historial) {
        boolean movEsValido = false;
        int[] posicionRey = { 7,4, 0,4 };// Rey blanco, Rey negro
        int[] posicionTorre = { 7,0, 7,7, 0,7, 0,0 };// Torres
        int columnaOrigen = movConFormato[0];
        int filaOrigen = movConFormato[1];
        int columnaDestino = movConFormato[2];
        int filaDestino = movConFormato[3];
        boolean puedeEnrocar = true;
        boolean puedeEnrocarEnCorto = true;
        boolean puedeEnrocarEnLargo = true;
        if((Math.abs(columnaDestino - columnaOrigen) == 2 || Math.abs(columnaDestino - columnaOrigen) == 3) && (filaDestino - filaOrigen == 0)) { // Intento de enroque
            if ((!turnoBlancas && tablero[7][5] == '-' && tablero[7][6] == '-') || (turnoBlancas && tablero[0][5] == '-' && tablero[0][6] == '-') || ((!turnoBlancas && tablero[7][1] == '-' && tablero[7][2] == '-' && tablero[7][3] == '-') || (turnoBlancas && tablero[0][1] == '-' && tablero[0][2] == '-' && tablero[0][3] == '-'))) {
                for (int i = 0; i < historial.length; i++) {
                    for (int j = 0; j < historial[i].length / 2; j++) {
                        if (turnoBlancas) {
                            if (historial[i][j] == posicionRey[j]) {
                                puedeEnrocar = false;
                                break;
                            } else if (historial[i][j] == posicionTorre[j]) {
                                puedeEnrocarEnLargo = false;
                            } else if (historial[i][j] == posicionTorre[j + 2]) {
                                puedeEnrocarEnCorto = false;
                            }
                        } else {
                            if (historial[i][j] == posicionRey[j + 2]) {
                                puedeEnrocar = false;
                                break;
                            } else if (historial[i][j] == posicionTorre[j + 4]) {
                                puedeEnrocarEnLargo = false;
                            } else if (historial[i][j] == posicionTorre[j + 6]) {
                                puedeEnrocarEnCorto = false;
                            }
                        }
                    }
                }
            }
        } else {
            puedeEnrocar = false;
        }
        if ((Math.abs(filaDestino - filaOrigen) == 1 || Math.abs(columnaDestino - columnaOrigen) == 1) && (Math.abs(filaDestino - filaOrigen) < 2 && Math.abs(columnaDestino - columnaOrigen) < 2)) { // Movimiento
            movEsValido = true;
        } else if (puedeEnrocar) { // Enroque
            if (Math.abs(columnaDestino - columnaOrigen) == 2) { // Enroque corto
                if (puedeEnrocarEnCorto) {
                    movEsValido = true;
                }
            } else if (Math.abs(columnaDestino - columnaOrigen) == 3) { // Enroque largo
                if (puedeEnrocarEnLargo) {
                    movEsValido = true;
                }
            }
        }
        return movEsValido;
    }

    static boolean movimientoTorre(int[] movConFormato, char[][] tablero) {
        boolean movEsValido = true;
        int columnaOrigen = movConFormato[0];
        int filaOrigen = movConFormato[1];
        int columnaDestino = movConFormato[2];
        int filaDestino = movConFormato[3];
        if (columnaDestino == columnaOrigen) { // Movimiento vertical
            if (filaDestino > filaOrigen) { // Hacia abajo
                for (int i = filaOrigen + 1; i < filaDestino; i++) {
                    if (tablero[i][columnaOrigen] != '-') {
                        movEsValido = false;
                        break;
                    }
                }
            } else { // Hacia arriba
                for (int i = filaDestino + 1; i < filaOrigen; i++) {
                    if (tablero[i][columnaOrigen] != '-') {
                        movEsValido = false;
                        break;
                    }
                }
            }
        } else if (filaDestino == filaOrigen) { // Movimiento horizontal
            if (columnaDestino > columnaOrigen) { // Hacia la derecha
                for (int j = columnaOrigen + 1; j < columnaDestino; j++) {
                    if (tablero[filaOrigen][j] != '-') {
                        movEsValido = false;
                        break;
                    }
                }
            } else { // Hacia la izquierda
                for (int j = columnaDestino + 1; j < columnaOrigen; j++) {
                    if (tablero[filaOrigen][j] != '-') {
                        movEsValido = false;
                        break;
                    }
                }
            }
        } else {
            movEsValido = false;
        }
        return movEsValido;
    }

    static boolean movimientoAlfil(int[] movConFormato, char[][] tablero) {
        boolean movEsValido = true;
        int columnaOrigen = movConFormato[0];
        int filaOrigen = movConFormato[1];
        int columnaDestino = movConFormato[2];
        int filaDestino = movConFormato[3];
        if (Math.abs(columnaDestino - columnaOrigen) == Math.abs(filaDestino - filaOrigen)) { // Movimiento diagonal
            if (columnaDestino > columnaOrigen && filaDestino > filaOrigen) { // Diagonal abajo derecha
                for (int i = 1; i < Math.abs(columnaDestino - columnaOrigen); i++) {
                    if (tablero[filaOrigen + i][columnaOrigen + i] != '-') {
                        movEsValido = false;
                        break;
                    }
                }
            } else if (columnaDestino < columnaOrigen && filaDestino > filaOrigen) { // Diagonal abajo izquierda
                for (int i = 1; i < Math.abs(columnaDestino - columnaOrigen); i++) {
                    if (tablero[filaOrigen + i][columnaOrigen - i] != '-') {
                        movEsValido = false;
                        break;
                    }
                }
            } else if (columnaDestino > columnaOrigen && filaDestino < filaOrigen) { // Diagonal arriba derecha
                for (int i = 1; i < Math.abs(columnaDestino - columnaOrigen); i++) {
                    if (tablero[filaOrigen - i][columnaOrigen + i] != '-') {
                        movEsValido = false;
                        break;
                    }
                }
            } else if (columnaDestino < columnaOrigen && filaDestino < filaOrigen) { // Diagonal arriba izquierda
                for (int i = 1; i < Math.abs(columnaDestino - columnaOrigen); i++) {
                    if (tablero[filaOrigen - i][columnaOrigen - i] != '-') {
                        movEsValido = false;
                        break;
                    }
                }
            }
        } else {
            movEsValido = false;
        }
        return movEsValido;
    }

    static boolean movimientoCaballo(int[] movConFormato, char[][] tablero) {
        boolean movEsValido = false;
        int columnaOrigen = movConFormato[0];
        int filaOrigen = movConFormato[1];
        int columnaDestino = movConFormato[2];
        int filaDestino = movConFormato[3];
        if ((Math.abs(columnaDestino - columnaOrigen) == 2 && Math.abs(filaDestino - filaOrigen) == 1) || (Math.abs(columnaDestino - columnaOrigen) == 1 && Math.abs(filaDestino - filaOrigen) == 2)) {
            movEsValido = true;
        }
        return movEsValido;
    }

    static boolean movimientoDama(int[] movConFormato, char[][] tablero) {
        boolean movEsValido = false;
        // La dama combina los movimientos de la torre y el alfil
        if (movimientoTorre(movConFormato, tablero) || movimientoAlfil(movConFormato, tablero)) {
            movEsValido = true;
        }
        return movEsValido;
    }

    static boolean validarMovimiento(char[][] tablero, boolean turnoBlancas, int[][] historial, int[] movConFormato) {
       
        if (movConFormato == null) {
            return true; // El jugador se rinde
        }
        boolean esValido;
        int columnaOrigen = movConFormato[0];
        int filaOrigen = movConFormato[1];
        int columnaDestino = movConFormato[2];
        int filaDestino = movConFormato[3];

        if (columnaOrigen == columnaDestino && filaOrigen == filaDestino) { // Misma posición
            esValido = false;
        } else if (turnoBlancas && (Character.isLowerCase(tablero[filaOrigen][columnaOrigen]) || (Character.isUpperCase(tablero[filaDestino][columnaDestino]) && tablero[filaDestino][columnaDestino] != '-'))) { // Turno de blancas pero pieza negra o captura a blanca
            esValido = false;
        } else if (!turnoBlancas && (Character.isUpperCase(tablero[filaOrigen][columnaOrigen]) || (Character.isLowerCase(tablero[filaDestino][columnaDestino]) && tablero[filaDestino][columnaDestino] != '-'))) { // Turno de negras pero pieza blanca o captura a negra
            esValido = false;
        } else if (seráJaque(tablero, turnoBlancas, historial, movConFormato)) {//TODO No detecta si mueve otra pieza y queda en jaque (Ya devería funcionar FALTA PROBARLO)
            esValido = false;
        } else {
            switch (Character.toUpperCase(tablero[filaOrigen][columnaOrigen])) {
                case 'P':
                    esValido = movimientoPeon(movConFormato, tablero, turnoBlancas, historial);
                break;
                case 'R':
                    esValido = movimientoRey(movConFormato, tablero, turnoBlancas, historial);
                    break;
                case 'T':
                    esValido = movimientoTorre(movConFormato, tablero);
                    break;
                case 'A':
                    esValido = movimientoAlfil(movConFormato, tablero);
                    break;
                case 'C':
                    esValido = movimientoCaballo(movConFormato, tablero);
                    break;
                case 'D':
                    esValido = movimientoDama(movConFormato, tablero);
                    break;
                default:
                    esValido = false; // Pieza no seleccionada
                    break;
            }
        }
        return esValido;
    }
    static int[] ubicacionRey(char[][] tablero, boolean turnoBlancas) {
        int[] posicionRey = new int[]{-1,-1};
        char rey = turnoBlancas ? 'R' : 'r';
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] == rey) {
                    posicionRey[0] = i;
                    posicionRey[1] = j;
                    return posicionRey;
                }
            }
        }
        return posicionRey;
    }
    static boolean esJaqueMate(char[][] tablero, boolean turnoBlancas, int[][] historial) {
        boolean jaqueMate = false;
        if (esPosicionJaque(tablero, turnoBlancas, historial)) {
            if (esAhogado(tablero, turnoBlancas, historial)) {
                jaqueMate = true;
            }
        }
        return jaqueMate;
    }
    static boolean esPosicionJaque(char[][] tablero, boolean turnoBlancas, int[][] historial) {
        boolean enJaque = false;
        int[] posicionRey = ubicacionRey(tablero, turnoBlancas);
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                int[] mov = new int[]{j,i,posicionRey[1],posicionRey[0]};
                if (validarMovimiento(tablero, turnoBlancas, historial, mov)) {
                    enJaque = true;
                    return enJaque;
                }
            }
        }
        return enJaque;
    }
    static boolean seráJaque(char[][] tablero, boolean turnoBlancas, int[][] historial, int[] movConFormato) {
        char[][] tableroAux = tableroVacio();
        copiarTablero(tablero, tableroAux); 
        tableroAux = actualizarTablero(tablero, movConFormato, turnoBlancas);
        return esPosicionJaque(tableroAux, turnoBlancas, historial);
    }
    static boolean esAhogado(char[][] tablero, boolean turnoBlancas, int[][] historial) {
        boolean ahogado = true;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (turnoBlancas && Character.isUpperCase(tablero[i][j]) || !turnoBlancas && Character.isLowerCase(tablero[i][j])) {
                    for (int filaDestino = 0; filaDestino < tablero.length; filaDestino++) {
                        for (int columnaDestino = 0; columnaDestino < tablero[filaDestino].length; columnaDestino++) {
                            int[] mov = new int[]{j,i,columnaDestino,filaDestino};
                            if (validarMovimiento(tablero, turnoBlancas, historial, mov)) {
                                ahogado = false;
                                return ahogado;
                            }
                        }
                    }
                }
            }
        }
        return ahogado;
    }
    static boolean esTablasPorRepeticion(int[][] historial) {
        boolean tablas = false;
        int contadorRepeticiones = 0;
        for (int i = 0; i < historial.length; i++) {
            for (int j = i + 1; j < historial.length; j++) {
                if (historial[i][0] == historial[j][0] && historial[i][1] == historial[j][1]
                        && historial[i][2] == historial[j][2] && historial[i][3] == historial[j][3]) {
                    contadorRepeticiones++;
                    if (contadorRepeticiones >= 2) { // Si se repite 3 veces
                        tablas = true;
                        return tablas;
                    }
                }
            }
        }
        return tablas;
    }
    static boolean esTablasPorFaltaDeMaterial(char[][] tablero) {
        boolean tablas = false;
        int contadorPiezasBlancas = 0;
        int contadorPiezasNegras = 0;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (Character.isUpperCase(tablero[i][j])) {
                    contadorPiezasBlancas++;
                } else if (Character.isLowerCase(tablero[i][j])) {
                    contadorPiezasNegras++;
                }
            }
        }
        if (contadorPiezasBlancas == 1 || contadorPiezasNegras == 1) {// Solo quedan un rey a alguno de los dos
            //TODO: Completar casos de tablas por falta de material
            tablas = true;
        }
        return tablas;
    }
    static boolean matchEvents(char[][] tablero,int[] movConFormato, boolean turnoBlancas, int[][] historial) {
        boolean contininueMatch = true;
        boolean jaqueMate = esJaqueMate(tablero, turnoBlancas, historial);
        boolean ahogado = esAhogado(tablero, turnoBlancas, historial);
        boolean tablas = esTablasPorRepeticion(historial);
        if (jaqueMate || ahogado || tablas) {
            contininueMatch = false;
        }
        return contininueMatch;
    }
    static char[][] actualizarTablero(char[][] tablero, int[] movConFormato, boolean turnoBlancas) {
        char[][] tableroActualizado = new char[tablero.length][];
        tableroActualizado = copiarTablero(tablero, tableroActualizado);
        if (movConFormato == null) {
            return tablero; // El jugador se rinde
        }
        // Obtener las coordenadas del movimiento
        int columnaOrigen = movConFormato[0];
        int filaOrigen = movConFormato[1];
        int columnaDestino = movConFormato[2];
        int filaDestino = movConFormato[3];
        
        // Mover la pieza
        tableroActualizado[filaDestino][columnaDestino] = tableroActualizado[filaOrigen][columnaOrigen];
        tableroActualizado[filaOrigen][columnaOrigen] = '-';

        // Manejar enroque
        if (Character.toUpperCase(tableroActualizado[filaOrigen][columnaOrigen]) == 'R') {
            if (Math.abs(columnaDestino - columnaOrigen) == 2) { // Enroque corto
                if (turnoBlancas) {
                    tableroActualizado[7][5] = 'T'; // Mover la torre blanca
                    tableroActualizado[7][7] = '-';
                } else {
                    tableroActualizado[0][5] = 't'; // Mover la torre negra
                    tableroActualizado[0][7] = '-';
                }
            } else if (Math.abs(columnaDestino - columnaOrigen) == 3) { // Enroque largo
                if (turnoBlancas) {
                    tableroActualizado[7][3] = 'T'; // Mover la torre blanca
                    tableroActualizado[7][0] = '-';
                } else {
                    tableroActualizado[0][3] = 't'; // Mover la torre negra
                    tableroActualizado[0][0] = '-';
                }
            }
        }

        // Manejar promoción de peón //TODO: Bug, a veces pide pieza aunque no llegue al final
        if (Character.toUpperCase(tablero[filaOrigen][columnaOrigen]) == 'P' && ((!turnoBlancas && filaDestino == 0) || (turnoBlancas && filaDestino == 7))) { //Promoción
            char piezaPromocion = escogerPiezaPromocion(turnoBlancas);
            //Sustituir peón por la pieza elegida
            tableroActualizado[movConFormato[2]][movConFormato[3]] = piezaPromocion;
        }

        //Manejar Ampasant
        if (Character.toUpperCase(tablero[filaOrigen][columnaOrigen]) == 'P') {
            if (columnaOrigen != columnaDestino && tablero[filaDestino][columnaDestino] == '-') { //Movimiento diagonal sin captura directa
                if (turnoBlancas) {
                    tableroActualizado[filaDestino + 1][columnaDestino] = '-'; //Eliminar peón
                } else {
                    tableroActualizado[filaDestino - 1][columnaDestino] = '-'; //Eliminar peón
                }
            }
        }

        return tableroActualizado;
    }

    public static void main(String[] args) {
        /*
         * mostrarTableroColoresCasillas(tableroVacio());
         * System.out.println();
         * mostrarTablero(inicializarTablero());
         * System.out.println();
         * mostrarTableroConLeyenda(inicializarTablero());
         * System.out.println("Piezas por fila: " + stringArray(contarPiezasPorFila(tablero)));
         * System.out.println("Piezas por columna: " + stringArray(contarPiezasPorColumnas(tablero)));
         * System.out.println();
         * mostrarTableroConLeyenda(tableroAleatorio());
         * System.out.println("Piezas por fila: " + stringArray(contarPiezasPorFila(tableroAleatorio())));
         * System.out.println("Piezas por columna: " + stringArray(contarPiezasPorColumnas(tableroAleatorio())));
         */
        int[][] historial = new int[1][4];
        char[][] tablero = inicializarTablero();
        mostrarTableroConLeyenda(tablero);
        boolean turnoBlancas = true;
        int[] mov;
        do {
            System.out.println(turnoBlancas ? "Turno de BLANCAS (Mayusculas)" : "Turno de NEGRAS (Minusculas)");
            mov = leerMovimiento();
            while (!validarMovimiento(tablero, turnoBlancas, historial, mov)) {
                if (esPosicionJaque(tablero, !turnoBlancas, historial)) {
                    System.out.println("Jaque al " + (!turnoBlancas ? "BLANCO" : "NEGRO") + "!");
                }
                System.out.println("Movimiento no válido. Inténtalo de nuevo.");
                mov = leerMovimiento();
            }
            
            tablero = actualizarTablero(tablero, mov, turnoBlancas);
            mostrarTableroConLeyenda(tablero);
            historial = agregarAHistorial(historial, mov);
            
            // Mensaje final: ganador/a o tablas
            if (mov == null) {
                System.out.println("Las " + (turnoBlancas ? "Blancas" : "Negras") + " se han rendido.");
                
            }
            turnoBlancas = !turnoBlancas;
        } while (matchEvents(tablero, mov, turnoBlancas, historial) && mov != null);
        System.out.println("GANAN LAS " + (!turnoBlancas ? "NEGRAS" : "BLANCAS"));
        System.out.println("Fin de la partida!");
    }
}
/*Crash: No encontró al rey porque se lo comió el peón (Consecuencia de que no lea bien el jaque y el ahogado)
Mov: f7 e8
      a b c d e f g h
   .-------------------.
8  |  t - a d P a c t  |  8
7  |  p p p - p - p p  |  7
6  |  - - - - - - - -  |  6
5  |  - - - - - - - -  |  5
4  |  - - P p - - - -  |  4
3  |  - - - c - - - -  |  3
2  |  P P - P - P P P  |  2
1  |  T C A D R A C T  |  1
   '-------------------'
      a b c d e f g h
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index -1 out of bounds for length 8
        at UD3.ClaseAjedrez.validarMovimiento(ClaseAjedrez.java:515)
        at UD3.ClaseAjedrez.esJaque(ClaseAjedrez.java:573)
        at UD3.ClaseAjedrez.esJaqueMate(ClaseAjedrez.java:560)
        at UD3.ClaseAjedrez.matchEvents(ClaseAjedrez.java:638)
        at UD3.ClaseAjedrez.main(ClaseAjedrez.java:749)
*/
