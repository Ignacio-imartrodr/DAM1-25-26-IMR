package UD4.Rol.Entity.Equipamiento.Armadura;

import org.json.JSONObject;

import UD4.Rol.Entity.Equipamiento.Rareza;
import UD4.Rol.Utilidades.EquipamientoException;
import UD4.Rol.Utilidades.Util;

public class Botas extends Armadura {
    final static String KEY = "Botas";

    public Botas(JSONObject botas){
        this(botas.getString("rareza"));
        int comparador;
        this.nombre = super.nombre = botas.getString("nombre");
        this.rareza = super.rareza = (Rareza) botas.get("rareza");
        comparador = botas.getInt("durabilidad");
        if (comparador >= 1) {
            this.durabilidad = super.durabilidad = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Botas");
        }
        
        comparador = botas.getInt("xp");
        if (comparador >= 0 && comparador <= 999) {
            this.xp = super.xp = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Botas");
        }

        this.lvl = super.lvl= (byte) botas.get("lvl");

        comparador = botas.getInt("constitucion");
        if (comparador >= 1) {
            this.constitucion = super.constitucion = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Botas");
        }
        this.encantamiento = super.encantamiento = botas.optString("encantamiento");
        this.objetoBase = super.objetoBase = getJsonObject();
    }
    public Botas(String rareza){
        int num = Util.UbiObjetoEnArray(Rareza.StringToRareza(rareza), Rareza.toArray());
        this(num);
    }
    public Botas(int num){
        newArmadura(KEY, num);
        this.nombre = super.nombre;
        this.rareza = super.rareza;
        this.durabilidad = super.durabilidad;
        this.xp = super.xp;
        this.lvl = super.lvl;
        this.encantamiento = super.encantamiento;
        this.constitucion = (int) Math.round(super.constitucion * 1.1);
        this.objetoBase = super.objetoBase = getJsonObject();
    }

    public JSONObject getJsonObject() {
        objetoBase = super.getJsonObject();
        objetoBase.put("constitucion", constitucion);
        objetoBase.put("class", KEY);

        return objetoBase;
    }
}
