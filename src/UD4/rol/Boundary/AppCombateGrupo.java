package UD4.Rol.Boundary;

import UD4.Rol.Control.Combate;
import UD4.Rol.Entity.Entidades.Monstruos.Monstruo;
import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Utilidades.Util;

public class AppCombateGrupo {//TODO recrear a mi modo
    public static void main(String[] args) {
        System.out.println("=== Combate de Grupo: Personajes vs Monstruos ===");

        Personaje[] heroes = AppCreaPersonaje.pedirPersonajes();
        for (int i = 0; i < heroes.length; i++) {
            heroes[i].setId(i);
        }

        int monstruosCantidad;
        while (true) {
            System.out.print("¿Cuántos monstruos quieres en el grupo? (2-5): ");
            String opcion = Util.pedirPorTeclado(true);
            try {
                monstruosCantidad = Integer.parseInt(opcion);
                if (monstruosCantidad >= 2 && monstruosCantidad <= 5) {
                    break;
                }
            } catch (Exception e) {
            }
            System.out.println("Por favor ingresa un número entre 2 y 5.");
        }

        Monstruo[] monstruos = new Monstruo[monstruosCantidad];
        /*Random rnd = new Random();
        for (int i = 0; i < monstruosCantidad; i++) {
            String nombre = "Monstruo" + (i + 1);
            String fuerza = String.valueOf(rnd.nextInt(50) + 20);
            String agilidad = String.valueOf(rnd.nextInt(40) + 10);
            String constitucion = String.valueOf(rnd.nextInt(50) + 20);
            String nivel = "1";
            String experiencia = "0";
            monstruos[i].newMonstruo(nombre, fuerza, agilidad, constitucion, nivel, experiencia, true);//Cambiar por monstruo correspondiente
        }*/

        Personaje[] personajesCreados = heroes;
        System.out.println("Iniciando combate de grupo...");
        personajesCreados = Combate.combateGrupo(heroes, monstruos, personajesCreados);

        System.out.println("El combate de grupo ha finalizado.");
        AppCombateSingular.bucleGuardadoPersonajes(personajesCreados);
    }
}
