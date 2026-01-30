package UD4.Personas;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Alumno {
    String nombre;
    String apellido1;
    String apellido2;
    LocalDate fechaNacimiento;
    double notaProg;
    double notaContornos;
    
    private static Scanner sc = new Scanner(System.in,"Windows-1252");
    public void pedirNombreCompleto(){
        System.out.print("Nombre: ");
        nombre = pedirPorTeclado();
        System.out.print("Primer apellido: ");
        apellido1 = pedirPorTeclado();
        System.out.print("Segundo apellido: ");
        apellido2 = pedirPorTeclado();
        System.out.print("Fecha de nacimiento (dd/mm/aaaa): ");
        fechaNacimiento = pedirFecha();
    }
    public void pedirNotas(){
        System.out.print("Nota de Programación: ");
        notaProg = Double.parseDouble(pedirPorTeclado());
        System.out.print("Nota de Contornos: ");
        notaContornos = Double.parseDouble(pedirPorTeclado());
    }
    private String pedirPorTeclado(){
        String var;
        try {
            var = sc.nextLine();
        } catch (Exception e) {
            sc.nextLine();
            System.out.println("Formato erróneo, vuelve a intentar");
            return pedirPorTeclado();
        }
        return var;
    }
    private LocalDate pedirFecha(){
        LocalDate var;
        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            var = LocalDate.parse(pedirPorTeclado(), formato);
        } catch (Exception e) {
            sc.nextLine();
            System.out.println("Error, vuelve a intentar");
            return pedirFecha();
        }
        return var;
    }

    String getUsername(){
        String username = "";
        String nom;
        String apell1;
        String apell2;
        nom = nombre.substring(0, 1);
        apell1 = apellido1.split(" ")[0];
        apell2 = apellido2.split(" ")[0];
        if (apell1.length() > 4){
            apell1 = apell1.substring(0, 4);
        }
        if (apell2.length() > 4){
            apell2 = apell2.substring(0, 4);
        }
        username = (nom + apell1 + apell2).toLowerCase();
        String charInvalidos = "áéíóúüñ";
        String charInvalidos2 = " ¡¢£¤"; //Para ISO-8859-1
        String charInvalidos3 = " ‚¡¢£�¤";//Para Windows-1252
        String charValidos = "aeiouun";
        String[] StringsInvalidos = new String[] {charInvalidos, charInvalidos2, charInvalidos3};
        for (int i = 0; i < username.length(); i++) {
            for (int j = 0; j < StringsInvalidos.length; j++) {
                for (int k = 0; k < StringsInvalidos[j].length(); k++) {
                    username = username.replace(StringsInvalidos[j].charAt(k), charValidos.charAt(k));
                }
            }
        }
        return username;
    }
    public static void main(String[] args) {
        Alumno alumno = new Alumno();
        alumno.pedirNombreCompleto();
        System.out.println("Nombre de usuario: " + alumno.getUsername());
    }
}
