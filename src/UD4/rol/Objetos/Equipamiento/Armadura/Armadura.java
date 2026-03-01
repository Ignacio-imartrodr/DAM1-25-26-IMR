package UD4.Rol.Objetos.Equipamiento.Armadura;

import org.json.JSONObject;

import UD4.Rol.Objetos.Equipamiento.Equipamiento;
import UD4.Rol.Utilidades.RarezaException;

public abstract class Armadura extends Equipamiento {//TODO Arreglar
    //Solo puede ser una pieza: Casco, Pechera, Pantalon o Botas y el Personaje solo puede tener equipado uno de cada 
    int constitucion = getConstitucion();
    String encantamiento;//TODO crear en Json
    final static String KEY = "Armadura";

    Armadura(String pieza, int num){
        super(KEY, pieza, num);
        this.nombre = super.nombre;
        this.rareza = super.rareza;
        this.durabilidad = super.durabilidad;
        this.xp = super.xp;
        this.lvl = super.lvl;
        this.encantamiento = getEncantamiento();//TODO crearlo en Json
        this.objetoBase = super.objetoBase = getJsonObject();
    }

    protected String getEncantamiento() {
        String encantamiento;
        if (objetoBase.opt("encantamiento") != null) {
            encantamiento = objetoBase.getString("encantamiento");
        } else {
            encantamiento = null;
        }
        return encantamiento;
    }
    protected int getConstitucion() {
        int constitucion = 0;
        switch (rareza) {
            case COMMUN:
                constitucion = 18;
                break;
            case SPECIAL:
                constitucion = 42;
                break;
            case RARE:
                constitucion = 59;
                break;
            case EPIC:
                constitucion = 68;
                break;
            case LEGENDARY:
                constitucion = 75;
                break;
            case CHAOTIC:
                constitucion = 86;
                break;
            default:
                throw new RarezaException("Rareza sin una constitución asignada");
        }
        for (int i = 1; i < getLvl(); i++) {
            constitucion += Math.round(constitucion * 0.2);
        }
        return constitucion;
    }
    public JSONObject getJsonObject() {
        objetoBase = super.getJsonObject();
        return objetoBase;
    }
}
