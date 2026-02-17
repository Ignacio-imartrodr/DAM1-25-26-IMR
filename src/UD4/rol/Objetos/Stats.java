package UD4.Rol.Objetos;

import java.util.Arrays;

public enum Stats {
    NOMBRE, RAZA, FUERZA, AGILIDAD, CONSTITUCION, NIVEL, EXPERIENCIA;

    @Override
    public String toString() {
        String string;
        string= this.name().charAt(0) + this.name().substring(1).toLowerCase();
        return string;
    }
    public static Stats[] toArray(){
        Stats[] stats = new Stats[0];
        for (Stats stat : Stats.values()) {
            stats = Arrays.copyOf(stats, stats.length + 1);
            stats[stats.length - 1] = stat;
        }
        return stats;
    }
}
