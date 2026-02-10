package UD4.rol;

import java.util.Arrays;

public enum Razas {
    HUMANO, ELFO, ENANO, HOBBIT, ORCO, TROLL;

    @Override
    public String toString() {
        String nombre;
        nombre= this.name().charAt(0) + this.name().substring(1).toLowerCase();
        return nombre;
    }
    public static Razas[] Array(){
        Razas[] razas = new Razas[0];
        for (Razas raza : Razas.values()) {
            razas = Arrays.copyOf(razas, razas.length + 1);
            razas[razas.length - 1] = raza;
        }
        return razas;
    }
}
