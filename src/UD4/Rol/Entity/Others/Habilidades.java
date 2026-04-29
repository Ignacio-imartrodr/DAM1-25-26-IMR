package UD4.Rol.Entity.Others;

public interface Habilidades {
    /**
     * Nombre de la habilidad
     * @return {@code String} del nombre de la habilidad
     */
    public String getHabilidadName();
    /**
     * Descripción de la habilidad
     * @return {@code String} con la descripción de la habilidad
     */
    public String getHabilidadDescription();
    default boolean isHabilidadActiva(){
        if (getCoolDown() == 0) {
            return true;
        }
        return false;
    }
    public void quitarHab();
    public void activarHab();
    /**
     * Reduce el coolDawn de la habilidad si es mayor a 0
     * @return {@code true} si se redujo el coolDawn 
     */
    public boolean countDown();
    /**
     * CoolDawn restante de la habilidad
     * @return CoolDawn restante
     */
    public int getCoolDown();
    /**
     * Asigna el coolDawn máximo de la habilidad
     * @return CoolDawn máximo
     */
    abstract int setCoolDown();
    /**
     * Nombre y descripción de la habilidad
     * @return {@code String} con el nombre y la descripción de la habilidad
     */
    default public String getHabilidad(){
        return getHabilidadName() + " (" + getHabilidadDescription() + ")";
    }
}
