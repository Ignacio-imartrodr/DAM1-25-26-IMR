package UD4.Rol.Entity.Others;

import java.util.Arrays;

import UD4.Rol.Entity.Entidades.Entidad;
import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Entity.Entidades.Monstruos.Monstruo;
import UD4.Rol.Utilidades.PersonajeException;

/**
 * @author Ignacio MR
 */

public enum Especie implements Habilidades{
    HUMANO, ELFO, ENANO, HOBBIT, ORCO, TROLL, DRAGON;// TODO añadir Especies de Mondtruos (Boses o enemigos extra)

    private boolean habilidadActiva = true;
    public static Especie[] toArray(){
        Especie[] razas = new Especie[0];
        for (Especie raza : Especie.values()) {
            razas = Arrays.copyOf(razas, razas.length + 1);
            razas[razas.length - 1] = raza;
        }
        return razas;
    }
    public static Especie StringToEspecie(String respuesta){
        Especie raza;
        try {
            raza = Especie.valueOf(respuesta.toUpperCase());
        } catch (Exception e) {
            throw new PersonajeException("Raza no válida.");
        }
        return raza;
    }
    public static int[] buffHabilidad(Entidad entity){
        int bonusFuerza = 0;
        int bonusAgilidad = 0;
        int bonusConstitucion = 0;
        int cura = 0;
        Especie switcher;
        if (entity instanceof Personaje) {
            Raza raza;
            raza = ((Personaje) entity).getRaza();
            switcher = Especie.StringToEspecie(raza.name());
        } else if (entity instanceof Monstruo) {
            switcher = ((Monstruo) entity).getEspecie();
        } else {
            switcher = null;
        }
        switch (switcher) {
                case HUMANO:
                    bonusFuerza = entity.getFuerza()/2;
                    bonusAgilidad = entity.getAgilidad()/2;
                    bonusConstitucion = entity.getConstitucion()/2;
                    break;
                case ELFO:
                    cura = -(entity.getVidaMax()/2);
                    break;
                case ENANO:
                    break;
                case HOBBIT:
                    break;
                case ORCO:
                    bonusFuerza = entity.getFuerza();
                    break;
                case TROLL:
                    cura = -((entity.getVidaMax() * 100) / 15 );
                    break;
                case DRAGON:
                    bonusFuerza = entity.getFuerza()*9;
                    break;
                default:
                    return null;
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
                default:
                    return null;
            }
        String[] bonus = {bonusFuerza, bonusAgilidad, bonusConstitucion};
        return bonus;
    }
    public static String getRazasStats() {
        String fichas = "";
        for (Especie raza : Especie.toArray()) {
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
                case DRAGON:
                    nombreHabilidad = "Aliento de Fuego";
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
                case DRAGON:
                    descripcion = "Lanza una llamarada que inflinge quemadura a todos menos a él";
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
