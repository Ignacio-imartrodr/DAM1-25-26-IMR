package UD4.Rol.Objetos.Equipamiento.Armadura;

import org.json.JSONObject;

import UD4.Rol.Utilidades.Util;

public class Casco extends Armadura {
    JSONObject casco;
    final static String KEY = "Casco";

    public Casco(String material){
        int num = Util.UbiObjetoEnArray(material, MATERIALES);
        this(num);
    }
    public Casco(int num){
        super(KEY, num);
        this.nombre = super.nombre;
        this.rareza = super.rareza;
        this.durabilidad = super.durabilidad;
        this.xp = super.xp;
        this.lvl = super.lvl;
        this.material = super.material;
        this.constitucion = (int) Math.round(super.constitucion * 1.2);
        this.casco = getCascoJsonObject();
    }
    public JSONObject getCascoJsonObject() {
        casco = getArmaduraJsonObject();
        return casco;
    }
}
