package UD4.rol;

import java.util.Random;


public class Personaje {
    String nombre;
    String raza;
    int fuerza;
    int agilidad;
    int constitucion;
    byte nivel;
    int experiencia;
    int vidaMax = 50 + constitucion;
    int puntosVida = vidaMax;
    final static int EXP_MAX = 255999; 
    final static String[] RAZAS_VALIDAS = {"HUMANO", "ELFO", "ENANO", "HOBBIT", "ORCO", "TROLL"};

    private int asignarStatRng(String texto){
        int num;
        final int MIN = 1;
        final int MAX = 100;
        if ( !texto.equals("-1") || (Integer.parseInt(texto) < MIN && Integer.parseInt(texto) > MAX)) {
            throw new PersonajeException("Valores fuera de límites");
        }
        if (texto.equals("-1")) {
            num = generarRnd1a100();
        } else {
            num = Integer.parseInt(texto);
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
    public static String asignarRaza(String respuesta){
        if ( !respuesta.equals("-1") || !Util.verificaObjetoEnArray(respuesta, Personaje.RAZAS_VALIDAS) ) {
            throw new PersonajeException("Raza no válida.");
        }
        if (respuesta.equals("-1")) {
            respuesta = RAZAS_VALIDAS[0];
        }
        return respuesta;
    }
    Personaje(Object nullObjet){
        
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
     * Crea un objeto con los parametros como {@code String} convertiendolos a los tipos necesarios o instaurandolos como sus valores predefinidos.
     * 
     * @param   nombre  : No puede ser null, ni "-1" ni estár en blanco. 
     * @param   raza    : Tiene que ser uno de los tipos validos si no será "HUMANO" por defecto.
     * @param   experiencia    : Asignarle un valor entre 0 (inclusive) y 1000 (exclusive) o "-1" para el valor por defecto.
     * @param   others  : Asignarles un valor entre 1 (inclusive) y 101 (exclusive) o "-1" para el valor por defecto.
     * @return Nuevo objeto de clase Personaje con los parametros otorgados.
     */
    Personaje(String nombre, String raza, String fuerza, String agilidad, String constitucion, String nivel, String experiencia){
        try {
            nombre = nombre.strip();
        } catch (Exception e) {
            throw new PersonajeException("El valor \"null\" no es valido");
        }
        if (nombre.equals("-1") || nombre.isEmpty() || nombre.isBlank()) {
            throw new PersonajeException("El nombre es necesario para la construcción de este objeto y no puede ser \"-1\", prueba \"Personaje()\" en su lugar");
        }
        this.nombre = nombre;
        try {
            this.raza = asignarRaza(raza.toUpperCase());
        } catch (PersonajeException e) {
            this.raza = RAZAS_VALIDAS[0];
        }
        String[] bonus = asignarBonus(raza);
        int bonusFuerza = bonus[0].equals("x") ? 0 : Integer.parseInt(bonus[0]);
        int bonusAgilidad = bonus[1].equals("x") ? 0 : Integer.parseInt(bonus[1]);
        int bonusConstitucion = bonus[2].equals("x") ? 0 : Integer.parseInt(bonus[2]);

        this.fuerza = asignarStatRng(fuerza) + bonusFuerza;
        this.agilidad = asignarStatRng(agilidad) + bonusAgilidad;
        this.constitucion = asignarStatRng(constitucion) + bonusConstitucion;
        this.nivel = (byte) asignarStatNoRng(false, nivel);
        this.experiencia = asignarStatNoRng(true, experiencia);
        /*
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

    private static String[] asignarBonus(String raza) {
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
    public static String getRazasStats() {
        String fichas = "";
        for (String raza : RAZAS_VALIDAS) {
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
        raza = Util.pedirPorTeclado(false);
        while ( !raza.equals("-1") && !Util.verificaObjetoEnArray(raza.toUpperCase(), RAZAS_VALIDAS) ) {
            System.out.println("Raza no válida. Introduce uno de las siguientes: orco, elfo, HUMANO, enano, hobbit o troll");
            raza = Util.pedirPorTeclado(false);
        }
        if (raza.equals("-1")) {
            raza = RAZAS_VALIDAS[0];
        } else {
            raza = raza.toUpperCase();
        }
        String[] bonus = asignarBonus(raza);
        int bonusFuerza = bonus[0].equals("x") ? 0 : Integer.parseInt(bonus[0]);
        int bonusAgilidad = bonus[1].equals("x") ? 0 : Integer.parseInt(bonus[1]);
        int bonusConstitucion = bonus[2].equals("x") ? 0 : Integer.parseInt(bonus[2]);

        System.out.println("Introduce las siguientes estadísticas. Si quieres que se generen aleatoriamente, pulsa \"Enter\" sin introducir ningún valor.");
        System.out.print("Fuerza: ");
        fuerza = pedirStatRng() + bonusFuerza;
        
        System.out.print("Agilidad: ");
        agilidad = pedirStatRng() + bonusAgilidad;

        System.out.print("Constitución: ");
        constitucion = pedirStatRng() + bonusConstitucion;
        
        System.out.print("Nivel: ");
        nivel = (byte) pedirStatNoRng(false);
        
        System.out.println("Nivel de experiencia: ");
        experiencia = pedirStatNoRng(true);

        vidaMax = 50 + constitucion;
        puntosVida = vidaMax;
    }

    private int pedirStatRng(){
        String texto;
        int num;
        texto = Util.pedirPorTeclado(true);
        final int MIN = 1;
        final int MAX = 100;
        while ( !texto.equals("-1") || (Integer.parseInt(texto) < MIN && Integer.parseInt(texto) > MAX)) {
            System.out.print("La estadística debe ser como mínimo " + MIN + " y como máximo " + MAX + ", da otro valor: ");
            texto = Util.pedirPorTeclado(true);
        }
        if (texto.equals("-1")) {
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
        String nombreRaza = this.raza.charAt(0) + this.raza.substring(1).toLowerCase();
        String raza = "Raza: " + nombreRaza;
        String nivel = "Nivel: " + this.nivel;
        String experiencia = "Experiencia: " + this.experiencia;
        String puntosVida = "Puntos de vida : (" + this.puntosVida + "/" + vidaMax + ")";
        String fuerza = "Fuerza: " + this.fuerza;
        String agilidad = "Agilidad: " + this.agilidad;
        String constitucion = "Constitución: " + this.constitucion;

        ficha = String.format("%s%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n", Cabecera, nombre, raza, nivel, experiencia, puntosVida, fuerza, agilidad, constitucion);
        System.out.println(ficha);
    }
    public String toString(){
        String nombreYVida = nombre + " (" + puntosVida + "/" + vidaMax + ")";
        return nombreYVida;
    }
    public byte sumarExperiencia(int puntos){
        if (puntos > EXP_MAX) { //TODO probar exception
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
        } else {
            daño = 0;
        }
        return daño;
    }
    
    public String toJsonString() {
        return String.format("{\"nombre\":\"%s\",\"raza\":\"%s\",\"fuerza\":%s,\"agilidad\":%s,\"constitucion\":%s,\"nivel\":%s,\"experiencia\":%s,\"vidaMax\":%s,\"puntosVida\":%s}%n", nombre, raza, String.valueOf(fuerza), String.valueOf(agilidad), String.valueOf(constitucion), String.valueOf(nivel), String.valueOf(experiencia), String.valueOf(vidaMax), String.valueOf(puntosVida));
    }
    public String toCsvString() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s%n", nombre, raza, String.valueOf(fuerza), String.valueOf(agilidad), String.valueOf(constitucion), String.valueOf(nivel), String.valueOf(experiencia), String.valueOf(vidaMax), String.valueOf(puntosVida));
    }
    public void toFile(String filePath) {
        if (filePath.endsWith(".json")) {
            Util.writeStringToFile(toJsonString(), filePath, true);
        } else if (filePath.endsWith(".csv")) {
            Util.writeStringToFile(toCsvString(), filePath, true);
        } else {
            System.out.println("Formato de archivo no soportado. Solo se aceptan archivos .json o .csv");
        }
    }   
}
