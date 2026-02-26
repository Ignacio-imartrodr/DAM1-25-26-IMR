package UD4.Rol.Objetos.Equipamiento.Arma;

import org.json.JSONObject;

import UD4.Rol.Utilidades.Util;

public class Espada extends Arma {
    JSONObject espada;
    final static String KEY = "Espada";

    public Espada(String material){
        int num = Util.UbiObjetoEnArray(material, MATERIALES);
        this(num);
    }
    public Espada(int num){
        super(KEY, num);
        this.nombre = super.nombre;
        this.rareza = super.rareza;
        this.afinidad = super.afinidad;
        this.durabilidad = super.durabilidad;
        this.xp = super.xp;
        this.lvl = super.lvl;
        this.habilidad = super.habilidad;
        this.material = super.material;
        this.fuerza = (int) Math.round(super.fuerza * 1.2);
        this.espada = getEspadaJsonObject();

    }
    public JSONObject getEspadaJsonObject() {
        espada = getArmaJsonObject();
        return espada;
    }
}
