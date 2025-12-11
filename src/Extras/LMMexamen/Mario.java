package Extras.LMMexamen;

import java.util.Scanner;

/**
 * @author Lunna Mendonça Miranda
 */

public class Mario {
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        contarSaltos();;
        
    }

    public static void contarSaltos() {

        int saltoAnterior = 0, contArriba = 0, contAbajo = 0, salto = 0;

        System.out.print("Introduzca la altura: ");
        salto = sc.nextInt();

        while (salto != -1) {
            try {
                System.out.print("Introduzca la altura: ");
                salto = sc.nextInt();
            
                if (salto >= 0) {
                    if (salto > saltoAnterior) {
                        contArriba++;
                    } else if (salto < saltoAnterior) {
                        contAbajo++;
                    }
                    saltoAnterior = salto;
                }

            } catch (Exception e) {
                sc.nextLine();
                System.out.println("Debe introducir un número natural.");
            }
        }
        System.out.println(contArriba + " " + contAbajo);
    }
}