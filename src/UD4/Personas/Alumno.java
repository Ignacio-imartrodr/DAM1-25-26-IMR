package UD4.Personas;

import java.time.LocalDate;
import java.util.Scanner;

public class Alumno {
    String nombre;
    String apellido1;
    String apellido2;
    LocalDate fechaNacimiento;
    int notaProg;
    int notaContornos;
    
    static Scanner sc = new Scanner(System.in);
    static void pedirNombreCompleto(String nombre, String apellido1, String apellido2){
        System.out.println("Nombre: ");

    }
    static void pedirPorTeclado(String var){
        try {
            var = sc.nextLine();
        } catch (Exception e) {
            sc.nextLine();
            System.out.println("Error, vuelve a intentar");
            pedirPorTeclado(var);
        }
    }
    static void pedirPorTeclado(int var){
        try {
            var = sc.nextInt();
        } catch (Exception e) {
            sc.nextLine();
            System.out.println("Error, vuelve a intentar");
            pedirPorTeclado(var);
        }
    }
    static void pedirPorTeclado(LocalDate var){
        CharSequence text;
        try {
            text = sc.nextLine();
        } catch (Exception e) {
            sc.nextLine();
            System.out.println("Error, vuelve a intentar");
            pedirPorTeclado(var);
        }
    }
}
