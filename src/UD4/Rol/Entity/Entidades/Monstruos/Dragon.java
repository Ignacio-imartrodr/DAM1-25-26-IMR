package UD4.Rol.Entity.Entidades.Monstruos;

import UD4.Rol.Entity.Others.Especie;
import UD4.Rol.Utilidades.EntidadException;

public class Dragon extends Monstruo{
    public final static  int probabilidad = 10;
    
    public Dragon(String nombre, String fuerza, String agilidad, String constitucion, String nivel, String experiencia, boolean yaExistente, String... vidaMax) {
        this.vidaMin = 120;
        int minRndStatF = 100;
        int minRndStatC = 60;
        int minRndStatA = 80;
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
            newMonstruo(nombre, fuerza, agilidad, constitucion, nivel, experiencia, Especie.DRAGON, yaExistente);
        } else {
            this.vidaMax = 250;
            int maxRndStatF = 20;
            int maxRndStatC = 100;
            int maxRndStatA = 120;
            this.minMaxRndStats = new int[]{minRndStatF, maxRndStatF, minRndStatA, maxRndStatA, minRndStatC, maxRndStatC};
            newMonstruo(nombre, fuerza, agilidad, constitucion, nivel, experiencia, Especie.DRAGON, yaExistente);
        }
    }
}
