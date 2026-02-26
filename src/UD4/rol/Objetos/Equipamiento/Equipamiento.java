package UD4.Rol.Objetos.Equipamiento;

import org.json.JSONArray;
import org.json.JSONObject;

import UD4.Rol.Objetos.Rareza;
import UD4.Rol.Utilidades.EquipamientoException;
import UD4.Rol.Utilidades.RarezaException;
import UD4.Rol.Utilidades.Util;

public abstract class Equipamiento {
    protected JSONObject objetoBase;
    protected String nombre;
    protected Rareza rareza;
    protected int durabilidad = getDurabilidadMax();
    protected int xp = 0;
    protected byte lvl = -128; //rango: [-128 a 127]
    private final static short CONVERSOR = 129; // Para pasar entre lvl y nivel
    private final static int XP_MAX = 256999;
    final static protected String[] MATERIALES = new String[] {"MADERA", "COBRE", "HIERRO", "DIAMANTE", "ADAMANTIUM"};
    final static protected String RUTA_EQUIPAMIENTOS = "src\\UD4\\Rol\\Objetos\\Equipacion\\Equipamientos.json";

    protected Equipamiento(String tipo, String subtipo, int num){
        try {
            JSONObject equipamiento = Util.rutaToJsonObject(RUTA_EQUIPAMIENTOS, tipo);
            JSONArray atributos = equipamiento.getJSONArray(subtipo);
            objetoBase = atributos.getJSONObject(num);
            this.nombre = objetoBase.getString("nombre");
            this.rareza = Rareza.StringToRareza(objetoBase.getString("rareza"));
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
}
