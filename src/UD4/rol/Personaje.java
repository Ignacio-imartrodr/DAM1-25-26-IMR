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
    int vidaMax = 50 + constitucion;
    int puntosVida = vidaMax;
    final static String[] RAZAS_VALIDAS = {"HUMANO", "ELFO", "ENANO", "HOBBIT", "ORCO", "TROLL"};
    static String[] asignarBonus(String raza) {
        String bonusFuerza = "x";
        String bonusAgilidad = "x";
        String bonusConstitucion = "x";
        switch (raza) {
                case "HUMANO":
                    bonusFuerza = "x";
                    bonusAgilidad = "x";
                    bonusConstitucion = "x";
                    break;
                case "ELFO":
                    bonusFuerza = "-5";
                    bonusAgilidad = "+15";
                    bonusConstitucion = "-10";
                    break;
                case "ENANO":
                    bonusFuerza = "+5";
                    bonusAgilidad = "-15";
                    bonusConstitucion = "+10";
                    break;
                case "HOBBIT":
                    bonusFuerza = "-10";
                    bonusAgilidad = "+20";
                    bonusConstitucion = "-5";
                    break;
                case "ORCO":
                    bonusFuerza = "+15";
                    bonusAgilidad = "-25";
                    bonusConstitucion = "+10";
                    break;
                case "TROLL":
                    bonusFuerza = "+10";
                    bonusAgilidad = "-25";
                    bonusConstitucion = "+15";
                    break;
            }
        String[] bonus = {bonusFuerza, bonusAgilidad, bonusConstitucion};
        return bonus;
    }

    private static int generarRnd1a100(){
        Random rnd = new Random();
        int num = rnd.nextInt(100) + 1;
        return num;
    };
    public void crearPersonaje(){
        System.out.print("Nombre del personaje: ");
        nombre = Util.pedirPorTeclado(false);
        while (nombre.equals(null) || nombre.strip().equals("")) {
            System.out.print("Escoge un nombre de personaje: ");
            nombre = Util.pedirPorTeclado(false);          
        }
        nombre = nombre.strip();
        System.out.println("Escoge una de las siguientes razas: orco, elfo, HUMANO, enano, hobbit o troll");
        raza = Util.pedirPorTeclado(false).toUpperCase();
        while ( !raza.equals(null) || !Util.verificaObjetoEnArray(raza, RAZAS_VALIDAS) ) {
            System.out.println("Raza no válida. Introduce uno de las siguientes: orco, elfo, HUMANO, enano, hobbit o troll");
            raza = Util.pedirPorTeclado(false).toUpperCase();
        }
        if (raza.equals(null)) {
            raza = "HUMANO";
        }
        String[] bonus = asignarBonus(raza);
        int bonusFuerza = bonus[0].equals("x") ? 0 : Integer.parseInt(bonus[0]);
        int bonusAgilidad = bonus[1].equals("x") ? 0 : Integer.parseInt(bonus[1]);
        int bonusConstitucion = bonus[2].equals("x") ? 0 : Integer.parseInt(bonus[2]);

        System.out.println("Introduce las siguientes estadísticas. Si quieres que se generen aleatoriamente, pulsa Enter sin introducir ningún valor.");
        System.out.print("Fuerza: ");
        fuerza = pedirStatRng() + bonusFuerza;
        
        System.out.print("Agilidad: ");
        agilidad = pedirStatRng() + bonusAgilidad;

        System.out.print("Constitución: ");
        constitucion = pedirStatRng() + bonusConstitucion;
        
        System.out.print("Nivel: ");
        nivel = pedirStatNoRng(false);
        
        System.out.println("Nivel de experiencia: ");
        experiencia = pedirStatNoRng(true);
        
        
    }

    private int pedirStatRng(){
        String texto;
        int num;
        texto = Util.pedirPorTeclado(true);
        final int MIN = 1;
        final int MAX = 100;
        while ( !texto.equals(null) || (Integer.parseInt(texto) < MIN && Integer.parseInt(texto) > MAX)) {
            System.out.print("La estadística debe ser como mínimo " + MIN + " y como máximo " + MAX + ", da otro valor: ");
            texto = Util.pedirPorTeclado(true);
        }
        if (texto.equals(null)) {
            num = generarRnd1a100();
        } else {
            num = Integer.parseInt(texto);
        }
        return num;
    }
    private int pedirStatNoRng(boolean esXp){
        String texto;
        int num;
        texto = Util.pedirPorTeclado(true);
        final int MIN = esXp ? 0 : 1;
        final int MAX = esXp ? 999 : 100;
        while ( !texto.equals(null) || (Integer.parseInt(texto) < MIN && Integer.parseInt(texto) > MAX)) {
            System.out.print("La estadística debe ser como mínimo " + MIN + " y como máximo " + MAX + ", da otro valor: ");
            texto = Util.pedirPorTeclado(true);
        }
        if (texto.equals(null)) {
            num = MIN;
        } else {
            num = Integer.parseInt(texto);
        }
        return num;
    }

    public void mostrar(){
        String ficha;
        String Cabecera = "Ficha Personaje\n=================\n";
        String nombre = "Nombre: " + this.nombre;
        String nombreRaza = this.raza.charAt(0) + this.raza.substring(1).toLowerCase();
        String raza = "Raza: " + nombreRaza;
        String nivel = "Nivel: " + this.nivel;
        String experiencia = "Experiencia: " + this.experiencia;
        String puntosVida = "Puntos de vida : " + this.puntosVida + "/" + vidaMax;
        String fuerza = "Fuerza: " + this.fuerza;
        String agilidad = "Agilidad: " + this.agilidad;
        String constitucion = "Constitución: " + this.constitucion;

        ficha = String.format("%s%s%n%s%n%s%n%s%n%s%n%s%n%s", Cabecera, nombre, raza, nivel, experiencia, puntosVida, fuerza, agilidad, constitucion);
        System.out.println(ficha);
    }
    public String toString(){
        String nombreYVida = nombre + " (" + puntosVida + "/" + vidaMax + ")";
        return nombreYVida;
    }
    public byte sumarExperiencia(int puntos){
        if (puntos > 255999) { //TODO probar exception
            throw new PersonajeException("Cantidad de experiencia excesiva para subir en una sola ejecución");
        }
        byte lvlsUp = 0;
        if (puntos/1000 != 0) {
            lvlsUp = (byte) (puntos / 1000);
            experiencia += puntos % 1000;
        }
        for (int i = 0; i < lvlsUp; i++) {
            subirNivel();
        }
        return lvlsUp;
    }
    public void subirNivel(){
        nivel++;
        fuerza += (fuerza*0.05);
        agilidad += (agilidad*0.05);
        constitucion += (constitucion*0.05);
        int oldVidaMax = vidaMax;
        vidaMax = 50 + constitucion;
        puntosVida += (vidaMax -  oldVidaMax);
    }
    public void curar(){
        if (puntosVida < vidaMax && puntosVida > 0) {
            puntosVida = vidaMax;
        }
    }
    public boolean perderVida(int puntos){
        boolean muerto = false;
        puntosVida -= puntos;
        if (puntosVida <= 0) {
            muerto = true;
        }
        return muerto;
    }
    public boolean estaVivo(){
        boolean vivo = true;
        if (puntosVida <= 0) {
            vivo = false;   
        }
        return vivo;
    }
    public int atacar(Personaje enemigo){
        int puntAtaque = generarRnd1a100() + fuerza;
        int puntDefensa = generarRnd1a100() + enemigo.agilidad;
        int daño = puntDefensa - puntAtaque;
        if ( daño > 0) {
            if (daño > enemigo.puntosVida) {
                daño = enemigo.puntosVida;
            }
            enemigo.perderVida(daño);
            enemigo.sumarExperiencia(daño);
            sumarExperiencia(daño);
        } else {
            daño = 0;
        }
        return daño;
    }
    public String getRazasStats() {
        String fichas = "";
        for (String raza : RAZAS_VALIDAS) {
            String[] bonus = asignarBonus(raza);
            fichas += String.format("Raza: %s%n-------------%nFuerza: %s, Agilidad: %s, Constitución: %s%n%n", raza, bonus[0], bonus[1], bonus[2]);
        }
        return fichas;
    }
    public String toJSONString() {
        return String.format("{\"nombre\":\"%s\",\"raza\":\"%s\",\"fuerza\":%d,\"agilidad\":%d,\"constitucion\":%d,\"nivel\":%d,\"experiencia\":%d,\"vidaMax\":%d,\"puntosVida\":%d}", nombre, raza, fuerza, agilidad, constitucion, nivel, experiencia, vidaMax, puntosVida);
    }
}
