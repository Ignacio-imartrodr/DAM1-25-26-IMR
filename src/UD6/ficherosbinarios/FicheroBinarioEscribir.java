package UD6.ficherosbinarios;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;

import UD6.Examen.Corregido.Puntuacion;

public class FicheroBinarioEscribir {
    public static void main(String[] args) {
        try {
            FileOutputStream archivo = new FileOutputStream("datos.dat");
            ObjectOutputStream out = new ObjectOutputStream(archivo);

            out.writeInt(33);
            out.writeDouble(8.7);
            out.writeBoolean(true);

            Puntuacion p = new Puntuacion("a", 23, LocalDateTime.now());
            out.writeObject(p);

            out.close();

        } catch (FileNotFoundException e) {
            System.out.println("No se encuentra el fichero");
        } catch (IOException e) {
            System.out.println("Error Entrada/Salida");
            e.printStackTrace();
        }
    }
}
