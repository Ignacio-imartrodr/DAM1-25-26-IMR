package UD5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author Ignacio MR
 * Un centro educativo necesita distribuir de forma aleatoria a los alumnos de un 
 * curso entre los grupos disponibles para ese curso.  Diseña la función que devuelve
 * una lista de listas, cada una de las cuales corresponde a un grupo. Cada nombre de
 * la lista de alumnos se asigna a uno de los grupos. 
 */
public class EP1226_CentroEducativo {
    public List<List<String>> repartoAlumnos(List<String> lista, int numGrupos){
        if (lista == null || numGrupos < 1) {
            return null;
        }
        Iterator<String> it = lista.iterator();
        Random rng = new Random();
        int grupoRnd;
        List<List<String>> grupos = new ArrayList<>(numGrupos);
        for (int i = 0; i < numGrupos; i++) {
            grupos.add(new ArrayList<>());
        }
        while (it.hasNext()) {
            String nombre = it.next();
            grupoRnd = rng.nextInt(0, numGrupos);
            grupos.get(grupoRnd).add(nombre);
        }
        return grupos;
    }
    /** 
     * Lee y carga el contenido de un fichero de texto a un array de String (un
     * elemento por línea y cada atributo separado por ",")
     * 
     * @param filePath Es la ruta del fichero
     * @return Array de String con el contenido del fichero
     * @author Profesor Óscar
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
    public static void main(String[] args) {
        EP1226_CentroEducativo c = new EP1226_CentroEducativo();
        List<String> alumnos = Arrays.asList(EP1226_CentroEducativo.readFileToStringArray("src\\UD5\\DATOS - Alumnado DAM1.txt"));
        System.out.println(c.repartoAlumnos(alumnos, 3));
    }
}
