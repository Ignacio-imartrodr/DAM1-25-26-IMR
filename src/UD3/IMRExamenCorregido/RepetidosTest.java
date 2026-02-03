package UD3.IMRExamenCorregido;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RepetidosTest {
    @Test
    void testRepetidos() {
        int[] t = {1, 3, 5, 3, 7, 4, 2, 8, 2, 4, 6, 2, 6};
        int[] resultado = {6, 4, 3, 2};
        assertArrayEquals(resultado, Repetidos.repetidos(t));
    }

    @Test
    void testSinRepetidos() {
        int[] t = {1, 3, 5, 7, 4, 2, 8};
        assertArrayEquals(new int[0], Repetidos.repetidos(t));
    }    

    @Test
    void testArrayVacio() {
        assertArrayEquals(new int[0], Repetidos.repetidos(new int[0]));
    }
    
    @Test
    void testArrayNull() {
        assertEquals(null, Repetidos.repetidos(null));
    }     
}
