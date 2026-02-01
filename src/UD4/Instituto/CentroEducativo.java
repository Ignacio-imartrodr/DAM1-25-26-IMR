package UD4.Instituto;

import java.util.Arrays;

import UD4.Instituto.Modulos.Modulo;
import UD4.Instituto.Personas.Alumno;
import UD4.Instituto.Personas.Profesor;

/**
 * @author Ignacio MR
 */

public class CentroEducativo {
    public String nombre;
    public Modulo[] modulosDisponibles = new Modulo[0];
    public Alumno[] Estudiantes;
    public Profesor[] Profesorado; 


    public void mostrarModulos(){
        System.out.println("Lista de Modulos y sus datos");
            System.out.println("-------------------------------");
        for (Modulo modulo : modulosDisponibles) {
            modulo.mostrar();
            System.out.println("Lista de alumnos");
            System.out.println("--------------------");
            modulo.mostrarAlumnado();
            System.out.println("_________________________________");
        }
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
        String Cabecera = "Ficha Alumno/a\n===============\n";
        String nombre = "Nombre: " + this.nombre;
        int cantModulos = 0;
        String modulosStr = "Módulos disponibles: ";

        for (int i = 0; i < modulosDisponibles.length; i++) {
            modulosStr +=  String.format(" %s", modulosDisponibles[i].nombre);
            if (i != modulosDisponibles.length-1) {
                modulosStr += ",";
            }
            cantModulos++;
        }
        String cantModulosStr = "Cantidad de módulos disponibles: " + cantModulos;
        ficha = String.format("%s%s%n%s%n%s%n", Cabecera, nombre, cantModulosStr, modulosStr);

        return ficha;
    }
    public void pedirModulos(){
        boolean parar = false;
        final String TERMINAR = "fin";
        while (!parar) {
            Modulo modulo = new Modulo();
            System.out.println("Responde \"fin\" cuando no haya más modulos");
            modulo.pedirAtributos();
            if (!modulo.nombre.equalsIgnoreCase(TERMINAR)) {
                modulo.pedirAlumnado();
                modulo.asignarNotasAAlumnos();
                modulosDisponibles = Arrays.copyOf(modulosDisponibles, modulosDisponibles.length + 1);
                modulosDisponibles[modulosDisponibles.length - 1] = modulo;
            } else {
                parar = true;
            }
        }
    }
    public static void main(String[] args) {
        CentroEducativo CentroEducativo = new CentroEducativo();

        CentroEducativo.nombre = "Chan do Monte";
        CentroEducativo.pedirModulos(); 
        CentroEducativo.mostrarModulos();
        System.out.println(CentroEducativo.getFicha());
    }
}
