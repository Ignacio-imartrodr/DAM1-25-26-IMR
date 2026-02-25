package UD4.Rol.Objetos.Equipamiento.Arma;

import org.json.JSONObject;

public class Espada extends Arma {
    JSONObject espada;
    final static String KEY = "Espada";
    Espada(int num){
        super(KEY, num);
        this.nombre = super.nombre;
        this.rareza = super.rareza;
        this.afinidad = super.afinidad;
        this.durabilidad = super.durabilidad;
        this.xp = super.xp;
        this.lvl = super.lvl;
        this.espada = getArmaJsonObject();
        this.fuerza = (int) Math.round(super.fuerza * 1.2);
        
    }
    public JSONObject getEspadaJsonObject() {
        espada = getArmaJsonObject();
        return espada;
    }
    public static String getKey() {
        return KEY;
    }
    
    
}
