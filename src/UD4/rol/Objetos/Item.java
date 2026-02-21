package UD4.Rol.Objetos;

import UD4.Rol.Utilidades.ItemException;

/**
 * @author Ignacio MR
 */

public class Item {
    private String nombre;
    private int sanar = 0;
    private int damage = 0;
    private int duracion = 1;
    private boolean tieneHabilidadExtra = false;
    private String description;
    public Item(String nombre) {
        Items items = Items.stringToItems(nombre);
        this.nombre = items.toString();
        switch (items) {
            case POCION_VIDA: //Cura
                this.sanar = 75;
                this.description = "Cura 75 puntos de vida (puede exceder vida máx)";
                break;

            case BOMBA_DE_HUMO: // Te hace esquivar el proximo ataque
                this.tieneHabilidadExtra = true;
                this.description = "El enemigo falla el próximo ataque";
                break;

            case ENREDADERAS: // Bloquea la habilidad de raza del oponente 1 turno
                this.tieneHabilidadExtra = true;
                this.description = "Bloquea la habilidad de raza del oponente el próximo turno";
                break;
            
            case MECHERO: // Hace 10 de daño al enemigo por 3 turnos (daño acumulable)
                this.tieneHabilidadExtra = true;
                this.damage = 10;
                this.duracion = 3;
                this.description = "Haces 10 de daño al enemigo durante 3 turnos (daño acumulable)";
                break;

            default:
                throw new ItemException("Item sin valores asignados");
        }
    }
    public String getNombre() {
        return nombre;
    }
    public int getSanar() {
        return sanar;
    }
    public int getDamage() {
        return damage;
    }
    public int getDuracion() {
        return duracion;
    }
    public String getDescription() {
        return description;
    }
    public boolean haveHabilidadExtra() {
        return tieneHabilidadExtra;
    }
    public int compareTo(Item otherItem){
        return Items.stringToItems(this.getNombre()).compareTo(Items.stringToItems(otherItem.getNombre()));
    }
}
