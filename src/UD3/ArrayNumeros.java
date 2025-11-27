package UD3;

import java.util.InputMismatchException;
import java.util.Scanner;

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
        int[] ArrayNumeros = new int[n];
        int numCeros = 0;
        int mediaPos;
        int mediaNeg;
        int numIntroducido;
        for (int i = 0; i < n; i++) {
            System.out.println("Introduce un número: ");
            numIntroducido = pedirNum();
            ArrayNumeros[i] = numIntroducido;
            switch (numIntroducido) {
                case 0:
                    numCeros++;
                    break;
            
                default:
                    if (numIntroducido > 0){
                        //TODO
                    } else {
                        //TODO
                    }
                    break;
            }
        }
    }
}
