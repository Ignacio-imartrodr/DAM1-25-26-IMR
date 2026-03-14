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
public class EntidadException extends RuntimeException {
    
    /**
     * Constructs an {@code PersonajeException} with no detail
     * message.
     */
    public EntidadException() {
        super();
    }

    /**
     * Constructs an {@code PersonajeException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public EntidadException(String s) {
        super(s);
    }
}
