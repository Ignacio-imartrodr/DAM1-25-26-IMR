package UD4.Rol.Objetos.Entidades;

import UD4.Rol.Objetos.Equipamiento.Armadura.*;
import UD4.Rol.Objetos.Equipamiento.Arma.*;
import UD4.Rol.Objetos.Equipamiento.Equipamiento;
import UD4.Rol.Utilidades.EquipamientoException;

public class EquipamientoTest {
    public static void main(String[] args) {
        Personaje p = new Personaje("Tester");

        try {
            System.out.println("========== PRUEBA 1: Equipar items básicos ==========");
            Casco casco1 = new Casco(0);
            System.out.println("Equipando casco1: " + casco1.getNombre());
            p.equipar(casco1);
            System.out.println("✓ Casco equipado correctamente.");

            Espada espada = new Espada(0);
            System.out.println("Equipando espada: " + espada.getNombre());
            p.equipar(espada);
            System.out.println("✓ Espada equipada correctamente.");

            Pechera pechera = new Pechera(0);
            System.out.println("Equipando pechera: " + pechera.getNombre());
            p.equipar(pechera);
            System.out.println("✓ Pechera equipada correctamente.");

            System.out.println("\n========== PRUEBA 2: Intentar equipar duplicados ==========");
            Casco casco2 = new Casco(1);
            System.out.println("Intentando equipar casco2: " + casco2.getNombre());
            try {
                p.equipar(casco2);
                System.out.println("ERROR: debería haber lanzado excepción por slot ocupado.");
            } catch (EquipamientoException ex) {
                System.out.println("✓ Excepción esperada: " + ex.getMessage());
            }

            Espada espada2 = new Espada(1);
            System.out.println("Intentando equipar espada2: " + espada2.getNombre());
            try {
                p.equipar(espada2);
                System.out.println("ERROR: debería haber lanzado excepción por arma ya equipada.");
            } catch (EquipamientoException ex) {
                System.out.println("✓ Excepción esperada: " + ex.getMessage());
            }

            System.out.println("\n========== PRUEBA 3: Equipar reemplazando (guarda en equipamientoGuardado) ==========");
            Casco casco3 = new Casco(2);
            System.out.println("Equipando casco3: " + casco3.getNombre() + " (reemplazará casco1)");
            p.equipar(casco3);
            System.out.println("✓ Casco3 equipado. El casco1 debe estar en equipamientoGuardado.");

            System.out.println("\n========== PRUEBA 4: Mostrar equipamientoEquipado ==========");
            System.out.println(p.getEquipamientoEquipado());

            System.out.println("\n========== PRUEBA 5: Verificar array equipamientoEquipado ==========");
            Equipamiento[] equipados = p.getArrayEquipado();
            System.out.println("Items equipados (acceso directo):");
            String[] nombresSlots = {"Casco", "Pechera", "Pantalón", "Botas", "Arma"};
            for (int i = 0; i < equipados.length; i++) {
                if (equipados[i] != null) {
                    System.out.println("  Slot " + i + " (" + nombresSlots[i] + "): " + equipados[i].getNombre());
                } else {
                    System.out.println("  Slot " + i + " (" + nombresSlots[i] + "): vacío");
                }
            }

            System.out.println("\n========== PRUEBA 6: Mostrar equipamientoGuardado ==========");
            System.out.println(p.getEquipamientoGuardado());

            System.out.println("\n========== PRUEBA 7: Quitar equipamiento ==========");
            System.out.println("Retirando casco (slot 0)...");
            Equipamiento retirado = p.quitarEquipamiento(0);
            System.out.println("✓ Retirado: " + (retirado != null ? retirado.getNombre() : "null"));

            System.out.println("\nEquipamiento actualizado:");
            equipados = p.getArrayEquipado();
            for (int i = 0; i < equipados.length; i++) {
                if (equipados[i] != null) {
                    System.out.println("  Slot " + i + ": " + equipados[i].getNombre());
                }
            }

            System.out.println("\n========== PRUEBA COMPLETADA EXITOSAMENTE ==========");

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
