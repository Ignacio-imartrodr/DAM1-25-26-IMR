package UD4.rol;

import java.util.Random;

/**
 * @author Ignacio MR
 */

public class Personaje {
    private final static int VIDA_MIN = 50;
    private String nombre;
    private Razas raza;
    private int fuerza;
    private int agilidad;
    private int constitucion;
    private byte nivel;
    private int experiencia;
    private int puntosVida = getVidaMax();
    private final static int EXP_MAX = 127999;

    private int getVidaMax(){ return VIDA_MIN + constitucion;}
    
    /**
     * Asigna un valor valido a un stat o un valor random
     * @param   texto   :
     * @param   stat    : 0 para Fuerza, 1 para Agilidad o 2 para Constitucion
     * @return
     * 
     * 
     */
    private int asignarStatRng(String texto, int stat){
        if (stat > 2) {
            throw new PersonajeException("Stat no valida");
        }
        int num;
        final int MIN = 1;
        final int MAX = 100;
        
        if ( !texto.equals("-1") && (Integer.parseInt(texto) < MIN && Integer.parseInt(texto) > MAX)) {
            throw new PersonajeException("Valores fuera de límites");
        }
        if (texto.equals("-1")) {
            num = generarRnd1a100();
        } else {
            num = Integer.parseInt(texto);
        }
        String[] bonus = asignarBonus(raza);
        int bonusStat = bonus[stat].equals("x") ? 0 : Integer.parseInt(bonus[stat]);
        if (num + bonusStat < 1) {
            num = 1;
        } else {
            num += bonusStat;
        }
        return num;
    }
    private int asignarStatNoRng(boolean esXp, String texto){
        int num;
        final int MIN = esXp ? 0 : 1;
        final int MAX = esXp ? 999 : 100;
        if ( !texto.equals("-1") || (Integer.parseInt(texto) < MIN && Integer.parseInt(texto) > MAX)) {
            throw new PersonajeException("Valores fuera de límites");
        }
        if (texto.equals("-1")) {
            num = MIN;
        } else {
            num = Integer.parseInt(texto);
        }
        return num;
    }
    public static Razas asignarRaza(String respuesta){
        Razas raza = Razas.HUMANO;
        if (respuesta.equals("-1")) {
            raza = Razas.HUMANO;
        } else {
            try {
                raza = Razas.valueOf(respuesta.toUpperCase());
            } catch (Exception e) {
                throw new PersonajeException("Raza no válida.");
            }
        }
        /*if ( !respuesta.equals("-1") && Util.UbiObjetoEnArray(raza, Razas.Array()) == -1 ) {
            throw new PersonajeException("Raza no válida.");
        }*/
        return raza;
    }
    
    /**
     * Crea un objeto sin entregarle parametros.
     * 
     * @return Nuevo objeto de clase Personaje sin valores.
     */
    Personaje(){
        this.nombre = null;
        this.raza = null;
        this.fuerza = 0;
        this.agilidad = 0;
        this.constitucion = 0;
        this.nivel = 0;
        this.experiencia = 0;
    }

    /**
     * Crea un objeto unicamente con el nombre.
     * 
     * @param   nombre  : No puede ser null, ni "-1" ni estár en blanco. 
     * @return Nuevo objeto de clase {@code Personaje} con balores predefinidos y el {@code nombre} dado.
     */
    Personaje(String nombre){
        new Personaje(nombre, "-1", "-1", "-1", "-1", "-1", "-1", false);
    }

    /**
     * Crea un objeto con los parametros como {@code String} convertiendolos a los tipos necesarios o instaurandolos como sus valores predefinidos.
     * 
     * @param   nombre  : No puede ser null, ni "-1" ni estár en blanco. 
     * @param   raza    : Tiene que ser uno de los tipos validos si no será "HUMANO" por defecto.
     * @param   experiencia    : Asignarle un valor entre 0 (inclusive) y 1000 (exclusive) o "-1" para el valor por defecto.
     * @param   others  : Asignarles un valor entre 1 (inclusive) y 101 (exclusive) o "-1" para el valor por defecto.
     * @return Nuevo objeto de clase Personaje con los parametros otorgados.
     */
    Personaje(String nombre, String raza, String fuerza, String agilidad, String constitucion, String nivel, String experiencia, boolean yaExistente){
        try {
            nombre = nombre.strip();
        } catch (NullPointerException e) {
            throw new PersonajeException("El valor \"null\" no es valido");
        } catch (Exception b){
            throw new PersonajeException("El valor de \"nombre\" no es valido");
        }
        if (nombre.equals("-1") || nombre.isEmpty() || nombre.isBlank()) {
            throw new PersonajeException("El nombre es necesario para la construcción de este objeto y no puede ser \"-1\", prueba \"Personaje()\" en su lugar");
        }
        this.nombre = nombre;
        try {
            this.raza = asignarRaza(raza);
        } catch (PersonajeException e) {
            this.raza = Razas.HUMANO;
        }
        if (yaExistente) {
            if (Integer.parseInt(constitucion) < 1 || Integer.parseInt(agilidad) < 1 || Integer.parseInt(fuerza) < 1 || Integer.parseInt(nivel) < 1 || Integer.parseInt(experiencia) < 0 ) {
                throw new PersonajeException("Valores fuera de límites");
            } else {
                this.fuerza = Integer.parseInt(fuerza);
                this.agilidad = Integer.parseInt(agilidad);
                this.constitucion = Integer.parseInt(constitucion);
                this.nivel = (byte) Integer.parseInt(nivel);
                this.experiencia = Integer.parseInt(experiencia);
            }
        } else {
            this.fuerza = asignarStatRng(fuerza, 0);
            this.agilidad = asignarStatRng(agilidad, 1);
            this.constitucion = asignarStatRng(constitucion, 2);
            this.nivel = (byte) asignarStatNoRng(false, nivel);
            this.experiencia = asignarStatNoRng(true, experiencia);
        }
        /* Ignorar
        try {
            int xp = Integer.parseInt(experiencia.strip());
            sumarExperiencia(xp);
        } catch (PersonajeException e) {
            try {
                int xp = Integer.parseInt(experiencia.strip());
                int extra = xp % EXP_MAX;
                int vecesXpMax = xp / EXP_MAX;
                for (int i= 0; i < vecesXpMax; i++) {
                    sumarExperiencia(EXP_MAX);
                }
                sumarExperiencia(extra);
            } catch (Exception a) {}
        } catch (Exception e) {}
        */
    }
    
    private static String[] asignarBonus(Razas raza) {
        String bonusFuerza = "x";
        String bonusAgilidad = "x";
        String bonusConstitucion = "x";
        switch (raza) {
                case HUMANO:
                    bonusFuerza = "x";
                    bonusAgilidad = "x";
                    bonusConstitucion = "x";
                    break;
                case ELFO:
                    bonusFuerza = "-5";
                    bonusAgilidad = "+15";
                    bonusConstitucion = "-10";
                    break;
                case ENANO:
                    bonusFuerza = "+5";
                    bonusAgilidad = "-15";
                    bonusConstitucion = "+10";
                    break;
                case HOBBIT:
                    bonusFuerza = "-10";
                    bonusAgilidad = "+20";
                    bonusConstitucion = "-5";
                    break;
                case ORCO:
                    bonusFuerza = "+15";
                    bonusAgilidad = "-25";
                    bonusConstitucion = "+10";
                    break;
                case TROLL:
                    bonusFuerza = "+10";
                    bonusAgilidad = "-25";
                    bonusConstitucion = "+15";
                    break;
            }
        String[] bonus = {bonusFuerza, bonusAgilidad, bonusConstitucion};
        return bonus;
    }
    public String getNombre(){
        return this.nombre;
    }
    public static String getRazasStats() {
        String fichas = "";
        for (Razas raza : Razas.toArray()) {
            String[] bonus = asignarBonus(raza);
            fichas += String.format("Raza: %s%n-------------%nFuerza: %s, Agilidad: %s, Constitución: %s%n%n", raza, bonus[0], bonus[1], bonus[2]);
        }
        return fichas;
    }
    private static int generarRnd1a100(){
        Random rnd = new Random();
        int num = rnd.nextInt(100) + 1;
        return num;
    };
    
    
    public void crearPersonaje(){
        System.out.print("Nombre del personaje: ");
        nombre = Util.pedirPorTeclado(false);
        while (nombre.equals("-1")) {
            System.out.print("El persnaje necesita un nombre: ");
            nombre = Util.pedirPorTeclado(false);          
        }
        nombre = nombre.strip();
        System.out.println("Escoge una de las siguientes razas: orco, elfo, HUMANO, enano, hobbit o troll");
        
        for (boolean error = true; error;) {
            try {
                raza = asignarRaza(Util.pedirPorTeclado(false));
                error = false;
            } catch (PersonajeException e) {
                System.out.println("Raza no válida. Introduce uno de las siguientes: orco, elfo, HUMANO, enano, hobbit o troll");
                error = true;
            }
        }

        System.out.println("Introduce las siguientes estadísticas. Si quieres que se generen aleatoriamente, pulsa \"Enter\" sin introducir ningún valor.");
        System.out.print("Fuerza: ");
        fuerza = asignarStatRng(pedirStatRng(), 0);
        
        System.out.print("Agilidad: ");
        agilidad = asignarStatRng(pedirStatRng(), 1);

        System.out.print("Constitución: ");
        constitucion = asignarStatRng(pedirStatRng(), 2);
        
        System.out.print("Nivel: ");
        nivel = (byte) pedirStatNoRng(false);
        
        System.out.println("Nivel de experiencia: ");
        experiencia = pedirStatNoRng(true);

        puntosVida = getVidaMax();
    }

    private String pedirStatRng(){
        String texto = Util.pedirPorTeclado(true);
        final int MIN = 1;
        final int MAX = 100;
        while ( !texto.equals("-1") || (Integer.parseInt(texto) < MIN && Integer.parseInt(texto) > MAX)) {
            System.out.print("La estadística debe ser como mínimo " + MIN + " y como máximo " + MAX + ", da otro valor: ");
            texto = Util.pedirPorTeclado(true);
        }
        if (texto.equals("-1")) {
            texto = String.valueOf(generarRnd1a100());
        }
        return texto;
    }
    private int pedirStatNoRng(boolean esXp){
        String texto;
        int num;
        texto = Util.pedirPorTeclado(true);
        final int MIN = esXp ? 0 : 1;
        final int MAX = esXp ? 999 : 100;
        while ( !texto.equals("-1") || (Integer.parseInt(texto) < MIN && Integer.parseInt(texto) > MAX)) {
            System.out.print("La estadística debe ser como mínimo " + MIN + " y como máximo " + MAX + ", da otro valor: ");
            texto = Util.pedirPorTeclado(true);
        }
        if (texto.equals("-1")) {
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
        String nombreRaza = this.raza.toString();
        String raza = "Raza: " + nombreRaza;
        String nivel = "Nivel: " + this.nivel;
        String experiencia = "Experiencia: " + this.experiencia;
        String puntosVida = "Puntos de vida : (" + this.puntosVida + "/" + getVidaMax() + ")";
        String fuerza = "Fuerza: " + this.fuerza;
        String agilidad = "Agilidad: " + this.agilidad;
        String constitucion = "Constitución: " + this.constitucion;
        String overAll = "OverAll: " + (this.fuerza + this.agilidad + this.constitucion);

        ficha = String.format("%s%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n", Cabecera, nombre, raza, nivel, experiencia, puntosVida, fuerza, agilidad, constitucion, overAll);
        System.out.println(ficha);
    }
    public String toString(){
        String nombreYVida = nombre + " (" + puntosVida + "/" + getVidaMax() + ")";
        return nombreYVida;
    }
    public byte sumarExperiencia(int puntos){// La xperiencia va de 0 a 999 y luego vuelve a 0
        if (puntos > EXP_MAX) {
            throw new PersonajeException("Cantidad de experiencia excesiva para subir en una sola ejecución");
        }
        byte lvlsUp = 0;
        if (puntos/1000 != 0) {
            lvlsUp = (byte) (puntos / 1000);
            experiencia += puntos % 1000;
            if (experiencia >= 1000 ) {
                if (lvlsUp == EXP_MAX/1000) {
                    throw new PersonajeException("Cantidad de experiencia excesiva para subir en una sola ejecución");
                }
                experiencia %= 1000;
                lvlsUp++;
            }
            for (int i = 0; i < lvlsUp; i++) {
                subirNivel();
            }
        }
        return lvlsUp;
    }
    public void subirNivel(){
        nivel++;
        fuerza += Math.round(fuerza * 0.05);
        agilidad += Math.round(agilidad * 0.05);
        int oldVidaMax = getVidaMax();
        constitucion += Math.round(constitucion * 0.05);
        puntosVida += (getVidaMax() - oldVidaMax);
    }
    public void curar(){
        if (puntosVida < getVidaMax()) {
            puntosVida = getVidaMax();
        }
    }
    public boolean perderVida(int puntos){
        puntosVida -= puntos;
        return !estaVivo();
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
        } else {
            daño = 0;
        }
        return daño;
    }
    
    public String toJsonString() {
        return String.format("{\"nombre\":\"%s\",\"raza\":\"%s\",\"fuerza\":%d,\"agilidad\":%d,\"constitucion\":%d,\"nivel\":%d,\"experiencia\":%d,\"vidaMax\":%d}%n", nombre, raza, fuerza, agilidad, constitucion, nivel, experiencia, getVidaMax());
    }
    public String toCsvString() {
        return String.format("%s,%s,%d,%d,%d,%d,%d,%d,%d%n", nombre, raza, fuerza, agilidad, constitucion, nivel, experiencia, getVidaMax(), puntosVida);
    }
      
}
