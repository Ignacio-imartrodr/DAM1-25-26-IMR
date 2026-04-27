package UD4.Rol.Entity.Others.Efectos;

import java.util.Set;

import UD4.Rol.Utilidades.EfectException;

public abstract class Efecto implements Comparable<Efecto> {//TODO refactorizar para hacer bien que sea abstracto

    protected String tipo;
    protected int duration;
    protected int cantEfect = 0;
    protected Set<Efecto> efectos;

    private static String[] tiposMultiples = new String[] {"FUROR_HEROICO"}; //Efectos que tienen múltiples efectos
    private static String[] debuffs = new String[] {"AGILIDAD", "ATURDIMIENTO", "CONSTITUCION", "CURA_EFICACE", "FUERZA", "QUEMADO"};
    private static String[] buffs = new String[] {"AGILIDAD", "CONSTITUCION", "CURA_EFICACE", "FUERZA", "REGENERACION"};

    public static Efecto newEfecto(String tipo, int duration, boolean esBuff, Efecto... efectosMultiples){
        boolean esMultiple = false;
        for (int i = 0; i < tiposMultiples.length; i++) {
            if (tipo.equals(tiposMultiples[i])) {
                esMultiple = true;
                break;
            }
        }
        if (esMultiple && efectosMultiples != null && efectosMultiples.length > 0) {
            return newEfecto(tipo, duration, 1, esBuff, efectosMultiples);
        }
        throw new EfectException("Valores invalidos para crear efecto");
    }
    public static Efecto newEfecto(String tipo, int duration, int cant, boolean esBuff, Efecto... efectosMultiples){
        if (duration < 1 || cant < 0) {
            throw new EfectException("Valores invalidos para crear efecto");
        }

        Efecto ef;
        tipo = tipo.strip().toUpperCase().replace(" ", "_");
        boolean esMultiple = false;
        for (int i = 0; i < tiposMultiples.length; i++) {
            if (tipo.equals(tiposMultiples[i])) {
                esMultiple = true;
                break;
            }
        }
        if (esMultiple && (efectosMultiples == null || efectosMultiples.length < 1)) {
            throw new EfectException("Valores invalidos para crear efecto");
        }
        if (esBuff) {
            for (int i = 0; i < buffs.length; i++) {
                if (esMultiple || tipo.equals(buffs[i])) {
                    try {
                        ef = new Buff(tipo, esMultiple ? efectosMultiples : null);
                        ef.setCantEfect(cant);
                        ef.setDuration(duration);
                        return ef;
                    } catch (Exception e) {
                        throw new EfectException("No existe este buff");
                    }
                }
            }
            throw new EfectException("No existe este buff");
        } else {
            for (int i = 0; i < debuffs.length; i++) {
                if (esMultiple || tipo.equals(debuffs[i])) {
                    try {
                        ef = new Debuff(tipo, esMultiple ? efectosMultiples : null);
                        ef.setCantEfect(cant * (-1));
                        ef.setDuration(duration);
                        return ef;
                    } catch (Exception a) {
                        throw new EfectException("No existe este debuff");
                    }
                }
            }
            throw new EfectException("No existe este debuff");
        }
    }

    public String getTipo() {
        return tipo;
    }
    public int getDuration() {
        return duration;
    }
    public int getCantEfect() {
        return cantEfect;
    }

    private boolean setDuration(int duration) {
        if (duration < 0) {
            return false;
        }
        this.duration = duration;
        return true;
    }
    private void setCantEfect(int cant) {
        this.cantEfect = cant;
    }
    
    public boolean durationDown(){
        if (duration > 0) {
            this.duration--;
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        String[] nom = tipo.split("_");
        for (int i = 0; i < nom.length; i++) {
            nom[i] = nom[i].charAt(0) + nom[i].substring(1).toLowerCase();
            if (i > 0) {
                nom[i] = " " + nom[i];
            }
        }
        String nombre = "";
        for (String string : nom) {
            nombre += string;
        }
        return nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())//TODO copmprobar si distingue entre buffs y debuffs
            return false;
        Efecto other = (Efecto) obj;
        if (tipo == null) 
            if (other.tipo != null)
                return false;
        
        if (!tipo.equals(other.tipo))
            return false;
        return true;
    }

    @Override
    public int compareTo(Efecto o) {
        return this.tipo.compareTo(o.tipo);
    }
}
