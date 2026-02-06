package UD4.rol;

import java.util.Arrays;

/** 
 * @author Ignacio MR
 * 
 * Crea un programa de consola que permita al usuario generar 
 * y editar personajes de diferentes modos y guardarlos
 * en disco en un fichero de texto en formato JSON. 
 */

public class AppCreaPersonaje {
    public static String pedirRuta(){
        boolean restart = true;
        String rutaFichero = "-1";
        while (restart) {
            restart = false;
            System.out.println("Opciones: \nJson o Csv");
            System.out.print("¿Quieres guardar en un fichero? (s/n): ");
            if (Util.confirmarSN()) {
                System.out.print("¿Quieres guardar en un fichero.CSV ? (s/n): ");
                if (Util.confirmarSN()) {
                    /*System.out.print("Ruta del fichero: ");
                    String rutaFichero = Util.pedirPorTeclado(false); */
                    rutaFichero = "src\\UD4\\rol\\PersonajesGuardados.csv";
                    if (!rutaFichero.endsWith(".csv")) {
                        System.out.println("La ruta debe contener un fichero con extensión .csv");
                        restart = true;
                    }
                } else {
                    System.out.print("¿Quieres guardar en un fichero.JSON ? (s/n): ");
                    if (Util.confirmarSN()) {
                        /*System.out.print("Ruta del fichero: ");
                        String rutaFichero = Util.pedirPorTeclado(false); */
                        rutaFichero = "src\\UD4\\rol\\PersonajesGuardados.json";
                        if (!rutaFichero.endsWith(".json")) {
                            System.out.println("La ruta debe contener un fichero con extensión .json");
                            restart = true;
                        } else {
                                restart = true;
                        }
                    }
                }
            } else {
                System.out.println("Personaje no guardado.");
            }
        }
        return rutaFichero;
    }
    public static Personaje[] pedirPersonajes() {
        Personaje[] personajesNuevos = new Personaje[0];
        boolean seguir = true;
        while (seguir) {
            System.out.print("¿Quieres crear un nuevo personaje? (s/n): ");
            if (Util.confirmarSN()) {
                Personaje personaje = new Personaje();
                System.out.println("\nRazas disponibles:\n\n" + Personaje.getRazasStats());
                personaje.crearPersonaje();
                personaje.mostrar();
                System.out.println("¿Es el personaje correcto? (s/n):");
                if (Util.confirmarSN()) {
                    personajesNuevos = Arrays.copyOf(personajesNuevos, personajesNuevos.length + 1);
                    personajesNuevos[personajesNuevos.length - 1] = personaje;
                }
            } else {
                System.out.println();
                seguir = false;
            }
        }
        return personajesNuevos;
    }
    public static void main(String[] args) {
        Personaje[] personajesNuevos = pedirPersonajes();
        for (Personaje personaje : personajesNuevos) {
            String ruta = pedirRuta();
            if (ruta.equals("-1")) {
                System.out.println("No se guardó el personaje.");
            } else {
                personaje.toFile(ruta);
            }
        }
    }
}
