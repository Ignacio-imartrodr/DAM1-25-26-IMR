package UD4.Rol.Objetos.Equipamiento;

import org.json.JSONObject;

import UD4.Rol.Objetos.Rareza;
import UD4.Rol.Utilidades.EquipamientoException;
import UD4.Rol.Utilidades.RarezaException;
import UD4.Rol.Utilidades.Util;

public class Equipamiento {
    protected String nombre;
    protected Rareza rareza;
    protected int durabilidad = getDurabilidadMax();
    protected int xp;
    protected byte lvl; //rango: [-128 a 127]
    private final short CONVERSOR = 129; // Para pasar entre lvl y nivel
    private final static int XP_MAX = 256999;
    private final String[] PIEZAS = new String[] {"CASCO", "PECHERA", "PANTALON", "BOTAS"};
    private final String[] ARMAS = new String[] {"ESPADA", "BARITA"};
    private final String[] EQUIPAMIENTO = new String[] {"ARMADURA", "ARMA"};
    final static protected String RUTA_EQUIPAMIENTOS = "src\\UD4\\Rol\\Objetos\\Equipacion\\Equipamientos.json";

    protected Equipamiento(String tipo, String Subtipo){
        boolean valido = false;
        for (String string : EQUIPAMIENTO) {
            if (tipo.toUpperCase().equals(string)) {
                if (string.toUpperCase().equals("ARMA")) {
                    for (String string2 : ARMAS) {
                        if (Subtipo.toUpperCase().equals(string2)) {
                            valido = true;
                            break;
                        }
                    }
                } else {
                    for (String string2 : PIEZAS) {
                        if (Subtipo.toUpperCase().equals(string2)) {
                            valido = true;
                            break;
                        }
                    }
                }
                break;
            }
        }
        if (valido) {
            JSONObject Atributos = Util.rutaToJsonObject(RUTA_EQUIPAMIENTOS, tipo);
            this.nombre = Atributos.getJSONObject(Subtipo).getString("nombre");
            this.rareza = Rareza.StringToRareza(Atributos.getJSONObject(Subtipo).getString("rareza"));
            this.xp = Atributos.getJSONObject(Subtipo).getInt("xp");
            this.lvl = (byte) (Atributos.getJSONObject(Subtipo).getInt("nivel") - CONVERSOR);
        } else {
            throw new EquipamientoException("Error con las clases o subclases");
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
                durabilidadMax = 500;
                break;
            case RARE:
                durabilidadMax = 2000;
                break;
            case SPECIAL:
                durabilidadMax = 3500;
                break;
            case ULTRARE:
                durabilidadMax = 5000;
                break;
            case EPIC:
                durabilidadMax = 7500;
                break;
            case LEGENDARY:
                durabilidadMax = 9000;
                break;
            default:
                throw new RarezaException("Rareza sin una durabilidad asignada");
        }
        for (int i = 1; i < getLvl(); i++) {
            durabilidadMax += Math.round(durabilidadMax * 0.5);
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
        if (!(getLvl()*1000 + xp + puntos >= XP_MAX)) {
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
        return (byte) lvlsUp;
    }
    protected void subirNivel(){
        lvl++;
        durabilidad = getDurabilidadMax();
    }
}
