package UD4.Rol.Main;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import UD4.Rol.Utilidades.*;
import UD4.Rol.Objetos.Personaje;

/** 
 * @author Ignacio MR
 * 
 * Crea un programa de consola que permita al usuario generar 
 * y editar personajes de diferentes modos y guardarlos
 * en disco en un fichero de texto en formato JSON. 
 */

public class AppCreaPersonaje {
    public static void mostrarPersonajes(Personaje[] personajesCreados) {
        System.out.println("\nPersonajes disponibles:\n");
        for (Personaje personaje : personajesCreados) {
            personaje.mostrar();
            System.out.println("________________________");
            System.out.println();
        }
    }
    public static Personaje[] getPersonajesJson(String ruta){
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
    public static Personaje[] getPersonajesJsonUrl(String ruta){
        Personaje[] personajesJson = new Personaje[0];
        String web = "";
        JSONArray personajes = new JSONArray();
        try {
            web = Util.getJson(ruta);
        } catch (Exception e) {
            throw new PersonajeException("Error en la URL");
        }
        try {
            JSONObject ArchivoUrl = new JSONObject(web);
            personajes = Util.JsonArray(ArchivoUrl.getString("Personajes"));
        } catch (Exception e) {
            try {
                personajes = Util.JsonArray(web);
            } catch (Exception b) {
                throw new PersonajeException("Error en el formato de la web");
            }
        }
        
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
    public static Personaje[] getPersonajesCsv(String ruta){
        String[] arrayPersonajes = Util.readFileToStringArray(ruta);
        Personaje[] personajesExtraidos = new Personaje[0];
        try{
            personajesExtraidos = new Personaje[arrayPersonajes.length];
            String[] atributos;
            int i = 0;
            int idPers = 1;
            for (String personaje : arrayPersonajes) {
                atributos = new String[7];
                atributos = personaje.split(",");
                try {
                    Personaje newPersonaje = new Personaje(atributos[0], atributos[1], atributos[2], atributos[3], atributos[4], atributos[5], atributos[6], true);
                    personajesExtraidos[i]= newPersonaje;
                    i++;
                } catch (Exception e) {
                    System.out.println("El personaje número " + idPers + "contiene un error.");
                }
                idPers++;
            }
        } catch (Exception x) {}
        return personajesExtraidos;
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
    public static void modificarPersonagesArray(Personaje[] personajesArray){
        mostrarPersonajes(personajesArray);
        
    }
    public static void main(String[] args) {
        Personaje[] personajesNuevos = pedirPersonajes();
        for (Personaje personaje : personajesNuevos) {
            String ruta = AppCombateSingular.pedirRutaGuardado();
            if (ruta == null) {
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
