package UD4.Rol.Main;

import java.util.Arrays;
import java.util.Random;

import UD4.Rol.Objetos.Item;
import UD4.Rol.Objetos.Personaje;
import UD4.Rol.Objetos.Raza;
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
        int id = 0;
        final int NUM_COMBATIENTES = 2; 
        Personaje[] personajesCreados;
        personajesCreados = getPersonajes();
        for (Personaje personaje : personajesCreados) {
            personaje.setId(id);
            personajesCreados[id] = personaje;
            id++;
        }
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
            boolean dosHobbit = personajesEnBatalla[0].getRaza().equals(Raza.HOBBIT) && personajesEnBatalla[1].getRaza().equals(Raza.HOBBIT);
            byte turnosEfecto0 = -1;
            byte turnosEfecto1 = -1;
            int[] buffTemporal0 = new int[] {0, 0, 0, 0};
            int[] buffTemporal1 = new int[] {0, 0, 0, 0};
            while (personajesEnBatalla[0].estaVivo() && personajesEnBatalla[1].estaVivo()) {
                int[] buffEnAccion = turno ? buffTemporal0 : buffTemporal1;
                int[] buffEnemigo = turno ? buffTemporal1 : buffTemporal0;
                byte turnosEfectoAccion = turno ? turnosEfecto0 : turnosEfecto1;
                byte personajeEnTurno = (byte) (turno ? 0 : 1);
                Personaje personajeActuando = personajesEnBatalla[personajeEnTurno];
                Personaje enemigo = personajesEnBatalla[1 - personajeEnTurno];
                String accion;
                boolean accionNoValida = true;
                int xp;
                System.out.println("\nTurno de " + personajeActuando.toString());
                while (accionNoValida) {
                    if (turnosEfectoAccion >= 0) {
                        buffEnAccion = Raza.buffHabilidad(personajeActuando);
                    }
                    personajeActuando.asignarBonus(buffEnAccion, false);
                    enemigo.asignarBonus(buffEnemigo, false);
                    System.out.println("¿Qué va a hacer? [ 1 - Atacar | 2 - Curar | 3 - "+ personajeActuando.stringHabilidadRaza() +" | 4 - Usar objeto ]");// Aún no :   | 5 - Huir
                    accion = Util.pedirPorTeclado(true);
                    switch (Integer.parseInt(accion)) {
                        case 1:
                            xp = personajeActuando.atacar(enemigo);
                            try {
                                personajeActuando.sumarExperiencia(xp);
                                enemigo.sumarExperiencia(xp);
                            } catch (PersonajeException e) {
                                int xpRest = xp;
                                for (; xpRest >= 125000; xpRest -= 125000) {
                                    personajeActuando.sumarExperiencia(125000);
                                    enemigo.sumarExperiencia(125000);
                                }
                                personajeActuando.sumarExperiencia(xpRest);
                                enemigo.sumarExperiencia(xpRest);
                            }
                            if (xp == 0) {
                                System.out.println("El ataque falló!");
                            } else {
                                System.out.println( "El personaje \"" + enemigo.getNombre() + "\" recibió " + xp + " de daño!");
                            }
                            accionNoValida = false;
                            break;
        
                        case 2:
                            personajeActuando.curar();
                            accionNoValida = false;
                            break;
                        
                        case 3:
                            if (personajeActuando.isHabilidadRazaActiva()) {
                                turnosEfectoAccion = personajeActuando.duracionHabilidadRaza(enemigo);
                                if (turnosEfectoAccion == -1) {
                                    if (dosHobbit) {//(dosHobbit || personaje.getRaza().equals(Razas.HOBBIT)) Posibilidad de añadir probabilidad de fallo en el robo de habilidad
                                        System.out.println("La habilidad de raza no surte efecto");
                                    } else {
                                        System.out.println("La habilidad no se puede utilizar durante este turno!");
                                        accionNoValida = true;
                                    }   
                                } else {
                                    if (turnosEfectoAccion == 0) {
                                        buffEnAccion = Raza.buffHabilidad(personajeActuando);
                                        personajeActuando.asignarBonus(buffEnAccion, false);
                                    }
                                }
                            } else {
                                System.out.println("La habilidad no se puede utilizar durante este turno!");
                                accionNoValida = true;
                            }
                            break;
                        
                        case 4:
                            Item objeto;
                            String bolsa = personajeActuando.mostrarBolsa();
                            System.out.println(bolsa);
                            System.out.println("Escoge Objeto (número de la izquierda): ");
                            accion = Util.pedirPorTeclado(true);
                            int ubNomObjeto = Integer.parseInt(accion);
                            String ubNom = ubNomObjeto + " - ";
                            ubNomObjeto = bolsa.indexOf(ubNom)+ ubNom.length();
                            accion = bolsa.substring(ubNomObjeto, ubNomObjeto + bolsa.substring(ubNomObjeto).indexOf(" ("));
                            objeto = new Item(accion); 
                            personajeActuando.usarObjeto(objeto);
                        default:
                            System.out.println("Acción no válida.");
                            accionNoValida = true;
                            break;
                    }
                }
                turnosEfectoAccion--;
                if (turno) {
                    turnosEfecto0 = turnosEfectoAccion;
                    buffTemporal0 = buffEnAccion;
                    buffTemporal1 = buffEnemigo;
                } else {
                    turnosEfecto1 = turnosEfectoAccion;
                    buffTemporal1 = buffEnAccion;
                    buffTemporal0 = buffEnemigo;
                }
                personajeActuando.asignarBonus(buffEnAccion, true);
                enemigo.asignarBonus(buffEnemigo, true);
                if (!personajeActuando.isHabilidadRazaActiva()) { personajeActuando.activarHabilidadRaza(); }
                personajesEnBatalla[personajeEnTurno] = personajeActuando;
                personajesEnBatalla[1 - personajeEnTurno] = enemigo;
                turno = cambiar(turno);
            }
            System.out.println("\nEl ganador es " + (personajesEnBatalla[0].estaVivo() ? personajesEnBatalla[0].toString() : personajesEnBatalla[1].toString()));
            System.out.println("¿Otra batalla? (S/n)");
            if (Util.escogerOpcion("S", "n")) {
                personajesEnBatalla[0].curar();
                personajesEnBatalla[1].curar();
                for (int i = 0; i < personajesEnBatalla.length; i++) {
                    for (int j = 0; j < personajesCreados.length; j++) {
                        if (personajesCreados[j].getId() == personajesEnBatalla[i].getId()) {
                            personajesCreados[j] = personajesEnBatalla[j];
                        }
                    }
                }
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
