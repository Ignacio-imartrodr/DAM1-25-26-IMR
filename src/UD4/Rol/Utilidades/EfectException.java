package UD4.Rol.Utilidades;

/**
 * @author Ignacio MR
 */
/**
 * Thrown when an exceptional efect condition has occurred. For
 * example, trying to create an efect out of the Efectos list.
 *
 */
public class EfectException extends RuntimeException {
    
    /**
     * Constructs an {@code EfectException} with no detail
     * message.
     */
    public EfectException() {
        super();
    }

    /**
     * Constructs an {@code EfectException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public EfectException(String s) {
        super(s);
    }
}
