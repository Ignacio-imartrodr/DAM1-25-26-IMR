package Extras;

/**
 * @author Ignacio MR
 */
import java.util.InputMismatchException;
import java.util.Scanner;

public class EP0212_DNI {
    static Scanner sc = new Scanner(System.in);
    static int longNum(int num) {
        int cont = 0;
        long n = num;
        while (n % 10 != 0 && num != 0) {
            cont++;
            n=n/10;
        }
        if(num==0){
            cont = 1;
        }
        return cont;
    }
    static int pedirNum() {
        int n = 0;
        try {
            n = sc.nextInt();
        } catch (InputMismatchException e) {
            sc.nextLine();
            System.out.println("Introduce unicamente valores enteros.");
        }
        return n;
    }
 public static void main(String[] args) {
    int numDni;
    do {
        System.out.println("Introduce el Numero del DNI (8 números): ");
        numDni = pedirNum();
    } while (longNum(numDni) != 8 || numDni < 0);
    char[] letra = {'T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E'};
    System.out.println("La letra para ese número de DNI es: "+ letra[numDni%23]);
 }
}
