package UD4.Rol.Main;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import UD4.Rol.Utilidades.*;
import UD4.Rol.Objetos.Personaje;

/** 
 * @author Ignacio MR
 * 
 * Crea un programa de consola que permita al usuario generar 
 * y editar personajes de diferentes modos y guardarlos
 * en disco en un fichero de texto en formato JSON. 
 */

public class AppCreaPersonaje {
    public static void mostrarPersonajes(Personaje[] personajesCreados) {
        System.out.println("\nPersonajes disponibles:\n");
        for (Personaje personaje : personajesCreados) {
            personaje.mostrar();
            System.out.println("________________________");
            System.out.println();
        }
    }
    public static Personaje[] getPersonajesJson(String ruta){
        JSONArray personajes = Util.JsonArray(ruta);
        Personaje[] personajesJson = new Personaje[0];
        for (int i = 0; i < personajes.length(); i++) {
            if (personajes.getJSONObject(i) != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject = personajes.getJSONObject(i);
                Personaje personaje = new Personaje(jsonObject.getString("nombre"), jsonObject.getString("raza"), jsonObject.getString("fuerza"), jsonObject.getString("agilidad"), jsonObject.getString("constitucion"), jsonObject.getString("nivel"), jsonObject.getString("experiencia"), true);
                personajesJson = Arrays.copyOf(personajesJson, personajesJson.length + 1);
                personajesJson[personajesJson.length - 1] = personaje;
            }
        }
        return personajesJson;
    }
    public static Personaje[] getPersonajesJsonUrl(String ruta){
        Personaje[] personajesJson = new Personaje[0];
        String web = "";
        JSONArray personajes = new JSONArray();
        try {
            web = Util.getJson(ruta);
        } catch (Exception e) {
            throw new PersonajeException("Error en la URL");
        }
        try {
            JSONObject ArchivoUrl = new JSONObject(web);
            personajes = Util.JsonArray(ArchivoUrl.getString("Personajes"));
        } catch (Exception e) {
            try {
                personajes = Util.JsonArray(web);
            } catch (Exception b) {
                throw new PersonajeException("Error en el formato de la web");
            }
        }
        
        for (int i = 0; i < personajes.length(); i++) {
            if (personajes.getJSONObject(i) != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject = personajes.getJSONObject(i);
                Personaje personaje = new Personaje(jsonObject.getString("nombre"), jsonObject.getString("raza"), jsonObject.getString("fuerza"), jsonObject.getString("agilidad"), jsonObject.getString("constitucion"), jsonObject.getString("nivel"), jsonObject.getString("experiencia"), true);
                personajesJson = Arrays.copyOf(personajesJson, personajesJson.length + 1);
                personajesJson[personajesJson.length - 1] = personaje;
            }
        }
        return personajesJson;
    }
    public static Personaje[] getPersonajesCsv(String ruta){
        String[] arrayPersonajes = Util.readFileToStringArray(ruta);
        Personaje[] personajesExtraidos = new Personaje[0];
        try{
            personajesExtraidos = new Personaje[arrayPersonajes.length];
            String[] atributos;
            int i = 0;
            int idPers = 1;
            for (String personaje : arrayPersonajes) {
                atributos = new String[7];
                atributos = personaje.split(",");
                try {
                    Personaje newPersonaje = new Personaje(atributos[0], atributos[1], atributos[2], atributos[3], atributos[4], atributos[5], atributos[6], true);
                    personajesExtraidos[i]= newPersonaje;
                    i++;
                } catch (Exception e) {
                    System.out.println("El personaje número " + idPers + "contiene un error.");
                }
                idPers++;
            }
        } catch (Exception x) {}
        return personajesExtraidos;
    }
    
    public static Personaje[] pedirPersonajes() {
        Personaje[] personajesNuevos = new Personaje[0];
        boolean seguir = true;
        while (seguir) {
            System.out.print("¿Quieres crear un nuevo personaje? (S/n): ");
            if (Util.escogerOpcion("s", "n")) {
                Personaje personaje = new Personaje();
                System.out.println("\nRazas disponibles:\n\n" + Personaje.getRazasStats());
                personaje.crearPersonaje();
                personaje.mostrar();
                System.out.println("¿Es el personaje correcto? (S/n):");
                if (Util.escogerOpcion("s", "n")) {
                    personajesNuevos = Arrays.copyOf(personajesNuevos, personajesNuevos.length + 1);
                    personajesNuevos[personajesNuevos.length - 1] = personaje;
                }
            } else {
                System.out.println();
                seguir = false;
            }
        }
        return personajesNuevos;
    }
    public static void modificarPersonagesArray(Personaje[] personajesArray){
        mostrarPersonajes(personajesArray);
        boolean esSiguiente = true;
        boolean salir = false;
        for (int i = -1, skip = -1; !salir;) {
            System.out.println("¿Quieres modificar algún personaje? (S/n)");
            if (Util.escogerOpcion("S", "n")) {
                if (esSiguiente) {
                    if (i == personajesArray.length -1) {
                        i = 0;
                    } else {
                        i++;
                    }
                } else {
                    if (i == 0) {
                        i = personajesArray.length -1;
                    } else {
                        i--;
                    }
                }
                if (i != skip){
                    boolean modificar;
                    do {
                        personajesArray[i].mostrar();
                        System.out.print("¿Quieres modificar este personaje? (S/n): ");
                        if (Util.escogerOpcion("S", "n")) {
                            modificar  = true;
                            String nombre = personajesArray[i].getNombre();
                            String raza = String.valueOf(personajesArray[i].getRaza());
                            String fuerza = String.valueOf(personajesArray[i].getFuerza());
                            String agilidad = String.valueOf(personajesArray[i].getAgilidad());
                            String constitucion = String.valueOf(personajesArray[i].getConstitucion());
                            String nivel = String.valueOf(personajesArray[i].getNivel());
                            String experiencia = String.valueOf(personajesArray[i].getExperiencia());
                            System.out.print("¿Que valor quieres modificar?");
                            System.out.println("Nombre, raza, fuerza, agilidad, constitucion, nivel o experiencia");
                            String valor = Util.pedirPorTeclado(false);
                            if (!(valor == null)) {
                                try {
                                    valor = valor.toUpperCase();
                                    switch (valor) {
                                        case "NOMBRE":
                                            valor = Util.pedirPorTeclado(false);
                                            personajesArray[i] = new Personaje(valor, raza, fuerza, agilidad, constitucion, nivel, experiencia, true);
                                            break;
                                        case "RAZA":
                                            valor = Util.pedirPorTeclado(false);
                                            personajesArray[i] = new Personaje(nombre, valor, fuerza, agilidad, constitucion, nivel, experiencia, true);
                                            break;
                                        case "FUERZA":
                                            valor = Util.pedirPorTeclado(true);
                                            personajesArray[i] = new Personaje(nombre, raza, valor, agilidad, constitucion, nivel, experiencia, true);
                                            break;
                                        case "AGILIDAD":
                                            valor = Util.pedirPorTeclado(true);
                                            personajesArray[i] = new Personaje(nombre, raza, fuerza, valor, constitucion, nivel, experiencia, true);
                                            break;
                                        case "CONSTITUCION":
                                            valor = Util.pedirPorTeclado(true);
                                            personajesArray[i] = new Personaje(nombre, raza, fuerza, agilidad, valor, nivel, experiencia, true);
                                            break;
                                        case "NIVEL":
                                            valor = Util.pedirPorTeclado(true);
                                            personajesArray[i] = new Personaje(nombre, raza, fuerza, agilidad, constitucion, valor, experiencia, true);
                                            break;
                                        case "EXPERIENCIA":
                                            valor = Util.pedirPorTeclado(true);
                                            personajesArray[i] = new Personaje(nombre, raza, fuerza, agilidad, constitucion, nivel, valor, true);
                                            break;
                                        default:
                                            throw new PersonajeException();
                                    }
                                } catch (Exception e) {
                                    System.out.println("Valor no válido.");
                                }
                            } else {
                                System.out.println("Introduce un valor válido.");
                            }
                        } else {
                            modificar = false;
                        }
                    } while (modificar);
                    skip = i;
                    System.out.println("Siguiente personaje o Anterior? (S/a): ");
                    esSiguiente = Util.escogerOpcion("S", "a");
                }
            } else {
                salir = true;
            } 
        }
    }
    public static void main(String[] args) {
        int id = 0;
        Personaje[] personajesNuevos = new Personaje[0];
        Personaje[] temp;
        System.out.print("¿Quieres cargar los personajes de un archivo? (S/n): ");
        if (Util.escogerOpcion("S", "n")) {
            String rutaFichero;
            rutaFichero = AppCombateSingular.pedirRuta();
            if (!(rutaFichero == null)) {
                temp = AppCombateSingular.cargarPersonajesDeArchivo(rutaFichero);
                for (Personaje personaje : temp) {
                    personaje.setId(id); 
                    personajesNuevos = Arrays.copyOf(personajesNuevos, personajesNuevos.length + 1);
                    personajesNuevos[personajesNuevos.length - 1] = personaje;
                    id++;
                }
            }
        }
        temp = pedirPersonajes();
        for (Personaje personaje : temp) {
            personaje.setId(id); 
            personajesNuevos = Arrays.copyOf(personajesNuevos, personajesNuevos.length + 1);
            personajesNuevos[personajesNuevos.length - 1] = personaje;
            id++;
        }
        modificarPersonagesArray(personajesNuevos);
    }
}
