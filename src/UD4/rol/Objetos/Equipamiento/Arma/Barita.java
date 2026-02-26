package UD4.Rol.Objetos.Equipamiento.Arma;

import org.json.JSONObject;

import UD4.Rol.Utilidades.Util;

public class Barita extends Arma {

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
        this.fuerza = (int) Math.round(super.fuerza * 0.8);
        this.objetoBase = getBaritaJsonObject(); 
    }
    protected void subirNivel(byte lvlsUp){
        super.subirNivel(lvlsUp);
        agilidad += Math.round(agilidad * 0.1);
        this.fuerza = (int) Math.round(super.fuerza * 0.8);
    }
    public JSONObject getBaritaJsonObject() {
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

    public static void main(String[] args) {
        Barita barita = new Barita(0);
        barita.subirNivel( (byte) -126);
        System.out.println(barita);
    }
}
