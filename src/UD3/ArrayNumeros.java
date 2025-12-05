package UD3;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Ignacio MR
 */

public class ArrayNumeros {
    static Scanner sc = new Scanner(System.in);
    static int pedirNum() {
        int n = 0;
        try {
            n = sc.nextInt();
        } catch (InputMismatchException e) {
            sc.nextLine();
            System.out.println("Introduce unicamente valores numericos.");

        }
        return n;
    }
    public static void main(String[] args) {
        int n;
        do {
            System.out.print("Cuantos números vas a introducír? (positivos mayor a 0): ");
            n = pedirNum(); 
        } while (n < 1);
        int[] arrayNumeros = new int[n];
        int cantCeros = 0;
        int sumaPos = 0;
        int sumaNeg = 0;
        int numIntroducido;
        int contPos = 0;
        int contNeg = 0;
        for (int i = 0; i < n; i++) {
            System.out.println("Introduce un número: ");
            numIntroducido = pedirNum();
            arrayNumeros[i] = numIntroducido;
        }   
        for (int j = 0; j < arrayNumeros.length; j++) {
            if (arrayNumeros[j]==0) {
                cantCeros++;
            } else if (arrayNumeros[j] > 0) {
                sumaPos += arrayNumeros[j];
                contPos++;
            } else {
                sumaNeg += arrayNumeros[j];
                contNeg++;
            }
        }
        int mediaPos = sumaPos/contPos;
        int mediaNeg = sumaNeg/contNeg;
        System.out.printf("La media de números positivos es: %d %nLa media de números negativos es: %d %nSe insertaron %d ceros", mediaPos, mediaNeg, cantCeros);
    }
}
