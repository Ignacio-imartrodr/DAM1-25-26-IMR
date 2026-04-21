package UD4.Rol.Entity.Others;

public interface Habilidades {
    boolean[] habilidadActiva = new boolean[] {true};
    public String getHabilidadName();
    public String getHabilidadDescription();
    boolean isHabilidadActiva();
    void quitarHabilidad();
    void activarHabilidad();
    default public String getHabilidad(){
        return getHabilidadName() + " (" + getHabilidadDescription() + ")";
    }
}
