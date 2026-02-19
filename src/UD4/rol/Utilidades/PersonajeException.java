package UD4.Rol.Utilidades;

/**
 * @author Ignacio MR
 */
/**
 * Thrown when an exceptional personaje condition has occurred. For
 * example, too much experience given at onece throws an
 * instance of this class.
 *
 */
public class PersonajeException extends RuntimeException {
    @java.io.Serial
    private static final long serialVersionUID = 2256477558314496007L;
    /**
     * Constructs an {@code PersonajeException} with no detail
     * message.
     */
    public PersonajeException() {
        super();
    }

    /**
     * Constructs an {@code PersonajeException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public PersonajeException(String s) {
        super(s);
    }
}
