package Extras;

import java.util.Arrays;

import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Entity.Equipamiento.Equipamiento;
import UD4.Rol.Entity.Equipamiento.Arma.Barita;
import UD4.Rol.Entity.Equipamiento.Armadura.Casco;

/**
 * @Author Ignacio MR
 */
public class Prueba {
    public static void main(String[] args) throws Exception {

        Personaje p = new Personaje();
        Personaje a = new Personaje();
        Equipamiento b = new Barita(2);
        Equipamiento c = new Casco(1);
        p.guardarEquipamiento(b);
        p.guardarEquipamiento(c);
        p.guardarEquipamiento(b);
        p.equipar(b);
        a.guardarEquipamiento(c);
        a.equipar(c);

        System.out.println(Arrays.toString(a.getEquipamientoEquipado()));
        System.out.println(Arrays.toString(p.getEquipamientoEquipado()));
    }
}