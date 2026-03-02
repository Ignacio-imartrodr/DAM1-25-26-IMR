package UD4.Rol.Objetos.Entidades;

import java.util.Arrays;
import java.util.Random;
import org.json.JSONObject;
import UD4.Rol.Objetos.Equipamiento.Equipamiento;
import UD4.Rol.Objetos.Equipamiento.Armadura.*;
import UD4.Rol.Objetos.Equipamiento.Arma.*;
import UD4.Rol.Utilidades.EquipamientoException;
import UD4.Rol.Utilidades.PersonajeException;
import UD4.Rol.Utilidades.Util;

public abstract class Entidad{
    protected String nombre;
    protected int fuerza = 0;
    protected int agilidad = 0;
    protected int constitucion = 0;
    protected byte nivel= -128; //rango: [-128 a 127]
    protected int experiencia = 0;
    protected int puntosVida;
    protected boolean habilidadRazaActiva = true;
    protected Equipamiento[] equipamientoEquipado = new Equipamiento[5];
    protected Equipamiento[] equipamientoGuardado = new Equipamiento[50];
    protected static int vidaMin = 50;
    protected final static int EXP_MAX = 256999;

    private final static short CONVERSOR = 129; // Para pasar entre lvl y nivel

    protected Entidad(){
        this.nombre = null;
        this.fuerza = 0;
        this.agilidad = 0;
        this.constitucion = 0;
        this.nivel = 0;
        this.experiencia = 0;
    }
    protected Entidad(String nombre, String fuerza, String agilidad, String constitucion, String nivel, String experiencia, boolean yaExistente){
        
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
            this.fuerza = asignarStatRnd(fuerza);
            this.agilidad = asignarStatRnd(agilidad);
            this.constitucion = asignarStatRnd(constitucion);
            this.nivel = (byte) asignarStatNoRng(false, nivel);
            this.experiencia = asignarStatNoRng(true, experiencia);
        }
        puntosVida = getVidaMax();
    }

    /**
     * Asigna un valor valido a un stat o un valor random
     * @param   texto   :
     * @param   stat    : 0 para Fuerza, 1 para Agilidad o 2 para Constitucion
     * @return
     * 
     * 
     */
    protected int asignarStatRnd(String texto){
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
        return num;
    }
    protected int asignarStatNoRng(boolean esXp, String texto){
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
    
    public String getNombre(){
        return this.nombre;
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
    public int getNivel() {
        return nivel + CONVERSOR;
    }
    public int getExperiencia() {
        return experiencia;
    }
    public int getPuntosVida(){
        return puntosVida;
    }
    public int getVidaMax(){
        return vidaMin + constitucion;
    }
    protected String getFicha(String nombreEntidad){
        String ficha;
        String Cabecera = "Ficha " + nombreEntidad + " \n=================\n";
        String nombre = "Nombre: " + this.nombre;
        String nivel = "Nivel: " + this.nivel;
        String experiencia = "Experiencia: " + this.experiencia;
        String puntosVida = "Puntos de vida : (" + this.puntosVida + "/" + getVidaMax() + ")";
        String fuerza = "Fuerza: " + this.fuerza;
        String agilidad = "Agilidad: " + this.agilidad;
        String constitucion = "Constitución: " + this.constitucion;
        String overAll = "OverAll: " + (this.fuerza + this.agilidad + this.constitucion);

        ficha = String.format("%s%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n", Cabecera, nombre, nivel, experiencia, puntosVida, fuerza, agilidad, constitucion, overAll);
        return ficha;
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
    
    protected static int generarRnd1a100(){
        Random rnd = new Random();
        int num = rnd.nextInt(100) + 1;
        return num;
    }
    public byte sumarExperiencia(int puntos){// La experiencia va de 0 a 999 y luego vuelve a 0
        if (puntos > EXP_MAX) {
            throw new PersonajeException("Cantidad de experiencia excesiva para subir en una sola ejecución");
        }
        int lvlsUpIdeal = 0;
        /*if (puntos/1000 != 0) {
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
        }*/
        if (!(getNivel()*1000 + experiencia + puntos <= EXP_MAX)) {
            if (puntos / 1000 != 0) {
                lvlsUpIdeal = (puntos / 1000);
                experiencia += puntos % 1000;
                if (experiencia >= 1000) {
                    lvlsUpIdeal += experiencia / 1000;
                    experiencia = experiencia % 1000;
                }
            }
        } else if (getNivel() == (EXP_MAX / 1000)) {
            if (experiencia + puntos >= (EXP_MAX % 1000)) {
                experiencia = (EXP_MAX % 1000);
            } else {
                experiencia += puntos;
            }
        }
        if (lvlsUpIdeal >= ((EXP_MAX/1000) - getNivel())) {
            lvlsUpIdeal = ((EXP_MAX/1000) - getNivel());
        }
        int lvlsUpReal = 0;
        for (int i = 0; i < lvlsUpIdeal; i++) {
            if (subirNivel()) {
                lvlsUpReal++;
            }
        }
        byte resultado = (byte) (lvlsUpReal - CONVERSOR);
        return resultado;
    }
    public boolean subirNivel(){
        if (nivel != EXP_MAX/1000) {
            nivel++;
            fuerza += Math.round(fuerza * 0.05);
            agilidad += Math.round(agilidad * 0.05);
            int oldVidaMax = getVidaMax();
            constitucion += Math.round(constitucion * 0.05);
            puntosVida += (getVidaMax() - oldVidaMax);
            return true;
        } else {
            return false;
        }
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
    protected String pedirStatRng(){
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
    protected int pedirStatNoRng(boolean esXp){
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
    public Boolean gachaEquipamiento(boolean esGeneral, boolean... gachaArmas){
        boolean estaLleno = true;
        for (int i = 0; i < equipamientoGuardado.length; i++) {
            if (equipamientoGuardado[i] == null) {
                equipamientoGuardado[i] = (Equipamiento) Equipamiento.gachaEquipamiento(this.getNivel(), esGeneral, gachaArmas);
                estaLleno = false;
            }
        }
        return !estaLleno;
    }

    public JSONObject toJsonObject(){
        JSONObject entidad = new JSONObject();
        entidad.accumulate("nombre", nombre);
        entidad.accumulate("fuerza", fuerza);
        entidad.accumulate("agilidad", agilidad);
        entidad.accumulate("constitucion", constitucion);
        entidad.accumulate("nivel", nivel);
        entidad.accumulate("experiencia", experiencia);
        entidad.accumulate("vidaMax", getVidaMax());
        return entidad;
    }
    protected String toCsvString() {
        return String.format("%s,%d,%d,%d,%d,%d,%d,%d", nombre, fuerza, agilidad, constitucion, nivel, experiencia, getVidaMax(), puntosVida);
    }
    @Override
    public String toString(){
        String nombreYVida = nombre + " (" + puntosVida + "/" + getVidaMax() + ")";
        return nombreYVida;
    }

    public boolean equipar(Equipamiento equip) {
        if (equip == null) {
            throw new EquipamientoException("Equipamiento nulo");
        }
        int slot = -1; // 0: Casco, 1: Pechera, 2: Pantalon, 3: Botas, 4: Arma
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
                if (equipamientoGuardado[i] == null) {
                    equipamientoGuardado[i] = antiguo;
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
        return null;
    }

    public String getEquipamientoEquipado() {
        String inventario = "Equipamiento activo:\n-------------------\n";
        for (int i = 0; i < equipamientoEquipado.length; i++) {
            String parte;
            switch (i) {
                case 0 -> parte = "Casco: ";
                case 1 -> parte = "Pechera: ";
                case 2 -> parte = "Pantalón: ";
                case 3 -> parte = "Botas: ";
                case 4 -> parte = "Arma: ";
                default -> parte = "Error: ";
            };
            if (equipamientoEquipado[i] != null) {
                inventario += (i + 1) + " - " + parte + equipamientoEquipado[i].getNombre() + " " + equipamientoEquipado[i].getDurabilidadString() + "\n";
            } else {
                inventario += (i + 1) + " - " + parte + "vacío\n";
            }
        }
        return inventario;
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
        //Arrays.sort(equipamientoGuardado);TODO implementar el sort de util que mueve los null
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

    public Equipamiento[] getArrayEquipado() {
        return java.util.Arrays.copyOf(equipamientoEquipado, equipamientoEquipado.length);
    }
}
