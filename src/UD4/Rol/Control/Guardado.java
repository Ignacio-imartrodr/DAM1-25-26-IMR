package UD4.Rol.Control;

import org.json.JSONArray;
import org.json.JSONObject;

import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Utilidades.Util;

public class Guardado {
    public static final String RUTA_BASE_GENERAL = "src\\UD4\\Rol\\Control\\BaseGeneral.json";
    private static void guardarPorPersonaje(Personaje[] personajesCreados){
        String rutaFichero = RUTA_BASE_GENERAL;
        JSONArray persBaseGeneral = (JSONArray) Util.rutaJsonToObjectJson(RUTA_BASE_GENERAL,"Personajes");
        int idErr = persBaseGeneral.length();
        System.out.println(Creacion.getStringPersonajes(personajesCreados));
        for (Personaje personaje : personajesCreados) {
            if (personaje != null) {
                System.out.println(personaje.getFicha());
                System.out.print("¿Quieres guardar este personaje? (S/n): ");
                if (Util.escogerOpcion("S", "n")) {
                    JSONObject persJO;
                    try {
                        persJO = personaje.toJsonObject();
                    } catch (Exception e) {
                        personaje.setId(idErr);
                        persJO = personaje.toJsonObject();
                    }
                    if (persBaseGeneral.opt(personaje.getId()) == null || !(persBaseGeneral.getJSONObject(personaje.getId()).getJSONObject("Stats").getString("nombre").equals(persJO.getJSONObject("Stats").getString("nombre")) && persBaseGeneral.getJSONObject(personaje.getId()).getJSONObject("Stats").getString("raza").equals(persJO.getJSONObject("Stats").getString("raza")))) {
                        Util.writeToJson(rutaFichero, true, "Personajes", null, persJO);
                        idErr++;
                    } else if (!(Creacion.getPersonajeFromJsonObject(persBaseGeneral.getJSONObject(personaje.getId())).equals(personaje))) {
                        Util.overrideJson(rutaFichero, new Object[]{"Personaje", personaje.getId()}, persJO);
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
    }
    public static void guardadoPersonajes(Personaje[] personajesCreados){
        boolean repetir = true;
        while (repetir) {
            repetir = false;
            System.out.println("¿Quieres guardar los personajes? (S/n): ");
            if (Util.escogerOpcion("S", "n")) {
                System.out.println("¿Quieres guardar todos los personajes creados y cargados? (S/n): ");
                if (Util.escogerOpcion("S", "n")) {
                    String rutaFichero;
                    rutaFichero = RUTA_BASE_GENERAL;
                    if (!(rutaFichero == null)) {
                        if (rutaFichero.endsWith(".json")) {
                            JSONArray persBaseGeneral = (JSONArray) Util.rutaJsonToObjectJson(RUTA_BASE_GENERAL,"Personajes");
                            int idErr = persBaseGeneral.length();
                            JSONObject persJO = null;
                            for (Personaje personaje : personajesCreados) {
                                if (!(personaje == null)) {
                                    try {
                                        persJO = personaje.toJsonObject();
                                    } catch (Exception e) {
                                        personaje.setId(idErr);
                                        persJO = personaje.toJsonObject();
                                    }
                                    if (persBaseGeneral.opt(personaje.getId()) == null || !(persBaseGeneral.getJSONObject(personaje.getId()).getJSONObject("Stats").getString("nombre").equals(persJO.getJSONObject("Stats").getString("nombre")) && persBaseGeneral.getJSONObject(personaje.getId()).getJSONObject("Stats").getString("raza").equals(persJO.getJSONObject("Stats").getString("raza")))) {
                                        Util.writeToJson(rutaFichero, true, "Personajes", null, persJO);
                                        idErr++;
                                    } else if (!(Creacion.getPersonajeFromJsonObject(persBaseGeneral.getJSONObject(personaje.getId())).equals(personaje))) {
                                        Util.overrideJson(rutaFichero, new Object[]{"Personaje", personaje.getId()}, persJO);
                                    }
                                }
                            }
                            if (persJO == null) {
                                System.out.println("No había personajes que guardar");
                            }
                        } else {
                            System.out.println("Ruta no valida.");
                            repetir = true;
                        }
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
