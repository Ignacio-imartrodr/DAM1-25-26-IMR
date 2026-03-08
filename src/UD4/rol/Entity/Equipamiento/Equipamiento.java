package UD4.Rol.Entity.Equipamiento;

import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;

import UD4.Rol.Entity.Equipamiento.Arma.*;
import UD4.Rol.Entity.Equipamiento.Armadura.*;
import UD4.Rol.Utilidades.EquipamientoException;
import UD4.Rol.Utilidades.RarezaException;
import UD4.Rol.Utilidades.Util;

public abstract class Equipamiento implements Comparable<Equipamiento>{
    protected int id;
    private String tipo;
    private String subtipo;
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
            objetoBase.accumulate("class", subtipo);
            this.nombre = objetoBase.getString("nombre");
            this.rareza = Rareza.StringToRareza(objetoBase.getString("rareza"));
            this.durabilidad = getDurabilidadMax();
        } catch (Exception e) {
            throw new EquipamientoException("Error con los tipos o subtipos");
        }
        this.tipo = tipo;
        this.subtipo = subtipo;
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
    
    public int getId() {
        return id;
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
    public JSONObject getJsonObject(){
        String key = "durabilidad";
        if (objetoBase.opt(key) != null) {
            objetoBase.remove(key);
        }
        objetoBase.accumulate(key, durabilidad);

        key = "xp";
        if (objetoBase.opt(key) != null) {
            objetoBase.remove(key);
        }
        objetoBase.accumulate(key, xp);

        key = "lvl";
        if (objetoBase.opt(key) != null) {
            objetoBase.remove(key);
        }
        objetoBase.accumulate(key, lvl);

        return objetoBase;
    }
    public String getString(){
        String este = "";
        este += getNombre() + "\n";
        este += "Rareza: " + getRareza() + "\n";
        este += "Durabilidad: " + getDurabilidadString() + "\n";
        este += "Nivel: " + getLvl() + "\n";
        este += "Experiencia: " + getXp() + "\n";
        return este;
    }
    public static Equipamiento newEquipamiento(JSONObject equip){
        Equipamiento equipamiento;
        String clase;
        try {
            clase = equip.getString("class").toUpperCase();
        } catch (Exception e) {
            throw new EquipamientoException("El JsonObject no es un equipamiento");
        }
        switch (clase) {
            case "CASCO":
                equipamiento = new Casco(equip);
                break;
            case "PECHERA":
                equipamiento = new Pechera(equip);
                break;
            case "PANTALON":
                equipamiento = new Pantalon(equip);
                break;
            case "BOTAS":
                equipamiento = new Botas(equip);
                break;
            case "ESPADA":
                equipamiento = new Espada(equip);
                break;
            case "BARITA":
                equipamiento = new Barita(equip);
                break;
            case "MAZA":
                equipamiento = new Maza(equip);
                break;
            default:
                throw new EquipamientoException("Equipamiento Erroneo");
        }
        return equipamiento;
    }

    public void setId(int id) {
        if (id >= 0) {
            this.id = id;
        } else {
            throw new EquipamientoException("Id no valido");
        }
    }

    private static int numDe1000ConInterpolarFromNivel(int porcentage, int cantARestar, int lvlEntidad) {
        int valorBase = porcentage * 10;
        int valorFinal = (porcentage - cantARestar) * 10;
        int resultado =  Math.round(valorBase + (valorFinal - valorBase) * ((lvlEntidad - 1) / (float) 255.0));
        
        return resultado;
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
        }
        
        int[] prob = new int[1000];
        if (esArma) {
            //conLvlMin: 40%     30% 20% 6% 4% || conLvlMax: 33%     28% 23% 9% 6%
            int p0 = numDe1000ConInterpolarFromNivel(40, 7, lvlEntidad);
            int p1 = p0 + numDe1000ConInterpolarFromNivel(30, 2, lvlEntidad);
            int p2 = p1 + numDe1000ConInterpolarFromNivel(20, -3, lvlEntidad);
            int p3 = p2 + numDe1000ConInterpolarFromNivel(6, -3, lvlEntidad);

            for (int i = 0; i < 1000; i++) {
                if (i < p0) {
                    prob[i] = 0;
                } else if (i < (p1)) {
                    prob[i] = 1;
                } else if (i < (p2)) {
                    prob[i] = 2;
                } else if (i < (p3)) {
                    prob[i] = 3;
                } else {
                    prob[i] = 4;
                }
            }
        } else {
            // conLvlMin: 35% 30% 20% 10% 3% 2% || conLvlMax: 30% 27% 18% 15% 6% 4%
            int p0 = numDe1000ConInterpolarFromNivel(35, 5, lvlEntidad);
            int p1 = p0 + numDe1000ConInterpolarFromNivel(30, 8, lvlEntidad);
            int p2 = p1 + numDe1000ConInterpolarFromNivel(20, 10, lvlEntidad);
            int p3 = p2 + numDe1000ConInterpolarFromNivel(10, 5, lvlEntidad);
            int p4 = p3 + numDe1000ConInterpolarFromNivel(3, 2, lvlEntidad);
            
            for (int i = 0; i < 1000; i++) {
                if (i < p0) {
                    prob[i] = 0;
                } else if (i < (p1)) {
                    prob[i] = 1;
                } else if (i < (p2)) {
                    prob[i] = 2;
                } else if (i < (p3)) {
                    prob[i] = 3;
                } else if (i < (p4)) {
                    prob[i] = 4;
                } else {
                    prob[i] = 5;
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
    protected void reparar(Equipamiento equip){
        int rep = -(Math.round(equip.durabilidad * (float) 0.75));
        int max = getDurabilidadMax();
        if (rep + durabilidad < max) {
            this.perderDurabilidad(rep);
        } else {
            durabilidad = max;
        }
    }
    public boolean perderDurabilidad(int dañoRecivido){
        if (dañoRecivido > durabilidad) {
            dañoRecivido = durabilidad;
        }
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
    /*public Class StringToEquipamiento(String subtipoEquipamiento){
        Investigar
    }*/
    @Override
    public String toString() {
        return nombre + " " + getDurabilidadString();
    }
    @Override
    public int compareTo(Equipamiento other) {
        //Comparar por Tipo
        int tipoComp = Integer.compare(getPrioridadTipo(this.tipo), getPrioridadTipo(other.tipo));
        if (tipoComp != 0) { return tipoComp; }

        //Si son del mismo tipo, comparar por Subtipo
        int subtipoComp = Integer.compare(getPrioridadSubtipo(this.subtipo), getPrioridadSubtipo(other.subtipo));
        if (subtipoComp != 0) { return subtipoComp; }

        //Si son del mismo subtipo, comparar por Rareza (orden inverso para que aparezca primero la mayor rareza)
        int rarezaComp = other.rareza.compareTo(this.rareza);
        if (rarezaComp != 0) { return rarezaComp; }

        //Si son de la misma Rareza, comparar por Lvl
        int lvlComp = Byte.compare(this.lvl, other.lvl);
        if (lvlComp != 0) { return lvlComp; }

        //Si son del mismo Lvl, comparar por xp
        return Integer.compare(this.xp, other.xp);
    }
    // Métodos auxiliares para definir el orden real (es como un indice de prioridad)
    private int getPrioridadTipo(String tipo) {
        return switch (tipo.toUpperCase()) {
            case "ARMADURA" -> 1;
            case "ARMA" -> 2;
            default -> throw new EquipamientoException("Tipo sin orden asignado asignado");
        };
    }
    private int getPrioridadSubtipo(String subtipo) {
        return switch (subtipo.toUpperCase()) {
            case "CASCO" -> 1;
            case "PECHERA" -> 2;
            case "PANTALON" -> 3;
            case "BOTAS" -> 4;
            case "ESPADA" -> 5;
            case "BARITA" -> 6;
            case "MAZA" -> 7;
            default -> throw new EquipamientoException("Subtipo sin orden asignado asignado");
        };
    }
    @Override
    public int hashCode() {//TODO investigar
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
        result = prime * result + ((subtipo == null) ? 0 : subtipo.hashCode());
        result = prime * result + ((objetoBase == null) ? 0 : objetoBase.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((rareza == null) ? 0 : rareza.hashCode());
        result = prime * result + durabilidad;
        result = prime * result + xp;
        result = prime * result + lvl;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Equipamiento other = (Equipamiento) obj;
        if (tipo == null) {
            if (other.tipo != null)
                return false;
        } else if (!tipo.equals(other.tipo))
            return false;
        if (subtipo == null) {
            if (other.subtipo != null)
                return false;
        } else if (!subtipo.equals(other.subtipo))
            return false;
        if (objetoBase == null) {
            if (other.objetoBase != null)
                return false;
        } else if (!objetoBase.equals(other.objetoBase))
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (rareza != other.rareza)
            return false;
        if (durabilidad != other.durabilidad)
            return false;
        if (xp != other.xp)
            return false;
        if (lvl != other.lvl)
            return false;
        return true;
    }
}
