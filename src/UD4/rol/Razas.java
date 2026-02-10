package UD4.rol;

public enum Razas {
    HUMANO, ELFO, ENANO, HOBBIT, ORCO, TROLL;

    @Override
    public String toString() {
        String nombre;
        nombre= this.name().charAt(0) + this.name().substring(1).toLowerCase();
        return nombre;
    }
}
