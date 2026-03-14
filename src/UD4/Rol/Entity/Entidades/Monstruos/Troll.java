package UD4.Rol.Entity.Entidades.Monstruos;

import UD4.Rol.Entity.Others.Especie;
import UD4.Rol.Utilidades.EntidadException;

public class Troll extends Monstruo{
    public final static  int probabilidad = 20;
    
    public Troll(String nombre, String fuerza, String agilidad, String constitucion, String nivel, String experiencia, boolean yaExistente, String... vidaMax) {
        this.vidaMin = 100;
        int minRndStatF = 60;
        int minRndStatC = 50;
        int minRndStatA = 20;
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
            newMonstruo(nombre, fuerza, agilidad, constitucion, nivel, experiencia, Especie.TROLL, yaExistente);
        } else {
            this.vidaMax = 200;
            int maxRndStatF = 120;
            int maxRndStatC = 70;
            int maxRndStatA = 40;
            this.minMaxRndStats = new int[]{minRndStatF, maxRndStatF, minRndStatA, maxRndStatA, minRndStatC, maxRndStatC};
            newMonstruo(nombre, fuerza, agilidad, constitucion, nivel, experiencia, Especie.TROLL, yaExistente);
        }
    }
}
