package UD6.Examen;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignacio Martínez Rodriguez
 * 
 */
public class MaximasPuntuaciones {
    public static final String RUTA = "src\\ud6\\examen\\imartrodr\\Puntuaciones.dat";

    private static List<Puntuacion> leerBinPunt(String rutaObjetivo) {
        List<Puntuacion> listaPuntos = new ArrayList<>();
        
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(rutaObjetivo))) {
            while (true) {
                Puntuacion puntos = (Puntuacion) in.readObject();
                listaPuntos.add(puntos);
            }
        } catch (EOFException e) {
            // Fin del fichero
            return listaPuntos;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Hubo un error leyendo el archivo: " + e);
            return null;
        }
    }

    private static boolean writeInPuntbin(String rutaObjetivo, Puntuacion o) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(rutaObjetivo))) {
            out.writeObject(o);
            return true;
        } catch (IOException e) {
            System.out.println("Hubo un error escribiendo en el archivo: " + e);
            return false;
        }
    }

    public static void mostrarPuntuaciones(String rutaPuntiaciones){
        List<Puntuacion> puntuaciones = leerBinPunt(rutaPuntiaciones);
        puntuaciones.sort((p1, p2) -> p1.compareTo(p2));

        for (Puntuacion puntuacion : puntuaciones) {
            System.out.println(puntuacion.toString());
        }
    }

    public static void addPuntuacionToFile(Puntuacion puntuacion, String ruta){
        List<Puntuacion> puntuaciones = leerBinPunt(RUTA);
        puntuaciones.add(puntuacion);
        for (Puntuacion p : puntuaciones) {
            writeInPuntbin(ruta, p);
        }
    }
    
}
