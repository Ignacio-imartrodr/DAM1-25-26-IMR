package UD4.Rol.Boundary;

import java.util.Random;

import UD4.Rol.Control.Combate;
import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Entity.Entidades.Monstruos.Monstruo;
import UD4.Rol.Utilidades.Util;

public class AppCombateMonstruos {//TODO recrear a mi modo
    public static void main(String[] args) {
        System.out.println("=== Personajes vs Monstruos ===");

        Personaje[] personajes = AppCreaPersonaje.pedirPersonajes();
        for (int i = 0; i < personajes.length; i++) {
            personajes[i].setId(i);
        }

        int cantidadMonstruos = -1;
        int nivelMonstruos = -1;
        String opcion = null;
        while (opcion == null) {
            System.out.print("¿Cuántos monstruos van a enfrentar?: ");
            opcion = Util.pedirPorTeclado(true);

            if (opcion == null) { continue; }
            
            cantidadMonstruos = Integer.parseInt(opcion);
            while (cantidadMonstruos >= 0) {
                System.out.println("El combate debe ser contra al menos 1 monstruo");
                opcion = Util.pedirPorTeclado(true);
                if (opcion == null) { break; }
                cantidadMonstruos = Integer.parseInt(opcion);
            }
        }

        System.out.print("¿Que nivel tendran los monstruos? (Entre 1 y 256 o \"Enter\" para aleatorio): ");
        opcion = Util.pedirPorTeclado(true);
        if (opcion != null) {
            nivelMonstruos = Integer.parseInt(opcion);
            while (nivelMonstruos >= 256 || nivelMonstruos <= 0) {
                System.out.println("El combate debe ser contra al menos 1 monstruo");
                opcion = Util.pedirPorTeclado(true);
                cantidadMonstruos = Integer.parseInt(opcion);
            }
        }

        Monstruo[] monstruos = new Monstruo[cantidadMonstruos];
        for (int i = 0; i < cantidadMonstruos; i++) {
            monstruos[i] = Monstruo.generaMonstruoAleatorio(nivelMonstruos == -1 ? null : nivelMonstruos);
        }
        Util.sortArray(monstruos);

        int preferencia;
        boolean orden;
        System.out.println("¿Que preferencia de ataque habrá? (Número o \"Enter\" para aleatorio): ");
        System.out.println(Combate.PREFERENCIAS_ATAQUE);
        opcion = Util.pedirPorTeclado(true);
        if (opcion != null) {
            preferencia = Integer.parseInt(opcion);
            while (preferencia < 0 || preferencia > 3) {
                System.out.println("Escoge una de las opciones (O \"Enter\" para aleatorio):");
                System.out.println(Combate.PREFERENCIAS_ATAQUE);
                opcion = Util.pedirPorTeclado(true);
                if (opcion == null) { break; }
                preferencia = Integer.parseInt(opcion);
            }
            if (opcion != null) {
                System.out.println("Atacar primero al que tiene \"Mayor\" o \"Menor\" stat? (\"Enter\" para \"Mayor\"):");
                orden = Util.escogerOpcion("Mayor", "Menor");
                opcion = orden ? "Mayor" : "Menor";
            }
        }
        if (opcion == null) {
            Random rng = new Random();
            //TODO escoger preferencia rnd
            orden = rng.nextBoolean();
            opcion = orden ? "Mayor" : "Menor";
        }
        //TODO aplicar preferencia y opcion (orden)
        Personaje[] personajesCreados = personajes;
        System.out.println("Iniciando combate de grupo...");
        personajesCreados = Combate.combateMonstruos(personajes, monstruos, personajesCreados);

        System.out.println("El combate de grupo ha finalizado.");
        AppCombateSingular.bucleGuardadoPersonajes(personajesCreados);
    }
}
