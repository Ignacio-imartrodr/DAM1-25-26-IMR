package UD4.Rol.Boundary;

import java.util.Arrays;
import java.util.Random;

import UD4.Rol.Control.Combate;
import UD4.Rol.Control.Creacion;
import UD4.Rol.Control.Guardado;
import UD4.Rol.Entity.Entidades.Personaje;
import UD4.Rol.Entity.Others.Item;
import UD4.Rol.Entity.Others.Items;
import UD4.Rol.Entity.Others.Raza;
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

    private static Personaje[] getPersonajes(){//TODO implementar "BaseGeneral.json"
        Personaje[] personajesCreados = new Personaje[0];
        Personaje[] temp;
        while (personajesCreados.length < 2) {
            if (Util.escogerOpcion("S", "n", "¿Quieres cargar los personajes de un archivo? (S/n)")) {
                String rutaFichero;
                rutaFichero = Util.pedirRuta();
                if (!(rutaFichero == null)) {
                    temp = Combate.cargarPersonajesDeArchivo(rutaFichero);
                    for (Personaje personaje : temp) {
                        personajesCreados = Arrays.copyOf(personajesCreados, personajesCreados.length + 1);
                        personajesCreados[personajesCreados.length - 1] = personaje;
                    }
                }
            }
            temp = Creacion.pedirPersonajes();
            for (Personaje personaje : temp) {
                personajesCreados = Arrays.copyOf(personajesCreados, personajesCreados.length + 1);
                personajesCreados[personajesCreados.length - 1] = personaje;
            }
            if (personajesCreados.length < 2) {
                System.out.println("Se necesitan al menos 2 personajes para la batalla.");
            }
        }
        return personajesCreados;
    }
    public static void main(String[] args) {
        Personaje[] personajesCreados;
        personajesCreados = getPersonajes();
        Guardado.guardadoPersonajes(personajesCreados);

        Personaje[] personajesEnBatalla = new Personaje[2];
        if (personajesCreados.length > 2) {
            personajesEnBatalla = Creacion.seleccionarPersonajes(personajesCreados, 2);
            while (personajesEnBatalla[0] == null || personajesEnBatalla[1] == null) {
                System.out.println("No hay suficientes personajes para la batalla, selecciona al menos 2 personajes.");
                personajesEnBatalla = Creacion.seleccionarPersonajes(personajesCreados, 2);
            }
        } else {
            personajesEnBatalla[0] = personajesCreados[0];
            personajesEnBatalla[1] = personajesCreados[1];
        }
        int[] posDeCombatientesEnCreados = new int[0];
        for (int i = 0; i < personajesEnBatalla.length; i++) {
            for (int j = 0; j < personajesCreados.length; j++) {
                if (personajesCreados[j].equals(personajesEnBatalla[i])) {
                    Arrays.copyOf(posDeCombatientesEnCreados, posDeCombatientesEnCreados.length + 1);
                    posDeCombatientesEnCreados[posDeCombatientesEnCreados.length - 1] = j;
                }
            }
        }        
        
        //TODO añadir efectos de armadura y armas
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
                turno = !turno;
            }
            System.out.println("\nEl ganador es " + (personajesEnBatalla[0].estaVivo() ? personajesEnBatalla[0].toString() : personajesEnBatalla[1].toString()));
            if (Util.escogerOpcion("S", "n", "¿Otra batalla? (S/n)")) {
                personajesEnBatalla[0].curar();
                personajesEnBatalla[1].curar();
                for (int i = 0; i < posDeCombatientesEnCreados.length; i++) {
                    personajesCreados[posDeCombatientesEnCreados[i]] = personajesEnBatalla[i];
                }
                personajesEnBatalla = Creacion.seleccionarPersonajes(personajesCreados, 2);
                while (personajesEnBatalla[0] == null || personajesEnBatalla[1] == null) {
                    System.out.println("No hay suficientes personajes para la batalla, selecciona al menos 2 personajes.");
                    personajesEnBatalla = Creacion.seleccionarPersonajes(personajesCreados, 2);
                }
            } else {
                batalla = false;
            }
        }

        Guardado.guardadoPersonajes(personajesCreados);
    }
}
