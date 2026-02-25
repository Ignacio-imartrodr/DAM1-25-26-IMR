package UD4.Rol.Objetos.Equipamiento.Arma;


public class Barita extends Arma {
    final static String KEY = "Barita";
    Barita(int num){
        super(KEY, num);
        this.nombre = super.nombre;
        this.rareza = super.rareza;
        this.durabilidad = super.durabilidad;
        this.xp = super.xp;
        this.lvl = super.lvl;
    }
}
