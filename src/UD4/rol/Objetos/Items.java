package UD4.Rol.Objetos;

import java.util.Arrays;

import UD4.Rol.Utilidades.ItemException;

/**
 * @author Ignacio MR
 */

public enum Items {
    POCION_VIDA, CUCHILO, SACO_DE_VIENTO;

    public static Items StringToItem(String respuesta){
        Items item;
        try {
            item = Items.valueOf(respuesta.toUpperCase());
            return item;
        } catch (Exception e) {
            throw new ItemException("Item no válido.");
        }
        
    }
    public static Items[] toArray(){
        Items[] items = new Items[0];
        for (Items stat : Items.values()) {
            items = Arrays.copyOf(items, items.length + 1);
            items[items.length - 1] = stat;
        }
        return items;
    }
    @Override
    public String toString() {
        String string;
        string = this.name().charAt(0) + this.name().substring(1).toLowerCase();
        string = string.replace("_", " ");
        return string;
    }
}
