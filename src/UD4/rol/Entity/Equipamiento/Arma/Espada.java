package UD4.Rol.Entity.Equipamiento.Arma;

import org.json.JSONObject;

import UD4.Rol.Entity.Equipamiento.Rareza;
import UD4.Rol.Utilidades.EquipamientoException;
import UD4.Rol.Utilidades.Util;

public class Espada extends Arma {
    final static String KEY = "Espada";

    public Espada(JSONObject espada){
        this(espada.getString("rareza"));
        int comparador;
        this.nombre = super.nombre = espada.getString("nombre");
        this.rareza = super.rareza = (Rareza) espada.get("rareza");
        comparador = espada.getInt("durabilidad");
        if (comparador >= 1) {
            this.durabilidad = super.durabilidad = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Espada");
        }
        
        comparador = espada.getInt("xp");
        if (comparador >= 0 && comparador <= 999) {
            this.xp = super.xp = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Espada");
        }

        this.lvl = super.lvl= (byte) espada.get("lvl");

        comparador = espada.getInt("fuerza");
        if (comparador >= 1) {
            this.fuerza = super.fuerza = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Espada");
        }
        this.habilidad = super.habilidad = espada.optString("habilidad");
        this.objetoBase = super.objetoBase = getJsonObject();
    }
    public Espada(String rareza){
        int num = Util.UbiObjetoEnArray(Rareza.StringToRareza(rareza), Rareza.toArray());
        if (num > 1) {
            num--;
        } else if (num == 1) {
            throw new EquipamientoException("Rareza no válida");
        }
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
        this.fuerza = (int) Math.round(super.fuerza * 1.2);
        this.objetoBase = super.objetoBase = getJsonObject();

    }
    protected void subirNivel(byte lvlsUp){
        super.subirNivel(lvlsUp);
        this.fuerza = (int) Math.round(super.fuerza * 1.2);
    }
    public JSONObject getJsonObject() {
        objetoBase = super.getJsonObject();
        String key = "fuerza";
        if (objetoBase.opt(key) != null) {
            objetoBase.remove(key);
        }
        objetoBase.accumulate(key, fuerza);
        
        return objetoBase;
    }
}
