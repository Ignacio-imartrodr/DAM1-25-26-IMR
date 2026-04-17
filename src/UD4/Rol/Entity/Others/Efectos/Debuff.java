package UD4.Rol.Entity.Others.Efectos;

import java.util.TreeSet;

public class Debuff extends Efecto{

    protected Debuff(String tipo, boolean esEspecial){
        tipo = tipo.strip().toUpperCase().replace(" ", "_");
        if (esEspecial) {
            efectos = new TreeSet<>();
        }
    }
}
