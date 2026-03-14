package UD4.Rol.Entity.Equipamiento.Arma;

import java.util.Arrays;

import org.json.JSONObject;

import UD4.Rol.Entity.Equipamiento.Equipamiento;
import UD4.Rol.Entity.Equipamiento.Rareza;
import UD4.Rol.Entity.Equipamiento.Armadura.*;
import UD4.Rol.Utilidades.EquipamientoException;
import UD4.Rol.Utilidades.Util;

public class Barita extends Arma {

    int agilidad = 7;
    final static String KEY = "Barita";

    public Barita(JSONObject barita){
        this(barita.getString("rareza"));
        int comparador;
        this.nombre = super.nombre = barita.getString("nombre");
        this.rareza = super.rareza = (Rareza) barita.get("rareza");
        comparador = barita.getInt("durabilidad");
        if (comparador >= 1) {
            this.durabilidad = super.durabilidad = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Barita");
        }
        
        comparador = barita.getInt("xp");
        if (comparador >= 0 && comparador <= 999) {
            this.xp = super.xp = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Barita");
        }

        this.lvl = super.lvl= (byte) barita.get("lvl");

        comparador = barita.getInt("fuerza");
        if (comparador >= 1) {
            this.fuerza = super.fuerza = comparador;
        } else {
            throw new EquipamientoException("Error con el Json de Barita");
        }
        this.habilidad = super.habilidad = barita.optString("habilidad");
        this.objetoBase = super.objetoBase = getJsonObject();
    }
    public Barita(String rareza){
        int num = Util.UbiObjetoEnArray(Rareza.StringToRareza(rareza), Rareza.toArray());
        if (num > 1) {
            num--;
        } else if (num == 1) {
            throw new EquipamientoException("Rareza no válida");
        }
        this(num);
    }
    public Barita(int num){
        newArma(KEY, num);
        this.nombre = super.nombre;
        this.rareza = super.rareza;
        this.durabilidad = super.durabilidad;
        this.xp = super.xp;
        this.lvl = super.lvl;
        this.afinidad = super.afinidad;
        this.habilidad = super.habilidad;
        this.fuerza = (int) Math.round(super.fuerza * 0.8);
        this.objetoBase = super.objetoBase = getJsonObject(); 
    }
    protected void subirNivel(byte lvlsUp){
        super.subirNivel(lvlsUp);
        agilidad -= Math.round(agilidad * 0.15);
        this.fuerza = (int) Math.round(super.fuerza * 0.8);
    }
    public JSONObject getJsonObject() {
        objetoBase = super.getJsonObject();
        String key = "agilidad";
        if (objetoBase.opt(key) != null) {
            objetoBase.remove(key);
        }
        objetoBase.accumulate(key, agilidad);

        key = "fuerza";
        if (objetoBase.opt(key) != null) {
            objetoBase.remove(key);
        }
        objetoBase.accumulate(key, fuerza);

        return objetoBase;
    }

    public static void main(String[] args) {
        Barita barita = new Barita(0);
        Object equipamiento = Equipamiento.gachaEquipamiento(1, false, true);
        while (!equipamiento.getClass().equals(barita.getClass())) {
            System.out.println(equipamiento);
            equipamiento = Equipamiento.gachaEquipamiento(1, false, true);
        }
        barita = (Barita) equipamiento;
        barita.subirNivel( (byte) -126);
        System.out.println(barita);
        Equipamiento[] equip = new Equipamiento[4];
        for (int i = 0; i < equip.length; i++) {
            equipamiento = Equipamiento.gachaEquipamiento(1, true, true);            
            try {
                Espada subtip = (Espada) equipamiento;
                equip[i] = subtip;
            } catch (Exception e) {
                try {
                    Barita subtip = (Barita) equipamiento;
                    equip[i] = subtip;
                } catch (Exception o) {
                    try {
                        Maza subtip = (Maza) equipamiento;
                        equip[i] = subtip;
                    } catch (Exception z) {
                        try {
                            Casco subtip = (Casco) equipamiento;
                            equip[i] = subtip;
                        } catch (Exception b) {
                            try {
                                Pechera subtip = (Pechera) equipamiento;
                                equip[i] = subtip;
                            } catch (Exception u) {
                                try {
                                    Pantalon subtip = (Pantalon) equipamiento;
                                    equip[i] = subtip;
                                } catch (Exception a) {
                                    Botas subtip = (Botas) equipamiento;
                                    equip[i] = subtip;
                                }
                            }
                        }
                    }
                }
            }
        }
        Arrays.sort(equip);
        System.out.println(Arrays.toString(equip));
    }
}
