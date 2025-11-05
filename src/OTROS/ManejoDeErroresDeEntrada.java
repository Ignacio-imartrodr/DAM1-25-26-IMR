package OTROS;

import java.util.Scanner;

public class ManejoDeErroresDeEntrada {
    public static void GeneralError() {
        // Paso de Char a String Character.toString(char)
        // Paso de String a Int: Integer.parseInt(String)
        // Paso de Int a String: Integer.toString(Int)
        // Caso general para números enteros
        Scanner scanner = new Scanner(System.in);
        int numero = 0;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.print("Ingrese un número: ");
                numero = Integer.parseInt(scanner.nextLine());
                entradaValida = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor ingrese un número válido");
            }
        }
        System.out.println(numero);
        scanner.close();
    }
    public static void SpecificError() {
        //Caso más específico para números enteros
        Scanner scanner = new Scanner(System.in);
        int numero = 0;
        if (scanner.hasNextInt()) {
            numero = scanner.nextInt();
            // El input es un número entero válido
        } else {
            System.out.println("Error: Por favor ingrese un número válido");
            scanner.next(); // limpia el buffer del scanner
        }
        System.out.println(numero);
        scanner.close();
    }
}
