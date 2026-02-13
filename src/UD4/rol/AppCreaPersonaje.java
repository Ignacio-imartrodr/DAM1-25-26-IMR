package UD4.rol;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

/** 
 * @author Ignacio MR
 * 
 * Crea un programa de consola que permita al usuario generar 
 * y editar personajes de diferentes modos y guardarlos
 * en disco en un fichero de texto en formato JSON. 
 */

public class AppCreaPersonaje {
    public static Personaje[] personajesDeJson(String ruta){
        JSONArray personajes = Util.JsonArray(ruta);
        Personaje[] personajesJson = new Personaje[0];
        for (int i = 0; i < personajes.length(); i++) {
            if (personajes.getJSONObject(i) != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject = personajes.getJSONObject(i);
                Personaje personaje = new Personaje(jsonObject.getString("nombre"), jsonObject.getString("raza"), jsonObject.getString("fuerza"), jsonObject.getString("agilidad"), jsonObject.getString("constitucion"), jsonObject.getString("nivel"), jsonObject.getString("experiencia"), true);
                personajesJson = Arrays.copyOf(personajesJson, personajesJson.length + 1);
                personajesJson[personajesJson.length - 1] = personaje;
            }
        }
        return personajesJson;
    }
    public static String pedirRutaGuardado(){
        boolean restart = true;
        String rutaFichero = "-1";
        while (restart) {
            restart = false;
            System.out.print("¿Quieres guardar en un fichero? (S/n): ");
            if (Util.escogerOpcion("s", "n")) {
                System.out.println("OPCIONES: \nJson o Csv");
                System.out.print("¿Tipo de fichero? (J/c): ");
                if (Util.escogerOpcion("j", "c")) {
                    System.out.print("Ruta del fichero: ");
                    rutaFichero = Util.pedirPorTeclado(false);
                    if (!rutaFichero.endsWith(".json")) {
                        System.out.println("La ruta debe contener un fichero con extensión .json");
                        restart = true;
                    } else {
                        restart = true;
                    }
                    
                } else {            
                    System.out.print("Ruta del fichero: ");
                    rutaFichero = Util.pedirPorTeclado(false);
                    if (!rutaFichero.endsWith(".csv")) {
                        System.out.println("La ruta debe contener un fichero con extensión .csv");
                        restart = true;
                    } else {
                        restart = true;
                    }
                }
            } else {
                System.out.println("Personaje no guardado.");
            }
        }
        return rutaFichero;
    }
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
    public static void main(String[] args) {
        Personaje[] personajesNuevos = pedirPersonajes();
        for (Personaje personaje : personajesNuevos) {
            String ruta = pedirRutaGuardado();
            if (ruta.equals("-1")) {
                System.out.println("No se guardó el personaje.");
            } else {
                if (ruta.endsWith(".json")) {
                    Util.writeStringToJson(personaje.toJsonString(), ruta, false);
                } else if (ruta.endsWith(".csv")) {
                    Util.writeStringToCsv(personaje.toCsvString(), ruta);
                }
            }
        }
    }
}
