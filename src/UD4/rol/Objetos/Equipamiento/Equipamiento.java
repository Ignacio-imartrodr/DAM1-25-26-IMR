package UD4.Rol.Objetos.Equipamiento;

import java.util.Arrays;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;
import UD4.Rol.Objetos.Equipamiento.Arma.*;
import UD4.Rol.Objetos.Equipamiento.Armadura.*;
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
        int durabilidadMax;
        switch (rareza) {
            case COMMUN:
                durabilidadMax = 2500;
                break;
            case SPECIAL:
                durabilidadMax = 4000;
                break;
            case RARE:
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

    private static int numDe1000ConInterpolarFromNivel(int porcentage, int cantARestar, int lvlEntidad){
        int interpolacion = (((lvlEntidad - 1) / 255) * (cantARestar * 10));
        int num = 1000 - (1000 - (porcentage * 10) - interpolacion);
        return num;
    }
    public static Object gachaEquipamiento(int lvlEntidad, boolean esGeneral, boolean... gachaArmas){
        if (lvlEntidad > 256 || lvlEntidad < 1) {
            throw new EquipamientoException("Probabilidades del gacha erroneas por el limite del nivel");
        }
        if (!esGeneral) {
            if (gachaArmas.length != 1) {
                throw new EquipamientoException("La variable \"gachaArmas\" debe contener una variable tipo bolean");
            }
        }
        
        Object equipamientoObtenido = new Object();
        Random rng = new Random();
        boolean esArma;
        if (esGeneral) {
            esArma = rng.nextBoolean();
        } else {
            esArma = gachaArmas[0];
        }
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
        
        int[] prob = new int[0];
        //TODO revisar, pone todo a 0 o 5
        for (int i = 0; i < 1000; i++) {//Modificar porcentage para tener diferentes prob y cantARestar para el % que arrastro para darle a la siguiente categoría según el nivel
            if (esArma) {
                prob = Arrays.copyOf(prob, prob.length + 1);             //conLvlMin: 40%     30% 20% 6% 4% || conLvlMax: 33%     28% 23% 9% 6%
                if (i < numDe1000ConInterpolarFromNivel(40, 7, lvlEntidad)) { 
                    prob[prob.length - 1] = 0;
                } else if (i < numDe1000ConInterpolarFromNivel(30, 9, lvlEntidad)) {
                    prob[prob.length - 1] = 1;
                } else if (i < numDe1000ConInterpolarFromNivel(20, 3, lvlEntidad)) {
                    prob[prob.length - 1] = 2;
                } else if (i < numDe1000ConInterpolarFromNivel(3, 2, lvlEntidad)) {
                    prob[prob.length - 1] = 3;
                } else {
                    prob[prob.length - 1] = 5;
                }
            } else {
                prob = Arrays.copyOf(prob, prob.length + 1); // conLvlMin: 35% 30% 20% 10% 3% 2% || conLvlMax: 30% 27% 18% 15% 6% 4%
                if (i < numDe1000ConInterpolarFromNivel(35, 5, lvlEntidad)) {
                    prob[prob.length - 1] = 0;
                } else if (i < numDe1000ConInterpolarFromNivel(30, 8, lvlEntidad)) {
                    prob[prob.length - 1] = 1;
                } else if (i < numDe1000ConInterpolarFromNivel(20, 10, lvlEntidad)) {
                    prob[prob.length - 1] = 2;
                } else if (i < numDe1000ConInterpolarFromNivel(10, 5, lvlEntidad)) {
                    prob[prob.length - 1] = 3;
                } else if (i < numDe1000ConInterpolarFromNivel(3, 2, lvlEntidad)) {
                    prob[prob.length - 1] = 4;
                } else {
                    prob[prob.length - 1] = 5;
                }
            }
        }
        int random1000 = esArma ? rng.nextInt(prob.length) : rng.nextInt(prob.length);
        rnd = (byte) (esArma ? prob[random1000] : prob[random1000]);
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
        if (!(getLvl()*1000 + xp + puntos <= XP_MAX)) {
            if (puntos / 1000 != 0) {
                lvlsUp = (puntos / 1000);
                xp += puntos % 1000;
                if (xp >= 1000) {
                    lvlsUp += xp / 1000;
                    xp = xp % 1000;
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
    @Override
    public String toString() {
        return nombre;
    }
    
}
