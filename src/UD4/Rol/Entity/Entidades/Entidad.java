package UD4.Rol.Entity.Entidades;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONObject;

import UD4.Rol.Entity.Equipamiento.Equipamiento;
import UD4.Rol.Entity.Others.Efectos.Buff;
import UD4.Rol.Entity.Others.Efectos.Efecto;
import UD4.Rol.Utilidades.EntidadException;

public abstract class Entidad implements Comparable<Entidad> {
    protected String nombre;
    protected int fuerza;
    protected int agilidad;
    protected int constitucion;
    protected byte nivel= -128; //rango: [-128 a 127] //el geter devolverá de [1 a 256]
    protected int experiencia;
    protected int puntosVida;
    protected int vidaMin;
    protected int[] minMaxRndStats;
    protected Set<Efecto> efectosAlterados = new TreeSet<>(); // Guarda los efectos si tiene si no está vacío y sin repetidos
    protected boolean isAturdido = false; //TODO implementar

    protected final static int EXP_MAX = 256999;

    protected final static short CONVERSOR = 129; // Para pasar entre lvl y nivel

    protected void newEntidad(String nombre, String fuerza, String agilidad, String constitucion, String nivel, String experiencia, boolean yaExistente){
        if (nombre == null || nombre.isBlank()) {
            this.nombre = null;
        } else {
            nombre = nombre.strip();
            this.nombre = Character.toUpperCase(nombre.charAt(0)) + nombre.substring(1).toLowerCase();
        }
        
        if (yaExistente) {
            if (Integer.parseInt(constitucion) < 1 || Integer.parseInt(agilidad) < 1 || Integer.parseInt(fuerza) < 1 || Integer.parseInt(nivel) < -128 || Integer.parseInt(nivel) > 127 || Integer.parseInt(experiencia) < 0 ) {
                throw new EntidadException("Valores fuera de límites");
            } else {
                this.fuerza = Integer.parseInt(fuerza);
                this.agilidad = Integer.parseInt(agilidad);
                this.constitucion = Integer.parseInt(constitucion);
                this.nivel = (byte) Integer.parseInt(nivel);
                this.experiencia = Integer.parseInt(experiencia);
            }
            
        } else {
            this.fuerza = asignarStatRnd(fuerza, 0);
            this.agilidad = asignarStatRnd(agilidad, 1);
            this.constitucion = asignarStatRnd(constitucion, 2);
            this.nivel = (byte) asignarStatNoRng(false, nivel);
            this.experiencia = asignarStatNoRng(true, experiencia);
        }
        puntosVida = getVidaMax();
    }

    /**
     * Asigna un valor valido a un stat o un valor random
     * @param   texto   : String con el valor numérico a asignar o null para num rnd de 1 a 100.
     * @return Valor int adecuado.
     */
    protected int asignarStatRnd(String texto, int stat){
        final int MIN;
        final int MAX;
        switch (stat) {
            case 0 ->{
                MIN = minMaxRndStats[0];
                MAX = minMaxRndStats[1];}
            case 1 ->{
                MIN = minMaxRndStats[2];
                MAX = minMaxRndStats[3];}
            case 2 -> {
                MIN = minMaxRndStats[4];
                MAX = minMaxRndStats[5];}
            default-> {
                MIN = -1;
                MAX = -1;}
        }
        int num;
        if ( !(texto == null) && (Integer.parseInt(texto) < MIN || Integer.parseInt(texto) > MAX)) {
            throw new EntidadException("Valores fuera de límites");
        }
        if (texto == null) {
            num = generarRndMinAMax(MIN, MAX);
        } else {
            num = Integer.parseInt(texto);
        }
        return num;
    }
    protected int asignarStatNoRng(boolean esXp, String texto){
        int num;
        final int MIN = esXp ? 0 : 1;
        final int MAX = esXp ? 999 : 256;
        if ( !(texto == null) && (Integer.parseInt(texto) < MIN || Integer.parseInt(texto) > MAX)) {
            throw new EntidadException("Valores fuera de límites");
        }
        if (texto == null) {
            num = MIN;
        } else {
            num = Integer.parseInt(texto);
            if (!esXp) {
                for (int i = 0; i < num; i++) {
                    if (subirNivel()) { nivel--; }
                }
            }
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
    public abstract String getFicha();
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

        ficha = String.format("%s%s%n%s%n%s%n%s%n%s%n%s%n%s%n", Cabecera, nombre, nivel, experiencia, puntosVida, fuerza, agilidad, constitucion);
        return ficha;
    }
    public boolean isAturdido(){
        return isAturdido;
    }

    public Integer aplicarEfectos() {
        Integer resultado = null;
        int curaEfect = 0;
        Iterator<Efecto> it = efectosAlterados.iterator();
        while (it.hasNext()) {
            Efecto efecto = it.next();
            String tipo = efecto.getTipo();
            boolean finEfecto = false;
            switch (tipo) {
                case "AGILIDAD":
                    if (efecto.durationDown()) {
                        if (efecto instanceof Buff) {
                            // TODO aplicar efecto
                        } else {
                            // TODO aplicar efecto
                        }
                    } else {
                        finEfecto = true;
                    }
                    break;
                case "FUERZA":
                    if (efecto.durationDown()) {
                        if (efecto instanceof Buff) {
                            // TODO aplicar efecto
                        } else {
                            // TODO aplicar efecto
                        }
                    } else {
                        finEfecto = true;
                    }
                    break;
                case "CONSTITUCION":
                    if (efecto.durationDown()) {
                        if (efecto instanceof Buff) {
                            // TODO aplicar efecto
                        } else {
                            // TODO aplicar efecto
                        }
                    } else {
                        finEfecto = true;
                    }
                    break;
                case "CURA_EFICACE":
                    if (efecto.durationDown()) {
                        if (efecto instanceof Buff) {
                            resultado = curaEfect =+ efecto.getCantEfect();
                        } else {
                            resultado = curaEfect =- efecto.getCantEfect();
                        }
                    } else {
                        finEfecto = true;
                    }
                    break;
                case "ATURDIMIENTO":
                    if (efecto.durationDown()) {
                        isAturdido = true;
                    } else {
                        efectosAlterados.remove(efecto);
                        isAturdido = false;
                    }
                    break;
                case "REGENERACION":
                    if (efecto.durationDown()) {
                        perderVida(efecto.getCantEfect() + ((efecto.getCantEfect() * curaEfect) / 100));
                    } else {
                        finEfecto = true;
                    }
                    break;
                case "QUEMADO":
                    if (efecto.durationDown()) {
                        perderVida(efecto.getCantEfect());
                    } else {
                        finEfecto = true;
                    }
                    break;
                default:
                    break;
            }
            if (finEfecto) {
                efectosAlterados.remove(efecto);
            }
        }
        return resultado;
        /*if (efectos == null || efectos.length == 0) {
            return;
        }
        int bonusFuerza = bonus[0];
        int bonusAgilidad = bonus[1];
        int bonusConstitucion = bonus[2];
        fuerza += sustraer ? -bonusFuerza : bonusFuerza;
        agilidad += sustraer ? -bonusAgilidad : bonusAgilidad;
        constitucion += sustraer ? -bonusConstitucion : bonusConstitucion;*/
    }
    
    protected static int generarRndMinAMax(int min, int max){
        Random rnd = new Random();
        int num = rnd.nextInt(min, max + 1);
        return num;
    }
    public byte sumarExperiencia(int puntos){// La experiencia va de 0 a 999 y luego vuelve a 0
        if (puntos > EXP_MAX) {
            throw new EntidadException("Cantidad de experiencia excesiva para subir en una sola ejecución");
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
    public int atacar(Entidad enemigo){
        int puntAtaque = generarRndMinAMax(1, 100) + fuerza;
        int puntDefensa = generarRndMinAMax(1, 100) + enemigo.agilidad;
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
    
    public Equipamiento equipamientoSemiRnd(boolean esGeneral, boolean... gachaArmas){
        return Equipamiento.gachaEquipamiento(this.getNivel(), esGeneral, gachaArmas);
    }

    public boolean addEfect(Efecto efecto){
        if (efecto == null) {
            return false;
        }
        if (!this.efectosAlterados.add(efecto)) {
            efectosAlterados.remove(efecto);
            this.efectosAlterados.add(efecto);
        }
        return true;
    }
    public boolean endEfect(String efecto){
        if (efecto == null || efectosAlterados.isEmpty()) {
            return false;
        }
        
        Efecto efect = null;
        for (Efecto ef : efectosAlterados) {
            if (ef.getTipo().equals(efecto.strip().toUpperCase().replace(" ", "_"))) {
                efect = ef;
            }
        }
        if (efect == null) {
            return false;
        }
        return efectosAlterados.remove(efect);
    }

    public boolean hasEfect(){
        return !efectosAlterados.isEmpty();
    }

    public Efecto[] getEfectosAlterados(){
        return efectosAlterados.toArray(new Efecto[0]);
    }

    public JSONObject toJsonObject() {
        JSONObject entidad = new JSONObject();
        JSONObject stats = new JSONObject();
        stats.put("nombre", nombre);
        stats.put("fuerza", fuerza);
        stats.put("agilidad", agilidad);
        stats.put("constitucion", constitucion);
        stats.put("nivel", nivel);
        stats.put("experiencia", experiencia);
        stats.put("vidaMax", getVidaMax());
        
        entidad.put("Stats", stats);
        
        return entidad;
    }

    abstract public String toString();
    abstract public boolean equals(Object other);
    @Override
    public int compareTo(Entidad other) {
        int agilidadComp = Integer.compare(this.agilidad, other.agilidad);
        if (agilidadComp != 0) { return agilidadComp; }
        int constComp = Integer.compare(this.constitucion, other.constitucion);
        if (constComp != 0) { return agilidadComp; }
        int fuerzaComp = Integer.compare(this.fuerza, other.fuerza);
        if (fuerzaComp != 0) { return agilidadComp; }
        int nivelComp = Integer.compare(this.nivel, other.nivel);
        if (nivelComp != 0) { return nivelComp; }
        int expComp = Integer.compare(this.experiencia, other.experiencia);
        if (expComp != 0) { return nivelComp; }
        int nomComp = (this.nombre).compareTo(other.nombre);
        return nomComp;
    }
}
