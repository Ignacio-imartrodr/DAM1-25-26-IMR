package UD4.Rol.Control;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Utilidades.PersonajeException;
import UD4.Rol.Utilidades.Util;

public abstract class Creacion {
    public static String getStringPersonajes(Personaje[] personajesCreados) {
        String texto;
        if (personajesCreados != null && personajesCreados.length > 0) {
            texto = "\nPersonajes disponibles:\n";
            for (Personaje personaje : personajesCreados) {
                texto += personaje.getFicha() + "\n________________________\n";
            }
        } else {
            texto = "\nNo hay Personajes disponibles\n";
        }
        return texto;
    }
    public static Personaje[] getPersonajesJson(String ruta){
        JSONArray personajes = new JSONArray(ruta);
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
        JSONArray personajes;
        try {
            personajes = Util.urlToJsonArray(ruta, "Personajes");
        } catch (Exception e) {
            throw new PersonajeException("Error en el formato de la web");
        }
        for (int i = 0; i < personajes.length(); i++) {
            JSONObject persVacio = new JSONObject("");
            JSONObject pers = personajes.getJSONObject(i);
            if (pers != null && !pers.equals(persVacio)) {
                JSONObject jsonObject = personajes.getJSONObject(i);
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
                    throw new PersonajeException("El personaje número " + idPers + "contiene un error.");
                }
                idPers++;
            }
        } catch (Exception x) {}
        return personajesExtraidos;
    }
    
}
