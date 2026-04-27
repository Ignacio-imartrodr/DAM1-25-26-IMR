package UD4.Rol.Entity.Entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
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
    private Equipamiento[] equipamientoEquipado = new Equipamiento[5];
    private List<Equipamiento> equipamientoGuardado = new ArrayList<>(50);

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
            if (equipamientoGuardado == null || (equipamientoGuardado.length >= 0 && equipamientoGuardado.length <= this.equipamientoGuardado.size())) {
                if (equipamientoGuardado != null) {
                    Util.sortArray(equipamientoGuardado);
                    for (int i = 0; i < equipamientoGuardado.length && equipamientoGuardado[i] != null; i++) {
                        this.equipamientoGuardado.set(i, equipamientoGuardado[i]);
                    }
                }
            } else {
                throw new EquipamientoException("Equipamiento guardado invalido");
            }
            if (equipamientoEquipado == null || EquipEquipado.isFormatoCorrecto(equipamientoEquipado)) {
                if (equipamientoEquipado != null) {
                    for (int i = 0; i < equipamientoEquipado.length; i++) {
                        if (equipamientoEquipado[i] != null) {
                            equipamientoEquipado[i].setId(i);
                            if (!equipar(equipamientoEquipado[i])){
                                equipamientoGuardado = Arrays.copyOf(equipamientoGuardado, equipamientoGuardado.length + 1);
                                Equipamiento old = quitarEquipado(i);
                                old.setId(equipamientoGuardado.length -1);
                                equipamientoGuardado[equipamientoGuardado.length - 1] = old;
                                equipar(equipamientoEquipado[i]);
                            }
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
        } else {
            if (tieneBolsillo) {
                this.bolsa = new Item[] {Items.getItemRnd(), Items.getItemRnd(), Items.getItemRnd(), Items.getItemRnd()};
            } else {
                this.bolsa = new Item[] {Items.getItemRnd(), Items.getItemRnd(), Items.getItemRnd()};
            }
            Util.sortArray(this.bolsa);
        }
        this.puntosVida = this.getVidaMax();
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
        String cabecera;
        String inventario = cabecera = "Objetos disponibles:\n";
        for (int i = 0, cant = 0 , id = 1; i < bolsa.length && !(bolsa[i] == null); cant--, i += cant, i++) {
            cant = Items.cantidadItem(bolsa, bolsa[i]);
            inventario += id + " - " + bolsa[i].getNombre() + " (" + cant + ") (" + bolsa[i].getDescription() + ")\n";
            id++;
        }
        if (inventario.equals(cabecera)) {
            return null;
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
    public boolean useHabilidadRaza(Personaje enemigo){
        boolean haceEfecto = isHabilidadRazaActiva();
        if (haceEfecto) {
            boolean esHobbit = getRaza().equals(Raza.HOBBIT);
            Raza habilidad = esHobbit ? enemigo.getRaza() : raza;
            switch (habilidad) {
                case HUMANO:
                    if (esHobbit) { enemigo.quitarHabilidadRaza(); }
                    haceEfecto = true;
                    break;            
                case ELFO:
                    if (esHobbit) { enemigo.quitarHabilidadRaza(); }
                    haceEfecto = true;
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
                        return false;
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
                    haceEfecto = true;
                    break;
                case HOBBIT:
                    haceEfecto = false;
                    break;
                case ORCO:
                    this.fuerza = fuerza*2;
                    this.atacar(enemigo);
                    this.fuerza = fuerza/2;
                    if (esHobbit) { enemigo.quitarHabilidadRaza(); }
                    haceEfecto = true;
                    break;
                case TROLL:
                    if (esHobbit) { enemigo.quitarHabilidadRaza(); }
                    haceEfecto = true;
                    break;
                default:
                    throw new EntidadException("Error con la habilidad de raza");
            }
        }
        return haceEfecto;
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
        for (int i = 0; i < equipamientoGuardado.size(); i++) {
            if (equipamientoGuardado.get(i) == null) {
                Equipamiento e = Equipamiento.gachaEquipamiento(this.getNivel(), esGeneral, gachaArmas);
                e.setId(i);
                equipamientoGuardado.set(i, e);
                equipamientoGuardado.sort(null);
                return e;
            }
        }
        return null;
    }

    //----------------------------------------Equipamiento------------------------------------------
    // Equipado
    /**
     * Asigna el id del slot al que corresponde este equipamiento o devuelve si el Equipamiento está guardado en {@code equipamientoGuardado}
     * 
     * @param equip Equipo a encontrar en {@code equipamientoGuardado} 
     * @param setSlot Decide si asignar o no id del slot correspondiente para equiparlo  
     * @return {@code false} si no se encuentra el equipamiento o no se pudo asignar el id
     */
    public boolean setEquipGuardadoIdSlot(Equipamiento equip, boolean setSlot){
        boolean encontrado = false;
        Iterator<Equipamiento> it = equipamientoGuardado.iterator();
        while (it.hasNext()) {
            Equipamiento actual = it.next();
            if (actual != null && actual.equals(equip)) {
                encontrado = true;
                if (setSlot) {
                    return setSlotToEquip(equip);
                }
                break;
            }
        }
        return encontrado;
    }
    @Override
    public boolean equipar(Equipamiento equip) {
        if (equip == null) {
            return false;
        }
        int slot;
        try {
            slot = getSlot(equip);
        } catch (Exception e) {
            return false;
        }
        Equipamiento antiguo = quitarEquipado(slot);
        equipamientoGuardado.sort(Comparator.nullsLast(Comparator.naturalOrder()));
        if (setEquipGuardadoIdSlot(equip, true)) {
            guardarEquipamiento(antiguo);
            equipamientoGuardado.remove(equip);
            asignarIdGuardados();
            equipamientoEquipado[slot] = equip;
            return true;
        } else {
            if (antiguo != null) {
                equipamientoEquipado[slot] = antiguo;
            }
        }

        return false;
    }
    
    @Override
    public Equipamiento[] getEquipamientoEquipado() {
        return equipamientoEquipado;
    }


    @Override
    public Equipamiento quitarEquipado(int slot) throws EquipamientoException {
        if (slot < 0 || slot >= equipamientoEquipado.length) {
            throw new EquipamientoException("Slot inválido");
        }
        Equipamiento antiguo = equipamientoEquipado[slot];
        equipamientoEquipado[slot] = null;
        guardarEquipamiento(antiguo);
        return antiguo;
    }

    @Override
    public boolean quitarEquipado(Equipamiento equip) throws EquipamientoException {
        if (equip == null) {
            throw new EquipamientoException("No se puede quitar \"null\"");
        }
        for (int i = 0; i < equipamientoEquipado.length; i++) {
            if (equipamientoEquipado[i] != null && equipamientoEquipado[i].equals(equip)) {
                Equipamiento antiguo = equipamientoEquipado[i];
                equipamientoEquipado[i] = null;
                guardarEquipamiento(antiguo);
                return true;
            }
        }
        return false;
    }


    // Guardado
    public boolean guardarEquipamiento(Equipamiento equip){
        if (equip == null) {
            return false;
        }
        try {
            equipamientoGuardado.add((Equipamiento) equip.clone());
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        asignarIdGuardados();
        return true;
    }
    public String getStringEquipamientoGuardado() {
        equipamientoGuardado.sort(Comparator.nullsLast(Comparator.naturalOrder()));
        Equipamiento[] armas;
        Equipamiento[] armaduras;
        String separador = "-----------------------\n";
        String inventario = "Equipamiento guardado\n=====================\n";
        int lenthArmadura = equipamientoGuardado.size();
        int lastArma = equipamientoGuardado.size();
        for (int i = 0; i < equipamientoGuardado.size(); i++) {
            Equipamiento este = equipamientoGuardado.get(i);
            if (este == null) {
                lastArma = i;
                if (lenthArmadura == equipamientoGuardado.size()) {
                    lenthArmadura = i;
                }
                break;
            }
            if (este instanceof Arma && i < lenthArmadura) {
                lenthArmadura = i;
            }
        }
        if (lenthArmadura > 0) {
            armaduras = Arrays.copyOf((Equipamiento[])equipamientoGuardado.toArray(), lenthArmadura);
        } else {
            armaduras = new Equipamiento[0];
        }
        if (lenthArmadura - lastArma > 0) {
            armas = Arrays.copyOfRange((Equipamiento[])equipamientoGuardado.toArray(), lenthArmadura, lastArma);
        } else {
            armas = new Equipamiento[0];
        }
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
        return equipamientoGuardado.toArray(new Equipamiento[0]);
    }

    public void asignarIdGuardados(){
        equipamientoGuardado.sort(Comparator.nullsLast(Comparator.naturalOrder()));
        for (int i = 0; i < equipamientoGuardado.size(); i++) {
            Equipamiento e = equipamientoGuardado.get(i);
            if (e != null) {
                e.setId(i);
            } else {
                break;
            }
        }
    }

    //------------------------------------------------------------------------------------------------
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
        equipamientoGuardado.sort(null);
        equipamientos.put("Guardado", new JSONArray());
        for (int i = 0; i < equipamientoGuardado.size() && equipamientoGuardado.get(i) != null; i++) {
            if (equipamientoGuardado.get(i) != null) {
                equipamientos.append("Guardado", equipamientoGuardado.get(i).getJsonObject());
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
        equipamientoGuardado.sort(null);
        other.equipamientoGuardado.sort(null);
        if (!equipamientoGuardado.equals(other.equipamientoGuardado))
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
