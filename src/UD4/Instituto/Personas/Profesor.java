package UD4.Instituto.Personas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import UD4.Instituto.Modulos.Modulo;

public class Profesor {
    public String nombre;
    public String apellido1;
    public String apellido2;
    public String nombreCompleto = apellido1 + " " + apellido2 + ", " + nombre;
    public int id;
    public LocalDate fechaNacimiento;
    public Modulo[] modulosImpartibles = new Modulo[0];
    private Persona personaAlumno = new Persona();

    public void pedirAtributos(){
        pedirNombreCompleto();
        pedirFechaNacimiento();
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
    public void asignarModuloImpartido(Modulo modulo){
        modulosImpartibles = Arrays.copyOf(modulosImpartibles, modulosImpartibles.length + 1);
        modulosImpartibles[modulosImpartibles.length - 1] = modulo;
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
        "Fecha de nacimiento: " + fechaNacimiento
        */
        String ficha;
        int numEdad = LocalDate.now().getYear() - fechaNacimiento.getYear();
        String Cabecera = "Ficha Alumno/a\n===============\n";
        String usuario = "Usuario: " + getUsername();
        String nombre = "Nombre: " + this.nombre;
        String apellidos = String.format("Apellidos: %s %s", apellido1, apellido2);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String nacimiento = "Fecha de nacimiento: " + fechaNacimiento.format(formato);
        String edad = "Edad: " + numEdad;
        String modulosStr = "\nModulos impartibles:";

        if (fechaNacimiento == null) {
            ficha = String.format("%s%s%n%s%n%s%n", Cabecera, usuario, nombre, apellidos);
        } else {
            ficha = String.format("%s%s%n%s%n%s%n%s%n%s%n", Cabecera, usuario, nombre, apellidos, nacimiento, edad);
        }
        for (int i = 0; i < modulosImpartibles.length; i++) {
            modulosStr +=  String.format(" %s", modulosImpartibles[i].nombre);
            if (i != modulosImpartibles.length-1) {
                modulosStr += ",";
            }
        }
        ficha += modulosStr;

        return ficha;
    }
}
