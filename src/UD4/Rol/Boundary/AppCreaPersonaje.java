package UD4.Rol.Boundary;

import java.util.Arrays;

import UD4.Rol.Control.Combate;
import UD4.Rol.Control.Creacion;
import UD4.Rol.Control.Guardado;
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
        Personaje[] personajesNuevos = new Personaje[0];
        Personaje[] temp;
        System.out.println("-----------App para Creación y Manejo de Personajes-----------");
        System.out.println("Opciones:\n1 - Cargar Personajes de un archivo\n2 - Crear personajes nuevos\n3 - Modificar personajes de la Base General\n4 - Salir");
        int num = Integer.valueOf(Util.pedirPorTeclado(true));
        for (boolean fin = false; !fin;) {
            switch (num) {
                case 1 :
                    String rutaFichero;
                    rutaFichero = Util.pedirRuta();
                    for (boolean repetir = true; repetir;) {
                        repetir = false;
                        if (!(rutaFichero == null)) {
                            Object[] keysToPers = new Object[0];
                            System.out.print("¿Quieres especificar las claves hasta el array de personajes? (s/N): ");
                            if (Util.escogerOpcion("N", "s")) {
                                temp = Creacion.getPersonajesFromJson(rutaFichero);
                                if (temp != null) {
                                    for (Personaje personaje : temp) {
                                        personajesNuevos = Arrays.copyOf(personajesNuevos, personajesNuevos.length + 1);
                                        personajesNuevos[personajesNuevos.length - 1] = personaje;
                                    }
                                } else {
                                    System.err.println("Error con el archivo");
                                }
                            } else{
                                String key;
                                do {
                                    System.out.print("Clave (Enter vacía para finalizar): ");
                                    key = Util.pedirPorTeclado(false);
                                } while (key != null);
                                temp = Creacion.getPersonajesFromJson(rutaFichero, keysToPers);
                                if (temp != null) {
                                    for (Personaje personaje : temp) {
                                        personajesNuevos = Arrays.copyOf(personajesNuevos, personajesNuevos.length + 1);
                                        personajesNuevos[personajesNuevos.length - 1] = personaje;
                                    }
                                } else {
                                    System.err.println("Error con el archivo");
                                }
                            }
                        } else {
                            System.err.println("Error con la ruta");
                        }
                        System.out.print("¿Quieres dar otra ruta? (S/n): ");
                        if (Util.escogerOpcion("S", "n")) {
                            repetir = true;
                        }
                    }
                    Guardado.guardadoPersonajes(personajesNuevos);
                    break;
                case 2 ://TODO terminar
                    break;
                case 3 ://TODO terminar
                    break;
                case 4 :
                    fin = true;
                    break;
                default:
                    fin = false;
                    break;
            }
        }
        
        temp = Creacion.pedirPersonajes();
        for (Personaje personaje : temp) {
            personajesNuevos = Arrays.copyOf(personajesNuevos, personajesNuevos.length + 1);
            personajesNuevos[personajesNuevos.length - 1] = personaje;
        }
        Guardado.guardadoPersonajes(personajesNuevos);
        System.out.println("Quieres ");
        modificarPersonagesArray(personajesNuevos);
    }
}
