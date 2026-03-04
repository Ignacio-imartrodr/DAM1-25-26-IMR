package UD4.Rol.Objetos;

import java.util.Arrays;

import UD4.Rol.Objetos.Entidades.Personaje;
import UD4.Rol.Utilidades.PersonajeException;

/**
 * @author Ignacio MR
 */

public enum Raza implements Habilidades {
    HUMANO, ELFO, ENANO, HOBBIT, ORCO, TROLL;

    public boolean habilidadActiva = true;//TODO comprobar funcionamiento
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
    public String toString() {
        String nombre;
        nombre= this.name().charAt(0) + this.name().substring(1).toLowerCase();
        return nombre;
    }
}
