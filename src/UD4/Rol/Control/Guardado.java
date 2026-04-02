package UD4.Rol.Control;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Utilidades.Util;

public class Guardado {
    public static final String RUTA_BASE_GENERAL = "src\\UD4\\Rol\\Control\\BaseGeneral.json";
    private static void guardarPorPersonaje(Personaje[] personajesCreados){
        //String rutaFichero = "src\\UD4\\Rol\\Control\\BaseGeneral.json";
        JSONArray persBaseGeneral = (JSONArray) Util.rutaJsonToObjectJson(RUTA_BASE_GENERAL,"Personajes");
        System.out.println(Creacion.getStringPersonajes(personajesCreados));
        for (Personaje personaje : personajesCreados) {
            System.out.println(personaje.getFicha());
            System.out.print("¿Quieres guardar este personaje? (S/n): ");
            if (Util.escogerOpcion("S", "n")) {
                JSONObject perJsonObject;
                try {
                    perJsonObject = personaje.toJsonObject();
                } catch (Exception e) {
                    personaje.setId(persBaseGeneral.length());
                    perJsonObject = personaje.toJsonObject();
                }
                //TODO revisar condiciones en casa 
                if (persBaseGeneral.opt(personaje.getId()) == null || !(persBaseGeneral.getJSONObject(personaje.getId()).getJSONObject("Stats").getString("nombre").equals(perJsonObject.getJSONObject("Stats").getString("nombre")) && persBaseGeneral.getJSONObject(personaje.getId()).getJSONObject("Stats").getString("raza").equals(perJsonObject.getJSONObject("Stats").getString("raza")))) {
                    Util.writeToJson(RUTA_BASE_GENERAL, true, "Personajes", null, perJsonObject);
                } else if (!(Creacion.getPersonajeFromJsonObject(persBaseGeneral.getJSONObject(personaje.getId())).equals(personaje))) {
                    Util.overrideJson(RUTA_BASE_GENERAL, new Object[]{"Personaje", personaje.getId()}, perJsonObject);
                }
                /* Ignorar
                    ----CODIGO DESCARTADO POR AGRUPACION DE INFORMACION EN "BaseGeneral.json"----
                String rutaFichero = null;
                String rutaPrevia = "";
                boolean repetir = true;
                while (repetir) {
                    System.out.print("Ruta del fichero Json (Enter para usar ruta previa): ");
                    for (boolean noRutaPrevia = true; noRutaPrevia;) {
                        noRutaPrevia = false;
                        rutaFichero = Util.pedirPorTeclado(false);
                        if (rutaFichero == null) {
                            if (noRutaPrevia) {
                                System.out.println("Necesitas dar una ruta al menos una vez");
                                noRutaPrevia = true;
                            } else {
                                rutaFichero = rutaPrevia;
                            }
                        } else {
                            rutaPrevia = rutaFichero;
                        }
                    }
                    if (rutaFichero.endsWith(".json")) {
                        if (Util.UbiObjetoEnArray(personaje, Creacion.getPersonajesJson(rutaFichero)) == -1) {
                            Util.writeToJson(rutaFichero, true, "Personajes", personaje.toJsonObject());
                            repetir = false;
                        }
                    } else {
                        System.out.println("Ruta no valida");
                        System.out.println( "¿Quieres intentar de nuevo? (S/n): ");
                        if (!Util.escogerOpcion("S", "n")) {
                            System.out.println("Personaje no guardado.");
                            repetir = false;
                        }
                    }
                } */
            } else {
                System.out.println("Personaje no guardado.");
            }
        }    
    }
    public static void guardadoPersonajes(Personaje[] personajesCreados){//TODO que guarde en BaseGeneral
        boolean repetir = true;
        while (repetir) {
            repetir = false;
            System.out.println("¿Quieres guardar los personajes? (S/n): ");
            if (Util.escogerOpcion("S", "n")) {
                System.out.println("¿Quieres guardar todos los personajes creados y cargados? (S/n): ");
                if (Util.escogerOpcion("S", "n")) {
                    String rutaFichero;
                    rutaFichero = Util.pedirRuta();
                    if (!(rutaFichero == null)) {
                        if (rutaFichero.endsWith(".json")) {
                            JSONObject[] personajes = new JSONObject[0];
                            JSONObject pers = new JSONObject();
                            for (Personaje personaje : personajesCreados) {
                                if (!(personaje == null)) {
                                    pers = personaje.toJsonObject();
                                    personajes = Arrays.copyOf(personajes, personajes.length + 1);
                                    personajes[personajes.length - 1] = pers;
                                }
                            }
                            if (personajes.length != 0) {
                                Util.writeToJson(rutaFichero, false, "Personajes", personajes);
                            } else{
                                System.out.println("No había personajes que guardar");
                            }
                        } else {
                            System.out.println("Ruta no valida.");
                            repetir = true;
                        }
                    } else {
                        System.out.println("Ruta no valida.");
                        repetir = true;
                    }
                } else{
                    System.out.println("¿Quieres elegir individualmente si guardar cada personaje creado y cargado? (S/n): ");
                    if (Util.escogerOpcion("S", "n")) {
                        guardarPorPersonaje(personajesCreados);
                    } else {
                        System.out.println("Personajes no guardados.");
                        repetir = true;
                        
                    }
                }
            }
        }
    }
}
