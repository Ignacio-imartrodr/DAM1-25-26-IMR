package UD4.Rol.Entity.Others;

public enum Efecto {

    ATURDIMIENTO, LENTITUD, QUEMADO;

    private int duration = 1;
    private int damage = 0;

    public String getTipo() {
        return this.name();
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
        return this.name().substring(0,1) + this.name().substring(1).toLowerCase();
    }
    public boolean equals(Efecto other) {
        if (!this.name().equals(other.name()))
            return false;
        return true;
    }
}
