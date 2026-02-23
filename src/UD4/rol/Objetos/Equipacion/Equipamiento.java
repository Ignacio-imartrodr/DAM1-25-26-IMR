package UD4.Rol.Objetos.Equipacion;

import UD4.Rol.Objetos.Rareza;

public class Equipamiento {
    private String nombre;
    private Rareza rareza;
    private int durabilidad;
    private int duravilidadMax;
    private int xp;
    private byte lvl;
    private final static int XP_MAX = 127999;

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
    protected void reparar(){

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
    public byte sumarXp(int puntos){// La experiencia va de 0 a 999 y luego vuelve a 0
        if (puntos == 0) {
            return 0;
        }
        int lvlsUp = 0;
        if (puntos > XP_MAX) {
            for (; puntos >= XP_MAX; puntos -= XP_MAX) {
                lvlsUp += this.sumarXp(125000);
            }
            lvlsUp += this.sumarXp(puntos);
        } else {
            if (puntos / 1000 != 0) {
                lvlsUp = (puntos / 1000);
                xp += puntos % 1000;
                if (xp >= 1000) {
                    xp %= 1000;
                    lvlsUp++;
                }
                for (int i = 0; i < lvlsUp; i++) {
                    subirNivel();
                }
            }
        }
        if (lvlsUp >= ((XP_MAX/1000) - ((int) lvl))) {
            lvlsUp = ((XP_MAX/1000) - ((int) lvl));
        }
        return (byte) lvlsUp;
    }
    protected void subirNivel(){
        lvl++;
        duravilidadMax += Math.round(duravilidadMax * 0.05);
        durabilidad = duravilidadMax;
    }
}
