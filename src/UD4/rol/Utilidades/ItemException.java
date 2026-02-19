package UD4.Rol.Utilidades;

/**
 * @author Ignacio MR
 */
/**
 * Thrown when an exceptional item condition has occurred. For
 * example, trying to create an item out of the Items list.
 *
 */
public class ItemException extends RuntimeException {
    @java.io.Serial
    private static final long serialVersionUID = 2256477558314496007L;
    /**
     * Constructs an {@code ItemException} with no detail
     * message.
     */
    public ItemException() {
        super();
    }

    /**
     * Constructs an {@code ItemException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public ItemException(String s) {
        super(s);
    }
}
