package UD4.Rol.Objetos;

import UD4.Rol.Utilidades.ItemException;

/**
 * @author Ignacio MR
 */

public class Item {
    String nombre;
    int fuerza = 0;
    int sanar = 0;
    int agilidad = 0;
    int duracion = 1;
    boolean tieneHabilidadExtra = false;
    public Item(String nombre) {
        boolean itemValido = false;
        String name = "";
        for (Items items : Items.values()) {
            if (nombre.toUpperCase().replace(" ", "_").equals(items.name())) {
                itemValido = true;
                name = items.toString();
                break;
            }
        }
        if (itemValido) {
            this.nombre = name;
        } else {
            throw new ItemException("Item no existente");
        }
        switch (Items.StringToItems(nombre)) {
            case POCION_VIDA: //Cura
                this.sanar = 75;
                break;

            case CUCHILLO: // Añade daño
                this.fuerza = 7;
                this.duracion = -1;
                break;
            
            case SACO_DE_VIENTO: //Añade agilidad
                this.agilidad = 7;
                this.duracion = -1;
                break;
            
            case BOMBA_DE_HUMO: // te hace esquivar el proximo ataque
                this.agilidad = 999;
                break;

            case ENREDADERAS: // bloquea la habilidad de raza del oponente 1 turno
                this.tieneHabilidadExtra = true;
                break;

            default:
                throw new ItemException("Item sin valores asignados");
        }
    }
    public String getNombre() {
        return nombre;
    }
    
}
