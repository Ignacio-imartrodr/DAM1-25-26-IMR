package UD4.Personas;

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
    Persona personaAlumno = new Persona();

    private static Scanner sc = new Scanner(System.in);
    public void pedirAtributos(){
        pedirNombreCompleto();
        pedirFechaNacimiento();
        pedirNotas();
    }
    public void pedirNombreCompleto(){
        personaAlumno.pedirNombreCompleto();
        nombre = personaAlumno.nombre;
        apellido1 = personaAlumno.apellido1;
        apellido2 = personaAlumno.apellido2;
    }
    public void pedirFechaNacimiento(){
        personaAlumno.pedirFechaNacimiento();
        fechaNacimiento = personaAlumno.fechaNacimiento;
    }
    public void pedirNotas(){
        System.out.print("Nota de Programación: ");
        notaProg = pedirNum();
        System.out.print("Nota de Contornos: ");
        notaContornos = pedirNum();
    }
    private Double pedirNum(){
        Double var;
        try {
            var = sc.nextDouble();
        } catch (Exception e) {
            sc.nextLine();
            System.out.println("Introduce números con \",\" como separador");
            System.out.print("Vuelve a intentar: ");
            return pedirNum();
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
    public String getFicha(){
        /*
        Ficha Alumno/a
        ===============
        "Usuario: " + getUsername()
        "Nombre: " + this.nombre
        "Apellidos: " apellido1 + apellido2 
        "Nota media: " + String.valueOf((notaProg*notaContornos)/2)
        "Fecha de nacimiento: " + 
        */
        
        String Cabecera = "Ficha Alumno/a\n=================\n";
        String usuario = "Usuario: " + getUsername();
        String nombre = "Nombre: " + this.nombre;
        String apellidos = String.format("Apellidos: %s %s", apellido1, apellido2);
        String media = String.format("Nota media: %.2f", (notaProg+notaContornos)/2);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String nacimiento = "Fecha de nacimiento: " + fechaNacimiento.format(formato);
        return String.format("%s%s%n%s%n%s%n%s%n%s", Cabecera, usuario, nombre, apellidos, media, nacimiento);
    }
    public static void main(String[] args) {
        Alumno alumno = new Alumno();
        alumno.pedirAtributos();
        System.out.println(alumno.getFicha());
    }
}
