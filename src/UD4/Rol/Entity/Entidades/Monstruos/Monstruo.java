package UD4.Rol.Entity.Entidades.Monstruos;

import java.util.Random;

import UD4.Rol.Entity.Entidades.Entidad;
import UD4.Rol.Entity.Others.Especie;
import UD4.Rol.Utilidades.EntidadException;

public abstract class Monstruo extends Entidad {

    Especie especie;
    int vidaMax;
    
    private static Random rng = new Random();
    protected void newMonstruo(String nombre, String fuerza, String agilidad, String constitucion, String nivel, String experiencia, Especie especie, boolean yaExistente) {
        this.especie = especie;
        if (!yaExistente) { this.vidaMax = getVida(); }
        if (nivel == null) {
            nivel = String.valueOf(generarRndMinAMax(1, 256));
        }
        newEntidad(nombre, fuerza, agilidad, constitucion, nivel, experiencia, yaExistente);
    }

    
    public int getVida() {
        Random rng = new Random();
        int rnd = rng.nextInt(vidaMin, vidaMax + 1);
        return rnd;
    }

	public Especie getEspecie() {
		return especie;
	}

    @Override
    public int getVidaMax() {
        return vidaMax;
    }

    
    @Override
    public String getFicha() {
        return getFicha("Monstruo");
    }


    @Override
    public int atacar(Entidad enemigo) {
        return super.atacar(enemigo);
    }

    @Override
    public String toString() {
        return nombre + "-"  + getEspecie() + " (" + getPuntosVida() + ")";
    }

    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((especie == null) ? 0 : especie.hashCode());
        result = prime * result + vidaMax;
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Monstruo other = (Monstruo) obj;
        if (especie != other.especie)
            return false;
        if (vidaMax != other.vidaMax)
            return false;
        if (nivel != other.nivel)
            return false;
        return true;
    }


    public static Monstruo generaMonstruoAleatorio(int... nivelMonstruo){
        int i = rng.nextInt(10);
        //La probabilidad está en un % pero al todos ser divisores de 10 ahorro iteraciones en el for
        int p0 = Orco.probabilidad/10;
        int p1 = p0 + Aranha.probabilidad/10;
        int p2 = p1 + Troll.probabilidad/10;
        int p3 = p2 + Dragon.probabilidad/10;

        int especie;
        if (i < p0) {
            especie = 0;
        } else if (i < (p1)) {
            especie = 1;
        } else if (i < (p2)) {
            especie = 2;
        } else if (i < (p3)) {
            especie = 3;
        } else { //No debería ocurrir ya que con el if anterior ya cubrí las 10 posibilidades de "i"
            especie = 4;
        }
        boolean conNivel = nivelMonstruo != null && nivelMonstruo.length > 0;
        String nivel = null;
        if (conNivel) {
            if (nivelMonstruo[0] < 1 || nivelMonstruo[0] > 256) { throw new EntidadException("Nivel invalido"); }
            nivelMonstruo[0] -= Entidad.CONVERSOR;
            nivel = String.valueOf(nivelMonstruo[0]);
        }
        Monstruo este;
        switch (Especie.numToEspecie(especie)) {
            case ARANHA:
                este = new Aranha(null, null, null, null, nivel, null, false);
                break;
            case ORCO:
                este = new Orco(null, null, null, null, nivel, null, false);
                break;
            case TROLL:
                este = new Troll(null, null, null, null, nivel, null, false);
                break;
            case DRAGON:
                este = new Dragon(null, null, null, null, nivel, null, false);
                break;
            default:
                throw new EntidadException("Especie sin clase");
        }
        return este;
    }
}
