package Extras;

import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Entity.Equipamiento.Equipamiento;
import UD4.Rol.Entity.Equipamiento.Arma.Barita;
import UD4.Rol.Entity.Equipamiento.Armadura.Casco;
import UD4.Rol.Entity.Others.Efectos.Efecto;

/**
 * @Author Ignacio MR
 */
public class Pruebas {
    public static void main(String[] args) throws Exception {
        
        Personaje p = new Personaje("p","Orco");
        Personaje a = new Personaje("a","Troll");
        Equipamiento b = new Barita(2);
        Equipamiento c = new Casco(1);
        p.addEfect(Efecto.newEfecto("Quemado", 2, 3, false));
        p.addEfect(Efecto.newEfecto("Agilidad", 2, 3, true));
        p.guardarEquipamiento(b);
        p.guardarEquipamiento(c);
        p.guardarEquipamiento(b);
        p.equipar(b);
        p.quitarEquipado(b);
        a.guardarEquipamiento(c);
        a.equipar(c);
        a.equipar(b);
        p.useHabilidadRaza(a);
        a.curar();
        p.atacar(a);
        System.out.println(a.getStringEquipamientoEquipado());
        System.out.println(p.getStringEquipamientoEquipado());

        a.quitarEquipado(0);
    }
}