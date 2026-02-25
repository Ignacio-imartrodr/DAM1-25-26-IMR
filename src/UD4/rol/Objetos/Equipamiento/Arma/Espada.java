package UD4.Rol.Objetos.Equipamiento.Arma;

public class Espada extends Arma {
    
    final static String KEY = "Espada";
    Espada(int num){
        super(KEY, num);
        this.nombre = super.nombre;
        this.rareza = super.rareza;
        this.durabilidad = super.durabilidad;
        this.xp = super.xp;
        this.lvl = super.lvl;
    }
}
