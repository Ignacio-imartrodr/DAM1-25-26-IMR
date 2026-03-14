package UD4.Rol.Entity.Entidades.Monstruos;

import UD4.Rol.Entity.Others.Especie;
import UD4.Rol.Utilidades.EntidadException;

public class Orco extends Monstruo{
    public final static  int probabilidad = 40;

    public Orco(String nombre, String fuerza, String agilidad, String constitucion, String nivel, String experiencia, boolean yaExistente, String... vidaMax) {
        this.vidaMin = 30;//El orco tiene 30 como mínimo para todo
        if (yaExistente) {
            try {
                this.vidaMax = Integer.valueOf(vidaMax[0]);
                if (this.vidaMax < vidaMin) { throw new EntidadException("vidaMax invalida"); }
            } catch (Exception e) {
                throw new EntidadException("vidaMax invalida");
            }
            int maxRndStatF = Integer.MAX_VALUE;
            int maxRndStatA = Integer.MAX_VALUE;
            int maxRndStatC = Integer.MAX_VALUE;
            this.minMaxRndStats = new int[]{vidaMin, maxRndStatF, vidaMin, maxRndStatA, vidaMin, maxRndStatC};
            newMonstruo(nombre, fuerza, agilidad, constitucion, nivel, experiencia, Especie.ORCO, yaExistente);
        } else {
            this.vidaMax = 100;
            int maxRndStatF = 80;
            int maxRndStatC = 50;
            int maxRndStatA = 60;
            this.minMaxRndStats = new int[]{vidaMin, maxRndStatF, vidaMin, maxRndStatA, vidaMin, maxRndStatC};
            newMonstruo(nombre, fuerza, agilidad, constitucion, nivel, experiencia, Especie.ORCO, yaExistente);
        }
    }
}
