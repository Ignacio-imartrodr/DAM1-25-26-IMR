package UD4.Rol.Entity.Equipamiento.Armadura;

import org.json.JSONObject;

import UD4.Rol.Entity.Equipamiento.Equipamiento;
import UD4.Rol.Utilidades.RarezaException;

public abstract class Armadura extends Equipamiento {
    //Solo puede ser una pieza: Casco, Pechera, Pantalon o Botas y el Personaje solo puede tener equipado uno de cada 
    int constitucion;
    String encantamiento;
    String encantDesc;
    final static String KEY = "Armadura";

    protected void newArmadura(String pieza, int num){
        newEquipamiento(KEY, pieza, num);
        this.constitucion = getConstitucion();
        this.encantamiento = optEncantamiento();
        this.encantDesc = optEncantDesc();
        this.objetoBase = super.objetoBase = getJsonObject();
    }
    public String getEncantamiento(){
        return this.encantamiento;
    }
    private String optEncantamiento() {
        String encantamiento;
        if (objetoBase.opt("encantamiento") != null) {
            encantamiento = objetoBase.getString("encantamiento");
        } else {
            encantamiento = null;
        }
        return encantamiento;
    }
    public String getEncantDesc(){
        return this.encantDesc;
    }
    private String optEncantDesc() {
        String encantDesc;
        if (objetoBase.opt("descripcion") != null) {
            encantDesc = objetoBase.getString("descripcion");
        } else {
            encantDesc = null;
        }   
        return encantDesc;
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
    public String getString(){
        String este = super.getString();
        este += "Constitución: " + this.constitucion + "\n";
        if (getEncantamiento() != null) {
            este += "Encantamiento: " + getEncantamiento() + "\n";
        }
        return este;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Armadura other = (Armadura) obj;
        if (constitucion != other.constitucion)
            return false;
        if (encantamiento == null) {
            if (other.encantamiento != null)
                return false;
        } else if (!encantamiento.equals(other.encantamiento))
            return false;
        return true;
    }

    
}
