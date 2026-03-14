package UD4.Rol.Entity.Equipamiento.Armadura;

import org.json.JSONObject;

import UD4.Rol.Entity.Equipamiento.Rareza;
import UD4.Rol.Utilidades.EquipamientoException;
import UD4.Rol.Utilidades.Util;

public class Casco extends Armadura {
    final static String KEY = "Casco";
    public Casco(JSONObject casco){
        this(casco.getString("rareza"));
        int comparador;
        this.nombre = super.nombre = casco.getString("nombre");
        this.rareza = super.rareza = (Rareza) casco.get("rareza");
        comparador = casco.getInt("durabilidad");
        if (comparador >= 1) {
            this.durabilidad = super.durabilidad = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Maza");
        }
        
        comparador = casco.getInt("xp");
        if (comparador >= 0 && comparador <= 999) {
            this.xp = super.xp = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Maza");
        }

        this.lvl = super.lvl= (byte) casco.get("lvl");

        comparador = casco.getInt("constitucion");
        if (comparador >= 1) {
            this.constitucion = super.constitucion = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Maza");
        }
        this.encantamiento = super.encantamiento = casco.optString("encantamiento");
        this.objetoBase = super.objetoBase = getJsonObject();
    }
    public Casco(String rareza){
        int num = Util.UbiObjetoEnArray(Rareza.StringToRareza(rareza), Rareza.toArray());
        this(num);
    }
    public Casco(int num){
        newArmadura(KEY, num);
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
