package UD6.ficherosbinarios;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import UD6.Examen.Corregido.Puntuacion;

public class FicheroBinarioLeer {
    public static void main(String[] args) {
    try {
        FileInputStream archivo = new FileInputStream("datos.dat");
        ObjectInputStream in = new ObjectInputStream(archivo);

        int entero = in.readInt(); // 33
        double real = in.readDouble(); // 8.7
        boolean bool = in.readBoolean(); // true

        Puntuacion p = (Puntuacion) in.readObject(); 

        System.out.println("Posición del Jugador: " + p);

        in.close();

        System.out.println(entero + " - " + real + " - " + bool);

    } catch (FileNotFoundException e) {
        System.out.println("No se encuentra el fichero");
    } catch (IOException e) {
        System.out.println("Error Entrada/Salida");
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        System.out.println("Problema con la clase partida");
    }
    }
}
