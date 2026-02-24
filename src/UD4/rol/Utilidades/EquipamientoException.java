package UD4.Rol.Utilidades;

/**
 * @author Ignacio MR
 */
/**
 * Thrown when an exceptional Equipamiento condition has occurred. For
 * example, trying to create an Equipamiento out of the Equipamientos list.
 *
 */
public class EquipamientoException extends RuntimeException {
    @java.io.Serial
    private static final long serialVersionUID = 2256477558314496007L;
    /**
     * Constructs an {@code EquipamientoException} with no detail
     * message.
     */
    public EquipamientoException() {
        super();
    }

    /**
     * Constructs an {@code EquipamientoException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public EquipamientoException(String s) {
        super(s);
    }
}
