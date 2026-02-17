package UD4.Rol.Objetos;

import java.util.Arrays;

import UD4.Rol.Utilidades.PersonajeException;

public enum Razas {
    HUMANO, ELFO, ENANO, HOBBIT, ORCO, TROLL;

    @Override
    public String toString() {
        String nombre;
        nombre= this.name().charAt(0) + this.name().substring(1).toLowerCase();
        return nombre;
    }
    public static Razas[] toArray(){
        Razas[] razas = new Razas[0];
        for (Razas raza : Razas.values()) {
            razas = Arrays.copyOf(razas, razas.length + 1);
            razas[razas.length - 1] = raza;
        }
        return razas;
    }
    public static Razas StringARaza(String respuesta){
        Razas raza = Razas.HUMANO;
        if (respuesta == null) {
            raza = Razas.HUMANO;
        } else {
            try {
                raza = Razas.valueOf(respuesta.toUpperCase());
            } catch (Exception e) {
                throw new PersonajeException("Raza no válida.");
            }
        }
        return raza;
    }
}
