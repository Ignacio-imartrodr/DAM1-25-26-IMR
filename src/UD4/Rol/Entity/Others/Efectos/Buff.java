package UD4.Rol.Entity.Others.Efectos;

import java.util.TreeSet;

public class Buff extends Efecto{

    protected Buff(String tipo, Efecto... efectosMultiples){
        this.tipo = tipo;
        if (efectosMultiples != null) {
            this.efectosMultiples = new TreeSet<>();
            for (int i = 0; i < efectosMultiples.length; i++) {
                this.efectosMultiples.add(efectosMultiples[i]);
            }
        }
    }
}
