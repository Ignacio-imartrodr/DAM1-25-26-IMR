package UD4.Rol.Control;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import UD4.Rol.Entity.Item;
import UD4.Rol.Entity.Items;
import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Entity.Equipamiento.Equipamiento;
import UD4.Rol.Entity.Equipamiento.Armadura.Casco;
import UD4.Rol.Entity.Equipamiento.Armadura.Pantalon;
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
        JSONArray personajesJson = new JSONArray(ruta);
        Personaje[] personajes = new Personaje[0];
        for (int i = 0; i < personajesJson.length(); i++) {
            if (personajesJson.getJSONObject(i) != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject = personajesJson.getJSONObject(i);

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

                personajes = Arrays.copyOf(personajes, personajes.length + 1);
                personajes[personajes.length - 1] = personaje;
            }
        }
        return personajes;
    }
    public static Personaje[] getPersonajesJsonUrl(String ruta){
        Personaje[] personajes = new Personaje[0];
        JSONArray personajesJson;
        try {
            personajesJson = Util.urlToJsonArray(ruta, "Personajes");
        } catch (Exception e) {
            throw new PersonajeException("Error en el formato de la web");
        }
        for (int i = 0; i < personajesJson.length(); i++) {
            JSONObject persVacio = new JSONObject("");
            JSONObject pers = personajesJson.getJSONObject(i);
            if (pers != null && !pers.equals(persVacio)) {
                JSONObject jsonObject = personajesJson.getJSONObject(i);

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
                personaje.setId(i);
                personajes = Arrays.copyOf(personajes, personajes.length + 1);
                personajes[personajes.length - 1] = personaje;
            }
        }
        return personajes;
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
    public static void main(String[] args) {
        //Personaje p = new Personaje("Prueba3");//TODO arreglar instanciador
        Equipamiento[] equip = new Equipamiento[] {null, null, new Pantalon("CHAOTIC"), null, null};
        Equipamiento[] guard = new Equipamiento[] {null, new Pantalon(2), null};
        Personaje p1 = new Personaje("Prueba4", null, "50", "50", "50", "2", "45", equip, guard, new Item[4], true);
        Personaje p2 = new Personaje("Prueba5", "Elfo", "50", "50", "50", "2", "45", new Equipamiento[5], new Equipamiento[] {new Pantalon(3), new Casco(1)}, new Item[] {null, Items.getItemRnd(), null}, true);
        Personaje[] personajes = new Personaje[] {p1, p2};
        JSONObject[] todos = new JSONObject[0];
        for (Personaje personaje : personajes) {
            Util.writeToJson("src\\UD4\\Rol\\PersonajesGuardados.json", true, "Personajes", personaje.toJsonObject());
            todos = Arrays.copyOf(todos,todos.length + 1);
            todos[todos.length -1] = personaje.toJsonObject();
        }
        Util.writeToJson("src\\UD4\\Rol\\PersonajesGuardados.json", false, "Personajes", todos);
    }
}
