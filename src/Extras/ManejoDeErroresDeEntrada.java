package Extras;


import java.util.Scanner;

public class ManejoDeErroresDeEntrada {
    // Info útil |
    //           v
    // Paso de Char a String Character.toString(char)
    // Paso de String a Int: Integer.parseInt(String)
    // Paso de Int a String: Integer.toString(Int)
    // Arrays.CopyOfRange solo corta un array nuevo, el introducido no.
    static String stringArray(int[] array) {
        // Convierte un array en String con formato [a,b,c]
        String arrayStr = "[";
        if (array.length == 0) {
            arrayStr += ']';
            return arrayStr;
        }
        for (int i = 0; i < array.length; i++) {
            if (i > 0 && i < array.length) {
                arrayStr += ',';
            }
            arrayStr += array[i];
            if (i == array.length - 1) {
                arrayStr += ']';
            }
        }
        return arrayStr;
    }

    static void ImaparToNegMod(int[] t) {
        // Versión semi-funcional de RndParImpar que sustitulle los impares por -1 y
        // luego los desplaza al final
        int PositionSelcFromEnd = 1;
        int intercambio;
        int cantImpar = 0;
        for (int i = 0; i < t.length; i++) {
            if (t[i] % 2 != 0) {
                t[i] = -1;
                cantImpar++;
            }
        }
        System.out.println(stringArray(t));
        for (int i = 0; i < t.length - cantImpar; i++) {
            if (t[i] % 2 != 0) {
                while (t[t.length - PositionSelcFromEnd] == -1 && PositionSelcFromEnd < t.length) {
                    PositionSelcFromEnd++;
                }
                intercambio = t[i];
                t[i] = t[t.length - PositionSelcFromEnd];
                t[t.length - PositionSelcFromEnd] = intercambio;
                if (PositionSelcFromEnd != t.length)
                    PositionSelcFromEnd++;
            }
        }
        System.out.println(stringArray(t));
    }

    static void GeneralError() {
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

    static void SpecificError() {
        // Caso más específico para números enteros
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
