package UD4.Rol.Objetos;

import java.util.Arrays;
import java.util.Random;

import UD4.Rol.Utilidades.*;

/**
 * @author Ignacio MR
 */

public enum Items {

    POCION_VIDA, BOMBA_DE_HUMO, ENREDADERAS, MECHERO;
    
    public static Item[] conHabilidades(){
        Item[] array = new Item[0];
        for (Items item : Items.values()) {
            Item objeto = new Item(item.name());
            if (objeto.haveHabilidadExtra()) {
                array = Arrays.copyOf(array, array.length + 1);
                array[array.length - 1] = objeto;
            }
        }
        Items.sort(array);
        return array;
    }

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
    public static Item[] sort(Item[] x){
        boolean conNull = false;
        int firtPosNull = x.length;
        for (Item item : x) {
            if (item == null) {
                conNull = true;
                break;
            }
        }
        if (conNull) {
            for (int i = 0, j = x.length - 1; i < j; i++) {
                if (x[i] == null) {
                    while (x[j] == null) {
                        j--;
                    }
                    x[i] = x[j];
                    x[j] = null;
                }
                firtPosNull = j;
            }
        }
        for (int i=0; i < firtPosNull; i++) {
            for (int j = i; j > 0 && ((Comparable<Items>) Items.stringToItems(x[j-1].getNombre())).compareTo(Items.stringToItems(x[j].getNombre()))>0; j--) {
                x = (Item[]) Util.swap(x, j, j-1);
            }
        }
        x = Arrays.copyOf(x, firtPosNull);
        return x;
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
        Items.sort(foo);
    }
}
