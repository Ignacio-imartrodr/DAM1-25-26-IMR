package UD4.Rol.Objetos.Equipamiento.Armadura;

import org.json.JSONObject;

import UD4.Rol.Utilidades.Util;

public class Botas extends Armadura {
    JSONObject botas;
    final static String KEY = "Botas";

    public Botas(String material){
        int num = Util.UbiObjetoEnArray(material, MATERIALES);
        this(num);
    }
    public Botas(int num){
        super(KEY, num);
        this.nombre = super.nombre;
        this.rareza = super.rareza;
        this.durabilidad = super.durabilidad;
        this.xp = super.xp;
        this.lvl = super.lvl;
        this.material = super.material;
        this.constitucion = (int) Math.round(super.constitucion * 1.1);
        this.botas = getBotasJsonObject();
    }
    public JSONObject getBotasJsonObject() {
        botas = getArmaduraJsonObject();
        return botas;
    }
}
