package ud2.IMRexamen;

/**
 * @author Ignacio MR
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class Mario {
    static Scanner sc = new Scanner(System.in);

    static int pedirNum() {
        int n = 0;
        try {
            n = sc.nextInt();
        } catch (InputMismatchException e) {
            sc.nextLine();
            System.out.println("Introduce unicamente valores enteros.");
            pedirNum();
        }
        return n;
    }

    public static void main(String[] args) {
        int saltoUp = 0;
        int saltoDown = 0;
        int alturaNextSalto;
        System.out.print("En que altura se encuentra Mario? ");
        int alturaActual = pedirNum();

        do {

            System.out.println("Introduce un número negativo para finalizar el programa.");
            System.out.print("Altura del salto: ");
            alturaNextSalto = pedirNum();
            if (alturaNextSalto > alturaActual) {
                saltoUp++;
            } else if (alturaNextSalto < alturaActual && alturaNextSalto >= 0) {
                saltoDown++;
            }
            alturaActual = alturaNextSalto;
        } while (alturaNextSalto >= 0);
        System.out.printf("Mario ha saltado %d veces hacia arriba %n", saltoUp);
        System.out.printf("Mario ha saltado %d veces hacia abajo", saltoDown);
    }
}
