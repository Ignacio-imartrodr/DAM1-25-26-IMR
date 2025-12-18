package UD3;

import java.util.Scanner;

/**
 * @author Ignacio MR
 */
public class LeerMovimiento {
    static String stringArray(int[] array) {
        String arrayStr = "[ ";
        if (array.length == 0) {
            arrayStr = "[]";
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
    public static void main(String[] args) {
    System.out.println(stringArray(leerMovimiento()));
    }
}
