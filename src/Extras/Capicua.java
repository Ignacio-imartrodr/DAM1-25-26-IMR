package Extras;


import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Ignacio MR
 */

public class Capicua {
    static Scanner sc = new Scanner(System.in);

    public static int pedirNum() {
        try {
            System.out.print("Número?: ");
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
        int inicioCadena = 0;
        boolean esCapicua = true;
        for (int i = longitud; i > i/2.0 && esCapicua; i--) {
            if (numeroText.charAt(i-1) != numeroText.charAt(inicioCadena)){
                esCapicua = false;
            }
            inicioCadena++;
        }
        System.out.println(esCapicua ? "Es capicúa" : "NO es capicúa");
    }
}
