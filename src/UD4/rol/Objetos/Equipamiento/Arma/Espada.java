package UD4.Rol.Objetos.Equipamiento.Arma;

import org.json.JSONObject;

import UD4.Rol.Objetos.Equipamiento.Rareza;
import UD4.Rol.Utilidades.EquipamientoException;
import UD4.Rol.Utilidades.Util;

public class Espada extends Arma {
    final static String KEY = "Espada";

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
        this.objetoBase = getEspadaJsonObject();

    }
    protected void subirNivel(byte lvlsUp){
        super.subirNivel(lvlsUp);
        this.fuerza = (int) Math.round(super.fuerza * 1.2);
    }
    public JSONObject getEspadaJsonObject() {
        String key = "fuerza";
        if (objetoBase.opt(key) != null) {
            objetoBase.remove(key);
        }
        objetoBase.accumulate(key, durabilidad);
        
        return objetoBase;
    }
}
