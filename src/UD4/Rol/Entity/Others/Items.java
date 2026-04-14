package UD4.Rol.Entity.Others;

import java.util.Random;

import UD4.Rol.Utilidades.*;

/**
 * @author Ignacio MR
 */

public enum Items {

    BOMBA_DE_HUMO, ENREDADERAS, MECHERO, POCION_VIDA;

    public static Items stringToItems(String respuesta){
        Items item;
        try {
            item = Items.valueOf(respuesta.strip().toUpperCase().replace(" ", "_"));
            return item;
        } catch (Exception e) {
            throw new ItemException("Item no válido.");
        }
        
    }

    public static Item getItemRnd(){
        Random rnd = new Random();
        int i = rnd.nextInt(values().length - 1);
        Item itemRnd = new Item(values()[i].name());
        return itemRnd;

    }
    public static int cantidadItem(Item[] t, Item clave){
        int cant = 0;
        for (int i = 0; i < t.length; i++) {
            if (t[i] != null && (t[i].getNombre()).equals(clave.getNombre())) {
                cant++;
            }
        }
        return cant;
    }
    @Override
    public String toString() {
        String string;
        string = this.name().charAt(0) + this.name().substring(1).toLowerCase();
        string = string.replace("_", " ");
        return string;
    }
    public static void main(String[] args) {
        Item[] foo = new Item[]{null, Items.getItemRnd(), new Item("Mechero"), Items.getItemRnd()};
        Util.sortArray(foo);
    }
}
