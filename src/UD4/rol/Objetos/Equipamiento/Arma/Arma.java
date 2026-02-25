package UD4.Rol.Objetos.Equipamiento.Arma;

import UD4.Rol.Objetos.Afinidad;
import UD4.Rol.Objetos.Equipamiento.Equipamiento;

public abstract class Arma extends Equipamiento {
    //Solo puede ser Espada o Barita y el Personaje solo puede equipar una
    Afinidad tipo;
    final static String KEY = "Arma";
    Arma(String subtipo, int num){
        super(KEY, subtipo, num);
        this.nombre = super.nombre;
        this.rareza = super.rareza;
        this.durabilidad = super.durabilidad;
        this.xp = super.xp;
        this.lvl = super.lvl;
        tipo = new Afinidad(super.objeto.getString("afinidad"));
    }
}
