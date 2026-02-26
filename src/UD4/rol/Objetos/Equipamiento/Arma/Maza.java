package UD4.Rol.Objetos.Equipamiento.Arma;

import org.json.JSONObject;

import UD4.Rol.Utilidades.Util;

public class Maza extends Arma {
    int agilidad = -10;
    final static String KEY = "Maza";
    
    public Maza(String material){
        int num = Util.UbiObjetoEnArray(material, MATERIALES);
        this(num);
    }
    public Maza(int num){
        super(KEY, num);
        this.nombre = super.nombre;
        this.rareza = super.rareza;
        this.durabilidad = super.durabilidad;
        this.xp = super.xp;
        this.lvl = super.lvl;
        this.afinidad = super.afinidad;
        this.habilidad = super.habilidad;
        this.material = super.material;
        this.fuerza = (int) Math.round(super.fuerza * 1.4);
        this.objetoBase = getMazaJsonObject();
    }
    protected void subirNivel(byte lvlsUp){
        super.subirNivel(lvlsUp);
        if (agilidad < 0) {
            agilidad += Math.abs(Math.round(agilidad * 0.1));
        }
        this.fuerza = (int) Math.round(super.fuerza * 1.4);
    }
    public JSONObject getMazaJsonObject() {
        String key = "agilidad";
        if (objetoBase.opt(key) != null) {
            objetoBase.remove(key);
        }
        objetoBase.accumulate(key, durabilidad);

        key = "fuerza";
        if (objetoBase.opt(key) != null) {
            objetoBase.remove(key);
        }
        objetoBase.accumulate(key, durabilidad);

        return objetoBase;
    }
}
