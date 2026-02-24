package UD4.Rol.Utilidades;

/**
 * @author Ignacio MR
 */
/**
 * Thrown when an exceptional Rareza condition has occurred. For
 * example, trying to create an Rareza out of the Rarezas list.
 *
 */
public class RarezaException extends RuntimeException {
    @java.io.Serial
    private static final long serialVersionUID = 2256477558314496007L;
    /**
     * Constructs an {@code RarezaException} with no detail
     * message.
     */
    public RarezaException() {
        super();
    }

    /**
     * Constructs an {@code RarezaException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public RarezaException(String s) {
        super(s);
    }
}
