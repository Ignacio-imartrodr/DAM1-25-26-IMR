package UD4.Rol.Entity.Others;

import UD4.Rol.Entity.Equipamiento.Equipamiento;
import UD4.Rol.Entity.Equipamiento.Arma.*;
import UD4.Rol.Entity.Equipamiento.Armadura.*;
import UD4.Rol.Utilidades.EquipamientoException;

public interface EquipEquipado {
    public Equipamiento[] equipamientoEquipado = new Equipamiento[5];

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
    default public boolean equipar(Equipamiento equip) {
        if (equip == null) {
            return false;
        }
        int slot = -1; // 0: Casco, 1: Pechera, 2: Pantalon, 3: Botas, 4: Arma
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
            return false;
        }
        equip.setId(slot);
        return setEquipado(equip);
        
    }
    default boolean setEquipado(Equipamiento equip) {
    //  Ejemplo basico
    int slot = equip.getId();
    if (equipamientoEquipado[slot] != null) {
            return false;
        }
        equipamientoEquipado[slot] = equip;
        return true;
    }

    default public Equipamiento quitarEquipamiento(int slot) {
        if (slot < 0 || slot >= equipamientoEquipado.length) {
            throw new EquipamientoException("Slot inválido");
        }
        Equipamiento antiguo = equipamientoEquipado[slot];
        equipamientoEquipado[slot] = null;
        return antiguo;
    }

    default public Equipamiento quitarEquipamiento(Equipamiento equip) {
        if (equip == null) { return null; }
        for (int i = 0; i < equipamientoEquipado.length; i++) {
            if (equipamientoEquipado[i] != null && equipamientoEquipado[i].equals(equip)) {
                Equipamiento antiguo = equipamientoEquipado[i];
                equipamientoEquipado[i] = null;
                return antiguo;
            }
        }
        return null;
    }

    default public String getStringEquipamientoEquipado() {
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
    default public Equipamiento[] getEquipamientoEquipado() {
        return java.util.Arrays.copyOf(equipamientoEquipado, equipamientoEquipado.length);
    }
}
