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
import UD4.Rol.Entity.Others.Raza;
import UD4.Rol.Utilidades.EntidadException;
import UD4.Rol.Utilidades.Util;

public abstract class Creacion {
    
    public static Personaje[] seleccionarPersonajes(Personaje[] personajesCreados, int cantidadASeleccionar) {
        Personaje[] personajesEnBatalla = new Personaje[cantidadASeleccionar];
        boolean esSiguiente = true;
        for (int i = -1, j = 0, skip = -1; j < personajesEnBatalla.length;) {
            if (esSiguiente) {
                if (i == personajesCreados.length - 1) {
                    i = 0;
                } else {
                    i++;
                }
            } else {
                if (i == 0) {
                    i = personajesCreados.length - 1;
                } else {
                    i--;
                }
            }
            if (i != skip) {
                System.out.println(personajesCreados[i].getFicha());
                System.out.print("¿Quieres seleccionar este personaje? (S/n): ");
                if (Util.escogerOpcion("S", "n")) {
                    personajesEnBatalla[j] = personajesCreados[i];
                    j++;
                    skip = i;
                }
            }
            if (j < personajesEnBatalla.length) {
                System.out.println("Siguiente personaje o anterior? (S/a): ");
                esSiguiente = Util.escogerOpcion("S", "a");
            }
        }
        return personajesEnBatalla;
    }
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
    public static Personaje getPersonajeFromJsonObject(JSONObject jsonObject) throws EntidadException {
        if (jsonObject == null) {
            return null;
        }

        JSONObject stats = jsonObject.optJSONObject("Stats");
        String nombre = null;
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
            throw new EntidadException("Error creando personaje from JsonObject: " + e.getMessage());
        }
    }

    public static Personaje[] getPersonajesFromJson(String ruta){
        String contenido = Util.readFileToString(ruta);
        if (contenido == null || contenido.isBlank()) {
            throw new EntidadException("Archivo JSON vacío o no encontrado");
        }

        JSONObject root = new JSONObject(contenido);
        JSONArray personajesJson = root.optJSONArray("Personajes");
        if (personajesJson == null) {
            throw new EntidadException("No se encontró la clave Personajes");
        }

        boolean esBaseGeneral = false;
        if (ruta.equals(Guardado.RUTA_BASE_GENERAL)) {
            esBaseGeneral = true;
        }
        Personaje[] personajes = new Personaje[0];
        for (int i = 0; i < personajesJson.length(); i++) {
            try {
                Personaje p = getPersonajeFromJsonObject(personajesJson.optJSONObject(i));
                if (p != null) {
                    if (esBaseGeneral) {
                        p.setId(i);
                    }
                    personajes = Arrays.copyOf(personajes, personajes.length + 1);
                    personajes[personajes.length - 1] = p;
                }
            } catch (EntidadException e) {
                System.err.println("Personaje " + i + " ignorado por error: " + e.getMessage());
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
                Personaje personaje = getPersonajeFromJsonObject(personajesJson.optJSONObject(i));
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

    public static Personaje[] pedirPersonajes() {
        Personaje[] personajesNuevos = new Personaje[0];
        boolean seguir = true;
        while (seguir) {
            System.out.print("¿Quieres crear un nuevo personaje? (S/n): ");
            if (Util.escogerOpcion("s", "n")) {
                Personaje personaje = new Personaje();
                System.out.println("\nRazas disponibles:\n\n" + Raza.getRazasStats());
                personaje = crearPersonaje();
                System.out.println(personaje.getFicha());
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
    private static Personaje crearPersonaje(){
        Personaje personaje;
        System.out.print("Nombre del personaje: ");
        String nombre = Util.pedirPorTeclado(false);
        while (nombre == null) {
            System.out.print("El persnaje necesita un nombre: ");
            nombre = Util.pedirPorTeclado(false);          
        }
        nombre = nombre.strip();
        System.out.println("Escoge una de las siguientes razas: orco, elfo, HUMANO, enano, hobbit o troll");
        String raza = "a";
        for (boolean error = true; error;) {
            try {
                Raza razaVal = Raza.stringToRaza(Util.pedirPorTeclado(false));
                raza = razaVal.toString();
                error = false;
            } catch (EntidadException e) {
                System.out.println("Raza no válida. Introduce uno de las siguientes: orco, elfo, HUMANO, enano, hobbit o troll");
                error = true;
            }
        }

        System.out.println("Introduce las siguientes estadísticas. Si quieres que se generen aleatoriamente, pulsa \"Enter\" sin introducir ningún valor.");
        System.out.print("Fuerza: ");
        String fuerza = Creacion.pedirStatRng();
        
        System.out.print("Agilidad: ");
        String agilidad = Creacion.pedirStatRng();

        System.out.print("Constitución: ");
        String constitucion = Creacion.pedirStatRng();
        
        System.out.print("Nivel: ");
        String nivel = Creacion.pedirStatNoRng(false);
        
        System.out.println("Nivel de experiencia: ");
        String experiencia = Creacion.pedirStatNoRng(true);

        personaje = new Personaje(nombre, raza, fuerza, agilidad, constitucion, nivel, experiencia, null, null, null, false);
        return personaje;
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
