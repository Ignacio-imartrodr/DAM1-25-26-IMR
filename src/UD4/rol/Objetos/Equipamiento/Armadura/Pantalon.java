package UD4.Rol.Objetos.Equipamiento.Armadura;

import org.json.JSONObject;

import UD4.Rol.Utilidades.Util;

public class Pantalon extends Armadura {
    JSONObject pantalon;
    final static String KEY = "Pantalon";

    public Pantalon(String material){
        int num = Util.UbiObjetoEnArray(material, MATERIALES);
        this(num);
    }
    public Pantalon(int num){
        super(KEY, num);
        this.nombre = super.nombre;
        this.rareza = super.rareza;
        this.durabilidad = super.durabilidad;
        this.xp = super.xp;
        this.lvl = super.lvl;
        this.material = super.material;
        this.encantamiento = super.encantamiento;
        this.constitucion = (int) Math.round(super.constitucion * 1.2);
        this.pantalon = getPantalonJsonObject();
    }
    public JSONObject getPantalonJsonObject() {
        pantalon = getArmaduraJsonObject();
        return pantalon;
    }
}
