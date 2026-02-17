package UD4.Rol.Main;

import java.util.Arrays;
import java.util.Random;

import UD4.Rol.Objetos.Personaje;
import UD4.Rol.Utilidades.PersonajeException;
import UD4.Rol.Utilidades.Util;

/**
 * @author Ignacio MR
 * 
 * Crea un programa de consola que permita al usuario generar
 * y editar personajes de diferentes modos y guardarlos
 * en disco en un fichero de texto en formato JSON o CSV.
 */

public class AppCombateSingular {
    private static boolean cambiar(boolean turno) {
        return turno = turno ? false : true;
    }
    public static String pedirRuta(){
        boolean restart = true;
        String rutaFichero = null;
        while (restart) {
            restart = false;
            System.out.print("¿Quieres dar una ruta? (S/n): ");
            if (Util.escogerOpcion("s", "n")) {
                System.out.println("OPCIONES: \nJson o Csv");
                System.out.print("Ruta del fichero (Ej| src\\UD4\\rol\\archivo.extensión): ");
                rutaFichero = Util.pedirPorTeclado(false);
                if ((rutaFichero == null) || (!rutaFichero.endsWith(".json") || !rutaFichero.endsWith(".csv"))) {
                    System.out.println("La ruta debe dirigir a un fichero con extensión .csv o .json");
                    restart = true;
                }
            } else {
                System.out.println("Ruta no guardada.");
                rutaFichero = null;
            }
        }
        return rutaFichero;
    }
    static Personaje[] seleccionarPersonajes(Personaje[] personajesCreados, int cantidadASeleccionar) {
        Personaje[] personajesEnBatalla = new Personaje[cantidadASeleccionar];
        boolean esSiguiente = true;
        for (int i = -1, j = 0, skip = -1; j < personajesEnBatalla.length;) {
            if (esSiguiente) {
                if (i == personajesCreados.length -1) {
                    i = 0;
                } else {
                    i++;
                }
            } else {
                if (i == 0) {
                    i = personajesCreados.length -1;
                } else {
                    i--;
                }
            }
            if (i != skip){
                personajesCreados[i].mostrar();
                System.out.print("¿Quieres seleccionar este personaje? (S/n): ");
                if (Util.escogerOpcion("S", "n")) {
                    personajesEnBatalla [j] = personajesCreados[i];
                    j++;
                    skip = i;
                }
            }
            if (j < personajesEnBatalla.length) {
                System.out.println("Siguiente personaje o anterior? (S/a): ");
                esSiguiente = Util.escogerOpcion("S", "a");
            }
        }
        return personajesEnBatalla;
    }
    private static void guardarPorPersonaje(Personaje[] personajesCreados){
        String rutaFichero;
        String rutaPrevia = "";
        for (Personaje personaje : personajesCreados) {
            personaje.mostrar();
            System.out.print("¿Quieres guardar este personaje? (S/n): ");
            if (Util.escogerOpcion("S", "n")) {
                boolean repetir = true;
                while (repetir) {
                    System.out.print("Ruta del fichero Json o Csv (Enter para usar ruta previa): ");
                    rutaFichero = Util.pedirPorTeclado(false);
                    if (rutaFichero == null) {
                        rutaFichero = rutaPrevia;
                    } else {
                        rutaPrevia = rutaFichero;
                    }
                    if (rutaFichero.endsWith(".json")) {
                        if (Util.UbiObjetoEnArray(personaje, AppCreaPersonaje.getPersonajesJson(rutaFichero)) == -1) {
                            Util.writeStringToJson(personaje.toJsonString(), rutaFichero, true);
                            repetir = false;
                        }
                    } else if (rutaFichero.endsWith(".csv")) {
                        Util.writeStringToCsv(personaje.toCsvString(), rutaFichero);
                        repetir = false;
                    } else {
                        System.out.println("Ruta no valida");
                        System.out.println( "¿Quieres intentar de nuevo? (S/n): ");
                        if (!Util.escogerOpcion("S", "n")) {
                            System.out.println("Personaje no guardado.");
                            repetir = false;
                        }
                    }
                }
            } else {
                System.out.println("Personaje no guardado.");
            }
        }    
    }
    static Personaje[] cargarPersonajesDeArchivo(String rutaFile){
        Personaje[] personajesFichero = new Personaje[0];
        boolean restart = true;
        while (restart) {
            restart = false;
            System.out.println("Opciones: \nJson o Csv");
            System.out.print("¿Quieres cargar personajes desde " + rutaFile + "? (S/n): ");
            if (Util.escogerOpcion("S", "n")) {
                if (rutaFile.endsWith(".csv")) {
                    personajesFichero = AppCreaPersonaje.getPersonajesCsv(rutaFile);
                    if (personajesFichero.length == 0) {
                        System.out.println("El fichero no contenía personajes");
                    }
                } else if (rutaFile.endsWith(".json") || rutaFile.startsWith("www.http") || rutaFile.startsWith("http")) {
                    if (rutaFile.startsWith("www.http") || rutaFile.startsWith("http")) {
                        boolean errorUrl = true;
                        String temp = rutaFile;
                        while (errorUrl) {
                            errorUrl = false;
                            try {
                                personajesFichero = AppCreaPersonaje.getPersonajesJson(rutaFile);
                                if (personajesFichero.length == 0) {
                                    System.out.println("El Json no contenía personajes");
                                }
                                System.out.print("¿Probar otra Url? (S/n): ");
                                if (Util.escogerOpcion("S", "n")) {
                                    errorUrl = true;
                                }
                            } catch (Exception e) {
                                errorUrl = true;
                                System.out.println("Error en la url");
                                System.out.print("URL? (\"n\" para salir): ");
                                temp = Util.pedirPorTeclado(false);
                                if (temp.equalsIgnoreCase("n")) {
                                    errorUrl = false;
                                    restart = true;
                                }
                            }
                        }
                    } else {
                        personajesFichero = AppCreaPersonaje.getPersonajesJson(rutaFile);
                        if (personajesFichero.length == 0) {
                            System.out.println("El fichero no contenía personajes");
                        }
                    }
                } else {
                    System.out.println("Error en la ruta");
                    System.out.print("Ruta? (\"n\" para salir): ");
                    rutaFile = Util.pedirPorTeclado(false);
                    if (rutaFile.equalsIgnoreCase("n")) {
                        System.out.println("personajes no cargados");
                        restart = false;
                    }
                }
            } else {
                System.out.println("Quierres ingresar otra ruta? (S/n)");
                if (Util.escogerOpcion("S", "n")) {
                    System.out.print("Ruta? (\"n\" para salir): ");
                    rutaFile = Util.pedirPorTeclado(false);
                    if (rutaFile.equalsIgnoreCase("n")) {
                        System.out.println("Personajes no cargados");
                        restart = false;
                    } else {
                        restart = true;
                    }
                } else {
                    System.out.println("No se han cargado personajes.");
                    restart = false;
                }
            }
        }
        return personajesFichero;
    }
    public static void bucleGuardadoPersonajes(Personaje[] personajesCreados){
        boolean repetir = true;
        while (repetir) {
            repetir = false;
            System.out.println("¿Quieres guardar los personajes? (S/n): ");
            if (Util.escogerOpcion("S", "n")) {
                System.out.println("¿Quieres guardar todos los personajes creados y cargados en un único Archivo? (S/n): ");
                if (Util.escogerOpcion("S", "n")) {
                    String rutaFichero;
                    rutaFichero = pedirRuta();
                    if (!(rutaFichero == null)) {
                        String personajes = "";
                        if (rutaFichero.endsWith(".json")) {
                            for (Personaje personaje : personajesCreados) {
                                if (!(personaje == null)) {
                                    personajes += personaje.toJsonString()+",\n";
                                }
                            }
                            if (!personajes.equals("")) {
                                personajes = personajes.substring(0, personajes.lastIndexOf(","));
                                Util.writeStringToJson(personajes, rutaFichero, false);
                            }
                        } else if (rutaFichero.endsWith(".csv")) {
                            Util.borrarFicheroYCrearloVacio(rutaFichero);
                            for (Personaje personaje : personajesCreados) {
                                if (!(personaje == null)) {
                                    Util.writeStringToCsv(personaje.toCsvString(), rutaFichero);
                                }
                            }
                        } else {
                            System.out.println("Ruta no valida.");
                            repetir = true;
                        }
                    } else {
                        System.out.println("Ruta no valida.");
                        repetir = true;
                    }
                } else{
                    System.out.println("¿Quieres elegir individualmente si guardar y donde cada personaje creado y cargado? (S/n): ");
                    if (Util.escogerOpcion("S", "n")) {
                        guardarPorPersonaje(personajesCreados);
                    } else {
                        System.out.println("Personajes no guardados.");
                        repetir = true;
                        
                    }
                }
            }
        }
    }
    private static Personaje[] getPersonajes(){
        Personaje[] personajesCreados = new Personaje[0];
        Personaje[] temp;
        while (personajesCreados.length < 2) {
            System.out.print("¿Quieres cargar los personajes de un archivo? (S/n): ");
            if (Util.escogerOpcion("S", "n")) {
                String rutaFichero;
                rutaFichero = pedirRuta();
                if (!(rutaFichero == null)) {
                    temp = cargarPersonajesDeArchivo(rutaFichero);
                    for (Personaje personaje : temp) {
                        personajesCreados = Arrays.copyOf(personajesCreados, personajesCreados.length + 1);
                        personajesCreados[personajesCreados.length - 1] = personaje;
                    }
                }
            }
            temp = AppCreaPersonaje.pedirPersonajes();
            for (Personaje personaje : temp) {
                personajesCreados = Arrays.copyOf(personajesCreados, personajesCreados.length + 1);
                personajesCreados[personajesCreados.length - 1] = personaje;
            }
            if (personajesCreados.length < 2) {
                System.out.println("Se necesitan al menos 2 personajes para la batalla.");
            }
        }
        return personajesCreados;
    }
    public static void main(String[] args) {
        final int NUM_COMBATIENTES = 2; 
        Personaje[] personajesCreados;
        personajesCreados = getPersonajes();

        AppCreaPersonaje.mostrarPersonajes(personajesCreados);
        bucleGuardadoPersonajes(personajesCreados);

        Personaje[] personajesEnBatalla = new Personaje[2];
        if (personajesCreados.length > 2) {
            personajesEnBatalla = seleccionarPersonajes(personajesCreados, NUM_COMBATIENTES);
            while (personajesEnBatalla[0] == null || personajesEnBatalla[1] == null) {
                System.out.println("No hay suficientes personajes para la batalla, selecciona al menos 2 personajes.");
                personajesEnBatalla = seleccionarPersonajes(personajesCreados, NUM_COMBATIENTES);
            }
        } else {
            personajesEnBatalla[0] = personajesCreados[0];
            personajesEnBatalla[1] = personajesCreados[1];
        }
        
        boolean batalla = true;
        while (batalla) {
            Random rnd = new Random();
            boolean turno = rnd.nextBoolean();
            while (personajesEnBatalla[0].estaVivo() && personajesEnBatalla[1].estaVivo()) {
                byte personajeEnTurno = (byte) (turno ? 0 : 1);
                String accion;
                boolean accionNoValida = true;
                int xp;
                System.out.println("\nTurno de " + personajesEnBatalla[personajeEnTurno].toString());
                while (accionNoValida) {
                    System.out.println("¿Qué va a hacer? ( 1 - Atacar | 2 - Curar )");// Aún no :  | 3 - Usar objeto | 4 - Huir
                    accion = Util.pedirPorTeclado(true);
                    switch (Integer.parseInt(accion)) {
                        case 1:
                            xp = personajesEnBatalla[personajeEnTurno].atacar(personajesEnBatalla[1 - personajeEnTurno]);
                            try {
                                personajesEnBatalla[personajeEnTurno].sumarExperiencia(xp);
                                personajesEnBatalla[1 - personajeEnTurno].sumarExperiencia(xp);
                            } catch (PersonajeException e) {
                                int xpRest = xp;
                                for (; xpRest >= 125000; xpRest -= 125000) {
                                    personajesEnBatalla[personajeEnTurno].sumarExperiencia(125000);
                                    personajesEnBatalla[1 - personajeEnTurno].sumarExperiencia(125000);
                                }
                                personajesEnBatalla[personajeEnTurno].sumarExperiencia(xpRest);
                                personajesEnBatalla[1 - personajeEnTurno].sumarExperiencia(xpRest);
                            }
                            if (xp == 0) {
                                System.out.println("El ataque falló!");
                            } else {
                                System.out.println( "El personaje \"" + personajesEnBatalla[1 - personajeEnTurno].getNombre() + "\" recibió " + xp + " de daño!");
                            }
                            accionNoValida = false;
                            break;
        
                        case 2:
                            personajesEnBatalla[personajeEnTurno].curar();
                            accionNoValida = false;
                            break;
                    
                        default:
                            System.out.println("Acción no válida.");
                            accionNoValida = true;
                            break;
                    }
                }
                turno = cambiar(turno);
            }
            System.out.println("\nEl ganador es " + (personajesEnBatalla[0].estaVivo() ? personajesEnBatalla[0].toString() : personajesEnBatalla[1].toString()));
            System.out.println("¿Otra batalla? (S/n)");
            if (Util.escogerOpcion("S", "n")) {
                personajesEnBatalla[0].curar();
                personajesEnBatalla[1].curar();
                personajesEnBatalla = new Personaje[2];
                personajesEnBatalla = seleccionarPersonajes(personajesCreados, NUM_COMBATIENTES);
                while (personajesEnBatalla[0] == null || personajesEnBatalla[1] == null) {
                    System.out.println("No hay suficientes personajes para la batalla, selecciona al menos 2 personajes.");
                    personajesEnBatalla = seleccionarPersonajes(personajesCreados, NUM_COMBATIENTES);
                }
            } else {
                bucleGuardadoPersonajes(personajesCreados);
                batalla = false;
            }
        }
    }
    
}
