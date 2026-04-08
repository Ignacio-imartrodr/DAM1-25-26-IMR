package UD4.Rol.Entity.Equipamiento.Armadura;

import org.json.JSONObject;

import UD4.Rol.Entity.Equipamiento.Rareza;
import UD4.Rol.Utilidades.EquipamientoException;
import UD4.Rol.Utilidades.Util;

public class Pechera extends Armadura {
    final static String KEY = "Pechera";
    public Pechera(JSONObject pechera){
        this(pechera.getString("rareza"));
        int comparador;
        this.nombre = super.nombre = pechera.getString("nombre");
        this.rareza = super.rareza = (Rareza) pechera.get("rareza");
        comparador = pechera.getInt("durabilidad");
        if (comparador >= 1) {
            this.durabilidad = super.durabilidad = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Pechera");
        }
        
        comparador = pechera.getInt("xp");
        if (comparador >= 0 && comparador <= 999) {
            this.xp = super.xp = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Pechera");
        }

        this.lvl = super.lvl= (byte) pechera.get("lvl");

        comparador = pechera.getInt("constitucion");
        if (comparador >= 1) {
            this.constitucion = super.constitucion = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Pechera");
        }
        this.encantamiento = super.encantamiento = pechera.optString("encantamiento");
        this.objetoBase = super.objetoBase = getJsonObject();
    }
    public Pechera(String rareza){
        int num = Util.UbiObjetoEnArray(Rareza.StringToRareza(rareza), Rareza.toArray());
        this(num);
    }
    public Pechera(int num){
        newArmadura(KEY, num);
        this.nombre = super.nombre;
        this.rareza = super.rareza;
        this.durabilidad = super.durabilidad;
        this.xp = super.xp;
        this.lvl = super.lvl;
        this.encantamiento = super.encantamiento;
        this.constitucion = (int) Math.round(super.constitucion * 1.5);
        this.objetoBase = super.objetoBase = getJsonObject();
    }
    public JSONObject getJsonObject() {
        objetoBase = super.getJsonObject();
        objetoBase.put("constitucion", constitucion);
        objetoBase.put("class", KEY);

        return objetoBase;
    }
}
