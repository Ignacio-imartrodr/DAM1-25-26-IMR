package UD4.Rol.Boundary;

import UD4.Rol.Control.Combate;
import UD4.Rol.Entity.Entidades.Personaje;

public class AppCombateGrupos{
    public static void main(String[] args) {
        Personaje[] equipo0 = null;
        Personaje[] equipo1 = null;
        Combate.combateGrupo(equipo0, equipo1);
    }
}
