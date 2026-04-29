package UD4.Rol.Boundary;

import java.util.Random;

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
    public static void main() {
        final int CANTIDAD_COMBATIENTES = 2;
        System.out.println("-----------App de Combate PvsP-----------");
        Personaje[] personajesBaseGeneral = Creacion.getPersonajesFromJson(Guardado.RUTA_BASE_GENERAL);
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
            System.out.println("Se escogieron los dos únicos pesonajes disponibles.");
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
            boolean[] puedeAtacar = new boolean[] {true, true};
            while (persEnBatalla[0].estaVivo() && persEnBatalla[1].estaVivo()) {
                byte perEnTurno = (byte) (turno ? 0 : 1);
                Personaje perActuando = persEnBatalla[perEnTurno];
                Personaje enemigo = persEnBatalla[1 - perEnTurno];
                String accion;
                boolean accionNoValida = true;
                int curaEfect = perActuando.aplicarEfectos();
                int xp = 0;
                
                if (perActuando.isAturdido()) {
                    System.out.println("\nTurno de " + perActuando.toString());
                    System.out.println("\n¡Pero " + perActuando.toString() + " está aturdido y pierde el turno!");
                } else {
                    System.out.println("\nTurno de " + perActuando.toString());
                    while (accionNoValida) {
                        System.out.println("¿Qué va a hacer? [ 1 - Atacar | 2 - Curar | 3 - " + perActuando.stringHabilidadRaza() + " | 4 - Abrir bolsa | 5 - Huir ]");
                        accion = Util.pedirPorTeclado(true);
                        switch (Integer.parseInt(accion)) {
                            case 1:
                                if (puedeAtacar[perEnTurno]) {
                                    xp = perActuando.atacar(enemigo);
                                    System.out.println(perActuando.getNombre() + " atacó a " + enemigo.getNombre());
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
                                    if (xp == 0) {
                                        System.out.println("El ataque falló!");
                                    } else {
                                        System.out.println("El personaje \"" + enemigo.getNombre() + "\" recibió " + xp + " de daño!");
                                    }
                                    accionNoValida = false;
                                } else {
                                    System.out.print(perActuando.toString() + " no puede atacar en este turno!");
                                }
                                break;
                            case 2:
                                int cura = (perActuando.getVidaMax() - perActuando.getPuntosVida()) + (curaEfect > 0 ? 0 : curaEfect);
                                perActuando.curar(cura);
                                System.out.println(perActuando.getNombre() + " se curó " + cura + "de vida!");
                                accionNoValida = false;
                                break;
                            case 3:
                                if (!perActuando.useHabilidadRaza(enemigo)) {
                                    if (dosHobbit) {
                                        System.out.println("No se puede robar la hablilidad \"Steal\"");
                                    } else {
                                        System.out.println("La habilidad no se puede utilizar durante este turno!");
                                    }
                                } else {
                                    perActuando.addEfect(Raza.buffHabilidad(perActuando));
                                    perActuando.setCoolDownHabilidadRaza();
                                    accionNoValida = false;
                                }
                                break;
                            case 4:
                                String bolsa = perActuando.getStringBolsa();
                                if (bolsa == null) {
                                    System.out.println("No tienes objetos disponibles!");
                                    System.out.println("Saliendo de la bolsa...");
                                    break;
                                }
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
                                    switch (Items.stringToItems(objeto.getNombre())) {
                                        case POCION_VIDA:
                                            perActuando.curar((objeto.getSanar() + Combate.efectoCuraEficace(objeto.getSanar(), curaEfect)));
                                            break;
                                        case BOMBA_DE_HUMO:
                                            puedeAtacar[1 - perEnTurno] = false;
                                            break;
                                        case ENREDADERAS:
                                            enemigo.quitarHabilidadRaza();
                                            break;
                                        case MECHERO:
                                            if (!enemigo.addEfect(Efecto.newEfecto("Quemado", objeto.getDuracion(), objeto.getDamage(), false))){
                                                throw new ItemException("Error añadiendo el efecto");
                                            }
                                        default:
                                            throw new ItemException("Item sin acción asignada.");
                                    }
                                    System.out.println("Usaste \"" + objeto.getNombre() + "\"!");
                                    perActuando.usarObjeto(ubNomObjeto);
                                    accionNoValida = false;
                                } catch (Exception e) {
                                    System.out.println("Saliendo de la bolsa...");
                                }
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
                }
                if (!perActuando.isHabilidadRazaActiva() && !perActuando.HabilidadRazaHaveCoolDown()) {
                    perActuando.activarHabilidadRaza();
                }
                if (!puedeAtacar[perEnTurno]){
                    puedeAtacar[perEnTurno] = true;
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
