package UD4.Rol.Entity.Equipamiento.Arma;

import org.json.JSONObject;

import UD4.Rol.Entity.Equipamiento.Rareza;
import UD4.Rol.Utilidades.EquipamientoException;
import UD4.Rol.Utilidades.Util;

public class Maza extends Arma {
    int agilidad = -10;
    final static String KEY = "Maza";
    
    public Maza(JSONObject maza){
        this(maza.get("rareza").toString());
        int comparador;
        this.nombre = super.nombre = maza.getString("nombre");
        this.rareza = super.rareza = (Rareza) maza.get("rareza");
        comparador = maza.getInt("durabilidad");
        if (comparador >= 1) {
            this.durabilidad = super.durabilidad = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Maza");
        }
        
        comparador = maza.getInt("xp");
        if (comparador >= 0 && comparador <= 999) {
            this.xp = super.xp = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Maza");
        }

        this.lvl = super.lvl= Byte.valueOf(maza.get("lvl").toString());

        comparador = maza.getInt("fuerza");
        if (comparador >= 1) {
            this.fuerza = super.fuerza = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Maza");
        }
        this.habilidad = super.habilidad = maza.optString("habilidad", null);
        this.objetoBase = super.objetoBase = getJsonObject();
    }
    public Maza(String rareza){
        int num = Util.UbiObjetoEnArray(Rareza.StringToRareza(rareza), Rareza.toArray());
        if (num > 1) {
            num--;
        } else if (num == 1) {
            throw new EquipamientoException("Rareza no válida");
        }
        this(num);
    }
    public Maza(int num){
        newArma(KEY, num);
        this.nombre = super.nombre;
        this.rareza = super.rareza;
        this.durabilidad = super.durabilidad;
        this.xp = super.xp;
        this.lvl = super.lvl;
        this.afinidad = super.afinidad;
        this.habilidad = super.habilidad;
        this.fuerza = (int) Math.round(super.fuerza * 1.4);
        this.objetoBase = super.objetoBase = getJsonObject();
    }
    protected void subirNivel(byte lvlsUp){
        super.subirNivel(lvlsUp);
        if (agilidad < 0) {
            agilidad += Math.abs(Math.round(agilidad * 0.1));
        }
        this.fuerza = (int) Math.round(super.fuerza * 1.4);
    }
    public JSONObject getJsonObject() {
        objetoBase = super.getJsonObject();
        objetoBase.put("agilidad", agilidad);
        objetoBase.put("fuerza", fuerza);
        objetoBase.put("class", KEY);

        return objetoBase;
    }
}
