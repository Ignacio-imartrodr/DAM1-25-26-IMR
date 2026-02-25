package UD4.Rol.Objetos.Equipamiento.Armadura;

import UD4.Rol.Objetos.Equipamiento.Equipamiento;

public abstract class Armadura extends Equipamiento {
    //Solo puede ser una pieza: Casco, Pechera, Pantalon o Botas y el Personaje solo puede tener equipado uno de cada 
    final static String KEY = "Armadura";
    Armadura(String pieza, int num){
        super(KEY, pieza, num);
        this.nombre = super.nombre;
        this.rareza = super.rareza;
        this.durabilidad = super.durabilidad;
        this.xp = super.xp;
        this.lvl = super.lvl;
    }
}
