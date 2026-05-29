package UD6.Recu.Corregido;

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
public class AppNotas {
    static String rutaNotas = "src\\UD6\\Recu\\Corregido\\notas.txt";
    static String rutaPromedios = "src\\UD6\\Recu\\Corregido\\promedios.txt";

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
        List<String> listNotas = null;
        List<String> listProm = null;
        try {
            listNotas = leerTxt(rutaNotas);
            if (listNotas == null || listNotas.isEmpty()) {
                System.err.println("Sin Notas con las que calcular");
                return null;
            }
            listProm = new ArrayList<>();
            for (int i = 0; i < listNotas.size(); i++) {
                String notas = listNotas.get(i);
                String[] partes = notas.split(",");
                String nombre = partes[0];
                if (partes.length <= 1) {
                    System.out.println("Formato de las notas de " + nombre + " erroneo (Ha sido omitido)");
                } else {
                    boolean errorNota = false;
                    Double prom = 0.;
                    for (int j = 1; j < partes.length; j++) {
                        Integer nota;
                        try {
                            nota = Integer.valueOf(partes[j].strip());
                            prom += nota;
                        } catch (Exception e) {
                            System.out.println("Formato de las notas de " + nombre + " erroneo (Ha sido omitido)");
                            errorNota = true;
                            break;
                        }
                    }
                    if (!errorNota) {
                        prom /= partes.length - 1;
                        listProm.add(String.format("%s: %.2f",nombre, prom));
                    }
                }
            }
            if (listProm.isEmpty()) {
                System.err.println("El formato del archivo es erroneo");
                return null;
            }
            return listProm;
        } catch (Exception e) {
            // leerTxt ya indica la causa del error
        }
        return listNotas;
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
