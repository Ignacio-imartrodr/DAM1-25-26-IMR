package UD4.Rol.Entity.Others;

import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Entity.Others.Efectos.Efecto;
import UD4.Rol.Utilidades.EntidadException;

/**
 * @author Ignacio MR
 */

public enum Raza implements Habilidades {
    HUMANO, ELFO, ENANO, HOBBIT, ORCO, TROLL;

    boolean isHabActiva = true;
    int coolDown = 0;

    public static Raza stringToRaza(String respuesta){
        Raza raza = HUMANO;
        if (respuesta == null) {
            raza = HUMANO;
        } else {
            try {
                raza = valueOf(respuesta.toUpperCase());
            } catch (Exception e) {
                throw new EntidadException("Raza no válida.");
            }
        }
        return raza;
    }
    
    public static Efecto buffHabilidad(Personaje personaje) {
        if (personaje == null) {
            return null;
        }
        Efecto efecto = null;
        int bonusFuerza = 0;
        int bonusAgilidad = 0;
        int bonusConstitucion = 0;
        int cura = 0;
        switch (personaje.getRaza()) {
                case HUMANO:
                    Efecto[] multiples;
                    String[] nomEfects = new String[]{"AGILIDAD", "CONSTITUCION", "FUERZA"};
                    bonusFuerza = personaje.getFuerza()/2;
                    bonusAgilidad = personaje.getAgilidad()/2;
                    bonusConstitucion = personaje.getConstitucion()/2;

                    Integer[] bonus = new Integer[] {bonusAgilidad, bonusConstitucion, bonusFuerza};
                    multiples = new Efecto[nomEfects.length];

                    for (int i = 0; i < multiples.length; i++) {
                        multiples[i] = Efecto.newEfecto(nomEfects[i], 2, bonus[i],true);
                    }

                    efecto = Efecto.newEfecto("FUROR_HEROICO", 2, true, multiples);
                    break;
                case ELFO:
                    cura = -(personaje.getVidaMax()/2);
                    efecto = Efecto.newEfecto("REGENERACION", 1, cura,true);
                    break;
                case ENANO:
                    break;
                case HOBBIT:
                    break;
                case ORCO:
                    bonusFuerza = personaje.getFuerza();
                    efecto = Efecto.newEfecto("FUERZA", 1, bonusFuerza,true);
                    break;
                case TROLL:
                    cura = -((personaje.getVidaMax() * 100) / 15 );
                    efecto = Efecto.newEfecto("REGENERACION", 3, cura,true);
                    break;
            }
        return efecto;
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
        for (Raza raza : values()) {
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
                    descripcion = "Buffea todas sus estadisticas en un 50% hasta terminar proximo turno";
                    break;
                case ELFO:
                    descripcion = "Añade a los puntos de vida el 50% de vida máx";
                    break;
                case ENANO:
                    descripcion = "Fabrica un objeto aleatorio";
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
    public boolean countDown(){
        if (coolDown > 0) {
            coolDown--;
            return true;
        }
        return false;
    }

    /**
     * @return CoolDown restante
     */
    @Override
    public int getCoolDown(){
        return coolDown;
    }

    @Override
    public int setCoolDown() {
        switch (this) {
                case HUMANO:
                    coolDown = 5;
                    break;
                case ELFO:
                    coolDown = 3;
                    break;
                case ENANO:
                    coolDown = 2;
                    break;
                case HOBBIT:
                    coolDown = 4;
                    break;
                case ORCO:
                    coolDown = 3;
                    break;
                case TROLL:
                    coolDown = 5;
                    break;
                default:
                    coolDown = 0;
                    break;
            }
        return coolDown;
    }
    
    @Override
    public void activarHab() {
        isHabActiva = true;
        
    }

    @Override
    public boolean isHabilidadActiva() {
        if (isHabActiva) {
            if (countDown()) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void quitarHab() {
        isHabActiva = false;
    }

    @Override
    public String toString() {
        String nombre;
        nombre= this.name().charAt(0) + this.name().substring(1).toLowerCase();
        return nombre;
    }
}
