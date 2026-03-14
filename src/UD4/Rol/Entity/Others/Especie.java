package UD4.Rol.Entity.Others;

import UD4.Rol.Utilidades.EntidadException;

/**
 * @author Ignacio MR
 */

public enum Especie {
    ORCO, ARANHA, TROLL, DRAGON;//Por orden de probabilidad de más a menos

    public static Especie stringToEspecie(String respuesta){
        Especie especie;
        try {
            especie = Especie.valueOf(respuesta.toUpperCase());
        } catch (Exception e) {
            throw new EntidadException("Especie no válida.");
        }
        return especie;
    }
    public static Especie numToEspecie(int respuesta){
        if (respuesta < 0 || respuesta > values().length - 1) {
            return null;
        }
        return values()[respuesta];
    }
    @Override
    public String toString() {
        String nombre;
        nombre= this.name().charAt(0) + this.name().substring(1).toLowerCase();
        return nombre;
    }
}
