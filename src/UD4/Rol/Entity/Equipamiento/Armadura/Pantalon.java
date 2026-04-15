package UD4.Rol.Entity.Equipamiento.Armadura;

import org.json.JSONObject;

import UD4.Rol.Entity.Equipamiento.Rareza;
import UD4.Rol.Utilidades.EquipamientoException;
import UD4.Rol.Utilidades.Util;

public class Pantalon extends Armadura {
    final static String KEY = "Pantalon";

    public Pantalon(JSONObject pantalon){
        this(pantalon.get("rareza").toString());
        int comparador;
        this.nombre = super.nombre = pantalon.getString("nombre");
        this.rareza = super.rareza = (Rareza) pantalon.get("rareza");
        comparador = pantalon.getInt("durabilidad");
        if (comparador >= 1) {
            this.durabilidad = super.durabilidad = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Pantalón");
        }
        
        comparador = pantalon.getInt("xp");
        if (comparador >= 0 && comparador <= 999) {
            this.xp = super.xp = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Pantalón");
        }

        this.lvl = super.lvl= Byte.valueOf(pantalon.get("lvl").toString());

        comparador = pantalon.getInt("constitucion");
        if (comparador >= 1) {
            this.constitucion = super.constitucion = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Pantalón");
        }
        this.encantamiento = super.encantamiento = pantalon.optString("encantamiento", null);
        this.objetoBase = super.objetoBase = getJsonObject();
    }
    public Pantalon(String rareza){
        int num = Util.UbiObjetoEnArray(Rareza.StringToRareza(rareza), Rareza.toArray());
        this(num);
    }
    public Pantalon(int num){
        newArmadura(KEY, num);
        this.constitucion = (int) Math.round(super.constitucion * 1.2);
        this.objetoBase = super.objetoBase = getJsonObject();
    }
    public JSONObject getJsonObject() {
        objetoBase = super.getJsonObject();
        objetoBase.put("constitucion", constitucion);
        objetoBase.put("class", KEY);

        return objetoBase;
    }
}
