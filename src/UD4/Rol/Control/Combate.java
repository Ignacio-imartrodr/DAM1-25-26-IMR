package UD4.Rol.Control;

import java.util.Random;
import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Entity.Entidades.Monstruos.Monstruo;
import UD4.Rol.Entity.Others.Item;
import UD4.Rol.Entity.Others.Items;
import UD4.Rol.Entity.Others.Raza;
import UD4.Rol.Utilidades.EntidadException;
import UD4.Rol.Utilidades.ItemException;
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
                                personajesFichero = Creacion.getPersonajesJson(rutaFile);
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
                        personajesFichero = Creacion.getPersonajesJson(rutaFile);
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
    public static Personaje[] combateSingular(Personaje[] personajesEnBatalla, Personaje[] personajesCreados, int numCombatientes) {//TODO añadir efectos de armadura y armas
        boolean batalla = true;
        while (batalla) {
            boolean turno;
            if (personajesEnBatalla[0].getAgilidad() == personajesEnBatalla[1].getAgilidad()) {
                Random rnd = new Random();
                turno = rnd.nextBoolean();
            } else if (personajesEnBatalla[0].getAgilidad() > personajesEnBatalla[1].getAgilidad()) {
                turno = true;
            } else {
                turno = false;
            }
            boolean dosHobbit = personajesEnBatalla[0].getRaza().equals(Raza.HOBBIT) && personajesEnBatalla[1].getRaza().equals(Raza.HOBBIT);
            byte[] turnosEfectoAccion = new byte[] {-1, -1};
            int[][] buffEnAccion = new int[][] {{0, 0, 0, 0},{0, 0, 0, 0}};
            boolean[] puedeAtacar = new boolean[] {true, true};
            boolean[] ardiendo = new boolean[] {false, false};
            int[] contLlamas = new int[] {0, 0};
            int[] damageFuego = new int[] {0, 0};
            while (personajesEnBatalla[0].estaVivo() && personajesEnBatalla[1].estaVivo()) {
                byte personajeEnTurno = (byte) (turno ? 0 : 1);
                Personaje personajeActuando = personajesEnBatalla[personajeEnTurno];
                Personaje enemigo = personajesEnBatalla[1 - personajeEnTurno];
                String accion;
                boolean accionNoValida = true;
                int xp = 0;
                if (contLlamas[personajeEnTurno] > 0) {
                    ardiendo[personajeEnTurno] = true;
                } else {
                    ardiendo[personajeEnTurno] = false;
                    damageFuego[personajeEnTurno] = 0;
                }
                if (contLlamas[1 - personajeEnTurno] > 0) {
                    ardiendo[1 - personajeEnTurno] = true;
                } else {
                    ardiendo[1 - personajeEnTurno] = false;
                    damageFuego[1 - personajeEnTurno] = 0;
                }
                System.out.println("\nTurno de " + personajeActuando.toString());
                while (accionNoValida) {
                    if (turnosEfectoAccion[personajeEnTurno] >= 0) {
                        buffEnAccion[personajeEnTurno] = Raza.buffHabilidad(personajeActuando);
                    }
                    if (ardiendo[personajeEnTurno]) {
                        personajeActuando.perderVida(damageFuego[personajeEnTurno]);
                        contLlamas[personajeEnTurno]--;
                    }
                    if (ardiendo[1 - personajeEnTurno]) {
                        personajeActuando.perderVida(damageFuego[1 - personajeEnTurno]);
                        contLlamas[1 - personajeEnTurno]--;
                    }
                    personajeActuando.asignarBonus(buffEnAccion[personajeEnTurno], false);
                    enemigo.asignarBonus(buffEnAccion[1 - personajeEnTurno], false);
                    System.out.println("¿Qué va a hacer? [ 1 - Atacar | 2 - Curar | 3 - " + personajeActuando.stringHabilidadRaza() + " | 4 - Abrir bolsa | 5 - Huir ]");
                    accion = Util.pedirPorTeclado(true);
                    switch (Integer.parseInt(accion)) {
                        case 1:
                            if (puedeAtacar[personajeEnTurno]) {
                                xp = personajeActuando.atacar(enemigo);
                                try {
                                    personajeActuando.sumarExperiencia(xp);
                                    enemigo.sumarExperiencia(xp);
                                } catch (EntidadException e) {
                                    int xpRest = xp;
                                    for (; xpRest >= 125000; xpRest -= 125000) {
                                        personajeActuando.sumarExperiencia(125000);
                                        enemigo.sumarExperiencia(125000);
                                    }
                                    personajeActuando.sumarExperiencia(xpRest);
                                    enemigo.sumarExperiencia(xpRest);
                                }
                            } else {
                                puedeAtacar[personajeEnTurno] = true;
                            }
                            if (xp == 0) {
                                System.out.println("El ataque falló!");
                            } else {
                                System.out.println("El personaje \"" + enemigo.getNombre() + "\" recibió " + xp + " de daño!");
                            }
                            accionNoValida = false;
                            break;
                        case 2:
                            personajeActuando.curar();
                            accionNoValida = false;
                            break;
                        case 3:
                            if (personajeActuando.isHabilidadRazaActiva()) {
                                turnosEfectoAccion[personajeEnTurno] = personajeActuando.duracionHabilidadRaza(enemigo);
                                if (turnosEfectoAccion[personajeEnTurno] == -1) {
                                    if (dosHobbit) {
                                        System.out.println("La habilidad de raza no surte efecto");
                                        accionNoValida = false;
                                    } else {
                                        System.out.println("La habilidad no se puede utilizar durante este turno!");
                                    }
                                } else {
                                    if (turnosEfectoAccion[personajeEnTurno] == 0) {
                                        buffEnAccion[personajeEnTurno] = Raza.buffHabilidad(personajeActuando);
                                        personajeActuando.asignarBonus(buffEnAccion[personajeEnTurno], false);
                                    }
                                    accionNoValida = false;
                                }
                            } else {
                                System.out.println("La habilidad no se puede utilizar durante este turno!");
                            }
                            break;
                        case 4:
                            String bolsa = personajeActuando.getStringBolsa();
                            System.out.println(bolsa);
                            System.out.println("Escoge Objeto (número de la izquierda) u otro número para cambiar de opción: ");
                            accion = Util.pedirPorTeclado(true);
                            int ubNomObjeto = Integer.parseInt(accion);
                            String ubNom = ubNomObjeto + " - ";
                            int ubNomIndex = bolsa.indexOf(ubNom);
                            if (ubNomIndex == -1) {
                                System.out.println("Objeto no válido. Saliendo de la bolsa...");
                                break;
                            }
                            int ubNomObjetoPos = ubNomIndex + ubNom.length();
                            accion = bolsa.substring(ubNomObjetoPos, ubNomObjetoPos + bolsa.substring(ubNomObjetoPos).indexOf(" ("));
                            try {
                                Item objeto = new Item(accion);
                                personajeActuando.usarObjeto(ubNomObjeto);
                                switch (Items.stringToItems(objeto.getNombre())) {
                                    case POCION_VIDA:
                                        personajeActuando.perderVida(-objeto.getSanar());
                                        break;
                                    case BOMBA_DE_HUMO:
                                        puedeAtacar[1 - personajeEnTurno] = false;
                                        break;
                                    case ENREDADERAS:
                                        enemigo.quitarHabilidadRaza();
                                        break;
                                    case MECHERO:
                                        damageFuego[1 - personajeEnTurno] += objeto.getDamage();
                                        contLlamas[1 - personajeEnTurno] = objeto.getDuracion();
                                        break;
                                    default:
                                        throw new ItemException("Item sin acción asignada.");
                                }
                                System.out.println("Usaste \"" + objeto.getNombre() + "\"!");
                            } catch (Exception e) {
                                System.out.println("Saliendo de la bolsa...");
                            }
                            accionNoValida = false;
                            break;
                        case 5:
                            personajeActuando.perderVida(personajeActuando.getPuntosVida());
                            System.out.println("\"" + personajeActuando.getNombre() + "\" ha huido del enfrentamiento!");
                            accionNoValida = false;
                            break;
                        default:
                            System.out.println("Acción no válida.");
                            break;
                    }
                }
                turnosEfectoAccion[personajeEnTurno]--;
                personajeActuando.asignarBonus(buffEnAccion[personajeEnTurno], true);
                enemigo.asignarBonus(buffEnAccion[1 - personajeEnTurno], true);
                if (!personajeActuando.isHabilidadRazaActiva()) {
                    personajeActuando.activarHabilidadRaza();
                }
                personajesEnBatalla[personajeEnTurno] = personajeActuando;
                personajesEnBatalla[1 - personajeEnTurno] = enemigo;
                turno = Util.alternarBoolean(turno);
            }
            System.out.println("\nEl ganador es " + (personajesEnBatalla[0].estaVivo() ? personajesEnBatalla[0].toString() : personajesEnBatalla[1].toString()));
            System.out.println("¿Otra batalla? (S/n)");
            if (Util.escogerOpcion("S", "n")) {
                personajesEnBatalla[0].curar();
                personajesEnBatalla[1].curar();
                for (int i = 0; i < personajesEnBatalla.length; i++) {
                    for (int j = 0; j < personajesCreados.length; j++) {
                        if (personajesCreados[j].getId() == personajesEnBatalla[i].getId()) {
                            personajesCreados[j] = personajesEnBatalla[j];
                        }
                    }
                }
                personajesEnBatalla = Creacion.seleccionarPersonajes(personajesCreados, numCombatientes);
                while (personajesEnBatalla[0] == null || personajesEnBatalla[1] == null) {
                    System.out.println("No hay suficientes personajes para la batalla, selecciona al menos 2 personajes.");
                    personajesEnBatalla = Creacion.seleccionarPersonajes(personajesCreados, numCombatientes);
                }
            } else {
                batalla = false;
            }
        }
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

