package UD4.rol;

import java.util.Random;

public class Personaje {
    String nombre;
    String raza;
    int fuerza;
    int agilidad;
    int constitucion;
    int nivel;
    int experiencia;
    int puntosVida = 50 + constitucion;
    final String[] RAZAS_VALIDAS = {"HUMANO", "ELFO", "ENANO", "HOBBIT", "ORCO", "TROLL"};
    public void crearPersonaje(){
        System.out.print("Nombre del personaje: ");
        nombre = Util.pedirPorTeclado(false);
        while (nombre.equals(null)) {
            System.out.print("Escoge un nombre de personaje: ");
            nombre = Util.pedirPorTeclado(false);          
        }
        System.out.println("Escoge una de las siguientes razas: orco, elfo, HUMANO, enano, hobbit o troll");
        raza = Util.pedirPorTeclado(false).toUpperCase();
        while ( !raza.equals(null) || !Util.verificaObjetoEnArray(raza, RAZAS_VALIDAS) ) {
            System.out.println("Raza no válida. Introduce uno de las siguientes: orco, elfo, HUMANO, enano, hobbit o troll");
            raza = Util.pedirPorTeclado(false).toUpperCase();
        }
        if (raza.equals(null)) {
            raza = "HUMANO";
        }
        Random rnd = new Random();
        String stat;
        System.out.print("Fuerza: ");
        stat = pedirStat(false);
        if (!stat.equals(null)) {
            fuerza = Integer.parseInt(pedirStat(false));
        } else {
            fuerza = rnd.nextInt(101);
        }
        System.out.print("Agilidad: ");
        stat = pedirStat(false);
        if (!stat.equals(null)) {
            agilidad = Integer.parseInt(pedirStat(false));
        } else {
            agilidad = rnd.nextInt(101);
        }
        System.out.print("Constitución: ");
        stat = pedirStat(false);
        if (!stat.equals(null)) {
            constitucion = Integer.parseInt(pedirStat(false));
        } else {
            constitucion = rnd.nextInt(101);
        }
        System.out.print("Nivel: ");
        stat = pedirStat(false);
        if (!stat.equals(null)) {
            nivel = Integer.parseInt(pedirStat(false));
        } else {
            nivel = 1;
        }
        experiencia = Integer.parseInt(pedirStat(true));
        
    }
    private String pedirStat(boolean esXp){
        String texto;
        texto = Util.pedirPorTeclado(true);
        final int MIN = esXp ? 0 : 1;
        final int MAX = 100;
        while ( !texto.equals(null) || (Integer.parseInt(texto) < MIN && Integer.parseInt(texto) > MAX)) {
            System.out.print("La estadística debe ser como mínimo " + MIN + " y como máximo " + MAX + ", da otro valor: ");
            texto = Util.pedirPorTeclado(true);
        }
        if (texto.equals(null)) {
            if (esXp) {
                texto = "0";
            } else {
                texto =  null;
            }
            
        }
        return texto;
    }
    public void mostrar(){
        String ficha;
        String Cabecera = "Ficha Personaje\n=================\n";
        String nombre = "Nombre: " + this.nombre;
        String nivel = "Nivel: " + this.nivel;
        String experiencia = "Experiencia: " + this.experiencia;
        String puntosVida = "Puntos de vida máximos: " + this.puntosVida;
        String fuerza = "Fuerza: " + this.fuerza;
        String agilidad = "Agilidad: " + this.agilidad;
        String constitucion = "Constitución: " + this.constitucion;
        String nombreRaza = this.raza.charAt(0) + this.raza.substring(1).toLowerCase();
        String raza = "Raza: " + nombreRaza;


        ficha = String.format("%s%s%n%s%n%s%n%s%n%s%n%s%n%s", Cabecera, nombre, raza, nivel, experiencia, puntosVida, fuerza, agilidad, constitucion);
        
        System.out.println(ficha);
    }
}
