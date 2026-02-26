package UD4.Rol.Objetos.Equipamiento.Armadura;

import java.util.Arrays;

import org.json.JSONObject;

import UD4.Rol.Objetos.Equipamiento.Equipamiento;
import UD4.Rol.Utilidades.RarezaException;

public abstract class Armadura extends Equipamiento {
    //Solo puede ser una pieza: Casco, Pechera, Pantalon o Botas y el Personaje solo puede tener equipado uno de cada 
    
    String material;
    int constitucion = getConstitucion();
    JSONObject armadura;
    final static String KEY = "Armadura";
    final static String[] MATERIALES = getMateriales();

    Armadura(String pieza, int num){
        super(KEY, pieza, num);
        this.nombre = super.nombre;
        this.rareza = super.rareza;
        this.durabilidad = super.durabilidad;
        this.xp = super.xp;
        this.lvl = super.lvl;
        this.material = MATERIALES[num];
        this.armadura = getArmaduraJsonObject();
    }

    protected int getConstitucion() {
        int constitucion = 0;
        switch (rareza) {
            case COMMUN:
                constitucion = 7;
                break;
            case RARE:
                constitucion = 18;
                break;
            case SPECIAL:
                constitucion = 42;
                break;
            case ULTRARE:
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
                throw new RarezaException("Rareza sin una constitución asignado");
        }
        for (int i = 1; i < getLvl(); i++) {
            constitucion += Math.round(constitucion * 0.2);
        }
        return constitucion;
    }
    private static String[] getMateriales(){
        String[] temp = new String[] {"CUERO"};
        for (String string : Equipamiento.MATERIALES) {
            temp = Arrays.copyOf(temp, temp.length + 1);
            temp[temp.length - 1] = string;
        }
        return temp;
    }
    protected JSONObject getArmaduraJsonObject() {
        armadura = new JSONObject(objetoBase);
        armadura.accumulate("durabilidad", durabilidad);
        armadura.accumulate("xp", xp);
        armadura.accumulate("lvl", lvl);
        armadura.accumulate("material", material);
        return armadura;
    }
}
