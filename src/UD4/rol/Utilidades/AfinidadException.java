package UD4.Rol.Utilidades;

/**
 * @author Ignacio MR
 */
/**
 * Thrown when an exceptional Afinidad condition has occurred. For
 * example, trying to create an Afinidad out of the Afinidads list.
 *
 */
public class AfinidadException extends RuntimeException {
    @java.io.Serial
    private static final long serialVersionUID = 2256477558314496007L;
    /**
     * Constructs an {@code AfinidadException} with no detail
     * message.
     */
    public AfinidadException() {
        super();
    }

    /**
     * Constructs an {@code AfinidadException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public AfinidadException(String s) {
        super(s);
    }
}
