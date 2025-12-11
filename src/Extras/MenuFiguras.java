package Extras;


import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Ignacio MR
 */
public class MenuFiguras {

    static Scanner sc = new Scanner(System.in);

    static void rectangulo(int columnas, int filas) {
        for (int i = 1; i <= columnas; i++) {
            System.out.print("* ");
            for (int j = 1; j <= filas - 2; j++) {
                if (i == 1 || i == columnas) {
                    System.out.print("* ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.print("*");
            System.out.println();
        }
        System.out.println();
    }

    static void triangulo(int n) {
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < n - i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= i; j++) {
                if (j == 1 || j == i || i == n) {
                    System.out.print("* ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    static int pedirValor2a20() {
        int num = 0;
        try {
            System.out.print("Introduce un numero del 2 al 20: ");
            num = sc.nextInt();
            while (num < 2 || num > 20) {
                System.out.print("Error de entrada.");
                System.out.print("Introduce solo numeros del 2 al 20: ");
                num = sc.nextInt();
            }
        } catch (InputMismatchException e) {
            System.out.print("Introduce solo numeros del 2 al 20: ");
            sc.nextLine();
            pedirValor2a20();
        }
        return num;
    }

    public static void main(String[] args) {

        int errores = 0;

        while (errores <= 3) {
            boolean valorIncorrecto = true;
            int figura = -1;
            do {
                try {
                    System.out.println("Escoge la figura: ");
                    System.out.println("===============================");
                    System.out.println("0 - salir      1 - triangulo       2 - rectangulo");
                    figura = sc.nextInt();
                    valorIncorrecto = false;
                } catch (InputMismatchException e) {
                    System.out.println("Escribe solo numeros.");
                    sc.nextLine();
                }
            } while (valorIncorrecto);

            int columnas;
            int filas;
            switch (figura) {
                case 1:
                    System.out.println("Número columnas? ");
                    columnas = pedirValor2a20();
                    triangulo(columnas);
                    break;

                case 2:
                    System.out.println("Número columnas? ");
                    columnas = pedirValor2a20();
                    System.out.println("Número de filas? ");
                    filas = pedirValor2a20();
                    rectangulo(columnas, filas);
                    break;

                case 0:
                    errores = 4;
                    break;

                default:
                    errores++;
                    System.out.println("Opcion no valida.");
                    System.out.println("Has cometido " + errores + " errores");
                    System.out.println("Te quedan " + (3 - errores) + " intentos");
                    break;
            }
        }
        System.out.println("Gracias por usar el programa!");
        System.out.println("********************************");
    }
}
