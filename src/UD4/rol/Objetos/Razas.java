package UD4.Rol.Objetos;

import java.util.Arrays;

import UD4.Rol.Utilidades.PersonajeException;

public enum Razas {
    HUMANO, ELFO, ENANO, HOBBIT, ORCO, TROLL;

    public static Razas[] toArray(){
        Razas[] razas = new Razas[0];
        for (Razas raza : Razas.values()) {
            razas = Arrays.copyOf(razas, razas.length + 1);
            razas[razas.length - 1] = raza;
        }
        return razas;
    }
    public static Razas StringToRaza(String respuesta){
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
    public static int[] buffHabilidad(Personaje personaje){
        int bonusFuerza = 0;
        int bonusAgilidad = 0;
        int bonusConstitucion = 0;
        int cura = 0;
        switch (personaje.getRaza()) {
                case HUMANO:
                    bonusFuerza = personaje.getFuerza()/2;
                    bonusAgilidad = personaje.getAgilidad()/2;
                    bonusConstitucion = personaje.getConstitucion()/2;
                    break;
                case ELFO:
                    cura = -(personaje.getVidaMax()/2);
                    break;
                case ENANO:
                    System.out.println("Juego en desarrollo, habilidad aún no implementada :(");
                    break;
                case HOBBIT:
                    break;
                case ORCO:
                    bonusFuerza = personaje.getFuerza();
                    break;
                case TROLL:
                    cura = -((personaje.getVidaMax() * 100) / 15 );
                    break;
            }
        int[] bonus = new int[] {bonusFuerza, bonusAgilidad, bonusConstitucion, cura};
        return bonus;
    }
    @Override
    public String toString() {
        String nombre;
        nombre= this.name().charAt(0) + this.name().substring(1).toLowerCase();
        return nombre;
    }
    
}
