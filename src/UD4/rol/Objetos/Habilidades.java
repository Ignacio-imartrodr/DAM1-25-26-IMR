package UD4.Rol.Objetos;

public interface Habilidades {
    String getHabilidadName();
    String getHabilidadDescription();
    boolean isHabilidadActiva();
    void quitarHabilidad();
    void activarHabilidad();

    default String getHabilidad(){
        return getHabilidadName() + " (" + getHabilidadDescription() + ")";
    }
    //TODO terminar
}
