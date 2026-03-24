package UD4.Rol.Boundary;

import java.util.Arrays;

import UD4.Rol.Control.Combate;
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
                temp = Combate.cargarPersonajesDeArchivo(rutaFichero);
                for (Personaje personaje : temp) {
                    personaje.setId(id); 
                    personajesNuevos = Arrays.copyOf(personajesNuevos, personajesNuevos.length + 1);
                    personajesNuevos[personajesNuevos.length - 1] = personaje;
                    id++;
                }
            }
        }
        temp = Creacion.pedirPersonajes();
        for (Personaje personaje : temp) {
            personaje.setId(id); 
            personajesNuevos = Arrays.copyOf(personajesNuevos, personajesNuevos.length + 1);
            personajesNuevos[personajesNuevos.length - 1] = personaje;
            id++;
        }
        modificarPersonagesArray(personajesNuevos);
    }
}
