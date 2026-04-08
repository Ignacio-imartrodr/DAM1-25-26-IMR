package UD4.Rol.Control;

import java.util.Arrays;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Entity.Equipamiento.Equipamiento;
import UD4.Rol.Entity.Equipamiento.Armadura.Casco;
import UD4.Rol.Entity.Equipamiento.Armadura.Pantalon;
import UD4.Rol.Entity.Others.EquipEquipado;
import UD4.Rol.Entity.Others.Item;
import UD4.Rol.Entity.Others.Items;
import UD4.Rol.Entity.Others.Raza;
import UD4.Rol.Utilidades.EntidadException;
import UD4.Rol.Utilidades.Util;

public abstract class Creacion {
    
    public static Personaje[] seleccionarPersonajes(Personaje[] personajes, int cantidadASeleccionar) {
        if (cantidadASeleccionar < 1 || personajes.length == 0 || personajes == null || personajes.length < cantidadASeleccionar) {
            return null;
        }
        if (personajes.length == cantidadASeleccionar) {
            return personajes;
        }
        Personaje[] personajesSelec = new Personaje[cantidadASeleccionar];
        boolean esSiguiente = true;
        for (int i = -1, j = 0, skip = -1; j < personajesSelec.length;) {
            if (esSiguiente) {
                if (i == personajes.length - 1) {
                    i = 0;
                } else {
                    i++;
                }
            } else {
                if (i == 0) {
                    i = personajes.length - 1;
                } else {
                    i--;
                }
            }
            if (i != skip) {
                System.out.println(personajes[i].getFicha());
                System.out.print("¿Quieres seleccionar este personaje? (S/n): ");
                if (Util.escogerOpcion("S", "n")) {
                    personajesSelec[j] = personajes[i];
                    j++;
                    skip = i;
                }
            }
            if (j < personajesSelec.length) {
                System.out.println("Siguiente personaje o anterior? (S/a): ");
                esSiguiente = Util.escogerOpcion("S", "a");
            }
        }
        return personajesSelec;
    }
    public static String getStringPersonajes(Personaje[] personajes) {
        String texto;
        if (personajes != null && personajes.length > 0) {
            texto = "\nPersonajes:\n";
            for (Personaje personaje : personajes) {
                texto += personaje.getFicha() + "\n________________________\n";
            }
        } else {
            texto = "\nNo hay Personajes disponibles\n";
        }
        return texto;
    }

    /**
     * Transforma un Objeto {@code JSONObject} ya formateado en un {@code Personaje}
     * 
     * @param persJO {@code JSONObject} con formato de {@code Personaje}
     * @return {@code Personaje} formado por {@code persJO} o null si ocurre un fallo
     */
    public static Personaje getPersonajeFromJsonObject(JSONObject persJO) {
        if (persJO == null) {
            System.err.println("el JSONObject es null");
            return null;
        }

        JSONObject stats = persJO.optJSONObject("Stats");
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
        } else {
            System.err.println("Faltan las stats");
            return null;
        }

        Equipamiento[] equipamientoEquipado = null;
        Equipamiento[] equipamientoGuardado = null;
        JSONObject equipObj = persJO.optJSONObject("Equipamientos");
        if (equipObj != null) {
            JSONArray equipadoJson = equipObj.optJSONArray("Equipado");
            if (equipadoJson != null) {
                equipamientoEquipado = new Equipamiento[equipadoJson.length()];
                for (int j = 0; j < equipadoJson.length(); j++) {
                    JSONObject equip = equipadoJson.optJSONObject(j);
                    if (equip != null) {
                        try {
                            equipamientoEquipado[j] = Equipamiento.newEquipamiento(equip);
                        } catch (Exception e) {
                            System.err.println("Error con el esquipamientoEquipado:" + e.getMessage());
                            return null;
                        }
                    }
                }
                if (!EquipEquipado.isFormatoCorrecto(equipamientoEquipado)) {
                    System.err.println("Error en el formato de EquipamientoEquipado");
                    return null;
                }
            } else {
                System.err.println("Falta el EquipamientoEquipado");
                return null;
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
        } else {
            System.err.println("Falta el equipamiento");
            return null;
        }

        Item[] bolsa = null;
        JSONArray bolsaJson = persJO.optJSONArray("Bolsa");
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
            System.err.println("Error creando personaje from JsonObject: " + e.getMessage());
            return null;
        }
    }
    public static Personaje[] cargarPersonajesDeArchivo(String rutaFile){   
        Personaje[] personajesFichero = new Personaje[0];
        boolean restart = true;
        while (restart) {
            restart = false;
            System.out.println("Opciones: Json");
            System.out.print("¿Quieres cargar personajes desde " + rutaFile + "? (S/n): ");
            if (Util.escogerOpcion("S", "n")) {
                if (rutaFile.endsWith(".json") || rutaFile.startsWith("www.http") || rutaFile.startsWith("http")) {
                    if (rutaFile.startsWith("www.http") || rutaFile.startsWith("http")) {
                        boolean errorUrl = true;
                        String temp = rutaFile;
                        while (errorUrl) {
                            errorUrl = false;
                            try {
                                personajesFichero = Creacion.getPersonajesFromJson(rutaFile);
                                if (personajesFichero.length == 0) {
                                    System.out.println("El Json no contenía personajes");
                                }
                                System.out.print("¿Probar otra Url? (S/n): ");
                                if (Util.escogerOpcion("S", "n")) {
                                    errorUrl = true;
                                }
                            } catch (Exception e) {
                                errorUrl = true;
                                System.out.println("Error en la url");
                                System.out.print("URL? (\"n\" para salir): ");
                                temp = Util.pedirPorTeclado(false);
                                if (temp.equalsIgnoreCase("n")) {
                                    errorUrl = false;
                                    restart = true;
                                }
                            }
                        }
                    } else {
                        personajesFichero = Creacion.getPersonajesFromJson(rutaFile);
                        if (personajesFichero.length == 0) {
                            System.out.println("El fichero no contenía personajes");
                        }
                    }
                } else {
                    System.out.println("Error en la ruta");
                    System.out.print("Ruta? (\"n\" para salir): ");
                    rutaFile = Util.pedirPorTeclado(false);
                    if (rutaFile.equalsIgnoreCase("n")) {
                        System.out.println("personajes no cargados");
                        restart = false;
                    }
                }
            } else {
                System.out.println("Quierres ingresar otra ruta? (S/n)");
                if (Util.escogerOpcion("S", "n")) {
                    System.out.print("Ruta? (\"n\" para salir): ");
                    rutaFile = Util.pedirPorTeclado(false);
                    if (rutaFile.equalsIgnoreCase("n")) {
                        System.out.println("Personajes no cargados");
                        restart = false;
                    } else {
                        restart = true;
                    }
                } else {
                    System.out.println("No se han cargado personajes.");
                    restart = false;
                }
            }
        }
        return personajesFichero;
    }
    public static Personaje[] getPersonajesFromJson(String ruta, Object... keysToPers){
        Object contenido = Util.rutaJsonToObjectJson(ruta, keysToPers);
        if (contenido == null) {
            System.err.println("Archivo JSON vacío o no encontrado");
            return null;
        }

        boolean esBaseGeneral = false;
        if (ruta.equals(Guardado.RUTA_BASE_GENERAL)) {
            esBaseGeneral = true;
        }

        String key = "Personajes";
        JSONObject rootJO = null;
        JSONArray personajesJson;
        if (esBaseGeneral) {
            rootJO = (JSONObject) contenido;
            personajesJson = rootJO.optJSONArray(key);
        } else {
            if (contenido instanceof JSONObject) {
                rootJO = (JSONObject) contenido;
                if (rootJO.has(key)) {
                    if (rootJO.get(key) instanceof JSONArray) {
                        personajesJson = rootJO.getJSONArray(key);
                    } else {
                        return null;
                    }
                } else {
                    if (getPersonajeFromJsonObject(rootJO) == null) {
                        return null;
                    } else {
                        return new Personaje[]{getPersonajeFromJsonObject(rootJO)};
                    }
                }
            } else {
                personajesJson = (JSONArray) contenido;
            }
                
        }
        
        
        if (personajesJson == null) {
            System.err.println("No se encontró Personajes en la clave");
            return null;
        }

        
        Personaje[] personajes = new Personaje[0];
        for (int i = 0; i < personajesJson.length(); i++) {
            Personaje p = getPersonajeFromJsonObject(personajesJson.optJSONObject(i));
            if (p != null) {
                if (esBaseGeneral) {
                    p.setId(i);
                }
                personajes = Arrays.copyOf(personajes, personajes.length + 1);
                personajes[personajes.length - 1] = p;
            } else {
                //La función "getPersonajeFromJsonObject" lanza un System.err.println con la causa del error por el que se ignora el personaje
                System.err.println("Por lo que se ignoró el Personaje " + i);
            }
        }
        return personajes;
    }
    /* ---------Descartada temporalmente---------
    public static Personaje[] getPersonajesJsonUrl(String ruta){//Revisar
        Personaje[] personajes = new Personaje[0];
        JSONArray personajesJson;
        try {
            personajesJson = Util.urlToJsonArray(ruta, "Personajes");
        } catch (Exception e) {
            throw new EntidadException("Error en el formato de la web");
        }
        for (int i = 0; i < personajesJson.length(); i++) {
            
            Personaje personaje = getPersonajeFromJsonObject(personajesJson.optJSONObject(i));
            if (personaje != null) {
                personaje.setId(i);
                personajes = Arrays.copyOf(personajes, personajes.length + 1);
                personajes[personajes.length - 1] = personaje;
            } else {
                //La función "getPersonajeFromJsonObject" lanza un System.err.println con la causa del error por el que se ignora el personaje
                System.err.println("Por lo que se ignoró el Personaje " + i);
            }
        }
        return personajes;
    }
    */
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
            System.out.print("El personaje necesita un nombre: ");
            nombre = Util.pedirPorTeclado(false);          
        }
        nombre = nombre.strip();
        System.out.println("Escoge una de las siguientes razas (Enter para Humano): ");
        System.out.println(Raza.getRazasStats());
        String raza = "e";
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
        Object[] todos = new JSONObject[0];
        for (Personaje personaje : personajes) {
            Util.writeToJson("src\\UD4\\Rol\\PersonajesGuardados.json", true,null, new Object[]{"Personajes"}, personaje.toJsonObject());
            todos = Arrays.copyOf(todos,todos.length + 1);
            todos[todos.length -1] = personaje.toJsonObject();
        }
        Util.writeToJson("src\\UD4\\Rol\\PersonajesGuardados.json", false, null, new Object[]{"Personajes"}, todos);
    }
}
