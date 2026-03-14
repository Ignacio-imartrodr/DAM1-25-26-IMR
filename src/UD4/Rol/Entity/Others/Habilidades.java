package UD4.Rol.Entity.Others;

public interface Habilidades {
    boolean[] habilidadActiva = new boolean[] {true};
    public String getHabilidadName();
    public String getHabilidadDescription();
    default boolean isHabilidadActiva(){
        return habilidadActiva[0];
    }
    default void quitarHabilidad(){
        habilidadActiva[0] = false;
    }
    default void activarHabilidad(){
        habilidadActiva[0] = true;
    }
    default String getHabilidad(){
        return getHabilidadName() + " (" + getHabilidadDescription() + ")";
    }
}
