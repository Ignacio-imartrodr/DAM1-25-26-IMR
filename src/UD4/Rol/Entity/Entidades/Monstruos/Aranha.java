package UD4.Rol.Entity.Entidades.Monstruos;

import UD4.Rol.Entity.Others.Especie;
import UD4.Rol.Utilidades.EntidadException;

public class Aranha extends Monstruo{
    public final static  int probabilidad = 30;

    public Aranha(String nombre, String fuerza, String agilidad, String constitucion, String nivel, String experiencia, boolean yaExistente, String... vidaMax) {
        this.vidaMin = 30;
        int minRndStatF = 10;
        int minRndStatC = 20;
        int minRndStatA = 40;
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
            this.minMaxRndStats = new int[]{minRndStatF, maxRndStatF, minRndStatA, maxRndStatA, minRndStatC, maxRndStatC};
            newMonstruo(nombre, fuerza, agilidad, constitucion, nivel, experiencia, Especie.ARANHA, yaExistente);
        } else {
            this.vidaMax = 80;
            int maxRndStatF = 50;
            int maxRndStatC = 40;
            int maxRndStatA = 70;
            this.minMaxRndStats = new int[]{minRndStatF, maxRndStatF, minRndStatA, maxRndStatA, minRndStatC, maxRndStatC};
            newMonstruo(nombre, fuerza, agilidad, constitucion, nivel, experiencia, Especie.ARANHA, yaExistente);
        }
    }
}
