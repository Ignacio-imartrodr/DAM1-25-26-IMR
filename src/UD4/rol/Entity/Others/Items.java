package UD4.Rol.Entity.Others;

import java.util.Arrays;
import java.util.Random;

import UD4.Rol.Utilidades.*;

/**
 * @author Ignacio MR
 */

public enum Items {

    POCION_VIDA, BOMBA_DE_HUMO, ENREDADERAS, MECHERO;

    public static Items stringToItems(String respuesta){
        Items item;
        try {
            item = Items.valueOf(respuesta.toUpperCase().strip().replace(" ", "_"));
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
    public static Item getItemRnd(){
        Random rnd = new Random();
        Items[] itemsCreados = toArray();
        int i = rnd.nextInt(itemsCreados.length - 1);
        Item itemRnd = new Item(itemsCreados[i].name());
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
