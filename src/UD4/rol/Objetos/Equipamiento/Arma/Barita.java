package UD4.Rol.Objetos.Equipamiento.Arma;

import org.json.JSONObject;

public class Barita extends Arma {
    JSONObject barita;
    int agilidad = 5;
    final static String KEY = "Barita";
    Barita(int num){
        super(KEY, num);
        this.nombre = super.nombre;
        this.rareza = super.rareza;
        this.durabilidad = super.durabilidad;
        this.xp = super.xp;
        this.lvl = super.lvl;
        this.afinidad = super.afinidad;
        this.barita = getBaritaJsonObject(); 
        this.fuerza = (int) Math.round(super.fuerza * 0.8);
    }
    protected void subirNivel(byte lvlsUp){
        super.subirNivel(lvlsUp);
        agilidad += Math.round(agilidad * 0.25);
    }
    public JSONObject getBaritaJsonObject() {
        barita = getArmaJsonObject();
        if (barita.opt("agilidad") != null) {
            barita.remove("agilidad");
        }
        arma.accumulate("agilidad", agilidad);
        return barita;
    }
}
