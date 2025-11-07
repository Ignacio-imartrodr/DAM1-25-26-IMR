package UD2;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Ignacio MR
 */
public class EP0211_Capicua {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        
        int n = 0;
        do{
            boolean numCorrecto = false;
            do {                
                try {
                System.out.print("Número del 100 al 9999: ");
                n = sc.nextInt();
                numCorrecto = true;
                } catch (InputMismatchException e) {
                    System.out.println("Introduce solo números enteros");
                    sc.nextLine();
                }

            } while (!numCorrecto);
        }while (n < 100 || n > 9999);

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