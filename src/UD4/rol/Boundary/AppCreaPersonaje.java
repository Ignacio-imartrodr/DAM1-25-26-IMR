package UD4.Rol.Boundary;

import java.util.Arrays;
import java.util.Random;

import UD4.Rol.Control.Creacion;
import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Entity.Others.Raza;
import UD4.Rol.Utilidades.*;

/** 
 * @author Ignacio MR
 * 
 * Crea un programa de consola que permita al usuario generar 
 * y editar personajes de diferentes modos y guardarlos
 * en disco en un fichero de texto en formato JSON. 
 */

public class AppCreaPersonaje {
    private static Personaje crearPersonaje(){
        Personaje personaje;
        System.out.print("Nombre del personaje: ");
        String nombre = Util.pedirPorTeclado(false);
        while (nombre == null) {
            System.out.print("El persnaje necesita un nombre: ");
            nombre = Util.pedirPorTeclado(false);          
        }
        nombre = nombre.strip();
        System.out.println("Escoge una de las siguientes razas: orco, elfo, HUMANO, enano, hobbit o troll");
        String raza = "a";
        for (boolean error = true; error;) {
            try {
                Raza razaVal = Raza.stringToRaza(Util.pedirPorTeclado(false));
                raza = razaVal.toString();
                error = false;
            } catch (PersonajeException e) {
                System.out.println("Raza no válida. Introduce uno de las siguientes: orco, elfo, HUMANO, enano, hobbit o troll");
                error = true;
            }
        }

        System.out.println("Introduce las siguientes estadísticas. Si quieres que se generen aleatoriamente, pulsa \"Enter\" sin introducir ningún valor.");
        System.out.print("Fuerza: ");
        String fuerza = pedirStatRng();
        
        System.out.print("Agilidad: ");
        String agilidad = pedirStatRng();

        System.out.print("Constitución: ");
        String constitucion = pedirStatRng();
        
        System.out.print("Nivel: ");
        String nivel = pedirStatNoRng(false);
        
        System.out.println("Nivel de experiencia: ");
        String experiencia = pedirStatNoRng(true);

        personaje = new Personaje(nombre, raza, fuerza, agilidad, constitucion, nivel, experiencia, null, null, null, false);
        return personaje;
    }
    private static String pedirStatRng(){
        Random rnd = new Random();
        int num = rnd.nextInt(100) + 1;
        String texto = Util.pedirPorTeclado(true);
        final int MIN = 1;
        final int MAX = 100;
        while ( !(texto == null) && (Integer.parseInt(texto) < MIN || Integer.parseInt(texto) > MAX)) {
            System.out.print("La estadística debe ser como mínimo " + MIN + " y como máximo " + MAX + ", da otro valor: ");
            texto = Util.pedirPorTeclado(true);
        }
        if (texto == null) {
            texto = String.valueOf(num);
        }
        return texto;
    }
    private static String pedirStatNoRng(boolean esXp){
        String texto;
        texto = Util.pedirPorTeclado(true);
        final int MIN = esXp ? 0 : 1;
        final int MAX = esXp ? 999 : 100;
        while ( !(texto == null) && (Integer.parseInt(texto) < MIN || Integer.parseInt(texto) > MAX)) {
            System.out.print("La estadística debe ser como mínimo " + MIN + " y como máximo " + MAX + ", da otro valor: ");
            texto = Util.pedirPorTeclado(true);
        }
        if (texto == null) {
            texto = String.valueOf(MIN);
        }
        return texto;
    }
    public static Personaje[] pedirPersonajes() {
        Personaje[] personajesNuevos = new Personaje[0];
        boolean seguir = true;
        while (seguir) {
            System.out.print("¿Quieres crear un nuevo personaje? (S/n): ");
            if (Util.escogerOpcion("s", "n")) {
                Personaje personaje = new Personaje();
                System.out.println("\nRazas disponibles:\n\n" + Raza.getRazasStats());
                personaje = crearPersonaje();
                System.out.println(personaje.getFicha());
                System.out.println("¿Es el personaje correcto? (S/n):");
                if (Util.escogerOpcion("s", "n")) {
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
    public static void modificarPersonagesArray(Personaje[] personajesArray){
        Creacion.getStringPersonajes(personajesArray);
        boolean esSiguiente = true;
        boolean salir = false;
        for (int i = -1, skip = -1; !salir;) {
            System.out.println("¿Quieres modificar algún personaje? (S/n)");
            if (Util.escogerOpcion("S", "n")) {
                if (esSiguiente) {
                    if (i == personajesArray.length -1) {
                        i = 0;
                    } else {
                        i++;
                    }
                } else {
                    if (i == 0) {
                        i = personajesArray.length -1;
                    } else {
                        i--;
                    }
                }
                if (i != skip){
                    boolean modificar;
                    do {
                        System.out.println(personajesArray[i].getFicha());
                        System.out.print("¿Quieres modificar este personaje? (S/n): ");
                        if (Util.escogerOpcion("S", "n")) {
                            modificar  = true;
                            System.out.print("¿Que valor quieres modificar?");
                            System.out.println("Nombre, raza, fuerza, agilidad, constitucion, nivel o experiencia");
                            String valor = Util.pedirPorTeclado(false);
                            if (valor != null) {
                                try {
                                    personajesArray[i] = Creacion.modPersonaje(valor, personajesArray[i]);
                                } catch (Exception e) {
                                    System.out.println("Valor no válido.");
                                }
                            } else {
                                System.out.println("Introduce un valor válido.");
                            }
                        } else {
                            modificar = false;
                        }
                    } while (modificar);
                    skip = i;
                    System.out.println("Siguiente personaje o Anterior? (S/a): ");
                    esSiguiente = Util.escogerOpcion("S", "a");
                }
            } else {
                salir = true;
            } 
        }
    }
    public static void main(String[] args) {
        int id = 0;
        Personaje[] personajesNuevos = new Personaje[0];
        Personaje[] temp;
        System.out.print("¿Quieres cargar los personajes de un archivo? (S/n): ");
        if (Util.escogerOpcion("S", "n")) {
            String rutaFichero;
            rutaFichero = Util.pedirRuta();
            if (!(rutaFichero == null)) {
                temp = AppCombateSingular.cargarPersonajesDeArchivo(rutaFichero);
                for (Personaje personaje : temp) {
                    personaje.setId(id); 
                    personajesNuevos = Arrays.copyOf(personajesNuevos, personajesNuevos.length + 1);
                    personajesNuevos[personajesNuevos.length - 1] = personaje;
                    id++;
                }
            }
        }
        temp = pedirPersonajes();
        for (Personaje personaje : temp) {
            personaje.setId(id); 
            personajesNuevos = Arrays.copyOf(personajesNuevos, personajesNuevos.length + 1);
            personajesNuevos[personajesNuevos.length - 1] = personaje;
            id++;
        }
        modificarPersonagesArray(personajesNuevos);
    }
}
