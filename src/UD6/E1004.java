package UD6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignacio MR
 * @implNote Crear con un editor el fichero de texto NumerosReales.txt en la
 *           carpeta del
 *           proyecto actual y escribir en él una serie de números reales
 *           separados por
 *           espacios
 *           simples.
 * 
 *           Implementar un programa que acceda a NumerosReales.txt, lea los
 *           números y
 *           calcule la
 *           Suma y la media aritmética, mostrando los resultados por pantalla.
 */
public class E1004 {
    final static String RUTA = "src\\UD6\\NumerosReales.txt";
    final static Double[] NUMEROS_REALES = new Double[] {1.3, 3., 234.5, 3., 69.02, 5.};

    public static boolean escribirTxtFromDouble(String ruta, Double[] numeros){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(ruta));
            String txt = "";
            for (int i = 0; i < numeros.length; i++) {
                txt += numeros[i].toString() + " ";
                if (i < numeros.length - 1) {
                    txt += " ";
                }
            }
            writer.write(txt);
            writer.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public static List<String> leerArchivoTxt(String ruta){
        List<String> res = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(ruta));
            List<String> lineas = new ArrayList<>();
            lineas = new ArrayList<>(in.readAllLines());
            in.close();
            for (String string : lineas) {
                String[] nums = string.split(" ");
                for (String num : nums) {
                    res.add(num);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return res;
    }
    public static void mostrarResArchivoNums(String ruta){
        List<String> nums = leerArchivoTxt(ruta);
        List<Double> doubles = new ArrayList<>();
        for (String d : nums) {
            Double num;
            try {
                num = Double.parseDouble(d);
                doubles.add(num);
            } catch (Exception e) {
                System.err.println("El archivo solo debe contener números reales para ejecutar esta función");
                return;
            }
        }
        
        Double sumatoria = 0.;
        for (Double d : doubles) {
            sumatoria += d;
        }

        Double media = sumatoria/doubles.size();
        System.out.printf("La suma total es: %.2f%nLa media es: %.2f", sumatoria, media);
    }
    public static void main(String[] args) {
        escribirTxtFromDouble(RUTA, NUMEROS_REALES);
        mostrarResArchivoNums(RUTA);
    }
}
