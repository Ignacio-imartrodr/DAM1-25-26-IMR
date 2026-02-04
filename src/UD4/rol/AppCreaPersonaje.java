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
    public static void guardarPersonaje(Personaje personaje){
        boolean restart = false;
        while (restart) {
            restart = false;
            System.out.println("Opciones: \nJson o Csv");
            System.out.print("¿Quieres guardar el personaje en un fichero? (s/n): ");
            if (Util.confirmarSN()) {
                System.out.print("¿Quieres guardar el personaje en un fichero.CSV ? (s/n): ");
                if (Util.confirmarSN()) {
                    /*System.out.print("Ruta del fichero: ");
                    String rutaFichero = Util.pedirPorTeclado(false); */
                    String rutaFichero = "src\\UD4\\rol\\PersonajesGuardados.csv";
                    Util.writeStringToFile(personaje.toCsvString(), rutaFichero);
                    System.out.println("Personaje guardado en " + rutaFichero);
                } else {
                    System.out.print("¿Quieres guardar el personaje en un fichero.JSON ? (s/n): ");
                    if (Util.confirmarSN()) {
                        System.out.println("Opciones: \nURL o Ruta del fichero");
                        System.out.print("¿Quieres guardar el personaje mediante una URL ? (s/n): ");
                        if (Util.confirmarSN()) {
                            /*System.out.print("Ruta del fichero: ");
                            String rutaFichero = Util.pedirPorTeclado(false); */
                            String rutaFichero = "src\\UD4\\rol\\PersonajesGuardados.json";
                            Util.writeStringToFile(personaje.toJsonString(), rutaFichero);
                            System.out.println("Personaje guardado en " + rutaFichero);
                        } else {
                            System.out.print("¿Quieres guardar el personaje mediante una Ruta a un archivo.JSON ? (s/n): ");
                            if (Util.confirmarSN()) {
                                /*System.out.print("Ruta del fichero: ");
                                String rutaFichero = Util.pedirPorTeclado(false); */
                                String rutaFichero = "src\\UD4\\rol\\PersonajesGuardados.json";
                                Util.writeStringToFile(personaje.toJsonString(), rutaFichero);
                                System.out.println("Personaje guardado en " + rutaFichero);
                            } else {
                                restart = true;
                            }
                        }
                    } else {
                        restart = true;
                    }
                }
            }
        }
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
                    personajesNuevos[personajesNuevos.length] = personaje;
                    guardarPersonaje(personaje);
                }
            } else {
                System.out.println();
                seguir = false;
            }
        }
        return personajesNuevos;
    }
}
