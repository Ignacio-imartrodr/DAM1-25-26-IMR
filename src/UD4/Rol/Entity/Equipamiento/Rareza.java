package UD4.Rol.Entity.Equipamiento;

import java.util.Arrays;

import UD4.Rol.Utilidades.EntidadException;

public enum Rareza {
        COMMUN, SPECIAL, RARE, EPIC, LEGENDARY, CHAOTIC;

    public static Rareza[] toArray(){
        Rareza[] rareza = new Rareza[0];
        for (Rareza raza : Rareza.values()) {
            rareza = Arrays.copyOf(rareza, rareza.length + 1);
            rareza[rareza.length - 1] = raza;
        }
        return rareza;
    }
    public static Rareza StringToRareza(String respuesta){
        Rareza rareza;
        if (respuesta == null) {
            rareza = null;
        } else {
            try {
                rareza = Rareza.valueOf(respuesta.toUpperCase());
            } catch (Exception e) {
                throw new EntidadException("Rareza no válida.");
            }
        }
        return rareza;
    }
    @Override
    public String toString() {
        String nombre;
        nombre= this.name().charAt(0) + this.name().substring(1).toLowerCase();
        return nombre;
    }
}
