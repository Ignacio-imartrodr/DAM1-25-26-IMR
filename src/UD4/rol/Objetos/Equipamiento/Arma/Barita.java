package UD4.Rol.Objetos.Equipamiento.Arma;

import org.json.JSONObject;

import UD4.Rol.Utilidades.Util;

public class Barita extends Arma {
    JSONObject barita;
    int agilidad = 7;
    final static String KEY = "Barita";

    public Barita(String material){
        int num = Util.UbiObjetoEnArray(material, MATERIALES);
        this(num);
    }
    public Barita(int num){
        super(KEY, num);
        this.nombre = super.nombre;
        this.rareza = super.rareza;
        this.durabilidad = super.durabilidad;
        this.xp = super.xp;
        this.lvl = super.lvl;
        this.afinidad = super.afinidad;
        this.habilidad = super.habilidad;
        this.material = super.material;
        this.barita = getBaritaJsonObject(); 
        this.fuerza = (int) Math.round(super.fuerza * 0.8);
    }
    protected void subirNivel(byte lvlsUp){
        super.subirNivel(lvlsUp);
        agilidad += Math.round(agilidad * 0.1);
    }
    public JSONObject getBaritaJsonObject() {
        barita = getArmaJsonObject();
        arma.accumulate("agilidad", agilidad);
        return barita;
    }
}
