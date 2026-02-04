package UD4.rol;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Scanner;

public class Util {
    /**
     * Lee y carga el contenido de un fichero de texto a un array de String (un
     * elemento por línea y cada atributo separado por ",")
     * 
     * @param filePath Es la ruta del fichero
     * @return Array de String con el contenido del fichero
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
     * Lee y carga el contenido de un fichero de texto a un String
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
     * @return
     */
    public static String pedirPorTeclado(boolean pideNumero){
        String var;
        boolean sonNumeros = true;
        try {
            var = sc.nextLine().strip();
            if (var.equals("")|| var.isBlank() || var.isEmpty()) {
                return "-1";
            }
            for (int i = 0; i < var.length() && sonNumeros; i++) {
                if (!Character.isDigit(var.charAt(i)) || var.charAt(i) != ',' || var.charAt(i) != '-' || var.charAt(i) != '+') {
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
     * Verifica si un objeto se encuentra en un array de ese objeto.
     * @param objetivo Objeto a buscar.
     * @param array Array donde buscar el objeto.
     * @return  {@code true} si {@code objetivo} está en el array {@code array} o {@code false} si {@code objetivo} NO está en el array {@code array}
     */
    public static boolean verificaObjetoEnArray(Object objetivo, Object[] array){ //Verificar como lo compara
        boolean verificado = false;
        for (Object object : array) {
            if (object.equals(objetivo)) {
                verificado = true;
            }
        }
        return verificado;
    }
    
    /**
     * Escribe una cadena de texto {@code str} en un archivo designado
     * @param   str   : Texto que se añadirá al fichero.
     * @param   filePath  : Ruta de la ubicación del archivo.
     */
    public static void writeStringToFile(String str, String filePath) {//TODO que no sobre-escriba
        try {
            // Creamos un objeto FileWriter que nos permitirá escribir en el fichero
            FileWriter writer = new FileWriter(filePath);

            // Escribimos el String en el fichero
            writer.write(str);

            // Cerramos el fichero
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pregunta por teclado "s" o "n" hasta que obtener una respuesta válida
     * 
     * @return {@code true} if "s", {@code false} if "n".
     */
    public static boolean confirmarSN(){
        String respuesta;
        boolean s = false;
        respuesta = Util.pedirPorTeclado(false);
        while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
            System.out.println("Responde unicamente con \"s\" o \"n\"");
            respuesta = Util.pedirPorTeclado(false);
        }
        if (respuesta.equalsIgnoreCase("s")) {
            s = true;
        }
        return s;
    }
    private static String getJson(String url) throws IOException, InterruptedException {
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
}
