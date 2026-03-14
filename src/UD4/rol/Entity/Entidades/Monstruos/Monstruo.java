package UD4.Rol.Entity.Entidades.Monstruos;

import UD4.Rol.Entity.Entidades.Entidad;
import UD4.Rol.Entity.Others.Especie;

public abstract class Monstruo extends Entidad {
    Especie especie;
    protected void newMonstruo(String nombre, String fuerza, String agilidad, String constitucion, String nivel, String experiencia, boolean yaExistente) {
        newEntidad(nombre, fuerza, agilidad, constitucion, nivel, experiencia, yaExistente);
    }

    @Override
    public int atacar(Entidad enemigo) {
        return super.atacar(enemigo);
    }

    @Override
    public String toString() {
        return "Monstruo " + getNombre() + " (Vida " + getPuntosVida() + "/" + getVidaMax() + ")";
    }

	public Especie getEspecie() {
		return especie;
	}
}
