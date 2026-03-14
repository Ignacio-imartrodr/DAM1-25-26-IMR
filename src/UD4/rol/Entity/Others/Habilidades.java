package UD4.Rol.Entity.Others;

public interface Habilidades {
    String getHabilidadName();
    String getHabilidadDescription();
    boolean isHabilidadActiva();
    void quitarHabilidad();
    void activarHabilidad();
    default String getHabilidad(){
        return getHabilidadName() + " (" + getHabilidadDescription() + ")";
    }
}
