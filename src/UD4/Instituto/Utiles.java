package UD4.Instituto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Utiles {
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
    public static String pedirPorTeclado(boolean pideNumero){
        String var;
        boolean sonNumeros = true;
        try {
            var = sc.nextLine();
            for (int i = 0; i < var.length() && sonNumeros; i++) {
                if (!Character.isDigit(var.charAt(i)) || var.charAt(i) != ',') {
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
    public static boolean verificaObjetoEnArray(Object objetivo, Object[] array){ //Verificar como lo compara
        boolean verificado = false;
        for (Object object : array) {
            if (object.equals(objetivo)) {
                verificado = true;
            }
        }
        return verificado;
    }
}
