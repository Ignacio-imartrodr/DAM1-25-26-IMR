package UD4.Rol.Control;

import java.util.Arrays;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Entity.Equipamiento.Equipamiento;
import UD4.Rol.Entity.Equipamiento.Armadura.Casco;
import UD4.Rol.Entity.Equipamiento.Armadura.Pantalon;
import UD4.Rol.Entity.Others.Item;
import UD4.Rol.Entity.Others.Items;
import UD4.Rol.Utilidades.EntidadException;
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
    private static Personaje parsePersonajeFromJsonObject(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }

        JSONObject stats = jsonObject.optJSONObject("Stats");
        String nombre = "SIN_NOMBRE";
        String raza = "Humano";
        String fuerza = "1";
        String agilidad = "1";
        String constitucion = "1";
        String nivel = "1";
        String experiencia = "0";
        if (stats != null) {
            nombre = stats.optString("nombre", nombre);
            raza = stats.optString("raza", raza);
            fuerza = String.valueOf(stats.optInt("fuerza", Integer.parseInt(fuerza)));
            agilidad = String.valueOf(stats.optInt("agilidad", Integer.parseInt(agilidad)));
            constitucion = String.valueOf(stats.optInt("constitucion", Integer.parseInt(constitucion)));
            nivel = String.valueOf(stats.optInt("nivel", Integer.parseInt(nivel)));
            experiencia = String.valueOf(stats.optInt("experiencia", Integer.parseInt(experiencia)));
        }

        Equipamiento[] equipamientoEquipado = null;
        Equipamiento[] equipamientoGuardado = null;
        JSONObject equipObj = jsonObject.optJSONObject("Equipamientos");
        if (equipObj != null) {
            JSONArray equipadoJson = equipObj.optJSONArray("Equipado");
            if (equipadoJson != null) {
                equipamientoEquipado = new Equipamiento[equipadoJson.length()];
                for (int j = 0; j < equipadoJson.length(); j++) {
                    JSONObject equip = equipadoJson.optJSONObject(j);
                    if (equip != null) {
                        equipamientoEquipado[j] = Equipamiento.newEquipamiento(equip);
                    }
                }
            }
            JSONArray guardadoJson = equipObj.optJSONArray("Guardado");
            if (guardadoJson != null) {
                equipamientoGuardado = new Equipamiento[guardadoJson.length()];
                for (int j = 0; j < guardadoJson.length(); j++) {
                    JSONObject equip = guardadoJson.optJSONObject(j);
                    if (equip != null) {
                        equipamientoGuardado[j] = Equipamiento.newEquipamiento(equip);
                    }
                }
            }
        }

        Item[] bolsa = null;
        JSONArray bolsaJson = jsonObject.optJSONArray("Bolsa");
        if (bolsaJson != null) {
            bolsa = new Item[bolsaJson.length()];
            for (int j = 0; j < bolsaJson.length(); j++) {
                JSONObject itemObj = bolsaJson.optJSONObject(j);
                if (itemObj != null) {
                    String itemName = itemObj.optString("nombre", null);
                    if (itemName != null && !itemName.isBlank()) {
                        try {
                            bolsa[j] = new Item(Items.stringToItems(itemName).name());
                        } catch (Exception e) {
                            bolsa[j] = null;
                        }
                    }
                }
            }
        }

        try {
            return new Personaje(nombre, raza, fuerza, agilidad, constitucion, nivel, experiencia, equipamientoEquipado, equipamientoGuardado, bolsa, true);
        } catch (Exception e) {
            throw new EntidadException("Error creando personaje from JSON: " + e.getMessage());
        }
    }

    public static Personaje[] getPersonajesJson(String ruta){
        String contenido = Util.readFileToString(ruta);
        if (contenido == null || contenido.isBlank()) {
            throw new EntidadException("Archivo JSON vacío o no encontrado");
        }

        JSONObject root = new JSONObject(contenido);
        JSONArray personajesJson = root.optJSONArray("Personajes");
        if (personajesJson == null) {
            throw new EntidadException("No se encontró la clave Personajes");
        }

        Personaje[] personajes = new Personaje[0];
        for (int i = 0; i < personajesJson.length(); i++) {
            try {
                Personaje p = parsePersonajeFromJsonObject(personajesJson.optJSONObject(i));
                if (p != null) {
                    personajes = Arrays.copyOf(personajes, personajes.length + 1);
                    personajes[personajes.length - 1] = p;
                }
            } catch (EntidadException e) {
                System.err.println("Personaje " + i + " ignorado: " + e.getMessage());
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
            throw new EntidadException("Error en el formato de la web");
        }
        for (int i = 0; i < personajesJson.length(); i++) {
            try {
                Personaje personaje = parsePersonajeFromJsonObject(personajesJson.optJSONObject(i));
                if (personaje != null) {
                    personaje.setId(i);
                    personajes = Arrays.copyOf(personajes, personajes.length + 1);
                    personajes[personajes.length - 1] = personaje;
                }
            } catch (EntidadException e) {
                System.err.println("Personaje " + i + " ignorado (web): " + e.getMessage());
            }
        }
        return personajes;
    }

    public static String pedirStatRng(){
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
    public static String pedirStatNoRng(boolean esXp){
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
                throw new EntidadException();
        }
        return p;
    }
    public static void main(String[] args) {
        Personaje p = new Personaje("Prueba3");
        Equipamiento[] equip = new Equipamiento[] {null, null, new Pantalon("CHAOTIC"), null, null};
        Equipamiento[] guard = new Equipamiento[] {null, new Pantalon(2), null};
        Personaje p1 = new Personaje("Prueba4", null, "50", "50", "50", "2", "45", equip, guard, new Item[4], true);
        Personaje p2 = new Personaje("Prueba5", "Elfo", "50", "50", "50", "2", "45", new Equipamiento[5], new Equipamiento[] {new Pantalon(3), new Casco(1)}, new Item[] {null, Items.getItemRnd(), null}, true);
        Personaje[] personajes = new Personaje[] {p, p1, p2};
        JSONObject[] todos = new JSONObject[0];
        for (Personaje personaje : personajes) {
            Util.writeToJson("src\\UD4\\Rol\\PersonajesGuardados.json", true, "Personajes", personaje.toJsonObject());
            todos = Arrays.copyOf(todos,todos.length + 1);
            todos[todos.length -1] = personaje.toJsonObject();
        }
        Util.writeToJson("src\\UD4\\Rol\\PersonajesGuardados.json", false, "Personajes", todos);
    }
}
