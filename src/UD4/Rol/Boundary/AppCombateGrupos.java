package UD4.Rol.Boundary;

import UD4.Rol.Control.Combate;
import UD4.Rol.Entity.Entidades.Personaje;

public class AppCombateGrupos extends AppCombateSingular{
    public static void main(String[] args) {
        Personaje[] equipo0;
        Personaje[] equipo1;
        Combate.combateGrupo(equipo0, equipo1);
    }
}
