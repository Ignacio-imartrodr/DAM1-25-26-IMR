package UD4.Rol.Objetos.Equipamiento;

import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import UD4.Rol.Objetos.Equipamiento.Arma.Barita;
import UD4.Rol.Objetos.Equipamiento.Arma.Espada;
import UD4.Rol.Objetos.Equipamiento.Arma.Maza;
import UD4.Rol.Objetos.Equipamiento.Armadura.Botas;
import UD4.Rol.Objetos.Equipamiento.Armadura.Casco;
import UD4.Rol.Objetos.Equipamiento.Armadura.Pantalon;
import UD4.Rol.Objetos.Equipamiento.Armadura.Pechera;
import UD4.Rol.Utilidades.EquipamientoException;
import UD4.Rol.Utilidades.RarezaException;
import UD4.Rol.Utilidades.Util;

public abstract class Equipamiento {
    protected JSONObject objetoBase;
    protected String nombre;
    protected Rareza rareza;
    protected int durabilidad;
    protected int xp = 0;
    protected byte lvl = -128; //rango: [-128 a 127]
    private final static short CONVERSOR = 129; // Para pasar entre lvl y nivel
    private final static int XP_MAX = 256999;
    final static protected String[] MATERIALES = new String[] {"MADERA", "COBRE", "HIERRO", "DIAMANTE", "ADAMANTIUM"};
    final static protected String RUTA_EQUIPAMIENTOS = "src\\UD4\\Rol\\Objetos\\Equipamiento\\Equipamientos.json";

    protected Equipamiento(String tipo, String subtipo, int num){
        try {
            JSONObject equipamiento = Util.rutaToJsonObject(RUTA_EQUIPAMIENTOS, tipo);
            JSONArray objetos = equipamiento.getJSONArray(subtipo);
            objetoBase = objetos.getJSONObject(num);
            this.nombre = objetoBase.getString("nombre");
            this.rareza = Rareza.StringToRareza(objetoBase.getString("rareza"));
            this.durabilidad = getDurabilidadMax();
        } catch (Exception e) {
            throw new EquipamientoException("Error con los tipos o subtipos");
        }
    }
    public String getNombre() {
        return nombre;
    }
    public Rareza getRareza() {
        return rareza;
    }
    public String getDurabilidadString() {
        return "(" + durabilidad + "/" + getDurabilidadMax() + ")";
    }
    public int getXp() {
        return xp;
    }
    public int getLvl() { //rango: [1 a 256]
        return ((int) lvl) + CONVERSOR;
    }
    protected int getDurabilidadMax() {
        int durabilidadMax = 0;
        switch (rareza) {
            case COMMUN:
                durabilidadMax = 1000;
                break;
            case RARE:
                durabilidadMax = 2500;
                break;
            case SPECIAL:
                durabilidadMax = 4000;
                break;
            case ULTRARE:
                durabilidadMax = 5500;
                break;
            case EPIC:
                durabilidadMax = 7000;
                break;
            case LEGENDARY:
                durabilidadMax = 9500;
                break;
            case CHAOTIC:
                durabilidadMax = 10000;
                break;
            default:
                throw new RarezaException("Rareza sin una durabilidad asignada");
        }
        for (int i = 1; i < getLvl(); i++) {
            durabilidadMax += Math.round(durabilidadMax * 0.25);
        }
        return durabilidadMax;
    }
    protected void reparar(){
        //TODO
    }
    public boolean perderDurabilidad(int dañoRecivido){
        durabilidad -= dañoRecivido;
        return estaRoto();
    }
    public boolean estaRoto(){
        return durabilidad <= 0 ? true : false;
    }
    public byte sumarXp(int puntos){// La experiencia va de 0 a 999 y luego vuelve a 0
        if (puntos == 0) {
            return 0;
        }
        int lvlsUp = 0;
        if (!(getLvl()*1000 + xp + puntos >= XP_MAX)) {
            if (puntos / 1000 != 0) {
                lvlsUp = (puntos / 1000);
                xp += puntos % 1000;
                if (xp >= 1000) {
                    xp %= 1000;
                    lvlsUp++;
                }
                
            }
        } else if (getLvl() == (XP_MAX / 1000)) {
            if (xp + puntos >= (XP_MAX % 1000)) {
                xp = (XP_MAX % 1000);
            } else {
                xp += puntos;
            }
        }
        if (lvlsUp >= ((XP_MAX/1000) - getLvl())) {
            lvlsUp = ((XP_MAX/1000) - getLvl());
        }
        byte resultado = (byte) (lvlsUp - CONVERSOR);
        subirNivel(resultado);
        return resultado;
    }
    protected void subirNivel(byte lvlsUp){
        for (int i = 0; i < (lvlsUp + CONVERSOR); i++) {
            lvl++;
        }
        durabilidad = getDurabilidadMax();
    }
    public static Object gacha(int lvlPersonaje){
        Object equipamientoObtenido = new Object();
        Random rng = new Random();
        boolean esArma = rng.nextBoolean();
        String key = "";
        byte rnd = (byte) (esArma ? rng.nextInt(3) : rng.nextInt(4));
        switch (rnd) {
            case 0:
                if (esArma) {
                    key = "Espada";
                } else {
                    key = "Casco";
                }
                break;
            case 1:
                if (esArma) {
                    key = "Barita";
                } else {
                    key = "Pechera";
                }
                break;
            case 2:
                if (esArma) {
                    key = "Maza";
                } else {
                    key = "Pantalon";
                }
                break;
            case 3:
                key = "Botas";
                break;
            
            default:
                break;
        }
        //TODO Modificar para tener diferentes prob
        int[] probArmas = new int[] {0,0,0,0,0,0,0,0,0,0,};
        int[] probArmaduras = new int[] {};
        rnd = (byte) (esArma ? rng.nextInt(probArmas.length) : rng.nextInt(probArmaduras.length));
        try {
            switch (key) {
                case "Espada":
                    equipamientoObtenido = new Espada(rnd);
                    break;
                case "Barita":
                    equipamientoObtenido = new Barita(rnd);
                    break;
                case "Maza":
                    equipamientoObtenido = new Maza(rnd);
                    break;
                case "Casco":
                    equipamientoObtenido = new Casco(rnd);
                    break;
                case "Pechera":
                    equipamientoObtenido = new Pechera(rnd);
                    break;
                case "Pantalon":
                    equipamientoObtenido = new Pantalon(rnd);
                    break;
                case "Botas":
                    equipamientoObtenido = new Botas(rnd);
                    break;
            }
        } catch (Exception e) {
            throw new EquipamientoException("Equipamiento no existente");
        }
        return equipamientoObtenido;
    }
    @Override
    public String toString() {
        return nombre;
    }
    
}
