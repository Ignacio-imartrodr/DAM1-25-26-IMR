package UD4.Instituto.Personas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import UD4.Instituto.Modulos.Modulo;

public class Alumno {
    public String nombre;
    public String apellido1;
    public String apellido2;
    public String nombreCompleto = apellido1 + " " + apellido2 + ", " + nombre;
    public int id;
    public LocalDate fechaNacimiento;
    public Modulo[] modulosMatriculado = new Modulo[0];
    public double[] notasModulos = new double[0];
    public double mediaGeneral = getNotaMedia();
    private Persona personaAlumno = new Persona();

    public void asignarModuloAAlumno(Modulo modulo){
        modulosMatriculado = Arrays.copyOf(modulosMatriculado, modulosMatriculado.length + 1);
        modulosMatriculado[modulosMatriculado.length - 1] = modulo;
    }
    public void asignarNotaAAlumno(double nota){
        notasModulos = Arrays.copyOf(notasModulos, notasModulos.length + 1);
        notasModulos[modulosMatriculado.length - 1] = nota;
    }
    public int posicionAlumnoEnModulo(Modulo modulo){
        int posicion = -1;
        boolean encontrado = false;
        for (int i = 0; i < modulosMatriculado.length && !encontrado; i++) {
            if (id == modulo.alumnosMatriculados[i].id){
                encontrado = true;
                posicion = i;
            }
        }
        return posicion;
    }
    public double getNotaMedia(){
        double media = 0;
        int posicion = 0;
        for (Modulo modulo : modulosMatriculado) {
            posicion = posicionAlumnoEnModulo(modulo);
            try {
                media += modulo.notas[posicion];
            } catch (Exception e) {
                System.out.println("Alumno no matriculado en alguno de los ciclos");
            }
            
        }
        media /= modulosMatriculado.length;
        return media;
    }
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
    public void mostrarNotas(){
        int posicion;
        for (Modulo modulo : modulosMatriculado) {
            System.out.print("Nota de " + modulo.nombre + ": ");
            posicion = posicionAlumnoEnModulo(modulo);
            try {
                System.out.println(modulo.notas[posicion]);
            } catch (Exception e) {
                System.out.println("Alumno no matriculado en el ciclo");
            }
        }
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
        "Nota media: " + notaMedia
        "Fecha de nacimiento: " + fechaNacimiento
        */
        String ficha;
        double notaMedia = getNotaMedia();
        int numEdad = LocalDate.now().getYear() - fechaNacimiento.getYear();

        String Cabecera = "Ficha Alumno/a\n=================\n";
        String usuario = "Usuario: " + getUsername();
        String nombre = "Nombre: " + this.nombre;
        String apellidos = String.format("Apellidos: %s %s", apellido1, apellido2);
        String media = String.format("Nota media: %.2f", notaMedia);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String nacimiento = "Fecha de nacimiento: " + fechaNacimiento.format(formato);
        String edad = "Edad: " + numEdad;

        if (fechaNacimiento == null || notaMedia == 0) {
            if (fechaNacimiento == null && notaMedia == 0) {
                ficha = String.format("%s%s%n%s%n%s", Cabecera, usuario, nombre, apellidos);
            } else if (fechaNacimiento == null) {
                ficha = String.format("%s%s%n%s%n%s%n%s", Cabecera, usuario, nombre, apellidos, media);
            } else {
                ficha = String.format("%s%s%n%s%n%s%n%s%n%s", Cabecera, usuario, nombre, apellidos, nacimiento, edad);
            }
        } else {
            ficha = String.format("%s%s%n%s%n%s%n%s%n%s%n%s", Cabecera, usuario, nombre, apellidos, media, nacimiento, edad);
        }
        return ficha;
    }
    public static void main(String[] args) {
        Alumno alumno = new Alumno();
        alumno.pedirAtributos();
        System.out.println(alumno.getFicha());
    }
}
