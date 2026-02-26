package UD4.Rol.Objetos.Equipamiento.Arma;

import org.json.JSONObject;

public class Maza extends Arma {
    JSONObject maza;
    int agilidad = -10;
    final static String KEY = "Maza";
    Maza(int num){
        super(KEY, num);
        this.nombre = super.nombre;
        this.rareza = super.rareza;
        this.durabilidad = super.durabilidad;
        this.xp = super.xp;
        this.lvl = super.lvl;
        this.afinidad = super.afinidad;
        this.habilidad = super.habilidad;
        this.material = super.material;
        this.maza = getMazaJsonObject(); 
        this.fuerza = (int) Math.round(super.fuerza * 1.4);
    }
    protected void subirNivel(byte lvlsUp){
        super.subirNivel(lvlsUp);
        if (agilidad < 0) {
            agilidad += Math.abs(Math.round(agilidad * 0.1));
        }
    }
    public JSONObject getMazaJsonObject() {
        maza = getArmaJsonObject();
        arma.accumulate("agilidad", agilidad);
        return maza;
    }
}
