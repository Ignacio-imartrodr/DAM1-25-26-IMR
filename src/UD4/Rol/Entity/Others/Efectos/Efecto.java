package UD4.Rol.Entity.Others.Efectos;

import java.util.Set;

import UD4.Rol.Utilidades.EfectException;

public abstract class Efecto {

    protected String tipo;
    protected int duration;
    protected int cantEfect = 0;
    protected Set<Efecto> efectos;

    private static String[] tiposEspeciales = new String[] {"FUROR_HEROICO"}; //Efectos que tienen múltiples efectos
    private static String[] debuffs = new String[] {"ATURDIMIENTO", "CONSTITUCION", "CURA", "DEBILIDAD", "LENTITUD", "QUEMADO"};
    private static String[] buffs = new String[] {"AGILIDAD", "ATURDIMIENTO", "CONSTITUCION", "CURA", "FUERZA", "LENTITUD", "REGENERACION"};

    public static Efecto newEfecto(String tipo, int duration, int cant){
        if (duration < 1 || cant == 0) {
            throw new EfectException("Valores invalidos para crear efecto");
        }

        Efecto ef;
        tipo = tipo.strip().toUpperCase().replace(" ", "_");
        boolean esEspecial = false;
        for (int i = 0; i < tiposEspeciales.length; i++) {
            if (tipo.equals(tiposEspeciales[i])) {
                switch (tipo) {
                    case "FUROR_HEROICO":
                        esEspecial = true;
                        break;
                    default:
                        esEspecial = false;
                    break;
                }
                break;
            }
        }

        boolean esBuff = false;
        for (int i = 0; i < buffs.length; i++) {
            if (tipo.equals(buffs[i])) {
                esBuff = true;
                break;
            }
        }//TODO terminar
        if (esBuff) {
            try {
                ef = new Buff(tipo, esEspecial);
                ef.setCantEfect(cant);
                ef.setDuration(duration);
            } catch (Exception e) {
                throw new EfectException("No existe este efecto");
            }
        } else {
            for (int i = 0; i < debuffs.length; i++) {
                if (tipo.equals(buffs[i])) {
                    try {
                        ef = new Debuff(tipo, esEspecial);
                    } catch (Exception a) {
                        throw new EfectException("No existe este efecto");
                    }
                    break;
                }
            }
        }
        
        
        return ef;
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
        if (getClass() != obj.getClass())
            return false;
        Efecto other = (Efecto) obj;
        if (tipo == null) {
            if (other.tipo != null)
                return false;
        } else if (!tipo.equals(other.tipo))
            return false;
        return true;
    }
}
