package UD4.Rol.Utilidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
import UD4.Rol.Objetos.Personaje;

/**
 * @author Ignacio MR (HM Profesor Óscar de programación)
 */

public class Util {
    public static Object[] swap(Object[] x, int a, int b) {
        Object t = x[a];
        x[a] = x[b];
        x[b] = t;
        return x;
    }
    /**
     * Lee y carga el contenido de una web por url en formato {@code Json} a un array de {@code Json} 
     * 
     * @param ruta Es la ruta del archivo Json.
     * @param key Es el parametro que contiene los objetos o null si solo es el array en la web
     * @return {@code JsonArray} de la librería json.JSONArray con el contenido del archivo Json.
     */
    public static JSONObject rutaToJsonObject(String ruta, String key){
        try {
            String text = readFileToString(ruta);
            JSONObject webPersonajes = new JSONObject();
            JSONArray jsonArray;
            try {
                webPersonajes = new JSONObject(text);
                jsonArray = new JSONArray(webPersonajes.getJSONArray(key));
                webPersonajes = new JSONObject();
                for (int i = 0; i < jsonArray.length(); i++) {
                    webPersonajes.accumulate(key, jsonArray.getJSONObject(i));
                }
            } catch (Exception e) {
                jsonArray = new JSONArray(text);
                for (int i = 0; i < jsonArray.length(); i++) {
                    webPersonajes.accumulate(key, jsonArray.getJSONObject(i));
                }
            }
            return webPersonajes;
        } catch (Exception e) {
            throw new PersonajeException("Error obteniendo los objetos del archivo");
        }
    }
    /**
     * Lee y carga el contenido de una web por url en formato {@code Json} a un array de {@code Json} 
     * 
     * @param url Es la url de la web.
     * @param key Es el parametro que contiene los objetos o null si solo es el array en la web
     * @return {@code JsonArray} de la librería json.JSONArray con el contenido de la web.
     */
    public static JSONArray urlToJsonArray(String url, String key){
        try {
            String web = getJsonFromUrl(url);
            JSONObject webPersonajes;
            JSONArray jsonArray;
            try {
                webPersonajes = new JSONObject(web);
                jsonArray = new JSONArray(webPersonajes.getJSONArray(key));
            } catch (Exception e) {
                jsonArray = new JSONArray(web);
            }
            return jsonArray;
        } catch (Exception e) {
            throw new PersonajeException("Error obteniendo los objetos de la web");
        }
    }
    /**
     * Lee y carga el contenido de un fichero de texto a un array de {@code String} (un
     * elemento por línea).
     * 
     * @param filePath Es la ruta del fichero-
     * @return {@code String[]} con el contenido del fichero esparado por lineas-
     */
    public static String[] readFileToStringArray(String filePath) {
        String[] lineas = new String[0];

        try {
            // Creamos un objeto FileReader que nos permitirá leer el fichero
            FileReader reader = new FileReader(filePath);

            // Creamos un buffer para leer el fichero de forma más eficiente
            BufferedReader buffer = new BufferedReader(reader);

            // Leemos el fichero línea a línea
            String line;
            while ((line = buffer.readLine()) != null) {
                lineas = Arrays.copyOf(lineas, lineas.length + 1);
                lineas[lineas.length - 1] = line;
            }

            // Cerramos el buffer y el fichero
            buffer.close();
            reader.close();
        } catch (IOException e) {
            System.out.println("No existe el fichero o hay un problema con él.");
            return null;
        }

        // Devolvemos el contenido del fichero como un String
        return lineas;
    }
    /**
     * Lee y carga el contenido de un fichero de texto a un array de {@code String} (un
     * elemento por línea).
     * 
     * @param filePath Es la ruta del fichero-
     * @return {@code String[]} con el contenido del fichero esparado por lineas-
     */
    public static String[] stringToStringArray(String text) {
        String[] lineas = null;
        try {
            /*int cantLineas = 0;
            for (char character : text.toCharArray()) {
                if (character == ('\n')) {
                    cantLineas++;
                }
            }
            // Leemos el fichero línea a línea
            String linea;
            for (int i = 0; i < cantLineas; i++) {
                
            }*/
            lineas = text.split("\n");
            for (int i = 0; i < lineas.length; i++) {
                if (lineas[i].equals("[") && i + 1 < lineas.length) {
                    swap(lineas, i, i + 1);
                }
                String string = lineas[i].strip();
                if (string.endsWith(",")) {
                    lineas[i] = string.substring(0, string.lastIndexOf(","));
                }
            }
        } catch (Exception e) {
            System.out.println("Error.");
            // e.printStackTrace();
        }

        // Devolvemos el contenido del fichero como un String
        return lineas;
    }
    public static void mostrarContenidoCSV(String filePath){
        String[] contenidoCSV = readFileToStringArray(filePath);
        for (String linea : contenidoCSV) {
            //AlumnoLeerMostrar.mostrarAlumno(alumno);
            System.out.println(linea);
        }
    }
    private static Scanner sc = new Scanner(System.in,"Windows-1252");

    /**
     * Lee y carga el contenido de un fichero de texto a un {@code String}
     * 
     * @param filePath
     * @return
     */
    public static String readFileToString(String filePath) {
        StringBuilder fileContent = new StringBuilder();
        try {
            // Creamos un objeto FileReader que nos permitirá leer el fichero
            FileReader reader = new FileReader(filePath);

            // Creamos un buffer para leer el fichero de forma más eficiente
            BufferedReader buffer = new BufferedReader(reader);

            // Leemos el fichero línea a línea
            String line;
            while ((line = buffer.readLine()) != null) {
                // Vamos añadiendo cada línea al StringBuilder
                fileContent.append(line);
                // Añadimos un salto de línea al final de cada línea
                fileContent.append("\n");
            }

            // Cerramos el buffer y el fichero
            buffer.close();
            reader.close();
        } catch (IOException e) {
            System.out.println("No existe el fichero.");
            // e.printStackTrace();
        }

        // Devolvemos el contenido del fichero como un String
        return fileContent.toString();
    }

    /**
     * Lee y carga el contenido por teclado y verifica si es un número dependiendo de {@code pideNumero}
     * elemento por línea)
     * 
     * @param pideNumero Indica si tiene que verificar que la entrada por teclado sea numérica.
     * @return {@code String} con el resulatado del teclado o "-1" si está vacía.
     */
    public static String pedirPorTeclado(boolean pideNumero){
        String var;
        boolean sonNumeros = true;
        try {
            var = sc.nextLine().strip();
            if (var.equals("")|| var.isBlank() || var.isEmpty()) {
                return null;
            }
            for (int i = 0; i < var.length() && pideNumero; i++) {
                if (!Character.isDigit(var.charAt(i)) && var.charAt(i) != ',' && var.charAt(i) != '-' && var.charAt(i) != '+') {
                    sonNumeros = false;
                }
            }
            if (!pideNumero) {
                if (!sonNumeros) {
                    String charCorrectos = "áéíóúüñ";
                    String charIncorrectos = " ¡¢£¤"; //Para ISO-8859-1
                    String charIncorrectos1 = " ‚¡¢£�¤";//Para Windows-1252
                    String[] StringsIncorrectos = new String[] {charIncorrectos, charIncorrectos1};
                    for (int i = 0; i < var.length(); i++) {
                        for (int j = 0; j < StringsIncorrectos.length; j++) {
                            for (int k = 0; k < StringsIncorrectos[j].length(); k++) {
                                var = var.replace(StringsIncorrectos[j].charAt(k), charCorrectos.charAt(k));
                            }
                        }
                    }
                }
            } else {
                if (!sonNumeros) {
                    System.out.print("Error, introdruce un valor numérico: ");
                    return pedirPorTeclado(pideNumero);
                }
            }
            return var;
        } catch (Exception e) {
            sc.nextLine();
            System.out.println("Error, vuelve a intentar");
            return pedirPorTeclado(pideNumero);
        }
    }

    /**
     * Busca la posición de un objeto en un array de ese objeto y si no existe devuelve -1.
     * @param objetivo Objeto a buscar.
     * @param array Array donde buscar el objeto.
     * @return  Ubicación de {@code objetivo} si está en el array {@code array} o {@code -1} si {@code objetivo} NO está en el array {@code array}
     */
    public static int UbiObjetoEnArray(Object objetivo, Object[] array){ //Verificar como lo compara
        int Ubi = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(objetivo)) {
                Ubi = i;
            }
        }
        return Ubi;
    }
    
    public static boolean borrarFicheroYCrearloVacio(String RutaFichero){
        boolean successful = false;
        try {
            File archivo = new File(RutaFichero);
            if (archivo.exists()) {
                if (!archivo.delete()) {
                    System.err.println("Error borrando el archivo");
                } else {
                    if (!archivo.createNewFile()){
                        System.err.println("Error creando el archivo de nuevo");
                    } else {
                        successful = true;
                    }
                }
            } else {
                System.out.println("El archivo no existe");
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
        return successful;
    }

    /**
     * Escribe una cadena de texto {@code str} en un archivo designado
     * @param   str   : Texto que se añadirá al fichero.
     * @param   filePath  : Ruta de la ubicación del archivo.
     * @param   append  : {@code true} Para añadir al final del fichero o {@code false} para sustituir el principio del fichero
     */
    public static void writeStringToCsv(String str, String filePath) {
        try {
            // Creamos un objeto FileWriter que nos permitirá escribir en el fichero
            FileWriter writer = new FileWriter(filePath, true);

            // Escribimos el String en el fichero
            writer.write(str);

            // Cerramos el fichero
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writePersonajeToJson(String filePath, boolean append, String key, JSONObject... personaje) {
        try {
            JSONObject pers = append ? rutaToJsonObject(filePath, key) : new JSONObject();
            for (Object object : personaje) {
                pers.accumulate(key, object);
            }    
            borrarFicheroYCrearloVacio(filePath);
            // Creamos un objeto FileWriter que nos permitirá escribir en el fichero
            FileWriter writer = new FileWriter(filePath, append);
            writer.write(pers.toString());
            // Cerramos el fichero
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Pregunta por teclado {@code opcion1 } o {@code opcion2 } hasta obtener una respuesta válida (si se presiona "Enter" selecciona {@code opcion1})
     * 
     * @param   opcion1    : El valor {@code String} introducido y es la respuesta por defecto.
     * @param   opcion2    : El valor {@code String} introducido.
     * @return {@code true} if {@code respuesta.equals(opcion1) || respuesta.equals("-1")}, {@code false} if {@code respuesta.equeals(opcion2)}.
     */
    public static boolean escogerOpcion(String opcion1, String opcion2){
        
        opcion1 = opcion1.toLowerCase();
        opcion2 = opcion2.toLowerCase();
        
        String respuesta;
        boolean s = false;
        respuesta = Util.pedirPorTeclado(false);
        while (!(respuesta == null) && !respuesta.equalsIgnoreCase(opcion1) && !respuesta.equalsIgnoreCase(opcion2)) {
            System.out.print("Responde unicamente con \"" + opcion1 + "\" o \"" + opcion2 +"\": ");
            respuesta = Util.pedirPorTeclado(false);
        }
        if (respuesta == null || respuesta.equalsIgnoreCase(opcion1)) {
            s = true;
        }
        return s;
    }
    /**
     * Lee el contenido de un fichero de texto a un Json mediante una URL y lo carga a una {@code String}.
     * 
     * @param   url   : Enlace al Archivo.json
     * @return  {@code String} con el contenido del Archivo.json
     */
    private static String getJsonFromUrl(String url) throws IOException, InterruptedException {
        // Configuración del proxy del sistema
        System.setProperty("java.net.useSystemProxies", "true");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            System.err.println("Error HTTP: " + resp.statusCode());
            System.exit(1);
        }

        String json = resp.body();
        return json;
    }

    public static void main(String[] args) {
        //String personajes = "";
        Personaje[] personajesCreados = new Personaje[3];
        Personaje prueba = new Personaje("prueba");
        Personaje prueba1 = new Personaje("prueba1");
        Personaje prueba2 = new Personaje("prueba2");
        personajesCreados[0] = prueba;
        personajesCreados[1] = prueba1;
        personajesCreados[2] = prueba2;/*
        for (Personaje personaje : personajesCreados) {
            if (!(personaje == null)) {
                personajes += personaje.toJsonString()+",\n";
            }
        }
        if (!personajes.equals("")) {
            personajes = personajes.substring(0, personajes.lastIndexOf(","));
            writeStringToJson(personajes, "src\\UD4\\rol\\PersonajesGuardados.json", true);
        }*/
        /*JSONObject pers = new JSONObject();
        pers.accumulate("Personajes", prueba.toJsonObject());
        pers.accumulate("Personajes", prueba1.toJsonObject());
        personajes = pers.get("Personajes").toString();
        System.out.println(personajes);
        JSONArray intento = new JSONArray(personajes);
        System.out.println(intento.toString());*/
        String rutaFichero = "src\\UD4\\Rol\\PersonajesGuardados copy.json";
        JSONObject[] personajes = new JSONObject[0];
        JSONObject pers = new JSONObject();
        for (Personaje personaje : personajesCreados) {
            if (!(personaje == null)) {
                pers = personaje.toJsonObject();
                personajes = Arrays.copyOf(personajes, personajes.length + 1);
                personajes[personajes.length - 1] = pers;
            }
        }
        if (personajes.length != 0) {
            Util.writePersonajeToJson(rutaFichero, true, "Personajes", personajes);
        } else{
            System.out.println("No había personajes que guardar");
        }
    }
}
