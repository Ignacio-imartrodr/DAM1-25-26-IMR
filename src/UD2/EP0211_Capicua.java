package UD2;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Ignacio MR
 */
public class EP0211_Capicua {
    static Scanner sc = new Scanner(System.in);

    public static int pedirNum() {
        try {
            System.out.print("Número del 100 al 9999: ");
            return sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Introduce solo números enteros");
            sc.nextLine();
            return pedirNum();
        }
    }

    public static void main(String[] args) {
        int n = pedirNum();

        while (n < 100 || n > 9999) {
            System.out.println("El número debe ser del 100 al 9999");
            n = pedirNum();
        }

        int mill = n / 1000;
        int unid = n % 10;
        int cent = ((n-n % 100) % 1000)/100;
        if (mill == 0) {
            System.out.println(unid == cent ? "Es capicúa" : "NO es capicúa");
        } else {
            int dec = ((n - unid) % 100)/10;
            System.out.println(unid == mill && dec == cent ? "Es capicúa" : "NO es capicúa");
        }
    }
}