package UD4.Rol.Entity.Others.Efectos;

import java.util.TreeSet;

public class Buff extends Efecto{

    protected Buff(String tipo, Efecto... efectosMultiples){
        this.tipo = tipo;
        if (efectosMultiples != null) {
            this.efectos = new TreeSet<>();
            for (int i = 0; i < efectosMultiples.length; i++) {
                this.efectos.add(efectosMultiples[i]);
            }
        }
    }
}
