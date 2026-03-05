package UD4.Rol.Entity.Equipamiento.Arma;

import org.json.JSONObject;

import UD4.Rol.Entity.Afinidad;
import UD4.Rol.Entity.Equipamiento.Equipamiento;
import UD4.Rol.Utilidades.RarezaException;

public abstract class Arma extends Equipamiento {
    //Solo puede ser Espada, Maza o Barita y el Personaje solo puede equipar una
    protected Afinidad afinidad;
    protected String habilidad;
    protected int fuerza;
    final static String KEY = "Arma";

    Arma(String subtipo, int num){
        super(KEY, subtipo, num);
        this.nombre = super.nombre;
        this.rareza = super.rareza;
        this.durabilidad = super.durabilidad;
        this.xp = super.xp;
        this.lvl = super.lvl;
        this.fuerza = getFuerzaBase();
        this.afinidad = new Afinidad(super.objetoBase.getString("afinidad"));
        this.habilidad = getHabilidad();
        this.objetoBase = super.objetoBase = getJsonObject();
    }
    private String getHabilidad() {
        String habilidad;
        if (objetoBase.opt("habilidad") != null) {
            habilidad = objetoBase.getString("habilidad");
        } else {
            habilidad = null;
        }
        return habilidad;
    }
    protected int getFuerzaBase() {
        int fuerza = 0;
        switch (rareza) {
            case COMMUN:
                fuerza = 18;
                break;
            case SPECIAL:
                fuerza = 42;
                break;
            case RARE:
                fuerza = 59;
                break;
            case EPIC:
                fuerza = 68;
                break;
            case LEGENDARY:
                fuerza = 75;
                break;
            case CHAOTIC:
                fuerza = 86;
                break;
            default:
                throw new RarezaException("Rareza sin un daño asignado");
        }
        return fuerza;
    }
    public JSONObject getJsonObject() {
        objetoBase = super.getJsonObject();
        String key = "afinidad";
        if (objetoBase.opt(key) != null) {
            objetoBase.remove(key);
        }
        objetoBase.accumulate(key, afinidad);
        key = "habilidad";
        if (objetoBase.opt(key) != null) {
            objetoBase.remove(key);
        }
        objetoBase.accumulate(key, habilidad);

        return objetoBase;
    }
    public String getString(){
        String este = super.getString();
        este += "Fuerza: " + this.fuerza + "\n";
        este += "Afinidad: " + this.afinidad + "\n";
        if (getHabilidad() != null) {
            este += "Habilidad: " + getHabilidad() + "\n";
        }
        return este;
    }

    protected void subirNivel(byte lvlsUp){
        super.subirNivel(lvlsUp);
        float fuerza = this.fuerza;
        for (int i = 1; i < getLvl(); i++) {
            fuerza += fuerza * 0.15;
        }
        this.fuerza += Math.round(fuerza);
    }
}
