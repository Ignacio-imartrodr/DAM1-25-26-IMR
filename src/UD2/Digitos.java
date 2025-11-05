package UD2;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Ignacio MR
 */

public class Digitos {
    static Scanner sc = new Scanner(System.in);
    static int pedirNum() {
        try {
            System.out.print("Número para descomponer en cifras: ");
            return sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Introduce solo números enteros");
            sc.nextLine();
            return pedirNum();
        }
    }
    public static void main(String[] args) {
        int n = pedirNum();
        String numeroText = Integer.toString(n);
        int longitud = numeroText.length();
        for (int i = longitud; i > 0; i--) {
            System.out.println(numeroText.charAt(i-1));
        }
    }
}