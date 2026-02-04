package UD4.rol;

import java.util.Arrays;

/** 
 * @author Ignacio MR
 * 
 * Crea un programa de consola que permita al usuario generar 
 * y editar personajes de diferentes modos y guardarlos
 * en disco en un fichero de texto en formato JSON o CSV. 
 */

public class AppCombateSingular {
    private static boolean cambiar(boolean turno){
        return turno ? false : true;
    }
    private static Personaje[] seleccionarPersonajes(Personaje[] PersonajesCreados){
        Personaje[] personajesEnBatalla = new Personaje[2];
        //TODO seleccionar del array dado
        return personajesEnBatalla;
    }
    private static Personaje[] extraerPersonaje(String[] arrayPersonajes, boolean esJson){
        Personaje[] personajesExtraidos = new Personaje[arrayPersonajes.length];
        String[] atributos;
        int i = 0;
        int idPers = 1;
        if (esJson) {
            for (String linea : arrayPersonajes) {
                atributos = new String[7];
                atributos = linea.split(",");
                for (String string : atributos) {
                    atributos[i] = string.substring(string.indexOf(":") + 1);
                }
                try {
                    Personaje newPersonaje = new Personaje(atributos[0], atributos[1], atributos[2], atributos[3], atributos[4], atributos[5], atributos[6]);
                    personajesExtraidos[i]= newPersonaje;
                    i++;
                } catch (Exception e) {
                    System.out.println("El personaje número " + idPers + "contiene un error.");
                }
                idPers++;
            }
        } else {
            for (String linea : arrayPersonajes) {
                atributos = new String[7];
                atributos = linea.split(",");
                try {
                    Personaje newPersonaje = new Personaje(atributos[0], atributos[1], atributos[2], atributos[3], atributos[4], atributos[5], atributos[6]);
                    personajesExtraidos[i]= newPersonaje;
                    i++;
                } catch (Exception e) {
                    System.out.println("El personaje número " + idPers + "contiene un error.");
                }
                idPers++;
            }
        }
        return personajesExtraidos;
    }
    private static Personaje[] cargarPersonajesDeArchivo(String rutaFile){
        String[] personajesGuardados;
        Personaje[] personajesFichero = null;
        boolean restart = false;
        while (restart) {
            restart = false;
            System.out.println("Opciones: \nJson o Csv");
            System.out.print("¿Quieres cargar personajes desde" + rutaFile + "? (s/n): ");
            if (Util.confirmarSN()) {
                System.out.print("¿Es un fichero.CSV ? (s/n): ");
                if (Util.confirmarSN()) {
                    personajesFichero = extraerPersonaje(Util.readFileToStringArray(rutaFile), false);
                } else {
                    System.out.print("¿Es un fichero.JSON ? (s/n): ");
                    if (Util.confirmarSN()) {
                        System.out.println("Opciones: \nURL o Ruta del fichero");
                        System.out.print("¿Quieres cargar mediante una URL ? (s/n): ");
                        if (Util.confirmarSN()) {
                            boolean errorUrl = true;
                            String temp = rutaFile;
                            while (errorUrl) {
                                errorUrl = false;
                                try {
                                    personajesGuardados = Util.getJson(temp).split("}");
                                    personajesFichero = extraerPersonaje(personajesGuardados, true);
                                } catch (Exception e) {
                                    System.out.println("Error en la url");
                                    System.out.print("URL? (\"n\" para salir): ");
                                    temp = Util.pedirPorTeclado(false);
                                    if (!temp.equalsIgnoreCase("n")) {
                                        errorUrl = true;
                                        restart = true;
                                    }
                                }
                            }
                        } else {
                            System.out.print("¿Es mediante una Ruta a un archivo.JSON ? (s/n): ");
                            if (Util.confirmarSN()) {
                                personajesFichero = extraerPersonaje(Util.readFileToStringArray(rutaFile), true);
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
        return personajesFichero;
    }
    private static void getPersonajes(Personaje[] personajesCreados){
        Personaje[] temp;

        System.out.print("¿Quieres cargar los personajes de un archivo? (s/n): ");
        if (Util.confirmarSN()) {
            /*System.out.print("Ruta del fichero (Ej| src\\UD4\\rol\\archivo.extensión): ");
            String rutaFichero = Util.pedirPorTeclado(false); */
            String rutaFichero = "src\\UD4\\rol\\PersonajesGuardados.json";
            temp = cargarPersonajesDeArchivo(rutaFichero);
            for (Personaje personaje : temp) {
                personajesCreados = Arrays.copyOf(personajesCreados, personajesCreados.length + 1);
                personajesCreados[personajesCreados.length] = personaje;
            }
        }
        temp = AppCreaPersonaje.pedirPersonajes();
        for (Personaje personaje : temp) {
            personajesCreados = Arrays.copyOf(personajesCreados, personajesCreados.length + 1);
            personajesCreados[personajesCreados.length] = personaje;
        }
    }
    public static void main(String[] args) {
        Personaje[] personajesCreados = new Personaje[0];
        Personaje[] personajesEnBatalla = new Personaje[2];
        boolean turno = true;

        getPersonajes(personajesCreados);

        System.out.println("\nPersonajes disponibles:\n");
        for (Personaje personaje : personajesCreados) {
            personaje.mostrar();
        }
        personajesEnBatalla = seleccionarPersonajes(personajesCreados);
        while (personajesEnBatalla[0].estaVivo() && personajesEnBatalla[1].estaVivo()) {
            

            turno = cambiar(turno);
        }

    }
}
