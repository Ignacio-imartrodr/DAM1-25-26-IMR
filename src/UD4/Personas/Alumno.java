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
    public static void pedirNombreCompleto(String nombre, String apellido1, String apellido2, LocalDate fechaNacimiento){
        System.out.print("Nombre: ");
        pedirPorTeclado(nombre);
        System.out.print("Primer apellido: ");
        pedirPorTeclado(apellido1);
        System.out.print("Segundo apellido: ");
        pedirPorTeclado(apellido2);
        System.out.print("Fecha de nacimiento (dd/mm/aaaa): ");
        pedirPorTeclado(fechaNacimiento);
    }
    public static void pedirNotas(int notaProg, int notaContornos){
        System.out.print("Nota de Programación: ");
        pedirPorTeclado(notaProg);
        System.out.print("Nota de Contornos: ");
        pedirPorTeclado(notaContornos);
    }
    private static void pedirPorTeclado(String var){
        try {
            var = sc.nextLine();
        } catch (Exception e) {
            sc.nextLine();
            System.out.println("Error, vuelve a intentar");
            pedirPorTeclado(var);
        }
    }
    private static void pedirPorTeclado(int var){
        try {
            var = sc.nextInt();
        } catch (Exception e) {
            sc.nextLine();
            System.out.println("Error, vuelve a intentar");
            pedirPorTeclado(var);
        }
    }
    private static void pedirPorTeclado(LocalDate var){
        try {
            //TODO Pedir fecha
        } catch (Exception e) {
            sc.nextLine();
            System.out.println("Error, vuelve a intentar");
            pedirPorTeclado(var);
        }
    }
}
