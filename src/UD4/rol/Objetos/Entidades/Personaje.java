package UD4.Rol.Objetos.Entidades;

import java.util.Arrays;
import org.json.JSONObject;

import UD4.Rol.Objetos.*;
import UD4.Rol.Objetos.Equipamiento.Equipamiento;
import UD4.Rol.Objetos.Equipamiento.Arma.Arma;
import UD4.Rol.Objetos.Equipamiento.Arma.Barita;
import UD4.Rol.Objetos.Equipamiento.Arma.Espada;
import UD4.Rol.Objetos.Equipamiento.Arma.Maza;
import UD4.Rol.Objetos.Equipamiento.Armadura.Armadura;
import UD4.Rol.Objetos.Equipamiento.Armadura.Botas;
import UD4.Rol.Objetos.Equipamiento.Armadura.Casco;
import UD4.Rol.Objetos.Equipamiento.Armadura.Pantalon;
import UD4.Rol.Objetos.Equipamiento.Armadura.Pechera;
import UD4.Rol.Utilidades.*;

/**
 * @author Ignacio MR
 */

public class Personaje extends Entidad {
    private int id = -1;
    protected Raza raza;
    private Item[] bolsa = Items.sort(new Item[] {Items.getItemRnd(), Items.getItemRnd(), Items.getItemRnd()});
    protected Equipamiento[] equipamientoGuardado = new Equipamiento[50];
    
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
        super(nombre, fuerza, agilidad, constitucion, nivel, experiencia, yaExistente);
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
    public String getFicha(){
        String ficha = getFicha("Personaje");
        String nombreRaza = this.raza.toString();
        ficha += "\nRaza: " + nombreRaza;
        return ficha;
    }
    public void mostrar(){
        String ficha = getFicha();
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
    /**
     * 
     * @param esGeneral {@code true} para cualquier equipamiento, {@code false} para escoger entre armas y armaduras.
     * @param gachaArmas {@code true} para obtener un arma, {@code false} para obtener una armadura. (no es necesario introducirlo si {@code esGeneral} es false).
     * @return Equipamiento obtenido y lo guarda en {@code equipamienoGuardado} o {@code null} si el {@code equipamienoGuardado} está lleno.
     */
    public Equipamiento gachaEquipamiento(boolean esGeneral, boolean... gachaArmas){
        for (int i = 0; i < equipamientoGuardado.length; i++) {
            if (equipamientoGuardado[i] == null) {
                Equipamiento e = (Equipamiento) Equipamiento.gachaEquipamiento(this.getNivel(), esGeneral, gachaArmas);
                equipamientoGuardado[i] = e;
                Util.sortArray(equipamientoGuardado);
                return e;
            }
        }
        return null;
    }
    @Override
    public boolean equipar(Equipamiento equip) {
        if (equip == null) {
            throw new EquipamientoException("Equipamiento nulo");
        }
        int slot; // 0: Casco, 1: Pechera, 2: Pantalon, 3: Botas, 4: Arma
        if (equip instanceof Casco) {
            slot = 0;
        } else if (equip instanceof Pechera) {
            slot = 1;
        } else if (equip instanceof Pantalon) {
            slot = 2;
        } else if (equip instanceof Botas) {
            slot = 3;
        } else if (equip instanceof Arma) {
            slot = 4;
        } else {
            throw new EquipamientoException("Tipo de equipamiento no equipable");
        }

        if (equipamientoEquipado[slot] != null) {
            Equipamiento antiguo = quitarEquipamiento(slot);
            for (int i = 0; i < equipamientoGuardado.length; i++) {//TODO que solo se puedan equipar cosas desde el equipoGuardado y tras convertir a null la posición del equipamiento a equipar
                if (equipamientoGuardado[i].compareTo(equip) == 0) {
                    antiguo.setId(i);
                    equipamientoGuardado[i] = antiguo;
                    Util.sortArray(equipamientoGuardado);
                    break;
                }
            }
        }
        equipamientoEquipado[slot] = equip;
        return true;
    }
    public Equipamiento quitarEquipamiento(int slot) {
        if (slot < 0 || slot >= equipamientoEquipado.length) {
            throw new EquipamientoException("Slot inválido");
        }
        Equipamiento antiguo = equipamientoEquipado[slot];
        equipamientoEquipado[slot] = null;
        return antiguo;
    }
    public Equipamiento quitarEquipamiento(Equipamiento equip) {
        if (equip == null) { return null; }
        for (int i = 0; i < equipamientoEquipado.length; i++) {
            if (equipamientoEquipado[i] != null && equipamientoEquipado[i].equals(equip)) {
                Equipamiento antiguo = equipamientoEquipado[i];
                equipamientoEquipado[i] = null;
                return antiguo;
            }
        }
        throw new EquipamientoException("Equipamiento no equipado");
    }
    public String getEquipamientoGuardado() {
        Equipamiento[] armas = new Equipamiento[0];
        Equipamiento[] armaduras = new Equipamiento[0];
        String separador = "-----------------------\n";
        String inventario = "Equipamiento guardado\n=====================\n";
        for (int i = 0; i < equipamientoGuardado.length; i++) {
            Equipamiento este = equipamientoGuardado[i];
            if (este != null) {
                if (este instanceof Armadura) {
                    armaduras = Arrays.copyOf(armaduras, armaduras.length + 1);
                    if (este instanceof Casco || este instanceof Pechera || este instanceof Pantalon || este instanceof Botas) {
                        armaduras[armaduras.length - 1] = este;
                    } else {
                        throw new EquipamientoException();
                    }
                } else {
                    armas = Arrays.copyOf(armas, armas.length + 1);
                    if (este instanceof Espada || este instanceof Barita || este instanceof Maza) {
                        armas[armas.length - 1] = este;
                    } else {
                        throw new EquipamientoException();
                    }
                }
            }
        }
        Util.sortArray(equipamientoGuardado);
        Arrays.sort(armaduras);
        Arrays.sort(armas);
        int id = 0;
        if (armas.length > 0) {
            inventario += "ARMAS:\n\n";
            for (int i = 0; i < armas.length; i++) {
                armas[i].setId(id);
                inventario += (armas[i].getId() + 1) + " - " + armas[i].getString();
                inventario += separador;
                id++;
            }
            inventario += "\n";
        }
        if (armaduras.length > 0) {
            inventario += "ARMADURAS:\n\n";
            for (int i = 0; i < armaduras.length; i++) {
                armaduras[i].setId(id);
                inventario += (armaduras[i].getId() + 1) + " - " + armaduras[i].getString();
                inventario += separador;
                id++;
            }
        }
        if (armas.length == 0 && armaduras.length == 0) {
            inventario += "No hay equipamiento guardado.\n";
        }
        return inventario;
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
