package Extras;

import org.json.JSONArray;
import org.json.JSONObject;

import UD4.Rol.Control.Creacion;
import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Entity.Equipamiento.Equipamiento;
import UD4.Rol.Entity.Equipamiento.Arma.Barita;
import UD4.Rol.Entity.Equipamiento.Armadura.Casco;
import UD4.Rol.Entity.Others.Efectos.Efecto;
import UD4.Rol.Utilidades.Util;

/**
 * @Author Ignacio MR
 */
public class Pruebas {
    public static void main(String[] args) throws Exception {
        
        Personaje p = new Personaje("p","Orco");
        Personaje a = new Personaje("a","Troll");
        Equipamiento b = new Barita(2);
        Equipamiento c = new Casco(1);
        p.addEfect(Efecto.newEfecto("Quemado", 2, 3, false));
        p.addEfect(Efecto.newEfecto("Agilidad", 2, 3, true));
        p.guardarEquipamiento(b);
        p.guardarEquipamiento(c);
        p.guardarEquipamiento(b);
        p.equipar(b);
        a.guardarEquipamiento(c);
        a.equipar(c);
        a.equipar(b);
        p.useHabilidadRaza(a);
        a.curar();
        p.atacar(a);
        p.setId(3);
        System.out.println(a.getStringEquipamientoEquipado());
        System.out.println(p.getStringEquipamientoEquipado());
        JSONArray persBaseGeneral = (JSONArray) Util.rutaJsonToObjectJson("src\\UD4\\Rol\\PersonajesGuardados.json","Personajes");
        int idErr = persBaseGeneral.length();
        JSONObject persJO = null;
        Personaje[] personajesCreados = new Personaje[] {p, a};
        for (Personaje personaje : personajesCreados) {
            if (!(personaje == null)) {
                try {
                    persJO = personaje.toJsonObject();
                } catch (Exception e) {
                    personaje.setId(idErr);
                    persJO = personaje.toJsonObject();
                }
                if (persBaseGeneral.opt(personaje.getId()) == null) {
                    if (!Util.writeToJson("src\\UD4\\Rol\\PersonajesGuardados.json", true, "Personajes", null, persJO)) {
                        System.out.println("Error guardando el personaje en el Json");// TODO revisar (NO es culpa de la funcion de Util)
                    }
                    idErr++;
                } else if (persBaseGeneral.getJSONObject(personaje.getId()).getJSONObject("Stats").getString("nombre").equals(persJO.getJSONObject("Stats").getString("nombre")) &&
                            persBaseGeneral.getJSONObject(personaje.getId()).getJSONObject("Stats").getString("raza").equals(persJO.getJSONObject("Stats").getString("raza")) &&
                            !(Creacion.getPersonajeFromJsonObject(persBaseGeneral.getJSONObject(personaje.getId())).equals(personaje))) {
                    Util.overrideJson("src\\UD4\\Rol\\PersonajesGuardados.json", new Object[] { "Personaje", personaje.getId()}, persJO);
                } else {
                    System.out.println("Id del personaje erroneo");
                }
            }
        }
        if (persJO == null) {
            System.out.println("No había personajes que guardar");
        }

        a.quitarEquipado(0);
    }
}