package UD4.Rol.Control;

import java.util.Random;

import UD4.Rol.Boundary.AppCreaPersonaje;
import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Entity.Entidades.Monstruos.Monstruo;
import UD4.Rol.Utilidades.Util;

public class Combate {
    public static final String PREFERENCIAS_ATAQUE = "1 - Vida\n2 - Ataque\n4 - Agilidad";
    
    public static int efectoCuraEficace(int cura, int curaEficace){
        return (cura * curaEficace) / 100;
    }

    public static boolean validarCantCombatientes(Personaje[] personajesBaseGeneral, int fightersNeeded){
        while (personajesBaseGeneral.length < fightersNeeded) {
            System.out.println("No hay suficientes personajes guardados para jugar");
            if (Util.escogerOpcion("S", "n", "Quieres ejecutar la app de creación de Personajes para añadir más? (S/n)")) {
                System.out.println("_______________________________________________________");
                AppCreaPersonaje.main(personajesBaseGeneral);
                System.out.println("_______________________________________________________");
                personajesBaseGeneral = Creacion.getPersonajesFromJson(Guardado.RUTA_BASE_GENERAL);
            } else {
                return false;
            }
        }
        return true;
    }
    public static Personaje[] combateSingular(Personaje[] personajesEnBatalla, Personaje[] personajesCreados, int numCombatientes) {
        return personajesCreados;
    }

    public static Personaje[] combateMonstruos(Personaje[] personajes, Monstruo[] monstruos, Personaje[] personajesCreados) {//TODO revisar
        boolean heroesVivos = true;
        boolean monstruosVivos = true;
        while (heroesVivos && monstruosVivos) {
            // Turno de personajes
            for (int i = 0; i < personajes.length && heroesVivos && monstruosVivos; i++) {
                Personaje heroe = personajes[i];
                if (heroe == null || !heroe.estaVivo()) { continue; }
                System.out.println("\nTurno de " + heroe.toString());
                System.out.println("Monstruos vivos:");
                for (int j = 0; j < monstruos.length; j++) {
                    if (monstruos[j] != null && monstruos[j].estaVivo()) {
                        System.out.println((j + 1) + " - " + monstruos[j].toString());
                    }
                }
                System.out.println("¿A qué monstruo atacar? (número) o 0 para curar:");
                String acción = Util.pedirPorTeclado(true);
                int opción;
                try {
                    opción = Integer.parseInt(acción);
                } catch (NumberFormatException e) {
                    opción = -1;
                }
                if (opción == 0) {
                    heroe.curar();
                    System.out.println(heroe.getNombre() + " se ha curado hasta " + heroe.getPuntosVida() + " de vida.");
                } else {
                    int idx = opción - 1;
                    if (idx >= 0 && idx < monstruos.length && monstruos[idx] != null && monstruos[idx].estaVivo()) {
                        int ataque = (new Random().nextInt(100) + 1) + heroe.getFuerza();
                        int defensa = (new Random().nextInt(100) + 1) + monstruos[idx].getAgilidad();
                        int daño = Math.max(0, defensa - ataque);
                        if (daño > monstruos[idx].getPuntosVida()) {
                            daño = monstruos[idx].getPuntosVida();
                        }
                        monstruos[idx].perderVida(daño);
                        System.out.println(heroe.getNombre() + " ataca a " + monstruos[idx].getNombre() + " y causa " + daño + " de daño.");
                    } else {
                        System.out.println("Opción inválida. Se salta acción.");
                    }
                }
                heroesVivos = false;
                for (Personaje p : personajes) { if (p != null && p.estaVivo()) { heroesVivos = true; break; } }
                monstruosVivos = false;
                for (Monstruo m : monstruos) { if (m != null && m.estaVivo()) { monstruosVivos = true; break; } }
            }
            if (!monstruosVivos || !heroesVivos) break;
            // Turno de monstruos
            for (Monstruo monstruo : monstruos) {
                if (monstruo == null || !monstruo.estaVivo()) { continue; }
                Personaje objetivo = null;
                for (Personaje p : personajes) {
                    if (p != null && p.estaVivo()) { objetivo = p; break; }
                }
                if (objetivo == null) { heroesVivos = false; break; }
                int daño = monstruo.atacar(objetivo);
                System.out.println(monstruo.getNombre() + " ataca a " + objetivo.getNombre() + " y causa " + daño + " de daño.");
                heroesVivos = false;
                for (Personaje p : personajes) { if (p != null && p.estaVivo()) { heroesVivos = true; break; } }
            }
            heroesVivos = false;
            for (Personaje p : personajes) { if (p != null && p.estaVivo()) { heroesVivos = true; break; } }
            monstruosVivos = false;
            for (Monstruo m : monstruos) { if (m != null && m.estaVivo()) { monstruosVivos = true; break; } }
        }

        if (heroesVivos) {
            System.out.println("Los heroes han ganado la batalla de grupo!");
        } else {
            System.out.println("Los monstruos han ganado la batalla de grupo.");
        }

        for (int i = 0; i < personajes.length; i++) {
            if (personajes[i] != null) {
                for (int j = 0; j < personajesCreados.length; j++) {
                    if (personajesCreados[j] != null && personajesCreados[j].getId() == personajes[i].getId()) {
                        personajesCreados[j] = personajes[i];
                        break;
                    }
                }
            }
        }
        return personajesCreados;
    }
}

