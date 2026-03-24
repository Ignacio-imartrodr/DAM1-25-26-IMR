package UD4.Rol.Boundary;

import java.util.Arrays;
import UD4.Rol.Control.Combate;
import UD4.Rol.Control.Creacion;
import UD4.Rol.Control.Guardado;
import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Utilidades.Util;

/**
 * @author Ignacio MR
 * 
 * Crea un programa de consola que permita al usuario generar
 * y editar personajes de diferentes modos y guardarlos
 * en disco en un fichero de texto en formato JSON.
 */

public class AppCombateSingular {

    private static Personaje[] getPersonajes(){//TODO implementar "BaseGeneral.json"
        Personaje[] personajesCreados = new Personaje[0];
        Personaje[] temp;
        while (personajesCreados.length < 2) {
            System.out.print("¿Quieres cargar los personajes de un archivo? (S/n): ");
            if (Util.escogerOpcion("S", "n")) {
                String rutaFichero;
                rutaFichero = Util.pedirRuta();
                if (!(rutaFichero == null)) {
                    temp = Combate.cargarPersonajesDeArchivo(rutaFichero);
                    for (Personaje personaje : temp) {
                        personajesCreados = Arrays.copyOf(personajesCreados, personajesCreados.length + 1);
                        personajesCreados[personajesCreados.length - 1] = personaje;
                    }
                }
            }
            temp = Creacion.pedirPersonajes();
            for (Personaje personaje : temp) {
                personajesCreados = Arrays.copyOf(personajesCreados, personajesCreados.length + 1);
                personajesCreados[personajesCreados.length - 1] = personaje;
            }
            if (personajesCreados.length < 2) {
                System.out.println("Se necesitan al menos 2 personajes para la batalla.");
            }
        }
        return personajesCreados;
    }
    public static void main(String[] args) {
        int id = 0;
        Personaje[] personajesCreados;
        personajesCreados = getPersonajes();
        for (Personaje personaje : personajesCreados) {
            personaje.setId(id);
            personajesCreados[id] = personaje;
            id++;
        }
        Guardado.GuardadoPersonajes(personajesCreados);

        Personaje[] personajesEnBatalla = new Personaje[2];
        if (personajesCreados.length > 2) {
            personajesEnBatalla = Creacion.seleccionarPersonajes(personajesCreados, 2);
            while (personajesEnBatalla[0] == null || personajesEnBatalla[1] == null) {
                System.out.println("No hay suficientes personajes para la batalla, selecciona al menos 2 personajes.");
                personajesEnBatalla = Creacion.seleccionarPersonajes(personajesCreados, 2);
            }
        } else {
            personajesEnBatalla[0] = personajesCreados[0];
            personajesEnBatalla[1] = personajesCreados[1];
        }
        
        personajesCreados = Combate.combateSingular(personajesEnBatalla, personajesCreados, 2);//TODO traer aquí

        Guardado.GuardadoPersonajes(personajesCreados);
    }
}
