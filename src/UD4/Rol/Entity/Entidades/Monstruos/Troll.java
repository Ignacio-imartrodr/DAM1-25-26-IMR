package UD4.Rol.Entity.Entidades.Monstruos;

import UD4.Rol.Entity.Others.Especie;

public class Troll extends Monstruo{
    public final static  int probabilidad = 20;
    public Troll(String nombre, String fuerza, String agilidad, String constitucion, String nivel, String experiencia, boolean yaExistente) {
        this.vidaMin = 99;
        this.minRndStat = 1;
        this.maxRndStat = 100;
        newMonstruo(nombre, fuerza, agilidad, constitucion, nivel, experiencia, Especie.TROLL, yaExistente);
    }
}
