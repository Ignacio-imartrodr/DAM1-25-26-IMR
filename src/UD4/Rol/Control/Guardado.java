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
                if (Util.escogerOpcion("S", "n", "¿Quieres guardar este personaje? (S/n)")) {
                    JSONObject persJO;
                    try {
                        persJO = personaje.toJsonObject();
                    } catch (Exception e) {
                        personaje.setId(idErr);
                        persJO = personaje.toJsonObject();
                    }
                    String guardadoNombre = null;
                    String guardadoRaza = null;
                    if (personaje.getId() >= idErr && personaje.getId() < 0) {
                        guardadoNombre = persBaseGeneral.optJSONObject(personaje.getId()).optJSONObject("Stats").optString("nombre");
                        guardadoRaza = persBaseGeneral.optJSONObject(personaje.getId()).optJSONObject("Stats").optString("raza");
                    }
                    if (persBaseGeneral.opt(personaje.getId()) == null) {
                        if (!Util.writeToJson(rutaFichero, true, "Personajes", null, persJO)) {
                            System.out.println("Error guardando el personaje en el Json");
                        } else {
                            idErr++;
                        }
                    } else if (guardadoNombre.equals(personaje.getNombre()) && guardadoRaza.equals(personaje.getRaza().name())
                            && !(Creacion.getPersonajeFromJsonObject(persBaseGeneral.getJSONObject(personaje.getId())).equals(personaje))) {
                        Util.overrideJson(rutaFichero, new Object[] {"Personaje", personaje.getId() }, persJO);
                    } else {
                        System.out.println("Parece que quieres sobre escribir al personaje " + guardadoNombre + " de Raza: \"" + guardadoRaza + "\"" + " por " + personaje.getNombre() + " de Raza: " + guardadoRaza);
                        if (!Util.escogerOpcion("N", "s", "Quieres sobre escribir la información del anterior y guardar el nuevo? (s/N)")) {
                            Util.overrideJson(rutaFichero, new Object[] { "Personaje", personaje.getId() }, persJO);
                        } else {
                            if (Util.escogerOpcion("S", "n", "Quieres guardarlo como un nuevo personaje? (S/n)")) {
                                personaje.setId(idErr);
                                Util.overrideJson(rutaFichero, new Object[] { "Personaje", personaje.getId() }, persJO);
                                idErr++;
                            } else {
                                System.out.println("Personaje \"" + personaje.getNombre() + "\" no guardado");
                            }
                        }
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
                            if (!Util.escogerOpcion("S", "n", "¿Quieres intentar de nuevo? (S/n)")) {
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
            if (Util.escogerOpcion("S", "n", "¿Quieres guardar algún personaje? (S/n)")) {
                if (Util.escogerOpcion("S", "n", "¿Quieres guardar todos los personajes creados y cargados? (S/n)")) {
                    String rutaFichero;
                    rutaFichero = RUTA_BASE_GENERAL;
                    if (!(rutaFichero == null)) {
                        if (rutaFichero.endsWith(".json")) {
                            JSONArray persBaseGeneral = (JSONArray) Util.rutaJsonToObjectJson(rutaFichero,"Personajes");
                            int idErr = persBaseGeneral.length();
                            JSONObject persJO = null;
                            for (Personaje personaje : personajesCreados) {
                                if (personaje == null) {
                                    continue;
                                }
                                try {
                                    persJO = personaje.toJsonObject();
                                } catch (Exception e) {
                                    personaje.setId(idErr);
                                    persJO = personaje.toJsonObject();
                                }
                                String guardadoNombre = null;
                                String guardadoRaza = null;
                                if (personaje.getId() >= idErr && personaje.getId() < 0) {
                                    guardadoNombre = persBaseGeneral.optJSONObject(personaje.getId()).optJSONObject("Stats").optString("nombre");
                                    guardadoRaza = persBaseGeneral.optJSONObject(personaje.getId()).optJSONObject("Stats").optString("raza");
                                }
                                if (persBaseGeneral.opt(personaje.getId()) == null) {
                                    if (!Util.writeToJson(rutaFichero, true, null, new Object[] { "Personajes" }, persJO)) {
                                        System.out.println("Error guardando el personaje en el Json");// TODO testear
                                    }
                                    idErr++;
                                } else if (guardadoNombre.equals(personaje.getNombre())
                                        && guardadoRaza.equals(personaje.getRaza().name())
                                        && !(Creacion.getPersonajeFromJsonObject(persBaseGeneral.getJSONObject(personaje.getId())).equals(personaje))) {
                                    Util.overrideJson(rutaFichero, new Object[] { "Personaje", personaje.getId() },
                                            persJO);
                                } else {
                                    System.out.println("Parece que quieres sobre escribir al personaje " + guardadoNombre + " de Raza: " + guardadoRaza + " por " + personaje.getNombre() + " de Raza: " + personaje.getRaza().name());
                                    if (!Util.escogerOpcion("N", "s", "Quieres sobre escribir la información del anterior y guardar el nuevo? (s/N)")) {
                                        Util.overrideJson(rutaFichero, new Object[] { "Personaje", personaje.getId() }, persJO);
                                    } else {
                                        if (Util.escogerOpcion("S", "n", "Quieres guardarlo como un nuevo personaje? (S/n)")) {
                                            personaje.setId(idErr);
                                            Util.overrideJson(rutaFichero, new Object[] { "Personaje", personaje.getId() }, persJO);
                                            idErr++;
                                        } else {
                                            System.out.println("Personaje \"" + personaje.getNombre() + "\" no guardado");
                                        }
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
                    if (Util.escogerOpcion("S", "n", "¿Quieres elegir individualmente si guardar cada personaje creado y cargado? (S/n)")) {
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
