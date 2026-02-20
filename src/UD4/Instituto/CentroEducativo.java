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
    public Alumno[] estudiantes;
    public Profesor[] profesorado; 


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
                modulo.asignarAProfesorado(profesorado);
                modulo.pedirAlumnado();
                modulo.asignarNotasAAlumnos();
                modulosDisponibles = Arrays.copyOf(modulosDisponibles, modulosDisponibles.length + 1);
                modulosDisponibles[modulosDisponibles.length - 1] = modulo;
            } else {
                parar = true;
            }
        }
    }
    public void pedirAlumnado(){
        boolean conCSV = false;
        String respuesta;
        System.out.print("¿Deseas introducir el alumnado desde un fichero CSV? (s/n): ");
        respuesta = Utiles.pedirPorTeclado(false);
        while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
            System.out.println("Responde unicamente con \"s\" o \"n\"");
            respuesta = Utiles.pedirPorTeclado(false);
        }
        if (respuesta.equalsIgnoreCase("s")) {
            conCSV = true;
        }
        if (conCSV) {
            /*System.out.print("Ruta del fichero CSV: ");
            String rutaCSV = pedirPorTeclado(false);*/
            String rutaCSV = "src\\UD4\\Instituto\\alumnos.csv";
            estudiantes = Alumno.darArrayAlumnosDeCSV(rutaCSV);
        }else {
            final String TERMINAR = "fin";
            int id = 0;
            boolean terminado = false;
            estudiantes = new Alumno[0];
            Alumno newAlumno;
            while (!terminado) {
                newAlumno = new Alumno();
                System.out.println("Responde \"fin\" para terminar el listado");
                newAlumno.pedirNombreCompleto();
                if (!(newAlumno.nombre.equalsIgnoreCase(TERMINAR) || newAlumno.apellido1.equalsIgnoreCase(TERMINAR)|| newAlumno.apellido2.equalsIgnoreCase(TERMINAR))) {
                    newAlumno.pedirFechaNacimiento();
                    newAlumno.id = id;
                    estudiantes = Arrays.copyOf(estudiantes, estudiantes.length + 1);
                    estudiantes[estudiantes.length - 1] = newAlumno;
                    id++;
                } else {
                    terminado = true;
                }
            }
        }
    }
    public void pedirProfesorado(){
        boolean conCSV = false;
        String respuesta;
        System.out.print("¿Deseas introducir el profesorado desde un fichero CSV? (s/n): ");
        respuesta = Utiles.pedirPorTeclado(false);
        while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
            System.out.println("Responde unicamente con \"s\" o \"n\"");
            respuesta = Utiles.pedirPorTeclado(false);
        }
        if (respuesta.equalsIgnoreCase("s")) {
            conCSV = true;
        }
        if (conCSV) {
            /*System.out.print("Ruta del fichero CSV: ");
            String rutaCSV = pedirPorTeclado(false);*/
            String rutaCSV = "src\\UD4\\Instituto\\profesores.csv";
            profesorado = Profesor.darArrayProfesoresDeCSV(rutaCSV);
        }else {
            final String TERMINAR = "fin";
            int id = 0;
            boolean terminado = false;
            profesorado = new Profesor[0];
            Profesor newProfesor;
            while (!terminado) {
                newProfesor = new Profesor();
                System.out.println("Responde \"fin\" para terminar el listado");
                newProfesor.pedirNombreCompleto();
                if (!(newProfesor.nombre.equalsIgnoreCase(TERMINAR) || newProfesor.apellido1.equalsIgnoreCase(TERMINAR)|| newProfesor.apellido2.equalsIgnoreCase(TERMINAR))) {
                    newProfesor.pedirFechaNacimiento();
                    newProfesor.id = id;
                    profesorado = Arrays.copyOf(profesorado, profesorado.length + 1);
                    profesorado[profesorado.length - 1] = newProfesor;
                    id++;
                } else {
                    terminado = true;
                }
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
