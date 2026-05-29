package UD6.Recu.Entregado;

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
    static String rutaNotas = "src\\UD6\\Recu\\Entregado\\notas.txt";
    static String rutaPromedios = "src\\UD6\\Recu\\Entregado\\notas.txt";

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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaObjetivo))) {
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
            listProm = new ArrayList<>();
            for (int i = 0; i < listNotas.size(); i++) {
                String notas = listNotas.get(i);
                String[] partes = notas.split(",");
                String nombre = partes[0];
                if (partes.length != 4) {
                    System.out.println("Formato de las notas erroneo en las notas de " + nombre + "(A sido omitido)");
                } else {
                    Integer nota1;
                    Integer nota2;
                    Integer nota3;
                    try {
                        nota1 = Integer.valueOf(partes[1]);
                        nota2 = Integer.valueOf(partes[2]);
                        nota3 = Integer.valueOf(partes[3]);

                        Double prom = (nota1 + nota2 + nota3)/3.0;
                        listProm.add(String.format("%s: %.2f",nombre, prom));
                    } catch (Exception e) {
                        System.out.println("Formato de las notas de " + nombre + " erroneo (Ha sido omitido)");
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
        if (listProm == null) {
            return false;
        }
        String todosProm = "";
        for (String prom : listProm) {
            todosProm += prom + "\n";
        }
        if (todosProm.length() == 0) {
            System.err.println("Sin promedios que guardar");
            return false;
        } else {
            todosProm = todosProm.substring(0, todosProm.length() - 2); //TODO
        }
        return writeInTxt(rutaPromedios, todosProm);
    }
    public static void main(String[] args) {
        writePromedios();
    }
}
