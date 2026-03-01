package UD4.Rol.Objetos.Equipamiento.Armadura;

import org.json.JSONObject;

import UD4.Rol.Objetos.Equipamiento.Rareza;
import UD4.Rol.Utilidades.Util;

public class Pechera extends Armadura {
    final static String KEY = "Pechera";

    public Pechera(String rareza){
        int num = Util.UbiObjetoEnArray(Rareza.StringToRareza(rareza), Rareza.toArray());
        this(num);
    }
    public Pechera(int num){
        super(KEY, num);
        this.nombre = super.nombre;
        this.rareza = super.rareza;
        this.durabilidad = super.durabilidad;
        this.xp = super.xp;
        this.lvl = super.lvl;
        this.encantamiento = super.encantamiento;
        this.constitucion = (int) Math.round(super.constitucion * 1.5);
        this.objetoBase = super.objetoBase = getPecheraJsonObject();
    }
    public JSONObject getPecheraJsonObject() {
        objetoBase = getArmaduraJsonObject();

        String key = "constitucion";
        if (objetoBase.opt(key) != null) {
            objetoBase.remove(key);
        }
        objetoBase.accumulate(key, constitucion);

        return objetoBase;
    }
}
