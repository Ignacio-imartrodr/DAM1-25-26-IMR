package UD4.rol;

import java.util.Arrays;
import java.util.Random;

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

    private static Personaje[] seleccionarPersonajes(Personaje[] personajesCreados) {
        Personaje[] personajesEnBatalla = new Personaje[2];
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
                if (Util.escogerOpcion("s", "n")) {
                    personajesEnBatalla [j] = personajesCreados[i];
                    j++;
                    skip = i;
                }
            }
            if (j < personajesEnBatalla.length) {
                System.out.println("Siguiente personaje o anterior? (S/a): ");
                esSiguiente = Util.escogerOpcion("s", "a");
            }
        }
        return personajesEnBatalla;
    }
    private static void guardarPorPersonaje(Personaje[] personajesCreados){
        String rutaFichero;
        for (Personaje personaje : personajesCreados) {
            personaje.mostrar();
            System.out.print("¿Quieres guardar este personaje? (S/n): ");
            if (Util.escogerOpcion("s", "n")) {
                boolean repetir = true;
                while (repetir) {
                    System.out.print("Ruta del fichero (Json o Csv): ");
                    rutaFichero = Util.pedirPorTeclado(false);
                    if (rutaFichero.endsWith(".json")) {
                        if (Util.UbiObjetoEnArray(personaje, AppCreaPersonaje.personajesDeJson(rutaFichero)) == -1) {
                            Util.writeStringToJson(personaje.toJsonString(), rutaFichero, true);
                            repetir = false;
                        }
                    } else if (rutaFichero.endsWith(".csv")) {
                        Util.writeStringToCsv(personaje.toCsvString(), rutaFichero);
                        repetir = false;
                    } else {
                        System.out.println( "¿Quieres intentar de nuevo? (S/n): ");
                        if (!Util.escogerOpcion("s", "n")) {
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
        Personaje[] personajesExtraidos = new Personaje[0];
        try{
            personajesExtraidos = new Personaje[arrayPersonajes.length];
        } catch (Exception x) {}
        String[] atributos;
        int i = 0;
        int idPers = 1;
        if (esJson) {
            for (String linea : arrayPersonajes) {
                atributos = new String[7];
                atributos = linea.split(",");
                for (int j = 0; j < atributos.length; j++) {
                    atributos[j] = atributos[j].substring(atributos[j].indexOf(":") + 1).replaceAll("\"", "");
                }
                try {
                    Personaje newPersonaje = new Personaje(atributos[0], atributos[1], atributos[2], atributos[3], atributos[4], atributos[5], atributos[6], true);
                    personajesExtraidos[i]= newPersonaje;
                    i++;
                } catch (Exception e) {
                    System.out.println("El personaje número " + idPers + " contiene un error.");
                }
                idPers++;
            }
        } else {
            for (String linea : arrayPersonajes) {
                atributos = new String[7];
                atributos = linea.split(",");
                try {
                    Personaje newPersonaje = new Personaje(atributos[0], atributos[1], atributos[2], atributos[3], atributos[4], atributos[5], atributos[6], true);
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
        Personaje[] personajesFichero = new Personaje[0];
        boolean restart = true;
        while (restart) {
            restart = false;
            System.out.println("Opciones: \nJson o Csv");
            System.out.print("¿Quieres cargar personajes desde " + rutaFile + "? (S/n): ");
            if (Util.escogerOpcion("s", "n")) {
                if (rutaFile.endsWith(".csv")) {
                    personajesFichero = extraerPersonaje(Util.readFileToStringArray(rutaFile), false);
                } else if (rutaFile.endsWith(".json") || rutaFile.startsWith("www.http") || rutaFile.startsWith("http")) {
                    if (rutaFile.startsWith("www.http") || rutaFile.startsWith("http")) {
                        boolean errorUrl = true;
                        String temp = rutaFile;
                        while (errorUrl) {
                            errorUrl = false;
                            try {
                                personajesGuardados = Util.getJson(temp).split("},");
                                personajesFichero = extraerPersonaje(personajesGuardados, true);
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
                        personajesFichero = AppCreaPersonaje.personajesDeJson(rutaFile);
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
                if (Util.escogerOpcion("s", "n")) {
                    System.out.print("Ruta? (\"n\" para salir): ");
                    rutaFile = Util.pedirPorTeclado(false);
                    if (rutaFile.equalsIgnoreCase("n")) {
                        System.out.println("personajes no cargados");
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
            if (Util.escogerOpcion("s", "n")) {
                String rutaFichero;
                System.out.println("¿Quieres guardar todos los personajes creados y cargados en un único Archivo? (S/n): ");
                if (Util.escogerOpcion("s", "n")) {
                    rutaFichero = AppCreaPersonaje.pedirRutaGuardado();
                    if (!rutaFichero.equals("-1")) {
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
                        }
                    } else {
                        System.out.println("Personajes no guardados.");
                        repetir = true;
                    }
                } else{
                    System.out.println("¿Quieres elegir individualmente si guardar y donde cada personaje creado y cargado? (S/n): ");
                    if (Util.escogerOpcion("s", "n")) {
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
            if (Util.escogerOpcion("s", "n")) {
                String rutaFichero;
                do {
                    do {
                        System.out.print("Ruta del fichero (Ej| src\\UD4\\rol\\archivo.extensión): ");
                        rutaFichero = Util.pedirPorTeclado(false);
                    } while (rutaFichero.equals("-1"));
    
                    temp = cargarPersonajesDeArchivo(rutaFichero);
                } while (temp == null);
                for (Personaje personaje : temp) {
                    personajesCreados = Arrays.copyOf(personajesCreados, personajesCreados.length + 1);
                    personajesCreados[personajesCreados.length - 1] = personaje;
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
        Personaje[] personajesCreados;
        personajesCreados = getPersonajes();

        System.out.println("\nPersonajes disponibles:\n");
        for (Personaje personaje : personajesCreados) {
            personaje.mostrar();
            System.out.println("________________________");
            System.out.println();
        }
        bucleGuardadoPersonajes(personajesCreados);

        Personaje[] personajesEnBatalla = new Personaje[2];
        personajesEnBatalla = seleccionarPersonajes(personajesCreados);
        while (personajesEnBatalla[0] == null || personajesEnBatalla[1] == null) {
            System.out.println("No hay suficientes personajes para la batalla, selecciona al menos 2 personajes.");
            personajesEnBatalla = seleccionarPersonajes(personajesCreados);
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
            if (Util.escogerOpcion("s", "n")) {
                personajesEnBatalla[0].curar();
                personajesEnBatalla[1].curar();
                personajesEnBatalla = new Personaje[2];
                personajesEnBatalla = seleccionarPersonajes(personajesCreados);
                while (personajesEnBatalla[0] == null || personajesEnBatalla[1] == null) {
                    System.out.println("No hay suficientes personajes para la batalla, selecciona al menos 2 personajes.");
                    personajesEnBatalla = seleccionarPersonajes(personajesCreados);
                }
            } else {
                bucleGuardadoPersonajes(personajesCreados);
                batalla = false;
            }
        }
    }
}
