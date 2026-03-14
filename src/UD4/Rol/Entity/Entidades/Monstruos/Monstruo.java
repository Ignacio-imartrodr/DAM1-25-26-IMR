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

    public static Monstruo generaMonstruoAleatorio(){
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
        } else { //No debería ocurrir ya que con el if anterior ya cubrí los 10 de "i"
            especie = 4;
        }

        Monstruo este;
        switch (Especie.numToEspecie(especie)) {
            case ARANHA:
                este = new Aranha(null, null, null, null, null, null, false);
                break;
            case ORCO:
                este = new Orco(null, null, null, null, null, null, false);
                break;
            case TROLL:
                este = new Troll(null, null, null, null, null, null, false);
                break;
            case DRAGON:
                este = new Dragon(null, null, null, null, null, null, false);
                break;
            default:
                throw new EntidadException("Especie sin clase");
        }
        return este;
    }
}
