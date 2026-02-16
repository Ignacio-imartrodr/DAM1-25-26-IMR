package UD4.rol;

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

public class Util {
    /**
     * Lee y carga el contenido de un fichero de texto a un array de {@code Json} 
     * 
     * @param ruta Es la ruta del fichero
     * @return {@code JsonArray} con el contenido del fichero de la librería json.JSONArray.
     */
    public static JSONArray JsonArray(String ruta){
        Util.readFileToString(ruta);
        JSONArray jsonArray = new JSONArray(ruta);
        return jsonArray;
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
                return "-1";
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
    public static void writeStringToJson(String str, String filePath, boolean append) {
        try {
            final String START = "[\n";
            final String END = "]";
            
            if (append) {
                String[] lineas = readFileToStringArray(filePath);
                String textoFinal = "";
                lineas[lineas.length-2] += ",";
                lineas[lineas.length - 1] = str.toString();
                lineas = Arrays.copyOf(lineas, lineas.length + 1);
                lineas[lineas.length -1] = END;
                for (String linea : lineas) {
                    textoFinal += linea + "\n";
                }
                textoFinal = textoFinal.substring(0, textoFinal.length() - 1);
                if(borrarFicheroYCrearloVacio(filePath)){
                    // Creamos un objeto FileWriter que nos permitirá escribir en el fichero
                    FileWriter writer = new FileWriter(filePath, append);
                    writer.write(textoFinal);
                    // Cerramos el fichero
                    writer.close();
                }
                
            } else {
                if (borrarFicheroYCrearloVacio(filePath)) {
                    // Creamos un objeto FileWriter que nos permitirá escribir en el fichero
                    FileWriter writer = new FileWriter(filePath, append);
                    writer.write(START + str + "\n" + END);
                    // Cerramos el fichero
                    writer.close();
                }
            }

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Pregunta por teclado {@code opcion1 } o {@code opcion2 } hasta obtener una respuesta válida (si se presiona "Enter" selecciona {@code opcion1})
     * 
     * @param   opcion1    : Si es null vale "s" sino el valor {@code String} introducido y es la respuesta por defecto.
     * @param   opcion2    : Si es null vale "n" sino el valor {@code String} introducido.
     * @return {@code true} if {@code respuesta.equals(opcion1) || respuesta.equals("-1")}, {@code false} if {@code respuesta.equeals(opcion2)}.
     */
    public static boolean escogerOpcion(String opcion1, String opcion2){
        
        opcion1 = opcion1.toLowerCase();
        opcion2 = opcion2.toLowerCase();
        
        String respuesta;
        boolean s = false;
        respuesta = Util.pedirPorTeclado(false).toLowerCase();
        while (!respuesta.equalsIgnoreCase(opcion1) && !respuesta.equalsIgnoreCase(opcion2) && !respuesta.equals("-1")) {
            System.out.print("Responde unicamente con \"" + opcion1 + "\" o \"" + opcion2 +"\": ");
            respuesta = Util.pedirPorTeclado(false).toLowerCase();
        }
        if (respuesta.equalsIgnoreCase(opcion1) || respuesta.equals("-1")) {
            s = true;
        }
        return s;
    }
    /**
     * Lee y carga el contenido de un fichero de texto a un Json mediante una URL.
     * 
     * @param   url   : Enlace al Archivo.json
     * @return  {@code String} con el contenido del Archivo.json
     */
    public static String getJson(String url) throws IOException, InterruptedException {
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
        String personajes = "";
        Personaje[] personajesCreados = new Personaje[3];
        Personaje prueba = new Personaje("prueba");
        Personaje prueba1 = new Personaje("prueba1");
        personajesCreados[0] = prueba;
        personajesCreados[1] = prueba1;
        for (Personaje personaje : personajesCreados) {
            if (!(personaje == null)) {
                personajes += personaje.toJsonString()+",\n";
            }
        }
        if (!personajes.equals("")) {
            personajes = personajes.substring(0, personajes.lastIndexOf(","));
            writeStringToJson(personajes, "src\\UD4\\rol\\PersonajesGuardados.json", true);
        }
        
    }
}
