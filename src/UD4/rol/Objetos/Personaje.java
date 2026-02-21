package UD4.Rol.Objetos;

import java.util.Arrays;
import java.util.Random;

import UD4.Rol.Utilidades.*;

/**
 * @author Ignacio MR
 */

public class Personaje {
    private int id = -1;
    private String nombre;
    private Raza raza;
    private int fuerza;
    private int agilidad;
    private int constitucion;
    private byte nivel;
    private int experiencia;
    private int puntosVida = getVidaMax();
    private boolean habilidadRazaActiva = true;
    private Item[] bolsa = Items.sort(new Item[] {Items.getItemRnd(), Items.getItemRnd(), Items.getItemRnd()});
    private final static int VIDA_MIN = 50;
    private final static int EXP_MAX = 127999;
    
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
        
        if ( !(texto == null) && (Integer.parseInt(texto) < MIN || Integer.parseInt(texto) > MAX)) {
            throw new PersonajeException("Valores fuera de límites");
        }
        if (texto == null) {
            num = generarRnd1a100();
        } else {
            num = Integer.parseInt(texto);
        }
        String[] bonus = asignarBonusRaza(raza);
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
        if ( !(texto == null) && (Integer.parseInt(texto) < MIN || Integer.parseInt(texto) > MAX)) {
            throw new PersonajeException("Valores fuera de límites");
        }
        if (texto == null) {
            num = MIN;
        } else {
            num = Integer.parseInt(texto);
        }
        return num;
    }
    
    
    /**
     * Crea un objeto sin entregarle parametros.
     * 
     * @return Nuevo objeto de clase Personaje sin valores.
     */
    public Personaje(){
        this.id = -1;
        this.nombre = null;
        this.raza = null;
        this.fuerza = 0;
        this.agilidad = 0;
        this.constitucion = 0;
        this.nivel = 0;
        this.experiencia = 0;
        this.bolsa = null;
    }

    /**
     * Crea un objeto unicamente con el nombre.
     * 
     * @param   nombre  : No puede ser null ni estár en blanco. 
     * @return Nuevo objeto de clase {@code Personaje} con balores predefinidos y el {@code nombre} dado.
     */
    public Personaje(String nombre){
        this(nombre, null, null, null, null, null, null, false);
    }
    /**
     * Crea un objeto unicamente con el nombre.
     * 
     * @param   nombre  : No puede ser null ni estár en blanco. 
     * @param   raza    : Tiene que ser una raza valida o será "Humano" por defecto. 
     * @return Nuevo objeto de clase {@code Personaje} con balores predefinidos y el {@code nombre} y la {@code raza} dados.
     */
    public Personaje(String nombre, String raza){
        this(nombre, raza, null, null, null, null, null, false);
    }
    /**
     * Crea un objeto con los parametros como {@code String} convertiendolos a los tipos necesarios o instaurandolos como sus valores predefinidos.
     * 
     * @param   nombre  : No puede ser null ni estár en blanco. 
     * @param   raza    : Tiene que ser uno de los tipos validos si no será "HUMANO" por defecto.
     * @param   experiencia    : Asignarle un valor entre 0 (inclusive) y 1000 (exclusive) o null para el valor por defecto.
     * @param   others  : Asignarles un valor entre 1 (inclusive) y 101 (exclusive) o null para el valor por defecto.
     * @return Nuevo objeto de clase Personaje con los parametros otorgados o {@code PersonajeExcepcion} si algun valor no es valido.
     */
    public Personaje(String nombre, String raza, String fuerza, String agilidad, String constitucion, String nivel, String experiencia, boolean yaExistente){
        this.id = -1;
        try {
            nombre = nombre.strip();
        } catch (NullPointerException e) {
            throw new PersonajeException("El valor \"null\" no es valido");
        } catch (Exception b){
            throw new PersonajeException("El valor de \"nombre\" no es valido");
        }
        if (nombre == null || nombre.isEmpty() || nombre.isBlank()) {
            throw new PersonajeException("El nombre es necesario para la construcción de este objeto y no puede ser \"null\" o estár en blanco, prueba \"Personaje()\" en su lugar");
        }
        this.nombre = nombre;
        try {
            this.raza = Raza.StringToRaza(raza);
        } catch (PersonajeException e) {
            throw new PersonajeException("Raza no válida.");
        }
        Items.sort(this.bolsa);
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
    public void asignarBonus(int[] bonus, boolean sustraer) {
        int bonusFuerza = bonus[0];
        int bonusAgilidad = bonus[1];
        int bonusConstitucion = bonus[2];
        int cura = bonus[3];
        fuerza += sustraer ? -bonusFuerza : bonusFuerza;
        agilidad += sustraer ? -bonusAgilidad : bonusAgilidad;
        constitucion += sustraer ? -bonusConstitucion : bonusConstitucion;
        perderVida(cura); // Al ser "cura" un valor negativo gana vida en vez de perderla
    }
    private static String[] asignarBonusRaza(Raza raza) {
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
    public void setId(int id) {
        if (id <= 0) {
            this.id = id;
        } else {
            throw new PersonajeException("Id invalida");
        }
    }
    public int getId(){
        return this.id;
    }
    public String getNombre(){
        return this.nombre;
    }
    public Raza getRaza() {
        return raza;
    }
    public int getFuerza() {
        return fuerza;
    }
    public int getAgilidad() {
        return agilidad;
    }
    public int getConstitucion() {
        return constitucion;
    }
    public byte getNivel() {
        return nivel;
    }
    public int getExperiencia() {
        return experiencia;
    }
    public int getVidaMax(){
        return VIDA_MIN + constitucion;
    }
    public int getPuntosVida(){
        return puntosVida;
    }
    public boolean isHabilidadRazaActiva() {
        return habilidadRazaActiva;
    }
    public static String getRazasStats() {
        String fichas = "";
        for (Raza raza : Raza.toArray()) {
            String[] bonus = asignarBonusRaza(raza);
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
        while (nombre == null) {
            System.out.print("El persnaje necesita un nombre: ");
            nombre = Util.pedirPorTeclado(false);          
        }
        nombre = nombre.strip();
        System.out.println("Escoge una de las siguientes razas: orco, elfo, HUMANO, enano, hobbit o troll");
        
        for (boolean error = true; error;) {
            try {
                raza = Raza.StringToRaza(Util.pedirPorTeclado(false));
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

        Items.sort(bolsa);
    }

    private String pedirStatRng(){
        String texto = Util.pedirPorTeclado(true);
        final int MIN = 1;
        final int MAX = 100;
        while ( !(texto == null) && (Integer.parseInt(texto) < MIN || Integer.parseInt(texto) > MAX)) {
            System.out.print("La estadística debe ser como mínimo " + MIN + " y como máximo " + MAX + ", da otro valor: ");
            texto = Util.pedirPorTeclado(true);
        }
        if (texto == null) {
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
        while ( !(texto == null) && (Integer.parseInt(texto) < MIN || Integer.parseInt(texto) > MAX)) {
            System.out.print("La estadística debe ser como mínimo " + MIN + " y como máximo " + MAX + ", da otro valor: ");
            texto = Util.pedirPorTeclado(true);
        }
        if (texto == null) {
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
    public void quitarHabilidadRaza(){
        habilidadRazaActiva = false; 
    }
    public void activarHabilidadRaza(){
        habilidadRazaActiva = true; 
    }
    public byte duracionHabilidadRaza(Personaje enemigo){
        boolean haceEfecto = habilidadRazaActiva;
        byte turnosEfecto = -1;
        if (haceEfecto) {
            boolean esHobbit = getRaza().equals(Raza.HOBBIT);
            Raza habilidad = esHobbit ? enemigo.getRaza() : raza;
            switch (habilidad) {
                    case HUMANO:
                        turnosEfecto = 1;
                        if (esHobbit) { enemigo.quitarHabilidadRaza(); }
                        
                    case ELFO:
                        turnosEfecto = 1;
                        if (esHobbit) { enemigo.quitarHabilidadRaza(); }
                        break;
                    case ENANO:
                        boolean bolsaLlena = true;
                        for (Item item : bolsa) {
                            if (item == null) {
                                bolsaLlena = false;
                                break;
                            }
                        }
                        if (bolsaLlena) {
                            bolsa = Arrays.copyOf(bolsa, bolsa.length + 1);
                            bolsa[bolsa.length - 1] = Items.getItemRnd();
                        } else {
                            for (int i = 0; i < bolsa.length; i++) {
                                if (bolsa[i] == null) {
                                    bolsa[i] = Items.getItemRnd();
                                    break;
                                }
                            }
                        }
                        bolsa = Items.sort(bolsa);
                        
                        if (esHobbit) { enemigo.quitarHabilidadRaza(); }
                        break;
                    case HOBBIT:
                        haceEfecto = false;
                        break;
                    case ORCO:
                        turnosEfecto = 0;
                        if (esHobbit) { enemigo.quitarHabilidadRaza(); }
                        break;
                    case TROLL:
                        turnosEfecto = 3;
                        if (esHobbit) { enemigo.quitarHabilidadRaza(); }
                        break;
                    default:
                        throw new PersonajeException("Error con la habilidad de raza");
                }
        }
        return haceEfecto ? turnosEfecto : -1;
    }
    public String stringHabilidadRaza(){
        String nombreYHabilidad = "";
        switch (this.raza) {
                case HUMANO:
                    nombreYHabilidad = "Furor Heróico (buffea todas sus estadisticas en un 50% hasta terminar proximo turno)";
                    break;
                case ELFO:
                    nombreYHabilidad = "Madre naturaleza (Añade a los puntos de vida el 50% de vida máx)";
                    break;
                case ENANO:
                    nombreYHabilidad = "Crear (Fabrica un objeto aleatorio)";
                    break;
                case HOBBIT:
                    nombreYHabilidad = "Steal (Roba la habillidad de raza de su enemigo por un turno)"; //Vigila a donde te apunta con su habilidad jajaja
                    break;
                case ORCO:
                    nombreYHabilidad = "Mamporro (Golpea al enemigo con el doble de fuerza)";
                    break;
                case TROLL:
                    nombreYHabilidad = "Regeneración (Se cura un 15% de su vida máx durante 3 turnos)";
                    break;
            }
        return nombreYHabilidad;
    }
    public String mostrarBolsa(){
        this.bolsa = Items.sort(this.bolsa);
        String inventario = "Objetos disponibles:\n";
        for (int i = 0, cant = 0 , id = 1; i < bolsa.length && !(bolsa[i] == null); cant--, i += cant, i++) {
            cant = Items.cantidadItem(bolsa, bolsa[i]);
            inventario += id + " - " + bolsa[i].getNombre() + " (" + cant + ") (" + bolsa[i].getDescription() + ")\n";
            id++;
        }
        return inventario;
        /* Mostrar todos
        for (Item item : bolsa) {
            if (item != null) {
                inventario += "-" + item.getNombre() + "\n";
            }
        }*/
    }
    public void usarObjeto(Item item){
        for (int i = 0; i < bolsa.length; i++) {
            if (item.compareTo(bolsa[i]) == 0) {
                bolsa[i] = null;
                break;
            }
        }
        bolsa = Items.sort(bolsa);
    }
    public String toJsonString() {
        String personajeJson = String.format("{\"nombre\":\"%s\",\"raza\":\"%s\",\"fuerza\":%d,\"agilidad\":%d,\"constitucion\":%d,\"nivel\":%d,\"experiencia\":%d,\"vidaMax\":%d}", nombre, raza, fuerza, agilidad, constitucion, nivel, experiencia, getVidaMax());
        return personajeJson;
    }
    public String toCsvString() {
        return String.format("%s,%s,%d,%d,%d,%d,%d,%d,%d%n", nombre, raza, fuerza, agilidad, constitucion, nivel, experiencia, getVidaMax(), puntosVida);
    }
    public static void main(String[] args) {
        Personaje p = new Personaje("pureba");
        p.bolsa = new Item[] {new Item("enredaderas"), new Item("bomba de humo"), new Item("enredaderas"), null};
        /*p.bolsa = new Item[]{null, Items.getItemRnd(), new Item("Enredaderas"), Items.getItemRnd(), new Item("eNREDADEraS")};*/
        String bolsa = p.mostrarBolsa();
        System.out.println(bolsa);
        String accion = "2";
        String ubNom = accion + " - ";
        int ubNomObjeto = bolsa.indexOf(ubNom)+ ubNom.length();
        accion = bolsa.substring(ubNomObjeto, ubNomObjeto + bolsa.substring(ubNomObjeto).indexOf(" ("));
        Item objeto = new Item(accion); 
        p.usarObjeto(objeto);
    }
}
