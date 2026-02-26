package UD4.Rol.Objetos.Equipamiento.Arma;

import org.json.JSONObject;

import UD4.Rol.Objetos.Afinidad;
import UD4.Rol.Objetos.Equipamiento.Equipamiento;
import UD4.Rol.Utilidades.RarezaException;

public abstract class Arma extends Equipamiento {
    //Solo puede ser Espada, Maza o Barita y el Personaje solo puede equipar una
    JSONObject arma;
    Afinidad afinidad;
    String habilidad = getHabilidad();
    int fuerza = getFuerza();
    String material;
    final static String KEY = "Arma";

    Arma(String subtipo, int num){
        super(KEY, subtipo, num);
        this.nombre = super.nombre;
        this.rareza = super.rareza;
        this.durabilidad = super.durabilidad;
        this.xp = super.xp;
        this.lvl = super.lvl;
        this.arma = getArmaJsonObject();
        this.afinidad = new Afinidad(super.objetoBase.getString("afinidad"));
        this.material = MATERIALES[num];
    }
    protected String getHabilidad() {
        String habilidad;
        if (arma.opt("habilidad") != null) {
            habilidad = arma.getString("habilidad");
        } else {
            habilidad = null;
        }
        
        return habilidad;
    }
    protected int getFuerza() {
        int fuerza = 0;
        switch (rareza) {
            case COMMUN:
                fuerza = 7;
                break;
            case RARE:
                fuerza = 18;
                break;
            case SPECIAL:
                fuerza = 42;
                break;
            case ULTRARE:
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
        for (int i = 1; i < getLvl(); i++) {
            fuerza += Math.round(fuerza * 0.15);
        }
        return fuerza;
    }
    protected JSONObject getArmaJsonObject() {
        arma = new JSONObject(objetoBase);
        arma.accumulate("durabilidad", durabilidad);
        arma.accumulate("xp", xp);
        arma.accumulate("lvl", lvl);
        arma.accumulate("fuerza", fuerza);
        arma.accumulate("material", material);
        return arma;
    }
    protected void subirNivel(byte lvlsUp){
        super.subirNivel(lvlsUp);
        fuerza = getFuerza();
    }
}
