package UD4.rol;

import java.util.Arrays;

/**
 * @author Ignacio MR
 * 
 *         Crea un programa de consola que permita al usuario generar
 *         y editar personajes de diferentes modos y guardarlos
 *         en disco en un fichero de texto en formato JSON o CSV.
 */

public class AppCombateSingular {
    private static void cambiar(boolean turno) {
        turno = turno ? false : true;
    }

    private static Personaje[] seleccionarPersonajes(Personaje[] PersonajesCreados) {
        Personaje[] personajesEnBatalla = new Personaje[2];
        // TODO seleccionar del array dado
        return personajesEnBatalla;
    }

    private static void guardarPorPersonaje(Personaje[] personajesCreados){
        String rutaFichero;
        for (Personaje personaje : personajesCreados) {
            personaje.mostrar();
            System.out.print("¿Quieres guardar este personaje? (s/n): ");
            if (Util.confirmarSN()) {
                boolean repetir = true;
                while (repetir) {
                    System.out.println("src\\UD4\\rol\\PersonajesGuardados.csv");//TODO borrar
                    System.out.print("Ruta del fichero (Json o Csv): ");
                    rutaFichero = Util.pedirPorTeclado(false);
                    personaje.toFile(rutaFichero);
                        if (rutaFichero.endsWith(".json") || rutaFichero.endsWith(".csv")) {
                        repetir = false;
                    } else {
                        System.out.println( "¿Quieres intentar de nuevo? (s/n): ");
                        if (!Util.confirmarSN()) {
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
                                personajesFichero = extraerPersonaje(Util.readFileToString(rutaFile).split("}"), true);
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
    public static void bucleGuardadoPersonajes(Personaje[] personajesCreados){
        boolean repetir = true;
        while (repetir) {
            repetir = false;
            System.out.println("¿Quieres guardar los personajes? (s/n): ");
            if (Util.confirmarSN()) {
                String rutaFichero;
                System.out.println("¿Quieres guardar todos los personajes creados y cargados en un único Archivo? (s/n): ");
                if (Util.confirmarSN()) {
                    rutaFichero = AppCreaPersonaje.pedirRuta();
                    if (!rutaFichero.equals("-1")) {
                        String[] borrado = new String[personajesCreados.length];
                        int i = 0;
                        for (String string : borrado) {
                            string = "\n";
                            borrado[i] = string;
                            i++;
                        }
                        Util.writeStringToFile(borrado.toString(), rutaFichero, false); 
                        for (Personaje personaje : personajesCreados) {
                            personaje.toFile(rutaFichero);
                        }
                    } else {
                        System.out.println("Personajes no guardados.");
                        repetir = true;
                    }
                } else{
                    System.out.println("¿Quieres elegir individualmente si guardar y donde cada personaje creado y cargado? (s/n): ");
                    if (Util.confirmarSN()) {
                        guardarPorPersonaje(personajesCreados);
                    } else {
                        System.out.println("Personajes no guardados.");
                        repetir = true;
                        
                    }
                }
            }
        }
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
        getPersonajes(personajesCreados);

        System.out.println("\nPersonajes disponibles:\n");
        for (Personaje personaje : personajesCreados) {
            personaje.mostrar();
        }
        Personaje[] personajesEnBatalla;
        personajesEnBatalla = seleccionarPersonajes(personajesCreados);

        boolean turno = true;
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
                        personajesEnBatalla[personajeEnTurno].sumarExperiencia(xp);
                        accionNoValida = false;
                        break;
    
                    case 2:
                        personajesEnBatalla[personajeEnTurno].curar();
                        accionNoValida = false;
                        break;
                
                    default:
                        System.out.println("Acción no válida.");
                        break;
                }
            }
            cambiar(turno);
        }
        System.out.println("\nEl ganador es " + (personajesEnBatalla[0].estaVivo() ? personajesEnBatalla[0].toString() : personajesEnBatalla[1].toString()));
        System.out.println("¿Otra batalla? (s/n)");
        if (Util.confirmarSN()) {
            
        } else {
            bucleGuardadoPersonajes(personajesCreados);
        }
        
    }
}
