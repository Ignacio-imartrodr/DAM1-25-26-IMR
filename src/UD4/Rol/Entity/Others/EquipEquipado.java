package UD4.Rol.Entity.Others;

import UD4.Rol.Entity.Equipamiento.Equipamiento;
import UD4.Rol.Entity.Equipamiento.Arma.*;
import UD4.Rol.Entity.Equipamiento.Armadura.*;
import UD4.Rol.Utilidades.EquipamientoException;

public interface EquipEquipado {
    public static boolean isFormatoCorrecto(Equipamiento[] equipamientoEquipado) {
        if (equipamientoEquipado.length != 5) {
            return false;
        }
        if (!(equipamientoEquipado[0] instanceof Casco || equipamientoEquipado[0] == null)) {
            return false;
        }
        if (!(equipamientoEquipado[1] instanceof Pechera || equipamientoEquipado[1] == null)) {
            return false;
        }
        if (!(equipamientoEquipado[2] instanceof Pantalon || equipamientoEquipado[2] == null)) {
            return false;
        }
        if (!(equipamientoEquipado[3] instanceof Botas || equipamientoEquipado[3] == null)) {
            return false;
        }
        if (!(equipamientoEquipado[4] instanceof Arma || equipamientoEquipado[4] == null)) {
            return false;
        }
        return true;
        
    }

    default public boolean setSlotToEquip(Equipamiento equip){
        if (equip == null) {
            return false;
        }
        int slot;
        try {
            slot = getSlot(equip);
        } catch (Exception e) {
            return false;
        }
        equip.setId(slot);
        return true;
    }

    default int getSlot(Equipamiento equip) throws EquipamientoException{

        int slot; // 0: Casco, 1: Pechera, 2: Pantalon, 3: Botas, 4: Arma
        if (equip instanceof Casco) {
            slot = 0;
        } else if (equip instanceof Pechera) {
            slot = 1;
        } else if (equip instanceof Pantalon) {
            slot = 2;
        } else if (equip instanceof Botas) {
            slot = 3;
        } else if (equip instanceof Arma) {
            slot = 4;
        } else {
            throw new EquipamientoException("Equipamiento no equipable");
        }
        return slot;
    }
    abstract boolean equipar(Equipamiento equip);/* {
        //  Ejemplo basico a para modificar en implementación
        if (equip == null) {
            return false;
        }
        if (!setSlotToEquip(equip)) {
            return false;
        }
        int slot;
        try {
            slot = getSlot(equip);
        } catch (Exception e) {
            return false;
        }
        if (equipamientoEquipado[slot] != null) {
            return false;
        }
        equipamientoEquipado[slot] = equip;
        return true;
    }*/

    abstract public Equipamiento quitarEquipado(int slot);

    abstract public boolean quitarEquipado(Equipamiento equip);

    default public String getStringEquipamientoEquipado() {
        Equipamiento[] equipamientoEquipado = getEquipamientoEquipado();
        String inventario = "Equipamiento activo:\n-------------------\n";
        for (int i = 0; i < equipamientoEquipado.length; i++) {
            String parte;
            switch (i) {
                case 0 -> parte = "Casco: ";
                case 1 -> parte = "Pechera: ";
                case 2 -> parte = "Pantalón: ";
                case 3 -> parte = "Botas: ";
                case 4 -> parte = "Arma: ";
                default -> parte = "Error: ";
            };
            if (equipamientoEquipado[i] != null) {
                inventario += (i + 1) + " - " + parte + equipamientoEquipado[i].getNombre() + " " + equipamientoEquipado[i].getDurabilidadString() + "\n";
            } else {
                inventario += (i + 1) + " - " + parte + "vacío\n";
            }
        }
        return inventario;
    }
    abstract Equipamiento[] getEquipamientoEquipado();
}
