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
        int[][] historialAux = new int[historial.length + 1][];
        System.arraycopy(historial, 0, historialAux, 0, historial.length);
        historialAux[historialAux.length - 1] = mov;
        return historialAux;
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

    static int[] leerMovimiento() {
        char[] letras = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
        char[] numeros = { '1', '2', '3', '4', '5', '6', '7', '8' };
        boolean letrasMal = true;
        boolean letraOrigenValida = false;
        boolean letraDestinoValida = false;
        int[] origen = new int[2];
        int[] destino = new int[2];
        do {
            System.out.print("Introduce el movimiento (formato: e1 e7): ");
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
                    origen[1] = i + 1;
                if (destino[1] == numeros[i])
                    destino[1] = i + 1;
            }
        } while (origen[1] > 8 || origen[1] < 1 || destino[1] > 8 || destino[1] < 1 || letrasMal);
        int[] movConFormato = { origen[0], origen[1], destino[0], destino[1] };
        return movConFormato;
    }

    static boolean movimientoPeon(int[] movConFormato, char[][] tablero, boolean turnoBlancas, int[][] historial) {
        boolean movEsValido = false;
        int columnaInicioMovAnterior = historial[historial.length - 1][1];
        int columnaFinalMovAnterior = historial[historial.length - 1][3];
        int filaMovAnterior = historial[historial.length - 1][4];
        int columnaOrigen = movConFormato[0];
        int filaOrigen = movConFormato[1];
        int columnaDestino = movConFormato[2];
        int filaDestino = movConFormato[3];
        if (turnoBlancas) {
            if (filaDestino == filaOrigen - 1 && tablero[filaDestino][columnaDestino] == '-') { // avanzar si no hay piezas delante
                movEsValido = true;
            } else if (filaDestino == filaOrigen - 1 && (columnaDestino == columnaOrigen + 1 || columnaDestino == columnaOrigen - 1) && (tablero[filaDestino][columnaDestino] != '-' || (tablero[filaDestino][columnaDestino] == '-' && (tablero[filaOrigen][columnaDestino - 1] == 'p' || tablero[filaOrigen][columnaDestino + 1] == 'p')  && (columnaInicioMovAnterior == columnaDestino && columnaFinalMovAnterior == columnaDestino
                                    && filaMovAnterior == filaDestino - 1)))) { // Capturar en diagonal y Anpasant
                // Captura diagonal
                // Si el peon avanza en diagonal filaDestino == filaOrigen - 1 &&
                // (columnaDestino == columnaOrigen + 1 || columnaDestino == columnaOrigen - 1)
                // Sí si hay una ficha que capturar tablero[filaDestino][columnaDestino] != '-'
                // Anpasant
                // O si hay un peon al lado (tablero[filaDestino][columnaDestino] == '-' &&
                // (tablero[filaOrigen][columnaDestino-1] == 'p' ||
                // tablero[filaOrigen][columnaDestino+1] == 'p')
                // Y el movimiento del rival fue avanzar recto el peon de en frente a al lado
                // (columnaInicioMovAnterior == columnaDestino && columnaMovAnterior ==
                // columnaDestino && filaMovAnterior == filaDestino + 1)
                movEsValido = true;
            } else if (filaOrigen == tablero.length - 2 && filaDestino == filaOrigen - 2
                    && tablero[filaDestino][columnaDestino] == '-') { // Doble avance inicial
                movEsValido = true;
            }
        } else {
            if (filaDestino == filaOrigen + 1 && tablero[filaDestino][columnaDestino] == '-') { // avanzar si no hay piezas delante
                movEsValido = true;
            } else if (filaDestino == filaOrigen + 1
                    && (columnaDestino == columnaOrigen + 1 || columnaDestino == columnaOrigen - 1)
                    && (tablero[filaDestino][columnaDestino] != '-' || (tablero[filaDestino][columnaDestino] == '-'
                            && (tablero[filaOrigen][columnaDestino - 1] == 'P'
                                    || tablero[filaOrigen][columnaDestino + 1] == 'P')
                            && (columnaInicioMovAnterior == columnaDestino && columnaFinalMovAnterior == columnaDestino
                                    && filaMovAnterior == filaDestino + 1)))) { // Capturar en diagonal y Anpasant
                movEsValido = true;
            } else if (filaOrigen == tablero.length - 2 && filaDestino == filaOrigen + 2
                    && tablero[filaDestino][columnaDestino] == '-') { // Doble avance inicial
                movEsValido = true;
            }
        }

        return movEsValido;
    }

    static boolean movimientoRey(int[] movConFormato, char[][] tablero, boolean turnoBlancas, int[][] historial) {
        boolean movEsValido = false;
        int[] movimientoRey = { 5, 1, 5, 8 };// Rey blanco, Rey negro
        int[] movimientoTorre = { 1, 1, 8, 1, 1, 8, 8, 8 };// Torres
        int columnaOrigen = movConFormato[0];
        int filaOrigen = movConFormato[1];
        int columnaDestino = movConFormato[2];
        int filaDestino = movConFormato[3];
        boolean puedeEnrocar = true;
        boolean puedeEnrocarEnCorto = true;
        boolean puedeEnrocarEnLargo = true;
        if ((!turnoBlancas && tablero[7][5] == '-' && tablero[7][6] == '-') || (turnoBlancas && tablero[0][5] == '-' && tablero[0][6] == '-')|| ((!turnoBlancas && tablero[7][1] == '-' && tablero[7][2] == '-' && tablero[7][3] == '-') || (turnoBlancas && tablero[0][1] == '-' && tablero[0][2] == '-' && tablero[0][3] == '-'))) {
            for (int i = 0; i < historial.length; i++) {
                for (int j = 0; j < historial[i].length / 2; j++) {
                    if (turnoBlancas) {
                        if (historial[i][j] == movimientoRey[j]) {
                            puedeEnrocar = false;
                            break;
                        } else if (historial[i][j] == movimientoTorre[j] && historial[i][j] == movimientoTorre[j + 2]) {
                            puedeEnrocar = false;
                            break;
                        } else if (historial[i][j] == movimientoTorre[j]) {
                            puedeEnrocarEnLargo = false;
                        } else if (historial[i][j] == movimientoTorre[j + 2]) {
                            puedeEnrocarEnCorto = false;
                        }
                    } else {
                        if (historial[i][j] == movimientoRey[j + 2]) {
                            puedeEnrocar = false;
                            break;
                        } else if (historial[i][j] == movimientoTorre[j + 4] && historial[i][j] == movimientoTorre[j + 6]) {
                            puedeEnrocar = false;
                            break;
                        } else if (historial[i][j] == movimientoTorre[j + 4]) {
                            puedeEnrocarEnLargo = false;
                        } else if (historial[i][j] == movimientoTorre[j + 6]) {
                            puedeEnrocarEnCorto = false;
                        }
                    }
                }
            }
        } else {
            puedeEnrocar = false;
        }
        if (filaDestino == filaOrigen - 1 || filaDestino == filaOrigen + 1 || columnaDestino == columnaOrigen + 1
                || columnaDestino == columnaOrigen - 1) { // Movimiento //TODO verificar que no se mueva a una casilla atacada
            movEsValido = true;
        } else if (puedeEnrocar && ((columnaDestino == columnaOrigen + 2 || columnaDestino == columnaOrigen - 3))) { // Enroque

            if (turnoBlancas) {
                if (columnaDestino == columnaOrigen + 2) { // Enroque corto
                    if (puedeEnrocarEnCorto) {
                        movEsValido = true;
                    }
                } else if (columnaDestino == columnaOrigen - 3) { // Enroque largo
                    if (puedeEnrocarEnLargo) {
                        movEsValido = true;
                    }
                }
            } else {
                if (columnaDestino == columnaOrigen + 2) { // Enroque corto
                    if (puedeEnrocarEnCorto) {
                        movEsValido = true;
                    }
                } else if (columnaDestino == columnaOrigen - 3) { // Enroque largo
                    if (puedeEnrocarEnLargo) {
                        movEsValido = true;
                    }
                }
            }
        }
        return movEsValido;
    }

    static boolean movimientoTorre(int[] movConFormato, char[][] tablero) {
        boolean movEsValido = false;
        int columnaOrigen = movConFormato[0];
        int filaOrigen = movConFormato[1];
        int columnaDestino = movConFormato[2];
        int filaDestino = movConFormato[3];
        boolean pasoLibre = true;
        if (columnaDestino == columnaOrigen) { // Movimiento vertical
            if (filaDestino > filaOrigen) { // Hacia abajo
                for (int i = filaOrigen + 1; i < filaDestino; i++) {
                    if (tablero[i][columnaOrigen] != '-') {
                        pasoLibre = false;
                        break;
                    }
                }
            } else { // Hacia arriba
                for (int i = filaDestino + 1; i < filaOrigen; i++) {
                    if (tablero[i][columnaOrigen] != '-') {
                        pasoLibre = false;
                        break;
                    }
                }
            }
        } else if (filaDestino == filaOrigen) { // Movimiento horizontal
            if (columnaDestino > columnaOrigen) { // Hacia la derecha
                for (int j = columnaOrigen + 1; j < columnaDestino; j++) {
                    if (tablero[filaOrigen][j] != '-') {
                        pasoLibre = false;
                        break;
                    }
                }
            } else { // Hacia la izquierda
                for (int j = columnaDestino + 1; j < columnaOrigen; j++) {
                    if (tablero[filaOrigen][j] != '-') {
                        pasoLibre = false;
                        break;
                    }
                }
            }
        }
        if (pasoLibre && (columnaDestino == columnaOrigen || filaDestino == filaOrigen)) {
            movEsValido = true;
        }
        return movEsValido;
    }

    static boolean movimientoAlfil(int[] movConFormato, char[][] tablero) {
        boolean movEsValido = false;
        int columnaOrigen = movConFormato[0];
        int filaOrigen = movConFormato[1];
        int columnaDestino = movConFormato[2];
        int filaDestino = movConFormato[3];
        boolean pasoLibre = true;
        if (Math.abs(columnaDestino - columnaOrigen) == Math.abs(filaDestino - filaOrigen)) { // Movimiento diagonal
            if (columnaDestino > columnaOrigen && filaDestino > filaOrigen) { // Diagonal abajo derecha
                for (int i = 1; i < Math.abs(columnaDestino - columnaOrigen); i++) {
                    if (tablero[filaOrigen + i][columnaOrigen + i] != '-') {
                        pasoLibre = false;
                        break;
                    }
                }
            } else if (columnaDestino < columnaOrigen && filaDestino > filaOrigen) { // Diagonal abajo izquierda
                for (int i = 1; i < Math.abs(columnaDestino - columnaOrigen); i++) {
                    if (tablero[filaOrigen + i][columnaOrigen - i] != '-') {
                        pasoLibre = false;
                        break;
                    }
                }
            } else if (columnaDestino > columnaOrigen && filaDestino < filaOrigen) { // Diagonal arriba derecha
                for (int i = 1; i < Math.abs(columnaDestino - columnaOrigen); i++) {
                    if (tablero[filaOrigen - i][columnaOrigen + i] != '-') {
                        pasoLibre = false;
                        break;
                    }
                }
            } else if (columnaDestino < columnaOrigen && filaDestino < filaOrigen) { // Diagonal arriba izquierda
                for (int i = 1; i < Math.abs(columnaDestino - columnaOrigen); i++) {
                    if (tablero[filaOrigen - i][columnaOrigen - i] != '-') {
                        pasoLibre = false;
                        break;
                    }
                }
            }
        }
        if (pasoLibre && Math.abs(columnaDestino - columnaOrigen) == Math.abs(filaDestino - filaOrigen)) {
            movEsValido = true;
        }
        return movEsValido;
    }

    static boolean movimientoCaballo(int[] movConFormato, char[][] tablero) {
        boolean movEsValido = false;
        int columnaOrigen = movConFormato[0];
        int filaOrigen = movConFormato[1];
        int columnaDestino = movConFormato[2];
        int filaDestino = movConFormato[3];
        if ((Math.abs(columnaDestino - columnaOrigen) == 2 && Math.abs(filaDestino - filaOrigen) == 1)
                || (Math.abs(columnaDestino - columnaOrigen) == 1 && Math.abs(filaDestino - filaOrigen) == 2)) {
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
        boolean esValido;
        int [] mov = movConFormato;
            if (mov == null) {
                return true; // El jugador se rinde
            }
            if (mov[0] == mov[2] && mov[1] == mov[3]) { // Misma posición
                esValido = false;
            } else if (turnoBlancas && Character.isLowerCase(tablero[mov[1]][mov[0]]) || Character.isUpperCase(tablero[mov[3]][mov[2]])) { // Turno de blancas pero pieza negra o captura a blanca
                esValido = false;
            } else if (!turnoBlancas && Character.isUpperCase(tablero[mov[1]][mov[0]]) || Character.isLowerCase(tablero[mov[3]][mov[2]])) { // Turno de negras pero pieza blanca o captura a negra
                esValido = false;
            } else {
                switch (Character.toUpperCase(tablero[mov[1]][mov[0]])) {
                    case 'P':
                        esValido = movimientoPeon(mov, tablero, turnoBlancas, historial);
                        break;
                    case 'R':
                        esValido = movimientoRey(mov, tablero, turnoBlancas, historial);
                        break;
                    case 'T':
                        esValido = movimientoTorre(mov, tablero);
                        break;
                    case 'A':
                        esValido = movimientoAlfil(mov, tablero);
                        break;
                    case 'C':
                        esValido = movimientoCaballo(mov, tablero);
                        break;
                    case 'D':
                        esValido = movimientoDama(mov, tablero);
                        break;
                    default:
                        esValido = false; // Pieza no seleccionada
                        break;
                }
            }
        return esValido;
    }
    static int[] ubicacionRey(char[][] tablero, boolean turnoBlancas) {
        int[] posicionRey = new int[]{-1,1};
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
    static boolean esJaque(char[][] tablero, boolean turnoBlancas, int[][] historial) {
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
    static boolean matchEvents(char[][] tablero,int[] movConFormato, boolean turnoBlancas, int[][] historial) {
        boolean contininueMatch = true;
        boolean jaqueMate = false;
        boolean tablas = false;
        boolean ahogado = false;

        // Verificar jaque mate
        if (validarMovimiento(tablero, turnoBlancas, historial, movConFormato)) {
            jaqueMate = true;
        }

        return contininueMatch;
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
        int[][] historial = new int[0][];
        char[][] tablero = inicializarTablero();
        mostrarTableroConLeyenda(tablero);
        boolean turnoBlancas = true;
        int[] mov;
        do {
            System.out.println(turnoBlancas ? "Turno de BLANCAS" : "Turno de NEGRAS");
            mov = leerMovimiento();
            while (!validarMovimiento(tablero, turnoBlancas, historial, mov) || esJaque(tablero, turnoBlancas, historial) ) {
                System.out.println("Movimiento no válido. Inténtalo de nuevo.");
                mov = leerMovimiento();
            }
    
            historial = agregarAHistorial(historial, mov);
    
            // Mensaje final: ganador/a o tablas
            if (mov == null) {
                System.out.println("Las " + (turnoBlancas ? "Blancas" : "Negras") + " se han rendido.");
                System.out.println("GANAN LAS " + (turnoBlancas ? "NEGRAS" : "BLANCAS"));
            }
        } while (matchEvents(tablero, mov, turnoBlancas, historial) && mov != null);

        System.out.println("Fin de la partida!");
    }
}
