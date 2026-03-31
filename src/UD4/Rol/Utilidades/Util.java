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
import java.util.Comparator;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

import UD4.Rol.Entity.Entidades.Personaje;

/**
 * @author Ignacio MR (HM Profesor Óscar de programación)
 */

public abstract class Util {
    /**
     * Ordena un array cuarquiera por su orden natural usando {@code Arrays.sort()}
     * pero en caso de tener valores null los deja al final del array.
     * 
     * @param x : Array de un tipo que implementa {@code compareTo} y que será
     *          ordenado
     */
    public static void sortArray(Object[] x) {
        if (x == null || x.length == 0 || x.length == 1) {
            return;
        }
        int firstNull = nullOfArrayToEnd(x);
        if (firstNull == -1) {
            firstNull = x.length;
        }
        Arrays.sort(x, 0, firstNull);
    }

    private static int nullOfArrayToEnd(Object[] x) {
        boolean conNull = false;
        for (Object obj : x) {
            if (obj == null) {
                conNull = true;
                break;
            }
        }
        if (conNull) {
            /*
             * class Comparador implements Comparator<Object>{ //Investigar para sustituir
             * por el de la API
             * 
             * @Override
             * public int compare(Object o1, Object o2) {
             * if (o1 == o2) { return 0; }
             * if (o1 == null) { return 1; }
             * if (o2 == null) { return -1; }
             * return 0;
             * }
             * };
             * Comparador nullToEnd = new Comparador();
             * Arrays.sort(x, nullToEnd);
             */
            Arrays.sort(x, Comparator.nullsLast(null));

            int izq = 0;
            int der = x.length;

            while (izq < der) {
                int medio = izq + (der - izq) / 2;
                if (x[medio] != null) {
                    izq = medio + 1;
                } else {
                    der = medio;
                }
            }
            return (izq < x.length && x[izq] == null) ? izq : -1;
        } else {
            return -1;
        }
    }

    public static Object[] swap(Object[] x, int a, int b) {
        Object t = x[a];
        x[a] = x[b];
        x[b] = t;
        return x;
    }

    /**
     * Lee y carga el contenido de un fichero en formato {@code Json} por su ruta
     * relativa a un Objeto de {@code JSONObject} o {@code JSONArray}
     * 
     * @param ruta     Es la ruta del archivo Json.
     * @param keysToUb Es el parametro que contiene los objetos o null si quieres
     *                 todo el contenido
     * @return {@code JsonObject} o {@code JSONArray} de la librería json-20220320
     *         con el contenido del archivo Json o {@code null} si hubo un fallo con
     *         la ruta, keysToUb o el formato del Json.
     */
    public static Object rutaJsonToObjectJson(String ruta, Object... keysToUb) {
        if (ruta == null || ruta.isBlank()) {
            return null;
        }
        try {
            Object objectJson;
            String text = readFileToString(ruta);
            String jsonPointer = getJsonPointer(keysToUb);

            JSONObject jo;
            JSONArray ja;
            if (text.startsWith("{")) {
                jo = new JSONObject(text);
                objectJson = jo;
                if (jsonPointer != null) {
                    Object obj;
                    obj = jo.query(jsonPointer);
                    if (obj instanceof JSONObject) {
                        jo = (JSONObject) obj;
                    } else if (obj instanceof JSONArray) {
                        ja = (JSONArray) obj;
                        objectJson = ja;
                    } else {
                        objectJson = null;
                    }
                }
            } else if (text.startsWith("[")) {
                ja = new JSONArray(text);
                objectJson = ja;
                if (jsonPointer != null) {
                    objectJson = ja.query(jsonPointer);
                    if (objectJson instanceof JSONObject) {
                        jo = (JSONObject) objectJson;
                        objectJson = jo;
                    } else if (objectJson instanceof JSONArray) {
                        ja = (JSONArray) objectJson;
                        objectJson = ja;
                    } else {
                        objectJson = null;
                    }
                }
            } else {
                objectJson = null;
            }
            return objectJson;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Lee y carga el contenido de una web por url en formato {@code Json} a un
     * array de {@code Json}
     * 
     * @param url Es la url de la web.
     * @param key Es el parametro que contiene los objetos o null si solo es el
     *            array en la web
     * @return {@code JsonArray} de la librería json.JSONArray con el contenido de
     *         la web.
     */
    public static JSONArray urlToJsonArray(String url, String key) {
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
            throw new EntidadException("Error obteniendo los objetos de la web");
        }
    }

    /**
     * Lee y carga el contenido de un fichero de texto a un array de {@code String}
     * (un
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
     * Lee y carga el contenido de un fichero de texto a un array de {@code String}
     * (un
     * elemento por línea).
     * 
     * @param filePath Es la ruta del fichero-
     * @return {@code String[]} con el contenido del fichero esparado por lineas-
     */
    public static String[] stringToStringArray(String text) {
        String[] lineas = null;
        try {
            /*
             * int cantLineas = 0;
             * for (char character : text.toCharArray()) {
             * if (character == ('\n')) {
             * cantLineas++;
             * }
             * }
             * // Leemos el fichero línea a línea
             * String linea;
             * for (int i = 0; i < cantLineas; i++) {
             * 
             * }
             */
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

    private static Scanner sc = new Scanner(System.in, "Windows-1252");

    /**
     * Lee y carga el contenido de un fichero de texto a un {@code String}.
     * 
     * @param filePath
     * @return La información del fichero como texto o null si hubo un fallo.
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
            e.printStackTrace();
            return null;
        }

        // Devolvemos el contenido del fichero como un String
        return fileContent.toString();
    }

    /**
     * Lee y carga el contenido por teclado y verifica si es un número dependiendo
     * de {@code pideNumero}
     * elemento por línea)
     * 
     * @param pideNumero Indica si tiene que verificar que la entrada por teclado
     *                   sea numérica.
     * @return {@code String} con el resulatado del teclado o "-1" si está vacía.
     */
    public static String pedirPorTeclado(boolean pideNumero) {
        String var;
        boolean sonNumeros = true;
        try {
            var = sc.nextLine();
            if (var == null) {
                return null;
            }
            var = var.strip();
            if (var.isBlank()) {
                return null;
            }
            for (int i = 0; i < var.length() && pideNumero; i++) {
                if (i == 0 && (var.charAt(0) == '-' || var.charAt(0) == '+')) {
                    i++;
                }
                if (!Character.isDigit(var.charAt(i))) {
                    if (var.charAt(i) != ',') {
                        sonNumeros = false;
                    } else if ((i + 1) >= var.length() || !Character.isDigit(var.charAt(i + 1))) {
                        sonNumeros = false;
                    }
                }
            }
            if (!pideNumero) {
                if (!sonNumeros) {
                    String charCorrectos = "áéíóúüñ";
                    String charIncorrectos = " ¡¢£¤"; // Para ISO-8859-1
                    String charIncorrectos1 = " ‚¡¢£�¤";// Para Windows-1252
                    String[] StringsIncorrectos = new String[] { charIncorrectos, charIncorrectos1 };
                    for (int i = 0; i < var.length(); i++) {
                        if (!Character.isDigit(var.charAt(i))) {
                            for (int j = 0; j < StringsIncorrectos.length; j++) {
                                for (int k = 0; k < StringsIncorrectos[j].length(); k++) {
                                    var = var.replace(StringsIncorrectos[j].charAt(k), charCorrectos.charAt(k));
                                }
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
     * Pide por teclado una ruta a un archivo Json.
     * 
     * @return Ruta otorgada por teclado.
     */
    public static String pedirRuta() {
        boolean restart = true;
        String rutaFichero = null;
        while (restart) {
            restart = false;
            System.out.print("¿Quieres dar una ruta a fichero Json? (S/n): ");
            if (Util.escogerOpcion("s", "n")) {
                System.out.print("Ruta del fichero (Ej| src\\UD4\\rol\\archivo.extensión): ");
                rutaFichero = Util.pedirPorTeclado(false);
                if ((rutaFichero == null) || !rutaFichero.endsWith(".json")) {
                    System.out.println("La ruta debe dirigir a un fichero con extensión .json");
                    restart = true;
                }
            } else {
                System.out.println("Ruta no guardada.");
                rutaFichero = null;
            }
        }
        return rutaFichero;
    }

    /**
     * Busca la posición de un objeto en un array de ese objeto y si no existe
     * devuelve -1.
     * 
     * @param objetivo Objeto a buscar.
     * @param array    Array donde buscar el objeto.
     * @return Ubicación de {@code objetivo} si está en el array {@code array} o
     *         {@code -1} si {@code objetivo} NO está en el array {@code array}
     */
    public static int UbiObjetoEnArray(Object objetivo, Object[] array) { // Verificar como lo compara
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(objetivo)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean borrarFicheroYCrearloVacio(String RutaFichero) {
        boolean successful = false;
        try {
            File archivo = new File(RutaFichero);
            if (archivo.exists()) {
                if (!archivo.delete()) {
                    System.err.println("Error borrando el archivo");
                } else {
                    if (!archivo.createNewFile()) {
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
     * Guarda cada objeto en la ubicacion indicada con {@code key} (todo en la misma
     * posición) del arrchivo Json indicado en {@code filePath}.
     * 
     * @param ruta     ruta del archivo.json
     * @param key      Key del objeto/s para insertarlo/s o {@code null} solo si
     *                 seva a insertar en un {@code JSONArray} para añadir
     *                 incrementalmente.
     * @param keysToUb Keys de la ubicación donde guardar el/los objeto/s
     *                 insertado/s {@code null} para insertar en el primer nivel del
     *                 Json.
     * @param object   Objeto/s a insertar en el Json (en la misma ubicación).
     * @param append   {@code true} si quieres añadir información al Json o
     *                 {@code false} para borrar la inforamción ya existente y
     *                 añadir {@code object} en el Json en blanco.
     * @return {@code true} si se ejecutó con exito o {@code false} si la
     *         {@code key} ya existe, {@code key} es {@code null} de forma inválida,
     *         o hubo un error y no se modificó el Json.
     */
    public static boolean writeToJson(String ruta, boolean append, String key, Object[] keysToUb, Object... object) {
        if (object == null || object.length <= 0 || ruta == null || ruta.isBlank()) {
            return false;// Nada donde guardar o que escribir
        }

        JSONObject infObj = null;
        JSONArray infoArray = null;
        if (append) {

            // Validación de contenido
            Object obj = rutaJsonToObjectJson(ruta);
            if (obj instanceof JSONArray) {
                infoArray = (JSONArray) obj;
            } else if (obj instanceof JSONObject) {
                infObj = (JSONObject) obj;
            } else {
                String text = readFileToString(ruta);
                if (text != null && text.isBlank()) {

                    // Este caso es el mismo que no append
                    if (keysToUb != null) {
                        return false;
                    }
                    if (key == null) {
                        if (object.length == 1) {
                            if (object[0] instanceof JSONArray) {
                                infoArray = (JSONArray) object[0];
                            } else if (object[0] instanceof JSONObject) {
                                infObj = (JSONObject) object[0];
                            } else {
                                infoArray = new JSONArray();
                            }
                        } else {
                            infoArray = new JSONArray();
                        }
                    } else {
                        infObj = new JSONObject();
                    }
                } else {
                    return false;// La estructura del Json original está mal o no existe el archivo
                }
            }
        } else {
            if (keysToUb != null) {
                return false;
            }
            if (key == null) {
                if (object.length == 1) {
                    if (object[0] instanceof JSONArray) {
                        infoArray = (JSONArray) object[0];
                    } else if (object[0] instanceof JSONObject) {
                        infObj = (JSONObject) object[0];
                    } else {
                        infoArray = new JSONArray();
                    }
                } else {
                    infoArray = new JSONArray();
                }
            } else if (key != null) {
                infObj = new JSONObject();
            }
        }

        String jsonPointer = getJsonPointer(keysToUb);

        // Validar keysToUb (la ruta jsonPointer) solo si no es null (si es null, se
        // inserta en el primer nivel del json)
        if (jsonPointer != null) {
            try {
                if (infObj != null) {
                    if (infObj.query(jsonPointer) == null || !(infObj.query(jsonPointer) instanceof JSONArray
                            || infObj.query(jsonPointer) instanceof JSONObject)) {
                        return false;
                    }
                } else {
                    if (infoArray.query(jsonPointer) == null || !(infoArray.query(jsonPointer) instanceof JSONArray
                            || infoArray.query(jsonPointer) instanceof JSONObject)) {
                        return false;
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }

        int cod = -1;

        // ---------Validar key---------

        // Verificar si se guarda en un Object
        boolean enJo;

        if (infObj != null) {
            // El Json es un Objeto
            if (jsonPointer == null) {
                enJo = true;
            } else {
                if (infObj.query(jsonPointer) instanceof JSONObject) {
                    enJo = true;
                } else {
                    enJo = false;
                }
            }
        } else {
            // El Json es un Array
            if (jsonPointer == null) {
                enJo = false;
            } else {
                if (infoArray.query(jsonPointer) instanceof JSONObject) {
                    enJo = true;
                } else {
                    enJo = false;
                }
            }
        }

        // Verificar key
        if (key == null) {
            if (enJo) {
                return false;// key null solo valido en Array
            }
        } else {

            // Verificar que key sea un número si se guarda en un Array
            if (!enJo) {
                try {
                    cod = Integer.valueOf(key);
                    if (cod < 0) {
                        return false;// Ubicación a guardar erronea en Array
                    }
                } catch (Exception e) {
                    return false;// Ubicación a guardar erronea en Array
                }
            }
        }

        // -------------Inserción de object--------------

        // Caso 1: Insertar en primer nivel
        if (keysToUb == null || keysToUb.length == 0) {

            // Para Objeto
            if (infObj != null) {
                if (infObj.has(key)) {
                    return false;// La clave ya existe
                }
                if (object.length == 1) {
                    infObj.put(key, object[0]);
                } else {
                    JSONArray valores = new JSONArray();
                    for (Object obj : object) {
                        valores.put(obj);
                    }
                    infObj.put(key, valores);
                }

                // Para Array
            } else {
                if (key == null) {
                    if (object.length == 1) {
                        infoArray.put(object[0]);
                    } else {
                        for (Object obj : object) {
                            infoArray.put(obj);
                        }
                    }
                } else {
                    if (infoArray.opt(cod) != null) {
                        return false;// La clave ya existe
                    }
                    if (object.length == 1) {
                        infoArray.put(cod, object[0]);
                    } else {
                        JSONArray valores = new JSONArray();
                        for (Object obj : object) {
                            valores.put(obj);
                        }
                        infoArray.put(cod, valores);
                    }
                }
            }

            // Caso 2: Insertar en niveles anidados (keysToUb válido)
        } else {
            Object oldInfo = infObj != null ? infObj : infoArray;
            Object resultadoRecreacion;
            Object objAux = null;
            JSONArray jaAux;
            JSONObject joAux;

            // Para Objeto
            if (infObj != null) {
                objAux = infObj.query(jsonPointer);

                // Para Array
            } else {
                objAux = infoArray.query(jsonPointer);
            } // Es imposible que sea otra cosa debido a la validación de contenido

            // objAux será un JSONObject o JSONArray debido a la validación de jsonPointer

            // Si key es null, la ubicación es un JSONArray (verificado en key)
            if (key == null) {
                jaAux = (JSONArray) objAux;
                if (object.length == 1) {
                    jaAux.put(object[0]);
                } else {
                    for (Object obj : object) {
                        jaAux.put(obj);
                    }
                }
                objAux = jaAux;

                // -----Recrear información-----
                resultadoRecreacion = recreacionJsonObjectOArray(keysToUb, objAux, oldInfo);
                if (resultadoRecreacion instanceof JSONObject) {
                    infObj = (JSONObject) resultadoRecreacion;
                } else if (resultadoRecreacion instanceof JSONArray) {
                    infoArray = (JSONArray) resultadoRecreacion;
                } else {
                    return false;
                }

                // Si key no es null
            } else {
                if (objAux instanceof JSONObject) {
                    joAux = (JSONObject) objAux;
                    if (joAux.has(key)) {
                        return false;
                    }
                    if (object.length == 1) {
                        joAux.put(key, object[0]);
                    } else {
                        JSONArray valores = new JSONArray();
                        for (Object obj : object) {
                            valores.put(obj);
                        }
                        joAux.put(key, valores);
                    }
                    objAux = joAux;
                } else {
                    jaAux = (JSONArray) objAux;
                    if (jaAux.opt(cod) != null) {
                        return false;
                    }
                    if (object.length == 1) {
                        jaAux.put(cod, object[0]);
                    } else {
                        JSONArray valores = new JSONArray();
                        for (Object obj : object) {
                            valores.put(obj);
                        }
                        jaAux.put(cod, valores);
                    }
                    objAux = jaAux;
                }

                // -----Recrear información-----
                resultadoRecreacion = recreacionJsonObjectOArray(keysToUb, objAux, oldInfo);
                if (resultadoRecreacion instanceof JSONObject) {
                    infObj = (JSONObject) resultadoRecreacion;
                } else if (resultadoRecreacion instanceof JSONArray) {
                    infoArray = (JSONArray) resultadoRecreacion;
                } else {
                    return false;
                }
            }
        }
        try {
            borrarFicheroYCrearloVacio(ruta);
            FileWriter writer = new FileWriter(ruta, false);
            writer.write(infObj != null ? infObj.toString() : infoArray.toString());
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String getJsonPointer(Object[] keysToUb) {
        if (keysToUb == null || keysToUb.length == 0) {
            return null;
        }
        Object obj;
        String llave;
        int cod;
        String jsonPointer = "";
        for (int i = 0; i < keysToUb.length; i++) {
            obj = keysToUb[i];
            if (obj instanceof String) {
                llave = (String) obj;
                cod = -1;
                jsonPointer += "/" + llave;
            } else if (obj instanceof int) {
                cod = (int) obj;
                llave = null;
                jsonPointer += "/" + String.valueOf(cod);
            } else {
                return null;
            }
        }
        return jsonPointer;
    }

    /**
     * Reconstruye recursivamente el árbol JSON desde la raíz, preservando elementos
     * no modificados después de una alteración profunda.
     * 
     * @param keysToUb        Ruta a la ubicación donde se realizó la modificación.
     * @param modifiedElement El elemento (JSONObject o JSONArray) que fue
     *                        modificado.
     * @param originalObj     JSONObject original para consultar partes no
     *                        modificadas, o {@code null} si la raíz es JSONArray.
     * @param originalArray   JSONArray original para consultar partes no
     *                        modificadas, o {@code null} si la raíz es JSONObject.
     * @return El árbol JSON reconstruido desde la raíz, o {@code null} si hay
     *         error.
     */
    private static Object recreacionJsonObjectOArray(Object[] keysToUb, Object modifiedElement, Object original) {
        JSONObject recreObj = null;
        JSONArray recreArray = null;
        JSONObject originalObj = null;
        JSONArray originalArray = null;

        if (modifiedElement instanceof JSONObject) {
            recreObj = (JSONObject) modifiedElement;
        } else if (modifiedElement instanceof JSONArray) {
            recreArray = (JSONArray) modifiedElement;
        } else {
            return null;
        }

        if (original instanceof JSONObject) {
            originalObj = (JSONObject) original;
        } else if (original instanceof JSONArray) {
            originalArray = (JSONArray) original;
        } else {
            return null;
        }

        String llaveActual = null;
        int codActual = -1;
        String jsonPointer;
        Object objAux;

        // For de recreación: reconstruir desde el penúltimo elemento hasta la raíz
        for (int i = keysToUb.length - 2; i >= 0; i--) {
            // Extraer key/cod del nivel actual (i+1)
            Object keyObj = keysToUb[i + 1];
            String llave = null;
            int cod = -1;
            if (keyObj instanceof String) {
                llave = (String) keyObj;
            } else if (keyObj instanceof int) {
                cod = (int) keyObj;
            }

            // --------------Recuperar la información no sustituida-----------------
            JSONObject joAux = null;
            JSONArray jaAux = null;

            jsonPointer = getJsonPointer(Arrays.copyOf(keysToUb, i + 1));
            jsonPointer = jsonPointer.substring(0, jsonPointer.lastIndexOf("/"));

            // Consultar desde el original apropiado
            if (originalObj != null) {
                if (jsonPointer.isBlank()) {
                    objAux = originalObj;
                } else {
                    objAux = originalObj.query(jsonPointer);
                }
            } else {
                if (jsonPointer.isBlank()) {
                    objAux = originalArray;
                } else {
                    objAux = originalArray.query(jsonPointer);
                }
            }

            if (objAux instanceof JSONObject) {
                joAux = (JSONObject) objAux;
            } else if (objAux instanceof JSONArray) {
                jaAux = (JSONArray) objAux;
            } else {
                return null;
            }
            // --------------Reemplazar la sustituida-------------
            if (joAux != null) {
                if (recreObj != null) {
                    if (llave != null) {
                        joAux.put(llave, recreObj);
                    } else {
                        joAux.put(String.valueOf(cod), recreObj);
                    }
                } else {
                    if (llave != null) {
                        joAux.put(llave, recreArray);
                    } else {
                        joAux.put(String.valueOf(cod), recreArray);
                    }
                }
            } else {
                if (recreObj != null) {
                    if (llave != null) {
                        try {
                            jaAux.put(Integer.valueOf(llave), recreObj);
                        } catch (NumberFormatException e) {
                            return null;
                        }
                    } else {
                        jaAux.put(cod, recreObj);
                    }
                } else {
                    if (llave != null) {
                        try {
                            jaAux.put(Integer.valueOf(llave), recreArray);
                        } catch (NumberFormatException e) {
                            return null;
                        }
                    } else {
                        jaAux.put(cod, recreArray);
                    }
                }
            }

            // ---------------Agregar la key correspondiente para la siguiente
            // iteración----------------
            objAux = keysToUb[i];
            llaveActual = null;
            codActual = -1;
            if (objAux instanceof String) {
                llaveActual = (String) objAux;
            } else if (objAux instanceof int) {
                codActual = (int) objAux;
            }

            if (joAux != null) {
                recreObj = new JSONObject();
                if (llaveActual != null) {
                    recreObj.put(llaveActual, joAux);
                } else {
                    recreObj.put(String.valueOf(codActual), joAux);
                }
            } else if (i >= 1) {
                recreArray = new JSONArray();
                if (llaveActual != null) {
                    recreArray.put(Integer.valueOf(llaveActual), jaAux);
                } else {
                    recreArray.put(codActual, jaAux);
                }
            }
        }

        // Construcción final desde la raíz
        objAux = keysToUb[0];
        String llaveRaiz = null;
        int codRaiz = -1;
        if (objAux instanceof String) {
            llaveRaiz = (String) objAux;
        } else if (objAux instanceof int) {
            codRaiz = (int) objAux;
        }

        if (originalObj != null) {
            // Es JSONObject
            JSONObject root = new JSONObject();
            if (recreArray != null) {
                if (llaveRaiz != null) {
                    root.put(llaveRaiz, recreArray);
                } else {
                    root.put(String.valueOf(codRaiz), recreArray);
                }
            } else {
                if (llaveRaiz != null) {
                    root.put(llaveRaiz, recreObj);
                } else {
                    root.put(String.valueOf(codRaiz), recreObj);
                }
            }
            return root;
        } else {
            // Es JSONArray
            JSONArray root = new JSONArray();
            if (recreObj != null) {
                if (llaveRaiz != null) {
                    root.put(Integer.valueOf(llaveRaiz), recreObj);
                } else {
                    root.put(codRaiz, recreObj);
                }
            } else {
                if (llaveRaiz != null) {
                    root.put(Integer.valueOf(llaveRaiz), recreArray);
                } else {
                    root.put(codRaiz, recreArray);
                }
            }
            return root;
        }
    }

    /**
     * Guarda {@code object} en la ubicacion indicada sustituyendo el elemento ya
     * ubicado en esa key
     * 
     * @param ruta     ruta del archivo.json
     * @param keysToUb seríe de keys y/o números para ubicar {@code object} dentro
     *                 del Json en la ubicación que el elemento a sustituir.
     * @param object   Objeto/s a insertar en el Json. Si es más de uno todos se
     *                 guardaran como un {@code JSONarray} en la misma ubicación.
     * @return {@code true} si se ejecutó con exito o {@code false} si no existía el
     *         objeto a sustituír o si hubo un error y no se modificó el Json
     */
    public static boolean overrideJson(String ruta, Object[] keysToUb, Object... object) {
        if (object == null || object.length <= 0 || ruta == null || ruta.isBlank() || keysToUb == null
                || keysToUb.length == 0) {
            return false;// Nada donde guardar o que escribir
        }

        JSONObject infObj = null;
        JSONArray infoArray = null;

        // Validación de contenido
        Object oldInfo = rutaJsonToObjectJson(ruta);
        if (oldInfo instanceof JSONArray) {
            infoArray = (JSONArray) oldInfo;
        } else if (oldInfo instanceof JSONObject) {
            infObj = (JSONObject) oldInfo;
        } else {
            return false;// La estructura del Json original está mal no existe el archivo o está vacío
        }

        String jsonPointer = getJsonPointer(keysToUb);
        Object objAux = keysToUb[keysToUb.length - 1];
        String key = null;
        int cod = -1;
        // Validar keysToUb (String jsonPointer)
        if (jsonPointer != null) {
            if (objAux instanceof String) {
                key = (String) objAux;
                try {
                    Integer.valueOf(key);
                } catch (Exception e) {
                    // Ignorar, se maneja adelante en la validación
                }
            } else if (objAux instanceof int) {
                cod = (int) objAux;
            } // No puede ser otra cosa por la verificación en la función "getJsonPointer"

            try {
                if (infObj != null) {
                    objAux = infObj.query(jsonPointer.substring(0, jsonPointer.lastIndexOf("/")));
                    if (objAux instanceof JSONObject) {
                        if (!((JSONObject) objAux).has(key)) {
                            return false;
                        }
                    } else {
                        if (((JSONArray) objAux).opt(cod) == null) {
                            return false;
                        }
                    }
                } else {
                    objAux = infoArray.query(jsonPointer.substring(0, jsonPointer.lastIndexOf("/")));
                    if (objAux instanceof JSONObject) {
                        if (!((JSONObject) objAux).has(key)) {
                            return false;
                        }
                    } else {
                        if (((JSONArray) objAux).opt(cod) == null) {
                            return false;
                        }
                    }
                }
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }

        // -------------Inserción de object--------------
        Object resultadoRecreacion;

        // objAux será un JSONObject o JSONArray debido a la validación de jsonPointer
        if (objAux instanceof JSONObject) {
            infObj = (JSONObject) objAux;
            if (infObj.has(key)) {
                return false;
            }
            if (object.length == 1) {
                infObj.put(key, object[0]);
            } else {
                JSONArray valores = new JSONArray();
                for (Object obj : object) {
                    valores.put(obj);
                }
                infObj.put(key, valores);
            }
            objAux = infObj;
        } else {
            infoArray = (JSONArray) objAux;
            if (infoArray.opt(cod) != null) {
                return false;
            }
            if (object.length == 1) {
                infoArray.put(cod, object[0]);
            } else {
                JSONArray valores = new JSONArray();
                for (Object obj : object) {
                    valores.put(obj);
                }
                infoArray.put(cod, valores);
            }
            objAux = infoArray;
        }

        // -----Recrear información-----
        resultadoRecreacion = recreacionJsonObjectOArray(Arrays.copyOf(keysToUb, keysToUb.length - 1), objAux, oldInfo);
        if (resultadoRecreacion instanceof JSONObject) {
            infObj = (JSONObject) resultadoRecreacion;
        } else if (resultadoRecreacion instanceof JSONArray) {
            infoArray = (JSONArray) resultadoRecreacion;
        } else {
            return false;
        }
        try {
            borrarFicheroYCrearloVacio(ruta);
            FileWriter writer = new FileWriter(ruta, false);
            writer.write(infObj != null ? infObj.toString() : infoArray.toString());
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
    }

    /**
     * Pregunta por teclado {@code opcion1 } o {@code opcion2 } hasta obtener una
     * respuesta válida (si se presiona "Enter" selecciona {@code opcion1})
     * 
     * @param opcion1 : El valor {@code String} introducido y es la respuesta por
     *                defecto.
     * @param opcion2 : El valor {@code String} introducido.
     * @return {@code true} if
     *         {@code respuesta.equals(opcion1) || respuesta.equals("-1")},
     *         {@code false} if {@code respuesta.equeals(opcion2)}.
     */
    public static boolean escogerOpcion(String opcion1, String opcion2) {
        if (opcion1 == null || opcion2 == null) {
            throw new NullPointerException("Debe asignarse el valor de ambas opciones");
        }
        opcion1 = opcion1.toUpperCase();
        opcion2 = opcion2.length() > 1 ? Character.toUpperCase(opcion2.charAt(0)) + opcion2.substring(1).toLowerCase()
                : opcion2.toLowerCase();

        String respuesta;
        boolean s = false;
        respuesta = Util.pedirPorTeclado(false);
        while (!(respuesta == null) && !respuesta.equalsIgnoreCase(opcion1) && !respuesta.equalsIgnoreCase(opcion2)) {
            System.out.print("Responde unicamente con \"" + opcion1 + "\" o \"" + opcion2 + "\" (\"Enter\" para "
                    + opcion1 + "): ");
            respuesta = Util.pedirPorTeclado(false);
        }
        if (respuesta == null || respuesta.equalsIgnoreCase(opcion1)) {
            s = true;
        }
        return s;
    }

    /**
     * Lee el contenido de un fichero de texto a un Json mediante una URL y lo carga
     * a una {@code String}.
     * 
     * @param url : Enlace al Archivo.json
     * @return {@code String} con el contenido del Archivo.json
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

    public static boolean alternarBoolean(boolean b) {
        return !b;
    }

    public static void main(String[] args) {
        // String personajes = "";
        Personaje[] personajesCreados = new Personaje[3];
        Personaje prueba = new Personaje("prueba");
        Personaje prueba1 = new Personaje("prueba1");
        Personaje prueba2 = new Personaje("prueba2");
        personajesCreados[0] = prueba;
        personajesCreados[1] = prueba1;
        personajesCreados[2] = prueba2;/*
                                        * for (Personaje personaje : personajesCreados) {
                                        * if (!(personaje == null)) {
                                        * personajes += personaje.toJsonString()+",\n";
                                        * }
                                        * }
                                        * if (!personajes.equals("")) {
                                        * personajes = personajes.substring(0, personajes.lastIndexOf(","));
                                        * writeStringToJson(personajes, "src\\UD4\\rol\\PersonajesGuardados.json",
                                        * true);
                                        * }
                                        */
        /*
         * JSONObject pers = new JSONObject();
         * pers.accumulate("Personajes", prueba.toJsonObject());
         * pers.accumulate("Personajes", prueba1.toJsonObject());
         * personajes = pers.get("Personajes").toString();
         * System.out.println(personajes);
         * JSONArray intento = new JSONArray(personajes);
         * System.out.println(intento.toString());
         */
        String rutaFichero = "src\\UD4\\Rol\\PersonajesGuardados copy.json";
        JSONObject[] personajes = new JSONObject[0];
        JSONObject pers = new JSONObject();
        for (Personaje personaje : personajesCreados) {
            if (!(personaje == null)) {
                try {
                    pers = personaje.toJsonObject();
                } catch (Exception e) {
                    personaje.setId(0);
                    pers = personaje.toJsonObject();
                }
                personajes = Arrays.copyOf(personajes, personajes.length + 1);
                personajes[personajes.length - 1] = pers;
            }
        }
        if (personajes.length != 0) {
            Util.writeToJson(rutaFichero, true, "Personajes", personajes);
        } else {
            System.out.println("No había personajes que guardar");
        }
    }
}
