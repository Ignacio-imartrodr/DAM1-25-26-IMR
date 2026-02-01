package UD4.Instituto;

import java.util.Arrays;

import UD4.Instituto.Modulos.Modulo;

/**
 * @author Ignacio MR
 */

public class ProgramaEducativo {
    public static void main(String[] args) {
        boolean parar = false;
        final String TERMINAR = "fin";
        Modulo[] ModulosCreados = new Modulo[0];
        while (!parar) {
            Modulo modulo = new Modulo();
            System.out.println("Responde \"fin\" cuando no haya más modulos");
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
