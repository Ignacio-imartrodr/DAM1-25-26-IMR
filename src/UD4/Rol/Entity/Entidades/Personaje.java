package UD4.Rol.Entity.Entidades;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import UD4.Rol.Entity.Equipamiento.Equipamiento;
import UD4.Rol.Entity.Equipamiento.Arma.*;
import UD4.Rol.Entity.Equipamiento.Armadura.*;
import UD4.Rol.Entity.Others.EquipEquipado;
import UD4.Rol.Entity.Others.Item;
import UD4.Rol.Entity.Others.Items;
import UD4.Rol.Entity.Others.Raza;
import UD4.Rol.Utilidades.*;

/**
 * @author Ignacio MR
 */

public class Personaje extends Entidad implements EquipEquipado {
    private int id = -1;
    private Raza raza;
    private Item[] bolsa = null;
    private Equipamiento[] equipamientoGuardado = new Equipamiento[50];
    
    protected int asignarBonusRaza(int stat){
        int num;
        switch (stat) {
            case 0 ->num = super.fuerza;
            case 1 ->num = super.agilidad;
            case 2 ->num = super.constitucion;
            default -> throw new EntidadException("Stat rng no existente");
        };
        String[] bonus = raza.arrayBonusRaza();
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
    }

    /**
     * Crea un objeto unicamente con el nombre.
     * 
     * @param   nombre  : No puede ser null ni estár en blanco. 
     * @return Nuevo objeto de clase {@code Personaje} con balores predefinidos y el {@code nombre} dado.
     */
    public Personaje(String nombre){
        this(nombre, null, null, null, null, null, null, null, null, null, false);
    }
    /**
     * Crea un objeto unicamente con el nombre.
     * 
     * @param   nombre  : No puede ser null ni estár en blanco. 
     * @param   raza    : Tiene que ser una raza valida o será "Humano" por defecto. 
     * @return Nuevo objeto de clase {@code Personaje} con balores predefinidos y el {@code nombre} y la {@code raza} dados.
     */
    public Personaje(String nombre, String raza){
        this(nombre, raza, null, null, null, null, null, null, null, null, false);
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
    public Personaje(String nombre, String raza, String fuerza, String agilidad, String constitucion, String nivel, String experiencia){
        this(nombre, raza, fuerza, agilidad, constitucion, nivel, experiencia, null, null, null, false);
    }
    public Personaje(String nombre, String raza, String fuerza, String agilidad, String constitucion, String nivel, String experiencia, Equipamiento[] equipamientoEquipado, Equipamiento[] equipamientoGuardado, Item[] bolsa, boolean yaExistente){
        
        if (nombre == null || nombre.isBlank()) {
            throw new EntidadException("El nombre es necesario para la construcción de Personaje y no puede ser \"null\" o estár en blanco, prueba \"Personaje()\" en su lugar");
        }
        this.vidaMin = 50;
        int minRndStat = 1;
        int maxRndStat = 100;
        this.minMaxRndStats = new int[] {minRndStat, maxRndStat, minRndStat, maxRndStat, minRndStat, maxRndStat};
        newEntidad(nombre, fuerza, agilidad, constitucion, nivel, experiencia, yaExistente);
        
        if (raza == null) {
            this.raza = Raza.HUMANO;
        }else{
            try {
                this.raza = Raza.stringToRaza(raza);
            } catch (EntidadException e) {
                throw new EntidadException("Raza no válida.");
            }
        }
        this.fuerza = super.fuerza = asignarBonusRaza(0);
        this.agilidad = super.agilidad = asignarBonusRaza(1);
        this.constitucion = super.constitucion = asignarBonusRaza(2);
        this.id = -1;

        if (bolsa != null) {
            Util.sortArray(bolsa);
        }
        boolean tieneBolsillo = false;
        if (equipamientoEquipado != null && equipamientoEquipado.length > 2 && equipamientoEquipado[2] instanceof Pantalon) {
            String encantamiento = ((Pantalon) equipamientoEquipado[2]).getEncantamiento();
            tieneBolsillo = encantamiento != null && encantamiento.equalsIgnoreCase("Bolsillo");
        }
        if (yaExistente) {
            if (equipamientoEquipado == null || (equipamientoEquipado.length == EquipEquipado.equipamientoEquipado.length && EquipEquipado.isFormatoCorrecto(equipamientoEquipado))) {
                if (equipamientoEquipado != null) {
                    for (int i = 0; i < equipamientoEquipado.length; i++) {
                        if (equipamientoEquipado[i] != null) {
                            equipamientoEquipado[i].setId(i);
                            EquipEquipado.super.setEquipado(equipamientoEquipado[i]);
                        }
                    }
                }
            } else {
                throw new EquipamientoException("Equipamiento equipado invalido");
            }
            if (bolsa != null) {
                if (tieneBolsillo) {
                    if (bolsa.length == 4) {
                        this.bolsa = bolsa;
                    } else {
                        throw new ItemException("Tamaño de la bolsa incorrecto");
                    }
                } else {
                    if (bolsa.length == 3) {
                        this.bolsa = bolsa;
                    } else {
                        throw new ItemException("Tamaño de la bolsa incorrecto");
                    }
                }
            } else {
                if (tieneBolsillo) {
                    bolsa = new Item[4];
                } else {
                    bolsa = new Item[3];
                }
            }
            if (equipamientoGuardado == null || (equipamientoGuardado.length >= 0 && equipamientoGuardado.length <= this.equipamientoGuardado.length)) {
                if (equipamientoGuardado != null) {
                    Util.sortArray(equipamientoGuardado);
                    for (int i = 0; i < equipamientoGuardado.length && equipamientoGuardado[i] != null; i++) {
                        this.equipamientoGuardado[i] = equipamientoGuardado[i];
                    }
                }
            } else {
                throw new EquipamientoException("Equipamiento guardado invalido");
            }
        } else {
            if (tieneBolsillo) {
                this.bolsa = new Item[] {Items.getItemRnd(), Items.getItemRnd(), Items.getItemRnd(), Items.getItemRnd()};
            } else {
                this.bolsa = new Item[] {Items.getItemRnd(), Items.getItemRnd(), Items.getItemRnd()};
            }
            Util.sortArray(this.bolsa);
        }
    }
    
    
    public void setId(int id) {
        if (id >= 0) {
            this.id = id;
        } else {
            throw new EntidadException("Id invalida");
        }
    }
    public int getId(){
        return this.id;
    }
    public Raza getRaza() {
        return raza;
    }
    public String getStringBolsa(){
        Util.sortArray(this.bolsa);
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
    public Item[] getBolsa() {
        return bolsa;
    }
    
    public boolean isHabilidadRazaActiva() {
        return this.raza.isHabilidadActiva();
    }

    public void quitarHabilidadRaza(){
        this.raza.quitarHabilidad();
    }
    public void activarHabilidadRaza(){
        this.raza.activarHabilidad();
    }
    public byte duracionHabilidadRaza(Personaje enemigo){
        boolean haceEfecto = isHabilidadRazaActiva();
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
                        if (esHobbit) { enemigo.quitarHabilidadRaza(); }
                        if (bolsaLlena) {
                            return -1;
                        } else {
                            Item itemRnd = Items.getItemRnd();
                            for (int i = 0; i < bolsa.length; i++) {
                                if (bolsa[i] == null) {
                                    bolsa[i] = itemRnd;
                                    break;
                                }
                            }
                            Util.sortArray(bolsa);
                            System.out.println("Obtuviste \"" + itemRnd.getNombre() + "\"!");
                        }
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
                        throw new EntidadException("Error con la habilidad de raza");
                }
        }
        return haceEfecto ? turnosEfecto : -1;
    }
    public String stringHabilidadRaza(){
        String nombreYHabilidad = this.raza.getHabilidad();
        return nombreYHabilidad;
    }
    public String getFicha(){
        String ficha = getFicha("Personaje");
        String nombreRaza = this.raza.toString();
        ficha += "\nRaza: " + nombreRaza;
        return ficha;
    }
    
    public boolean usarObjeto(int pos) {
        if (pos >= bolsa.length || pos <= 0) {
            return false;
        }
        Util.sortArray(bolsa);
        quitarObjetoDeBolsa(bolsa[pos]);
        return true;
    }
    private boolean quitarObjetoDeBolsa(Item item){
        boolean fueUsado = false;
        if (item != null && bolsa != null) {
            Util.sortArray(bolsa);
            for (int i = 0; i < bolsa.length; i++) {
                if (item.compareTo(bolsa[i]) == 0) {
                    bolsa[i] = null;
                    fueUsado = true;
                    break;
                }
            }
            Util.sortArray(bolsa);
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
    public boolean setEquipado(Equipamiento equip) {
        int slot = equip.getId();
        if (slot != -1) {
            Equipamiento antiguo = quitarEquipamiento(slot);
            Util.sortArray(equipamientoGuardado);
            for (int i = 0; i < equipamientoGuardado.length && equipamientoGuardado[i] != null; i++) {
                if (equipamientoGuardado[i].equals(equip)) {
                    equipamientoGuardado[i] = antiguo;
                    Util.sortArray(equipamientoGuardado);
                    for (int j = 0; j < equipamientoGuardado.length; j++) {
                        equipamientoGuardado[j].setId(j);
                    }
                    
                    equipamientoEquipado[slot] = equip;
                    return true;
                }
            }
            equipamientoEquipado[slot] = antiguo;
        }
        return false;
    }
    
    public String getStringEquipamientoGuardado() {
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
    
    public Equipamiento[] getEquipamientoGuardado() {
        return equipamientoGuardado;
    }

    @Override
    public JSONObject toJsonObject() throws EntidadException{
        JSONObject personaje = super.toJsonObject();
        
        JSONObject stats = personaje.getJSONObject("Stats");
        stats.accumulate("raza", this.raza);
        if (id < 0) {
            throw new EntidadException("Es necesario asignar la id según la posicion del personaje en la BaseGeneral");
        }
        personaje.put("Stats", stats);
        
        JSONObject equipamientos = new JSONObject();

        for (int i = 0; i < equipamientoEquipado.length; i++) {
            if (equipamientoEquipado[i] == null) {
                equipamientos.accumulate("Equipado", new JSONObject());
            } else {
                equipamientos.accumulate("Equipado", equipamientoEquipado[i].getJsonObject());
            }
        }
        Util.sortArray(equipamientoGuardado);
        equipamientos.put("Guardado", new JSONArray());
        for (int i = 0; i < equipamientoGuardado.length && equipamientoGuardado[i] != null; i++) {
            if (equipamientoGuardado[i] != null) {
                equipamientos.append("Guardado", equipamientoGuardado[i].getJsonObject());
            }
        }
        
        JSONArray bolsa = new JSONArray();
        Util.sortArray(this.bolsa);
        for (int i = 0; i < this.bolsa.length; i++) {
            if (this.bolsa[i] != null) {
                bolsa.put(this.bolsa[i]);
            } else {
                bolsa.put(new JSONObject());
            }
        }
        personaje.put("Equipamientos", equipamientos);
        personaje.put("Bolsa", bolsa);
        
        return personaje;
    }
    
    
    @Override
    public String toString() {
        String nombreYVida = nombre + " (" + puntosVida + "/" + getVidaMax() + ")";
        return nombreYVida;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Personaje other = (Personaje) obj;
        if (raza != other.raza)
            return false;
        if (nombre != other.nombre)
            return false;
        if (!Arrays.equals(bolsa, other.bolsa))
            return false;
        if (!Arrays.equals(equipamientoGuardado, other.equipamientoGuardado))
            return false;
        if (!Arrays.equals(getEquipamientoEquipado(), other.getEquipamientoEquipado()))
            return false;
        if (nivel != other.nivel)
            return false;
        if (experiencia != other.experiencia)
            return false;
        if (fuerza != other.fuerza)
            return false;
        if (agilidad != other.agilidad)
            return false;
        if (constitucion != other.constitucion)
            return false;
        return true;
    }


    public static void main(String[] args) {
        Personaje p = new Personaje("pureba");
        p.bolsa = new Item[] {new Item("enredaderas"), new Item("bomba de humo"), new Item("enredaderas"), null};
        /*p.bolsa = new Item[]{null, Items.getItemRnd(), new Item("Enredaderas"), Items.getItemRnd(), new Item("eNREDADEraS")};*/
        String bolsa = p.getStringBolsa();
        System.out.println(bolsa);
        String accion = "2";
        String ubNom = accion + " - ";
        int ubNomObjeto = bolsa.indexOf(ubNom)+ ubNom.length();
        accion = bolsa.substring(ubNomObjeto, ubNomObjeto + bolsa.substring(ubNomObjeto).indexOf(" ("));
        //Item objeto = new Item(accion); 
        //p.usarObjeto(objeto);
    }
}
