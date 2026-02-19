package UD4.Rol.Objetos;

import UD4.Instituto.Util;
import UD4.Rol.Utilidades.ItemException;

/**
 * @author Ignacio MR
 */

public class Item {
    String nombre;
    int fuerza = 0;
    int sanar = 0;
    int agilidad = 0;
    boolean tieneHabilidadExtra;
    public Item(String nombre) {
        if (Util.verificaObjetoEnArray(nombre.toUpperCase(), Items.toArray())) {
            this.nombre = nombre;
        } else {
            throw new ItemException("Item no existente");
        }
        this.tieneHabilidadExtra = false;
        switch (Items.StringToItem(nombre)) {
            case POCION_VIDA:
                this.sanar = 75;
                break;

            case CUCHILO:
                this.fuerza = 7;
                break;
            
            case SACO_DE_VIENTO:
                this.agilidad = 7;
                break;

            default:
                throw new ItemException("Item sin valores asignados");
        }
    }

    
}
