package UD4.Rol.Control;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import UD4.Rol.Entity.Item;
import UD4.Rol.Entity.Items;
import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Entity.Equipamiento.Equipamiento;
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
                

                Equipamiento[] equipamientoEquipado = new Equipamiento[jsonObject.getJSONObject("Equipamiento").getJSONArray("Equipado").length()];
                for (int j = 0; j < equipamientoEquipado.length; j++) {
                    JSONObject equip = jsonObject.getJSONObject("Equipamiento").getJSONArray("Equipado").getJSONObject(j);
                    equipamientoEquipado[j] = Equipamiento.newEquipamiento(equip);
                }

                Equipamiento[] equipamientoGuardado = new Equipamiento[jsonObject.getJSONObject("Equipamiento").getJSONArray("Guardado").length()];
                for (int j = 0; j < equipamientoGuardado.length; j++) {
                    JSONObject equip = jsonObject.getJSONObject("Equipamiento").getJSONArray("Guardado").getJSONObject(j);
                    equipamientoGuardado[j] = Equipamiento.newEquipamiento(equip);
                }

                Item[] bolsa = new Item[jsonObject.getJSONArray("bolsa").length()];
                for (int j = 0; j < bolsa.length; j++) {
                    bolsa[j] = new Item(Items.stringToItems(jsonObject.getJSONArray("bolsa").getString(i)).name());
                }
                
                Personaje personaje = new Personaje(
                                                    jsonObject.getString("nombre"), jsonObject.getString("raza"), 
                                                    jsonObject.getString("fuerza"), jsonObject.getString("agilidad"), 
                                                    jsonObject.getString("constitucion"), jsonObject.getString("nivel"), 
                                                    jsonObject.getString("experiencia"), equipamientoEquipado, 
                                                    equipamientoGuardado,  bolsa, true
                                                    );

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

                Equipamiento[] equipamientoEquipado = new Equipamiento[jsonObject.getJSONObject("Equipamiento").getJSONArray("Equipado").length()];
                for (int j = 0; j < equipamientoEquipado.length; j++) {
                    JSONObject equip = jsonObject.getJSONObject("Equipamiento").getJSONArray("Equipado").getJSONObject(j);
                    equipamientoEquipado[j] = Equipamiento.newEquipamiento(equip);
                }

                Equipamiento[] equipamientoGuardado = new Equipamiento[jsonObject.getJSONObject("Equipamiento").getJSONArray("Guardado").length()];
                for (int j = 0; j < equipamientoGuardado.length; j++) {
                    JSONObject equip = jsonObject.getJSONObject("Equipamiento").getJSONArray("Guardado").getJSONObject(j);
                    equipamientoGuardado[j] = Equipamiento.newEquipamiento(equip);
                }

                Item[] bolsa = new Item[jsonObject.getJSONArray("bolsa").length()];
                for (int j = 0; j < bolsa.length; j++) {
                    bolsa[j] = new Item(Items.stringToItems(jsonObject.getJSONArray("bolsa").getString(i)).name());
                }
                
                Personaje personaje = new Personaje(
                    jsonObject.getString("nombre"), jsonObject.getString("raza"), 
                    jsonObject.getString("fuerza"), jsonObject.getString("agilidad"), 
                    jsonObject.getString("constitucion"), jsonObject.getString("nivel"), 
                    jsonObject.getString("experiencia"), equipamientoEquipado, 
                    equipamientoGuardado,  bolsa, true
                );

                personajesJson = Arrays.copyOf(personajesJson, personajesJson.length + 1);
                personajesJson[personajesJson.length - 1] = personaje;
            }
        }
        return personajesJson;
    }
    public static Personaje[] getPersonajesCsv(String ruta){//TODO arreglar(faltan datos)
        String[] arrayPersonajes = Util.readFileToStringArray(ruta);
        Personaje[] personajesExtraidos = new Personaje[0];
        try{
            personajesExtraidos = new Personaje[arrayPersonajes.length];
            String[] atributos;
            int i = 0;
            int idPers = 1;
            for (String personaje : arrayPersonajes) {
                atributos = new String[7];
                atributos = personaje.split(",");//TODO implementar un switch segun la cantidad de atributos para usar distinto inicializador
                try {
                    Personaje newPersonaje = new Personaje(atributos[0], atributos[1], atributos[2], atributos[3], atributos[4], atributos[5], atributos[6]);
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
    public static Personaje modPersonaje(String valor, Personaje p){
        
        String nombre = p.getNombre();
        String raza = String.valueOf(p.getRaza());
        String fuerza = String.valueOf(p.getFuerza());
        String agilidad = String.valueOf(p.getAgilidad());
        String constitucion = String.valueOf(p.getConstitucion());
        String nivel = String.valueOf(p.getNivel());
        String experiencia = String.valueOf(p.getExperiencia());
        Item[] bolsa = p.getBolsa();
        valor = valor.toUpperCase();
        switch (valor) {
            case "NOMBRE":
                valor = Util.pedirPorTeclado(false);
                p = new Personaje(valor, raza, fuerza, agilidad, constitucion, nivel, experiencia, null, null, bolsa, true);
                break;
            case "RAZA":
                valor = Util.pedirPorTeclado(false);
                p = new Personaje(nombre, valor, fuerza, agilidad, constitucion, nivel, experiencia, null, null, bolsa, true);
                break;
            case "FUERZA":
                valor = Util.pedirPorTeclado(true);
                p = new Personaje(nombre, raza, valor, agilidad, constitucion, nivel, experiencia, null, null, bolsa, true);
                break;
            case "AGILIDAD":
                valor = Util.pedirPorTeclado(true);
                p = new Personaje(nombre, raza, fuerza, valor, constitucion, nivel, experiencia, null, null, bolsa, true);
                break;
            case "CONSTITUCION":
                valor = Util.pedirPorTeclado(true);
                p = new Personaje(nombre, raza, fuerza, agilidad, valor, nivel, experiencia, null, null, bolsa, true);
                break;
            case "NIVEL":
                valor = Util.pedirPorTeclado(true);
                p = new Personaje(nombre, raza, fuerza, agilidad, constitucion, valor, experiencia, null, null, bolsa, true);
                break;
            case "EXPERIENCIA":
                valor = Util.pedirPorTeclado(true);
                p = new Personaje(nombre, raza, fuerza, agilidad, constitucion, nivel, valor, null, null, bolsa, true);
                break;
            default:
                throw new PersonajeException();
        }
        return p;
    }
}
