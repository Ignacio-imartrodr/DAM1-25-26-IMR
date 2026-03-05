package UD4.Rol.Entity;

import java.util.Arrays;

import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Utilidades.PersonajeException;

/**
 * @author Ignacio MR
 */

public enum Raza implements Habilidades {
    HUMANO, ELFO, ENANO, HOBBIT, ORCO, TROLL;

    private boolean habilidadActiva = true;//TODO comprobar funcionamiento
    public static Raza[] toArray(){
        Raza[] razas = new Raza[0];
        for (Raza raza : Raza.values()) {
            razas = Arrays.copyOf(razas, razas.length + 1);
            razas[razas.length - 1] = raza;
        }
        return razas;
    }
    public static Raza StringToRaza(String respuesta){
        Raza raza = HUMANO;
        if (respuesta == null) {
            raza = HUMANO;
        } else {
            try {
                raza = Raza.valueOf(respuesta.toUpperCase());
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
    public String[] arrayBonusRaza() {
        String bonusFuerza = "x";
        String bonusAgilidad = "x";
        String bonusConstitucion = "x";
        switch (this) {
                case HUMANO:
                    bonusFuerza = "x";
                    bonusAgilidad = "x";
                    bonusConstitucion = "x";
                    break;
                case ELFO:
                    bonusFuerza = "-5";
                    bonusAgilidad = "+15";
                    bonusConstitucion = "-10";
                    break;
                case ENANO:
                    bonusFuerza = "+5";
                    bonusAgilidad = "-15";
                    bonusConstitucion = "+10";
                    break;
                case HOBBIT:
                    bonusFuerza = "-10";
                    bonusAgilidad = "+20";
                    bonusConstitucion = "-5";
                    break;
                case ORCO:
                    bonusFuerza = "+15";
                    bonusAgilidad = "-25";
                    bonusConstitucion = "+10";
                    break;
                case TROLL:
                    bonusFuerza = "+10";
                    bonusAgilidad = "-25";
                    bonusConstitucion = "+15";
                    break;
            }
        String[] bonus = {bonusFuerza, bonusAgilidad, bonusConstitucion};
        return bonus;
    }
    public static String getRazasStats() {
        String fichas = "";
        for (Raza raza : Raza.toArray()) {
            String[] bonus = raza.arrayBonusRaza();
            fichas += String.format("Raza: %s%n-------------%nFuerza: %s, Agilidad: %s, Constitución: %s%n%n", raza, bonus[0], bonus[1], bonus[2]);
        }
        return fichas;
    }
    @Override
    public String getHabilidadName() {
        String nombreHabilidad = "";
        switch (this) {
                case HUMANO:
                    nombreHabilidad = "Furor Heróico";
                    break;
                case ELFO:
                    nombreHabilidad = "Madre naturaleza";
                    break;
                case ENANO:
                    nombreHabilidad = "Crear";
                    break;
                case HOBBIT:
                    nombreHabilidad = "Steal"; //Vigila a donde te apunta con su habilidad jajaja
                    break;
                case ORCO:
                    nombreHabilidad = "Mamporro";
                    break;
                case TROLL:
                    nombreHabilidad = "Regeneración";
                    break;
            }
        return nombreHabilidad;
    }
    @Override
    public String getHabilidadDescription() {
        String descripcion = "";
        switch (this) {
                case HUMANO:
                    descripcion = "Furor Heróico (buffea todas sus estadisticas en un 50% hasta terminar proximo turno)";
                    break;
                case ELFO:
                    descripcion = "Madre naturaleza (Añade a los puntos de vida el 50% de vida máx)";
                    break;
                case ENANO:
                    descripcion = "Crear (Fabrica un objeto aleatorio)";
                    break;
                case HOBBIT:
                    descripcion = "Roba la habillidad de raza de su enemigo por un turno";
                    break;
                case ORCO:
                    descripcion = "Golpea al enemigo con el doble de fuerza";
                    break;
                case TROLL:
                    descripcion = "Se cura un 15% de su vida máx durante 3 turnos";
                    break;
            }
        return descripcion;
    }
    @Override
    public boolean isHabilidadActiva() {
        return this.habilidadActiva;
    }
    
    @Override
    public void activarHabilidad() {
        this.habilidadActiva = true;
        
    }
    @Override
    public void quitarHabilidad() {
        this.habilidadActiva = false;
        
    }
    @Override
    public String toString() {
        String nombre;
        nombre= this.name().charAt(0) + this.name().substring(1).toLowerCase();
        return nombre;
    }
}
