package UD4.Rol.Objetos.Equipacion;

import UD4.Rol.Objetos.Rareza;
import UD4.Rol.Utilidades.PersonajeException;

public class Equipamiento {
    private String nombre;
    private Rareza rareza;
    private int durabilidad;
    private int duravilidadMax;
    private int xp;
    private byte lvl;
    private final static int EXP_MAX = 127999;

    public String getNombre() {
        return nombre;
    }
    public Rareza getRareza() {
        return rareza;
    }
    public String getDurabilidadString() {
        return "(" + durabilidad + "/" + duravilidadMax + ")";
    }
    public int getXp() {
        return xp;
    }
    public byte getLvl() {
        return lvl;
    }
    public void reparar(){

    }
    public boolean perderDurabilidad(int dañoRecivido){
        durabilidad -= dañoRecivido;
        return estaRoto();
    }
    public boolean estaRoto(){
        boolean Roto = false;
        if (durabilidad <= 0) {
            Roto = true;   
        }
        return Roto;
    }
    public byte sumarExperiencia(int puntos){// La xperiencia va de 0 a 999 y luego vuelve a 0
        if (puntos > EXP_MAX) {
            throw new PersonajeException("Cantidad de experiencia excesiva para subir en una sola ejecución");
        }
        byte lvlsUp = 0;
        if (puntos/1000 != 0) {
            lvlsUp = (byte) (puntos / 1000);
            xp += puntos % 1000;
            if (xp >= 1000 ) {
                if (lvlsUp == EXP_MAX/1000) {
                    throw new PersonajeException("Cantidad de experiencia excesiva para subir en una sola ejecución");
                }
                xp %= 1000;
                lvlsUp++;
            }
            for (int i = 0; i < lvlsUp; i++) {
                subirNivel();
            }
        }
        return lvlsUp;
    }
    public void subirNivel(){
        lvl++;
        duravilidadMax += Math.round(duravilidadMax * 0.05);
        durabilidad = duravilidadMax;
    }
}
