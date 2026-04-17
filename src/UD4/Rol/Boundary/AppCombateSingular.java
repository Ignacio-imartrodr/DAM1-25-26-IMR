package UD4.Rol.Boundary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import UD4.Rol.Control.Combate;
import UD4.Rol.Control.Creacion;
import UD4.Rol.Control.Guardado;
import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Entity.Others.Item;
import UD4.Rol.Entity.Others.Items;
import UD4.Rol.Entity.Others.Raza;
import UD4.Rol.Entity.Others.Efectos.Efecto;
import UD4.Rol.Utilidades.EntidadException;
import UD4.Rol.Utilidades.ItemException;
import UD4.Rol.Utilidades.Util;

/**
 * @author Ignacio MR
 * 
 * Crea un programa de consola que permita al usuario generar
 * y editar personajes de diferentes modos y guardarlos
 * en disco en un fichero de texto en formato JSON.
 */

public class AppCombateSingular {
    public static void main(Personaje[] personajesBaseGeneral) {
        final int CANTIDAD_COMBATIENTES = 2;
        System.out.println("-----------App de Combate PvsP-----------");
        if (!Combate.validarCantCombatientes(personajesBaseGeneral, CANTIDAD_COMBATIENTES)) {
            return;
        }
        if (personajesBaseGeneral.length < CANTIDAD_COMBATIENTES) {
            personajesBaseGeneral = Creacion.getPersonajesFromJson(Guardado.RUTA_BASE_GENERAL);
        }
        Personaje[] persEnBatalla = new Personaje[2];
        if (personajesBaseGeneral.length > 2) {
            persEnBatalla = Creacion.seleccionarPersonajes(personajesBaseGeneral, CANTIDAD_COMBATIENTES);
        } else {
            persEnBatalla[0] = personajesBaseGeneral[0];
            persEnBatalla[1] = personajesBaseGeneral[1];
        }
        
        //TODO añadir efectos de armadura y armas
        boolean batalla = true;
        while (batalla) {
            boolean turno;
            if (persEnBatalla[0].getAgilidad() == persEnBatalla[1].getAgilidad()) {
                Random rnd = new Random();
                turno = rnd.nextBoolean();
            } else if (persEnBatalla[0].getAgilidad() > persEnBatalla[1].getAgilidad()) {
                turno = true;
            } else {
                turno = false;
            }
            boolean dosHobbit = persEnBatalla[0].getRaza().equals(Raza.HOBBIT) && persEnBatalla[1].getRaza().equals(Raza.HOBBIT);
            //byte[] turnosEfectoAccion = new byte[] {-1, -1};
            //int[][] buffEnAccion = new int[][] {{0, 0, 0, 0},{0, 0, 0, 0}};
            boolean[] puedeAtacar = new boolean[] {true, true};
            while (persEnBatalla[0].estaVivo() && persEnBatalla[1].estaVivo()) {
                byte perEnTurno = (byte) (turno ? 0 : 1);
                Personaje perActuando = persEnBatalla[perEnTurno];
                Personaje enemigo = persEnBatalla[1 - perEnTurno];
                Efecto[] efectosPerAct = perActuando.getEfectosAlterados();
                Efecto[] efectosEnemigo = enemigo.getEfectosAlterados();
                String accion;
                boolean accionNoValida = true;
                int xp = 0;
                for (int i = 0; i < efectosPerAct.length; i++) {
                    Efecto efecto = efectosPerAct[i];
                    String nombre = efecto.getTipo();
                    switch (nombre) {
                        case "ATURDIMIENTO":
                            int duration = efecto.getDuration();
                            if (duration > 0) {
                                efecto.setDuration(duration - 1);
                                if (duration > 0) {
                                    perActuando.addEfect(efecto);
                                } else {
                                    perActuando.endEfect(nombre);
                                }
                                //TODO terminar
                            }
                            break;
                        case "LENTITUD":
                            duration = efecto.getDuration();
                            if (duration > 0) {
                                efecto.setDuration(duration - 1);
                                //TODO terminar
                            }
                            break;
                        case "QUEMADO":
                            duration = efecto.getDuration();
                            if (duration > 0) {
                                efecto.setDuration(duration - 1);
                                perActuando.perderVida(efecto.getCantEfect());
                            }
                            break;
                        default:
                            break;
                    }
                    perActuando.addEfect(efecto);
                }
                for (int i = 0; i < efectosEnemigo.length; i++) {
                    Efecto efecto = efectosEnemigo[i];
                    String nombre = efecto.getTipo();
                    switch (nombre) {
                        case "ATURDIMIENTO":
                            int duration = efecto.getDuration();
                            if (duration > 0) {
                                efecto.setDuration(duration - 1);
                                if (duration > 0) {
                                    perActuando.addEfect(efecto);
                                } else {
                                    perActuando.endEfect(nombre);
                                }
                                //TODO terminar
                            }
                            break;
                        case "LENTITUD":
                            duration = efecto.getDuration();
                            if (duration > 0) {
                                efecto.setDuration(duration - 1);
                                //TODO terminar
                            }
                            break;
                        case "QUEMADO":
                            duration = efecto.getDuration();
                            if (duration > 0) {
                                efecto.setDuration(duration - 1);
                                perActuando.perderVida(efecto.getCantEfect());
                            }
                            break;
                        default:
                            break;
                    }
                    perActuando.addEfect(efecto);
                }
                System.out.println("\nTurno de " + perActuando.toString());
                while (accionNoValida) {
                    if (turnosEfectoAccion[perEnTurno] >= 0) {
                        buffEnAccion[perEnTurno] = Raza.buffHabilidad(perActuando);
                    }
                    if (ardiendo[perEnTurno]) {
                        perActuando.perderVida(damageFuego[perEnTurno]);
                        contLlamas[perEnTurno]--;
                    }
                    if (ardiendo[1 - perEnTurno]) {
                        perActuando.perderVida(damageFuego[1 - perEnTurno]);
                        contLlamas[1 - perEnTurno]--;
                    }
                    perActuando.asignarBonus(buffEnAccion[perEnTurno], false);
                    enemigo.asignarBonus(buffEnAccion[1 - perEnTurno], false);
                    System.out.println("¿Qué va a hacer? [ 1 - Atacar | 2 - Curar | 3 - " + perActuando.stringHabilidadRaza() + " | 4 - Abrir bolsa | 5 - Huir ]");
                    accion = Util.pedirPorTeclado(true);
                    switch (Integer.parseInt(accion)) {
                        case 1:
                            if (puedeAtacar[perEnTurno]) {
                                xp = perActuando.atacar(enemigo);
                                System.out.println(perActuando.getNombre() + "atacó a " + enemigo.getNombre());
                                try {
                                    perActuando.sumarExperiencia(xp);
                                    enemigo.sumarExperiencia(xp);
                                } catch (EntidadException e) {
                                    int xpRest = xp;
                                    for (; xpRest >= 125000; xpRest -= 125000) {
                                        perActuando.sumarExperiencia(125000);
                                        enemigo.sumarExperiencia(125000);
                                    }
                                    perActuando.sumarExperiencia(xpRest);
                                    enemigo.sumarExperiencia(xpRest);
                                }
                            } else {
                                puedeAtacar[perEnTurno] = true;
                            }
                            if (xp == 0) {
                                System.out.println("El ataque falló!");
                            } else {
                                System.out.println("El personaje \"" + enemigo.getNombre() + "\" recibió " + xp + " de daño!");
                            }
                            accionNoValida = false;
                            break;
                        case 2:
                            perActuando.curar();
                            accionNoValida = false;
                            break;
                        case 3:
                            if (perActuando.isHabilidadRazaActiva()) {
                                turnosEfectoAccion[perEnTurno] = perActuando.duracionHabilidadRaza(enemigo);
                                if (turnosEfectoAccion[perEnTurno] == -1) {
                                    if (dosHobbit) {
                                        System.out.println("No se puede robar la hablilidad \"Steal\"");
                                    } else {
                                        System.out.println("La habilidad no se puede utilizar durante este turno!");
                                    }
                                } else {
                                    if (turnosEfectoAccion[perEnTurno] == 0) {
                                        buffEnAccion[perEnTurno] = Raza.buffHabilidad(perActuando);
                                        perActuando.asignarBonus(buffEnAccion[perEnTurno], false);
                                    }
                                    accionNoValida = false;
                                }
                            } else {
                                System.out.println("La habilidad no se puede utilizar durante este turno!");
                            }
                            break;
                        case 4:
                            String bolsa = perActuando.getStringBolsa();
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
                                perActuando.usarObjeto(ubNomObjeto);
                                switch (Items.stringToItems(objeto.getNombre())) {
                                    case POCION_VIDA:
                                        perActuando.perderVida(-objeto.getSanar());
                                        break;
                                    case BOMBA_DE_HUMO:
                                        puedeAtacar[1 - perEnTurno] = false;
                                        break;
                                    case ENREDADERAS:
                                        enemigo.quitarHabilidadRaza();
                                        break;
                                    case MECHERO:
                                        damageFuego[1 - perEnTurno] += objeto.getDamage();
                                        contLlamas[1 - perEnTurno] = objeto.getDuracion();
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
                            perActuando.perderVida(perActuando.getPuntosVida());
                            System.out.println("\"" + perActuando.getNombre() + "\" ha huido del enfrentamiento!");
                            accionNoValida = false;
                            break;
                        default:
                            System.out.println("Acción no válida.");
                            break;
                    }
                }
                turnosEfectoAccion[perEnTurno]--;
                perActuando.asignarBonus(buffEnAccion[perEnTurno], true);
                enemigo.asignarBonus(buffEnAccion[1 - perEnTurno], true);
                if (!perActuando.isHabilidadRazaActiva()) {
                    perActuando.activarHabilidadRaza();
                }
                persEnBatalla[perEnTurno] = perActuando;
                persEnBatalla[1 - perEnTurno] = enemigo;
                turno = !turno;
            }
            System.out.println("\nEl ganador es " + (persEnBatalla[0].estaVivo() ? persEnBatalla[0].toString() : persEnBatalla[1].toString()));
            if (Util.escogerOpcion("S", "n", "¿Otra batalla? (S/n)")) {
                persEnBatalla[0].curar();
                persEnBatalla[1].curar();
                for (int i = 0; i < persEnBatalla.length; i++) {
                    personajesBaseGeneral[persEnBatalla[i].getId()] = persEnBatalla[i];
                }
                persEnBatalla = Creacion.seleccionarPersonajes(personajesBaseGeneral, 2);
                while (persEnBatalla[0] == null || persEnBatalla[1] == null) {
                    System.out.println("No hay suficientes personajes para la batalla, selecciona al menos 2 personajes.");
                    persEnBatalla = Creacion.seleccionarPersonajes(personajesBaseGeneral, 2);
                }
            } else {
                batalla = false;
            }
        }

        Guardado.guardadoPersonajes(personajesBaseGeneral);
    }
}
