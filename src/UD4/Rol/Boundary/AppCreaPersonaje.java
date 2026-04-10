package UD4.Rol.Boundary;

import java.util.Arrays;
import UD4.Rol.Control.Creacion;
import UD4.Rol.Control.Guardado;
import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Entity.Equipamiento.Equipamiento;
import UD4.Rol.Utilidades.*;

/** 
 * @author Ignacio MR
 * 
 * Crea un programa de consola que permita al usuario generar 
 * y editar personajes de diferentes modos y guardarlos
 * en disco en un fichero de texto en formato JSON. 
 */

public class AppCreaPersonaje {
    
    private static void modificarPersonagesArray(Personaje[] personajesArray){
        for (boolean salir = false; !salir;) {
            Arrays.sort(personajesArray);
            int posPers = -1;
            Creacion.getStringPersonajes(personajesArray);
            System.out.println();
            if (Util.escogerOpcion("S", "n", "¿Quieres modificar algúno de estos personajes? (S/n)")) {
                Personaje persSelec = Creacion.seleccionarPersonajes(personajesArray, 1)[0];
                posPers = Arrays.binarySearch(personajesArray, persSelec);
                boolean modificar;
                do {
                    System.out.println(persSelec.getFicha());
                    if (Util.escogerOpcion("S", "n", "¿Quieres modificar este personaje? (S/n)")) {
                        modificar = true;
                        System.out.print("¿Que valor quieres modificar?");
                        System.out.println("Nombre, raza, fuerza, agilidad, constitucion, nivel, experiencia o equipamiento");
                        String valor = Util.pedirPorTeclado(false);
                        if (valor != null) {
                            try {
                                persSelec = Creacion.modPersonaje(valor, persSelec);
                                if (persSelec == null) {
                                    System.out.println("No se puede modificar el equipamiento equipado de este personaje ya que no tiene equipamiento guardado");
                                } else {
                                    personajesArray[posPers] = persSelec;
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
            } else {
                salir = true;
            }
        }
    }
    public static void main(Personaje[] personajesBaseGeneral) {
        Personaje[] personajesNuevos = Arrays.copyOf(personajesBaseGeneral, personajesBaseGeneral.length);
        Personaje[] temp;
        for (boolean fin = false; !fin;) {
            System.out.println("-----------App para Creación y Manejo de Personajes-----------");
            System.out.println("Opciones:\n1 - Cargar Personajes de un archivo\n2 - Crear personajes nuevos\n3 - Modificar personajes de la Base General\n4 - Gacha de Equipamiento\n5 - Salir");
            int num = Integer.valueOf(Util.pedirPorTeclado(true));
            switch (num) {
                case 1 :
                    String rutaFichero;
                    for (boolean repetir = true; repetir;) {
                        repetir = false;
                        rutaFichero = Util.pedirRutaAJson();
                        if (rutaFichero != null) {
                            if (!rutaFichero.equals(Guardado.RUTA_BASE_GENERAL)) {
                                Object[] keysToPers = new Object[0];
                                if (Util.escogerOpcion("N", "s", "¿Quieres especificar las claves hasta el array de personajes? (s/N)")) {
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
                                System.out.println("Los personajes de la Base General ya están añadidos");
                            }
                        } else {
                            System.err.println("Error con la ruta");
                        }
                        if (Util.escogerOpcion("S", "n", "¿Quieres dar otra ruta? (S/n)")) {
                            repetir = true;
                        }
                    }
                    Guardado.guardadoPersonajes(personajesNuevos);
                    break;
                case 2 :
                    temp = Creacion.pedirPersonajes();
                    for (Personaje personaje : temp) {
                        personajesNuevos = Arrays.copyOf(personajesNuevos, personajesNuevos.length + 1);
                        personajesNuevos[personajesNuevos.length - 1] = personaje;
                    }
                    Guardado.guardadoPersonajes(personajesNuevos);
                    break;
                case 3 :
                    modificarPersonagesArray(personajesNuevos);
                    Guardado.guardadoPersonajes(personajesNuevos);
                    break;
                case 4 :
                    System.out.println("---------Gacha de Equipamiento---------");
                    System.out.println("Info:\n- Hay un gacha de Armas, otro de Armaduras y uno de ambos. Escogerás en cual probar suerte despues.\n- Puedes seleccionar un personaje que obtendrá el equipamiento para que tenga un buff de probabilidad en mayor rareza según su nivel o hacer un lanzamiento genérico y escoger que personaje lo obtendrá.");
                    boolean esGeneral = false;
                    boolean esArmas = false;
                    if (Util.escogerOpcion("G", "e", "Que gacha quieres usar? General o específico (G/e)")) {
                        esGeneral = true;
                    } else {
                        if (Util.escogerOpcion("1", "2", "Gacha de Armas (1) o de Armaduras (2)?")) {
                            esArmas = true;
                        } else {
                            esArmas = false;
                        }
                    }
                    Equipamiento equipObt;
                    Personaje persSelec;
                    if (Util.escogerOpcion("S", "n", "Quieres seleccionar un personaje? (S/n)")) {
                        System.out.println(Creacion.getStringPersonajes(personajesNuevos));
                        persSelec = Creacion.seleccionarPersonajes(personajesNuevos, 1)[0];
                        equipObt = persSelec.gachaEquipamiento(esGeneral, esArmas);
                        System.out.printf("Obtuviste %s de rareza %s!", equipObt.getNombre(), equipObt.getRareza().toString());
                    } else {
                        equipObt = Equipamiento.gachaEquipamiento(esGeneral, esArmas);
                        System.out.printf("Obtuviste \"%s\" de rareza %s!", equipObt.getNombre(), equipObt.getRareza().toString());
                        System.out.println(Creacion.getStringPersonajes(personajesNuevos));
                        System.out.println("Cual lo guardará?");
                        persSelec = Creacion.seleccionarPersonajes(personajesNuevos, 1)[0];
                        persSelec.guardarEquipamiento(equipObt);                        
                    }
                    Guardado.guardadoPersonajes(personajesNuevos);
                    break;
                case 5 :
                    fin = true;
                    break;
                default:
                    System.out.println("Escoge uno de los números de las opciones");
                    fin = false;
                    break;
            }
        }
    }
}
