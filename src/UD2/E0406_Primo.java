package UD2;

import java.util.Scanner;

/**
 * E0406_Primo. Diseñar una función con el siguiente prototipo:
 * boolean esPrimo(int n)
 * 
 * @author Ignacio MR
 */
public class E0406_Primo {
    public static boolean esPrimo(int n) {
        boolean primo = true;
        if (n == 1 || n == 0) {
            primo = false;
        } else {
            for (int i = 2; i <= Math.pow(Math.abs(n), 1 / 2.0); ++i) {
                if (n % i == 0) {
                    primo = false;
                }
            }
        }
        return primo;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Dime un numero entero: ");
        int num = sc.nextInt();
        sc.close();
        System.out.println(esPrimo(num) ? "El número es primo" : "El número NO es primo");
    }
}
