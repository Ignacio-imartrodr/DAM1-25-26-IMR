package UD2;

import java.util.Scanner;

/**
 * E0405_EsVocal. Crear una función que devuelva un valor booleano que indique si el carácter que 
 * se le pasa como parámetro de entrada corresponde a una vocal. 
 * Cómo se pueden gestionar también las minúsculas, mayúsculas, acentos y diéresis? 
 * 
 * @author Ignacio MR
 */
public class E0405_EsVocal {
    public static boolean contieneVocal(String caract) {
        switch (caract) {
            case "a", "e", "i", "o", "u",
                 "A", "E", "I", "O", "U",
                 "á", "é", "í", "ó", "ú",
                 "Á", "É", "Í", "Ó", "Ú",
                 "ä", "ë", "ï", "ö", "ü",
                 "Ä", "Ë", "Ï", "Ö", "Ü":
                return true;
            default:
                return false;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce un carácter: ");
        String caract = sc.nextLine();
        sc.close();
        System.out.println(contieneVocal(caract) ? "Es una vocal" : "NO es una vocal");
    }
}
