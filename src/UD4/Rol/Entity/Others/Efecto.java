package UD4.Rol.Entity.Others;

import UD4.Rol.Utilidades.EfectException;

public enum Efecto {

    Aturdimiento, Lentitud, Quemado;

    private int duration = 1;
    private int damage = 0;

    public Efecto efecto(String nombre) {
        //TODO verificar nombre
        Efecto efecto;
        try {
            nombre = nombre.strip();
            efecto = Efecto.valueOf(nombre.substring(0,1) + nombre.substring(1).toLowerCase().replace(" ", "_"));
            return efecto;
        } catch (Exception e) {
            throw new EfectException("Efecto no válido.");
        }
    
    }
    
    public String getTipo() {
        return this.name().toUpperCase();
    }
    public int getDuration() {
        return duration;
    }
    public int getDamage() {
        return damage;
    }

    public boolean setDuration(int duration) {
        if (duration < 0) {
            return false;
        }
        this.duration = duration;
        return true;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public String toString() {
        return this.name();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Efecto other = (Efecto) obj;
        if (tipo == null) {
            if (other.tipo != null)
                return false;
        } else if (!tipo.equals(other.tipo))
            return false;
        return true;
    }
}
