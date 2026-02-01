package UD4.Instituto.Modulos;

import java.util.Arrays;
import java.util.Scanner;

import UD4.Instituto.Personas.Alumno;

public class Modulo {
    public Alumno[] alumnadoMatriculado;
    public int horas;
    public String nombre;
    public double[] notas;
    public void mostrar() {
        System.out.println("Modulo: " + nombre);
        System.out.println("=========================");
        System.out.println("Horas: " + horas);
        System.out.println("Cantidad de alumnado matriculado: " + alumnadoMatriculado.length);
    }
    public void mostrarAlumnado(){
        for (Alumno alumno : alumnadoMatriculado) {
            System.out.println(alumno.getFicha());
            System.out.println("____________________");
        }
    }
    
    private static Scanner sc = new Scanner(System.in,"Windows-1252");
    public void asignarNotasAAlumnos(){
        notas = new double[alumnadoMatriculado.length];
        int i = 0;
        for (Alumno alumno : alumnadoMatriculado) {
            System.out.print("Nota de " + alumno.nombreCompleto + " con id \"" + alumno.id + ": ");
            notas[i] = Double.parseDouble(pedirPorTeclado(true));
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
        nombre = pedirPorTeclado(false);
        
    }
    public void pedirHoras(){
        System.out.print("Horas: ");
        horas = Integer.parseInt(pedirPorTeclado(true));
    }
    public void pedirAlumnado(){
        final String TERMINAR = "fin";
        int id = 0;
        boolean terminado = false;
        alumnadoMatriculado = new Alumno[0];
        Alumno newAlumno;
        while (!terminado) {
            newAlumno = new Alumno();
            System.out.println("Responde \"fin\" para terminar el listado");
            newAlumno.pedirNombreCompleto();
            if (!(newAlumno.nombre.equalsIgnoreCase(TERMINAR) || newAlumno.apellido1.equalsIgnoreCase(TERMINAR)|| newAlumno.apellido2.equalsIgnoreCase(TERMINAR))) {
                newAlumno.pedirFechaNacimiento();
                newAlumno.id = id;
                newAlumno.asignarModuloAAlumno(this);
                alumnadoMatriculado = Arrays.copyOf(alumnadoMatriculado, alumnadoMatriculado.length + 1);
                alumnadoMatriculado[alumnadoMatriculado.length - 1] = newAlumno;
                id++;
            } else {
                terminado = true;
            }
        }
    }
    private String pedirPorTeclado(boolean pideNumero){
        String var;
        boolean sonNumeros = true;
        try {
            var = sc.nextLine();
            for (int i = 0; i < var.length() && sonNumeros; i++) {
                if (!Character.isDigit(var.charAt(i)) || var.charAt(i) != ',') {
                    sonNumeros = false;
                }
            }
            
            if (!pideNumero) {
                if (!sonNumeros) {
                    String charCorrectos = "áéíóúüñ";
                    String charIncorrectos = " ¡¢£¤"; //Para ISO-8859-1
                    String charIncorrectos1 = " ‚¡¢£�¤";//Para Windows-1252
                    String[] StringsIncorrectos = new String[] {charIncorrectos, charIncorrectos1};
                    for (int i = 0; i < var.length(); i++) {
                        for (int j = 0; j < StringsIncorrectos.length; j++) {
                            for (int k = 0; k < StringsIncorrectos[j].length(); k++) {
                                var = var.replace(StringsIncorrectos[j].charAt(k), charCorrectos.charAt(k));
                            }
                        }
                    }
                }
            } else {
                if (!sonNumeros) {
                    System.out.print("Error, introdruce un valor numérico: ");
                    return pedirPorTeclado(pideNumero);
                }
            }
            return var;
        } catch (Exception e) {
            sc.nextLine();
            System.out.println("Error, vuelve a intentar");
            return pedirPorTeclado(pideNumero);
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
