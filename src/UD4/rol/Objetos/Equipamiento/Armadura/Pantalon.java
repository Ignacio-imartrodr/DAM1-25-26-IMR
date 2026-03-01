package UD4.Rol.Objetos.Equipamiento.Armadura;

import org.json.JSONObject;

import UD4.Rol.Objetos.Equipamiento.Rareza;
import UD4.Rol.Utilidades.EquipamientoException;
import UD4.Rol.Utilidades.Util;

public class Pantalon extends Armadura {
    final static String KEY = "Pantalon";

    public Pantalon(JSONObject pantalon){
        this(pantalon.getString("rareza"));
        int comparador;
        this.nombre = super.nombre = pantalon.getString("nombre");
        this.rareza = super.rareza = (Rareza) pantalon.get("rareza");
        comparador = pantalon.getInt("durabilidad");
        if (comparador >= 1) {
            this.durabilidad = super.durabilidad = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Maza");
        }
        
        comparador = pantalon.getInt("xp");
        if (comparador >= 0 && comparador <= 999) {
            this.xp = super.xp = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Maza");
        }

        this.lvl = super.lvl= (byte) pantalon.get("lvl");

        comparador = pantalon.getInt("constitucion");
        if (comparador >= 1) {
            this.constitucion = super.constitucion = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Maza");
        }
        this.encantamiento = super.encantamiento = pantalon.optString("encantamiento");
        this.objetoBase = super.objetoBase = getJsonObject();
    }
    public Pantalon(String rareza){
        int num = Util.UbiObjetoEnArray(Rareza.StringToRareza(rareza), Rareza.toArray());
        this(num);
    }
    public Pantalon(int num){
        super(KEY, num);
        this.nombre = super.nombre;
        this.rareza = super.rareza;
        this.durabilidad = super.durabilidad;
        this.xp = super.xp;
        this.lvl = super.lvl;
        this.encantamiento = super.encantamiento;
        this.constitucion = (int) Math.round(super.constitucion * 1.2);
        this.objetoBase = super.objetoBase = getJsonObject();
    }
    public JSONObject getJsonObject() {
        objetoBase = super.getJsonObject();

        String key = "constitucion";
        if (objetoBase.opt(key) != null) {
            objetoBase.remove(key);
        }
        objetoBase.accumulate(key, constitucion);

        return objetoBase;
    }
}
