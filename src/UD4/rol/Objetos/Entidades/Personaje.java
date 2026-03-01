package UD4.Rol.Objetos.Entidades;

import java.util.Arrays;
import org.json.JSONObject;
import UD4.Rol.Objetos.*;
import UD4.Rol.Utilidades.*;

/**
 * @author Ignacio MR
 */

public class Personaje extends Entidad {
    private int id = -1;
    protected Raza raza;
    private Item[] bolsa = Items.sort(new Item[] {Items.getItemRnd(), Items.getItemRnd(), Items.getItemRnd()});
    
    protected int asignarBonusRaza(int stat){
        int num;
        switch (stat) {
            case 0 ->num = super.fuerza;
            case 1 ->num = super.agilidad;
            case 2 ->num = super.constitucion;
            default -> throw new PersonajeException("Stat rng no existente");
        };
        String[] bonus = arrayBonusRaza(raza);
        int bonusStat = bonus[stat].equals("x") ? 0 : Integer.parseInt(bonus[stat]);
        if (num + bonusStat < 1) {
            num = 1;
        } else {
            num += bonusStat;
        }
        return num;
    }
    
    
    /**
     * Crea un objeto sin entregarle parametros.
     * 
     * @return Nuevo objeto de clase Personaje sin valores.
     */
    public Personaje(){
        super();
        this.id = -1;
        this.raza = null;
        Items.sort(this.bolsa);
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
        super(nombre, fuerza, agilidad, constitucion, nivel, experiencia, yaExistente);//TODO areglar que devuelve null en todos los stats 
        if (raza == null) {
            this.raza = Raza.HUMANO;
        }else{
            try {
                this.raza = Raza.StringToRaza(raza);
            } catch (PersonajeException e) {
                throw new PersonajeException("Raza no válida.");
            }
        }
        this.fuerza = asignarBonusRaza(0);
        this.agilidad = asignarBonusRaza(1);
        this.constitucion = asignarBonusRaza(2);
        this.id = -1;
        Items.sort(this.bolsa);
    }
    
    protected static String[] arrayBonusRaza(Raza raza) {
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
    public Raza getRaza() {
        return raza;
    }
    public String getBolsa(){
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
    
    public boolean isHabilidadRazaActiva() {
        return habilidadRazaActiva;
    }
    public static String getRazasStats() {
        String fichas = "";
        for (Raza raza : Raza.toArray()) {
            String[] bonus = arrayBonusRaza(raza);
            fichas += String.format("Raza: %s%n-------------%nFuerza: %s, Agilidad: %s, Constitución: %s%n%n", raza, bonus[0], bonus[1], bonus[2]);
        }
        return fichas;
    }
    
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
        super.fuerza = super.asignarStatRnd(pedirStatRng());
        this.fuerza = asignarBonusRaza(0);
        
        System.out.print("Agilidad: ");
        super.agilidad = super.asignarStatRnd(pedirStatRng());
        this.agilidad = asignarBonusRaza(1);

        System.out.print("Constitución: ");
        super.constitucion = super.asignarStatRnd(pedirStatRng());
        this.constitucion = asignarBonusRaza(2);
        
        System.out.print("Nivel: ");
        this.nivel = super.nivel = (byte) pedirStatNoRng(false);
        
        System.out.println("Nivel de experiencia: ");
        this.experiencia = super.experiencia = pedirStatNoRng(true);

        puntosVida = getVidaMax();

        Items.sort(bolsa);
    }
    public void quitarHabilidadRaza(){
        this.habilidadRazaActiva = super.habilidadRazaActiva = false; 
    }
    public void activarHabilidadRaza(){
        this.habilidadRazaActiva = super.habilidadRazaActiva = true; 
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
                        Item itemRnd = Items.getItemRnd();
                        if (bolsaLlena) {
                            bolsa = Arrays.copyOf(bolsa, bolsa.length + 1);
                            bolsa[bolsa.length - 1] = itemRnd;
                        } else {
                            for (int i = 0; i < bolsa.length; i++) {
                                if (bolsa[i] == null) {
                                    bolsa[i] = itemRnd;
                                    break;
                                }
                            }
                        }
                        bolsa = Items.sort(bolsa);
                        System.out.println("Obtuviste \"" + itemRnd.getNombre() + "\"!");
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
    public void mostrar(){
        String ficha = super.getFicha("Personaje");
        String nombreRaza = this.raza.toString();
        ficha += "\nRaza: " + nombreRaza;
        System.out.println(ficha);
    }
    
    public boolean usarObjeto(Item item){
        boolean fueUsado = false;
        if (item != null) {
            for (int i = 0; i < bolsa.length; i++) {
                if (item.compareTo(bolsa[i]) == 0) {
                    bolsa[i] = null;
                    fueUsado = true;
                    break;
                }
            }
            bolsa = Items.sort(bolsa);
        }
        return fueUsado;
    }
    @Override
    public JSONObject toJsonObject(){
        JSONObject personaje = super.toJsonObject();
        personaje.accumulate("raza", this.raza);
        return personaje;
    }
    @Override
    public String toCsvString(){
        return super.toCsvString() + "," + raza + "\n";
    }
    public static void main(String[] args) {
        Personaje p = new Personaje("pureba");
        p.bolsa = new Item[] {new Item("enredaderas"), new Item("bomba de humo"), new Item("enredaderas"), null};
        /*p.bolsa = new Item[]{null, Items.getItemRnd(), new Item("Enredaderas"), Items.getItemRnd(), new Item("eNREDADEraS")};*/
        String bolsa = p.getBolsa();
        System.out.println(bolsa);
        String accion = "2";
        String ubNom = accion + " - ";
        int ubNomObjeto = bolsa.indexOf(ubNom)+ ubNom.length();
        accion = bolsa.substring(ubNomObjeto, ubNomObjeto + bolsa.substring(ubNomObjeto).indexOf(" ("));
        Item objeto = new Item(accion); 
        p.usarObjeto(objeto);
    }
}
