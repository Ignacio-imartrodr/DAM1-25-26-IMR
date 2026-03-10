package UD4.Rol.Entity;

import UD4.Rol.Utilidades.AfinidadException;
import UD4.Rol.Utilidades.Util;

public record Afinidad(String afinidad) implements Comparable<Afinidad>{//TODO entender funcionamiento para usarlo bien
    
    static String[] afinidades = new String[] {"CORTE", "GOLPEO", "MAGIA"};

    public Afinidad(String afinidad) {
        boolean correcto = false;
        for (String string : afinidades) {
            if (afinidad.equalsIgnoreCase(string)) {
                afinidad = string;
                correcto = true;
                break;
            }
        }
        if (!correcto) {
            throw new AfinidadException("Afinidad no existente");
        }
        this.afinidad = afinidad;
    }
    
    @Override
    public int compareTo(Afinidad otherAfinidad){
        int idThis = Util.UbiObjetoEnArray(this.afinidad, afinidades);
        int idOther = Util.UbiObjetoEnArray(otherAfinidad, afinidades);
        if ( idThis == (afinidades.length - 1) && idOther == (0)) {
            return -1;
        } else if (idThis == (0) && idOther == (afinidades.length - 1)) {
            return 1;
        } else {
            return Integer.compare(idThis, idOther);
        }
    }
    @Override
    public String toString() {
        String nombre;
        nombre = this.afinidad.charAt(0) + this.afinidad.substring(1).toLowerCase();
        return nombre;
    }
}
