package UD4.Rol.Control;

import java.util.Random;
import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Entity.Entidades.Monstruos.Monstruo;
import UD4.Rol.Utilidades.Util;

public class Combate {
    public static final String PREFERENCIAS_ATAQUE = "1 - Vida\n2 - Ataque\n4 - Agilidad";
    
    public static Personaje[] cargarPersonajesDeArchivo(String rutaFile){   
        Personaje[] personajesFichero = new Personaje[0];
        boolean restart = true;
        while (restart) {
            restart = false;
            System.out.println("Opciones: Json");
            System.out.print("¿Quieres cargar personajes desde " + rutaFile + "? (S/n): ");
            if (Util.escogerOpcion("S", "n")) {
                if (rutaFile.endsWith(".json") || rutaFile.startsWith("www.http") || rutaFile.startsWith("http")) {
                    if (rutaFile.startsWith("www.http") || rutaFile.startsWith("http")) {
                        boolean errorUrl = true;
                        String temp = rutaFile;
                        while (errorUrl) {
                            errorUrl = false;
                            try {
                                personajesFichero = Creacion.getPersonajesFromJson(rutaFile);
                                if (personajesFichero.length == 0) {
                                    System.out.println("El Json no contenía personajes");
                                }
                                System.out.print("¿Probar otra Url? (S/n): ");
                                if (Util.escogerOpcion("S", "n")) {
                                    errorUrl = true;
                                }
                            } catch (Exception e) {
                                errorUrl = true;
                                System.out.println("Error en la url");
                                System.out.print("URL? (\"n\" para salir): ");
                                temp = Util.pedirPorTeclado(false);
                                if (temp.equalsIgnoreCase("n")) {
                                    errorUrl = false;
                                    restart = true;
                                }
                            }
                        }
                    } else {
                        personajesFichero = Creacion.getPersonajesFromJson(rutaFile);
                        if (personajesFichero.length == 0) {
                            System.out.println("El fichero no contenía personajes");
                        }
                    }
                } else {
                    System.out.println("Error en la ruta");
                    System.out.print("Ruta? (\"n\" para salir): ");
                    rutaFile = Util.pedirPorTeclado(false);
                    if (rutaFile.equalsIgnoreCase("n")) {
                        System.out.println("personajes no cargados");
                        restart = false;
                    }
                }
            } else {
                System.out.println("Quierres ingresar otra ruta? (S/n)");
                if (Util.escogerOpcion("S", "n")) {
                    System.out.print("Ruta? (\"n\" para salir): ");
                    rutaFile = Util.pedirPorTeclado(false);
                    if (rutaFile.equalsIgnoreCase("n")) {
                        System.out.println("Personajes no cargados");
                        restart = false;
                    } else {
                        restart = true;
                    }
                } else {
                    System.out.println("No se han cargado personajes.");
                    restart = false;
                }
            }
        }
        return personajesFichero;
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
                    System.out.println(heroe.getNombre() + " se ha curado a " + heroe.getPuntosVida() + " de vida.");
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

   

    public static void combateGrupo(Personaje[] equipo0, Personaje[] equipo1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'combateGrupo'");
    }
}

