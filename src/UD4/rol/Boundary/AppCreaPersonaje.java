package UD4.Rol.Boundary;

import java.util.Arrays;
import UD4.Rol.Control.Creacion;
import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Utilidades.*;

/** 
 * @author Ignacio MR
 * 
 * Crea un programa de consola que permita al usuario generar 
 * y editar personajes de diferentes modos y guardarlos
 * en disco en un fichero de texto en formato JSON. 
 */

public class AppCreaPersonaje {
    public static Personaje[] pedirPersonajes() {
        Personaje[] personajesNuevos = new Personaje[0];
        boolean seguir = true;
        while (seguir) {
            System.out.print("¿Quieres crear un nuevo personaje? (S/n): ");
            if (Util.escogerOpcion("s", "n")) {
                Personaje personaje = new Personaje();
                System.out.println("\nRazas disponibles:\n\n" + Personaje.getRazasStats());
                personaje.crearPersonaje();
                personaje.mostrar();
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
                        personajesArray[i].mostrar();
                        System.out.print("¿Quieres modificar este personaje? (S/n): ");
                        if (Util.escogerOpcion("S", "n")) {
                            modificar  = true;
                            String nombre = personajesArray[i].getNombre();
                            String raza = String.valueOf(personajesArray[i].getRaza());
                            String fuerza = String.valueOf(personajesArray[i].getFuerza());
                            String agilidad = String.valueOf(personajesArray[i].getAgilidad());
                            String constitucion = String.valueOf(personajesArray[i].getConstitucion());
                            String nivel = String.valueOf(personajesArray[i].getNivel());
                            String experiencia = String.valueOf(personajesArray[i].getExperiencia());
                            System.out.print("¿Que valor quieres modificar?");
                            System.out.println("Nombre, raza, fuerza, agilidad, constitucion, nivel o experiencia");
                            String valor = Util.pedirPorTeclado(false);
                            if (!(valor == null)) {
                                try {
                                    valor = valor.toUpperCase();
                                    switch (valor) {
                                        case "NOMBRE":
                                            valor = Util.pedirPorTeclado(false);
                                            personajesArray[i] = new Personaje(valor, raza, fuerza, agilidad, constitucion, nivel, experiencia, true);
                                            break;
                                        case "RAZA":
                                            valor = Util.pedirPorTeclado(false);
                                            personajesArray[i] = new Personaje(nombre, valor, fuerza, agilidad, constitucion, nivel, experiencia, true);
                                            break;
                                        case "FUERZA":
                                            valor = Util.pedirPorTeclado(true);
                                            personajesArray[i] = new Personaje(nombre, raza, valor, agilidad, constitucion, nivel, experiencia, true);
                                            break;
                                        case "AGILIDAD":
                                            valor = Util.pedirPorTeclado(true);
                                            personajesArray[i] = new Personaje(nombre, raza, fuerza, valor, constitucion, nivel, experiencia, true);
                                            break;
                                        case "CONSTITUCION":
                                            valor = Util.pedirPorTeclado(true);
                                            personajesArray[i] = new Personaje(nombre, raza, fuerza, agilidad, valor, nivel, experiencia, true);
                                            break;
                                        case "NIVEL":
                                            valor = Util.pedirPorTeclado(true);
                                            personajesArray[i] = new Personaje(nombre, raza, fuerza, agilidad, constitucion, valor, experiencia, true);
                                            break;
                                        case "EXPERIENCIA":
                                            valor = Util.pedirPorTeclado(true);
                                            personajesArray[i] = new Personaje(nombre, raza, fuerza, agilidad, constitucion, nivel, valor, true);
                                            break;
                                        default:
                                            throw new PersonajeException();
                                    }
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
            rutaFichero = AppCombateSingular.pedirRuta();
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
