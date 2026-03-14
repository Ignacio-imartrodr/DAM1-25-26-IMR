package UD4.Rol.Entity.Entidades.Monstruos;

import java.util.Random;

import UD4.Rol.Entity.Entidades.Entidad;
import UD4.Rol.Entity.Others.Especie;
import UD4.Rol.Utilidades.EntidadException;
public abstract class Monstruo extends Entidad {
    Especie especie;
    
    private static Random rng = new Random();
    protected void newMonstruo(String nombre, String fuerza, String agilidad, String constitucion, String nivel, String experiencia, Especie especie, boolean yaExistente) {
        newEntidad(nombre, fuerza, agilidad, constitucion, nivel, experiencia, yaExistente);
        this.especie = especie;
    }

    @Override
    public int atacar(Entidad enemigo) {
        return super.atacar(enemigo);
    }

    @Override
    public String toString() {
        return getNombre() + "-"  + getEspecie() + " (" + getPuntosVida() + ")";
    }

	public Especie getEspecie() {
		return especie;
	}

    public Monstruo generaMonstruoAleatorio(){
        int[] prob = new int[10];
        //La probabilidad está en un % pero al todos ser divisores de 10 ahorro iteraciones en el for
        int p0 = Orco.probabilidad/10;
        int p1 = p0 + Aranha.probabilidad/10;
        int p2 = p1 + Troll.probabilidad/10;
        int p3 = p2 + Dragon.probabilidad/10;

        for (int i = 0; i < prob.length; i++) {
            if (i < p0) {
                prob[i] = 0;
            } else if (i < (p1)) {
                prob[i] = 1;
            } else if (i < (p2)) {
                prob[i] = 2;
            } else if (i < (p3)) {
                prob[i] = 3;
            } else {
                prob[i] = 4;
            }
        }
        Monstruo este;
        int rnd = rng.nextInt(prob.length - 1);
        int especie = prob[rnd];
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
