package UD6.Stream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignacio Martínez Rodríguez
 */
public class AppNotasStream {
    static String rutaNotas = "src\\UD6\\Stream\\notas.txt";
    static String rutaPromedios = "src\\UD6\\Stream\\promedios.txt";

    public static List<String> leerTxt(String rutaObjetivo){
        try (BufferedReader in = new BufferedReader(new FileReader(rutaObjetivo))) {

            List<String> txt =  new ArrayList<>(in.readAllLines());
            return txt;
        } catch (FileNotFoundException e) {
            System.out.println("No se encuentra el fichero");
        } catch (IOException e) {
            System.out.println("Error Entrada/Salida");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error desconocido: " + e.getStackTrace());
        }
        return null;
    }
    public static boolean writeInTxt(String rutaObjetivo, String texto){
        java.io.File archivo = new java.io.File(rutaObjetivo);
        archivo.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            writer.write(texto);
            writer.flush();
            return true;
        } catch (Exception e) {
            System.err.println("Hubo un error escribiendo en el archivo");
            return false;
        }
    }

    public static List<String> getPromedios(String rutaNotas) {
        if (rutaNotas == null) {
            System.err.println("La ruta a las notas no puede ser null");
            return null;
        }

        List<String> listNotas = leerTxt(rutaNotas);
        if (listNotas == null || listNotas.isEmpty()) {
            System.err.println("Sin Notas con las que calcular");
            return null;
        }

        // --- INICIO DEL STREAM ---
        List<String> listProm = listNotas.stream()
            // 1. Transformamos (map) cada línea de texto en el resultado final (Funciona como un forEach pero con return para cada iteración)
            .map(linea -> {
                String[] partes = linea.split(",");
                if (partes.length <= 1) {
                    System.out.println("Formato de las notas erróneo en una línea");
                    return null; // Marcamos como inválido
                }

                String nombre = partes[0];
                double suma = 0.0;

                for (int j = 1; j < partes.length; j++) {
                    try {
                        suma += Integer.valueOf(partes[j].strip());
                    } catch (Exception e) {
                        System.out.println("Formato de las notas de " + nombre + " erróneo (Ha sido omitido)");
                        return null; // Marcamos como inválido si hay un error
                    }
                }
                
                double promedio = suma / (partes.length - 1);
                return String.format("%s: %.2f", nombre, promedio);
            })
            // 2. Filtramos y dejamos pasar SOLAMENTE los que no sean null (Es como un .removeIf con el Procedure.reversed())
            .filter(resultado -> resultado != null)
            // 3. Empaquetamos en una lista nueva
            .toList(); 
        // --- FIN DEL STREAM ---

        if (listProm.isEmpty()) {
            System.err.println("El formato del archivo es erróneo o no hay datos válidos");
            return null;
        }

        return listProm;
    }
    private static boolean writePromedios() {
        List<String> listProm = getPromedios(rutaNotas);

        if (listProm == null || listProm.isEmpty()) {
            return false;
        }

        String todosProm = String.join("\n", listProm);

        return writeInTxt(rutaPromedios, todosProm);
    }
    public static void main(String[] args) {
        writePromedios();
    }
}
