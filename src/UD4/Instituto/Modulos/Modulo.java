package UD4.Instituto.Modulos;

import java.util.Arrays;

import UD4.Instituto.Utiles;
import UD4.Instituto.Personas.Alumno;
import UD4.Instituto.Personas.Profesor;

public class Modulo {
    public Alumno[] alumnosMatriculados;
    public Profesor[] ProfesoresImpartiendo; 
    public int horas;
    public String nombre;
    public double[] notas;
    
    public void mostrar() {
        System.out.println("Modulo: " + nombre);
        System.out.println("=========================");
        System.out.println("Horas: " + horas);
        System.out.println("Cantidad de alumnado matriculado: " + alumnosMatriculados.length);
    }
    public void mostrarAlumnado(){
        for (Alumno alumno : alumnosMatriculados) {
            System.out.println(alumno.getFicha());
            System.out.println("____________________");
        }
    }
    
    public void asignarNotasAAlumnos(){
        notas = new double[alumnosMatriculados.length];
        int i = 0;
        for (Alumno alumno : alumnosMatriculados) {
            System.out.print("Nota de " + alumno.nombreCompleto + " con id \"" + alumno.id + ": ");
            notas[i] = Double.parseDouble(Utiles.pedirPorTeclado(true));
            alumno.asignarNotaAAlumno(notas[i]);
            i++;
        }

    }
    public void pedirAtributos(){
        pedirNombre();
        pedirHoras();
    }
    public void pedirNombre(){
        System.out.print("Nombre: ");
        nombre = Utiles.pedirPorTeclado(false);
        
    }
    public void pedirHoras(){
        System.out.print("Horas: ");
        horas = Integer.parseInt(Utiles.pedirPorTeclado(true));
    }
    public void asignarAProfesorado(Profesor[] profesorado){
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
            int id = 0;
            /*System.out.print("Ruta del fichero CSV: ");
            String rutaCSV = pedirPorTeclado(false);*/
            String rutaCSV = "src\\UD4\\Instituto\\profesoresModulo.csv";
            Profesor[] ProfesoresCSV = Profesor.darArrayProfesoresDeCSV(rutaCSV);
            for (Profesor profesor : ProfesoresCSV) {
                profesor.id = id;
                if (Utiles.verificaObjetoEnArray(profesor, profesorado)) {
                    profesor.asignarModuloImpartido(this);
                    id++;
                } else {
                    System.out.println("El profesor/a " + profesor.nombreCompleto + " con id " + profesor.id + " no existe: ");
                }
            }
        }else{
            boolean imparte = false;
            respuesta = "";
            for (Profesor profesor : profesorado) {
                System.out.print("El profesor/a " + profesor.nombreCompleto + " con id " + profesor.id + " imparte este modulo? (s/n): " );
                respuesta = Utiles.pedirPorTeclado(false);
                while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
                    System.out.println("Responde unicamente con \"s\" o \"n\"");
                    respuesta = Utiles.pedirPorTeclado(false);
                }
                if (respuesta.equalsIgnoreCase("s")) {
                    imparte = true;
                }
                if (imparte) {
                    profesor.modulosImpartidos = Arrays.copyOf(profesor.modulosImpartidos, profesor.modulosImpartidos.length + 1);
                    profesor.modulosImpartidos[profesor.modulosImpartidos.length -1] = this;
                }
                imparte = false;
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
            ProfesoresImpartiendo = Profesor.darArrayProfesoresDeCSV(rutaCSV);
            for (Profesor profesor : ProfesoresImpartiendo) {
                profesor.asignarModuloImpartido(this);
            }
        }else {
            final String TERMINAR = "fin";
            int id = 0;
            boolean terminado = false;
            ProfesoresImpartiendo = new Profesor[0];
            Profesor newProfesor;
            while (!terminado) {
                newProfesor = new Profesor();
                System.out.println("Responde \"fin\" para terminar el listado");
                newProfesor.pedirNombreCompleto();
                if (!(newProfesor.nombre.equalsIgnoreCase(TERMINAR) || newProfesor.apellido1.equalsIgnoreCase(TERMINAR)|| newProfesor.apellido2.equalsIgnoreCase(TERMINAR))) {
                    newProfesor.pedirFechaNacimiento();
                    newProfesor.id = id;
                    newProfesor.asignarModuloImpartido(this);
                    ProfesoresImpartiendo = Arrays.copyOf(ProfesoresImpartiendo, ProfesoresImpartiendo.length + 1);
                    ProfesoresImpartiendo[ProfesoresImpartiendo.length - 1] = newProfesor;
                    id++;
                } else {
                    terminado = true;
                }
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
            alumnosMatriculados = Alumno.darArrayAlumnosDeCSV(rutaCSV);
            for (Alumno alumno : alumnosMatriculados) {
                alumno.asignarModuloAAlumno(this);
            }
        }else {
            final String TERMINAR = "fin";
            int id = 0;
            boolean terminado = false;
            alumnosMatriculados = new Alumno[0];
            Alumno newAlumno;
            while (!terminado) {
                newAlumno = new Alumno();
                System.out.println("Responde \"fin\" para terminar el listado");
                newAlumno.pedirNombreCompleto();
                if (!(newAlumno.nombre.equalsIgnoreCase(TERMINAR) || newAlumno.apellido1.equalsIgnoreCase(TERMINAR)|| newAlumno.apellido2.equalsIgnoreCase(TERMINAR))) {
                    newAlumno.pedirFechaNacimiento();
                    newAlumno.id = id;
                    newAlumno.asignarModuloAAlumno(this);
                    alumnosMatriculados = Arrays.copyOf(alumnosMatriculados, alumnosMatriculados.length + 1);
                    alumnosMatriculados[alumnosMatriculados.length - 1] = newAlumno;
                    id++;
                } else {
                    terminado = true;
                }
            }
        }
    }
    public static void main(String[] args) {
        boolean parar = false;
        final String TERMINAR = "fin";
        Modulo[] ModulosCreados = new Modulo[0];
        while (!parar) {
            Modulo modulo = new Modulo();
            System.out.println("Responde \"fin\" para terminar el listado");
            modulo.pedirAtributos();
            if (!modulo.nombre.equalsIgnoreCase(TERMINAR)) {
                modulo.pedirAlumnado();
                modulo.asignarNotasAAlumnos();
                ModulosCreados = Arrays.copyOf(ModulosCreados, ModulosCreados.length + 1);
                ModulosCreados[ModulosCreados.length - 1] = modulo;
            } else {
                parar = true;
            }
        }
        System.out.println("Lista de Modulos y sus datos");
            System.out.println("-------------------------------");
        for (Modulo modulo : ModulosCreados) {
            modulo.mostrar();
            System.out.println("Lista de alumnos");
            System.out.println("--------------------");
            modulo.mostrarAlumnado();
            System.out.println("_________________________________");
        }
    }
}
