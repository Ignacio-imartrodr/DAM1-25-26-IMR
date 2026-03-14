package UD4.Rol.Entity.Entidades.Monstruos;

import UD4.Rol.Entity.Others.Especie;

public class Orco extends Monstruo{
    public final static  int probabilidad = 40;
    public Orco(String nombre, String fuerza, String agilidad, String constitucion, String nivel, String experiencia, boolean yaExistente) {
        this.vidaMin = 29;
        this.minRndStat = 30;
        this.maxRndStat = 50;
        newMonstruo(nombre, fuerza, agilidad, constitucion, nivel, experiencia, Especie.ORCO, yaExistente);
    }
}
