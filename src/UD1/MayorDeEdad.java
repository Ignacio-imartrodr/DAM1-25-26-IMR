package UD1;
import java.util.Scanner;

public class MayorDeEdad {
 public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Cual es tu nombre?");
        String nombre = sc.nextLine();
        System.out.println("Cuantos años tienes?");
        int edad = sc.nextInt();
        sc.close();
        final int MAYORYA_DE_EDAD = 18;
        boolean mayorDeEdad = edad>=MAYORYA_DE_EDAD;
        System.out.println(nombre+", eres mayor de edad? "+mayorDeEdad);
     }
 }
