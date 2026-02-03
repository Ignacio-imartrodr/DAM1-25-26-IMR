package UD3.IMRExamenCorregido;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PreguntaMasDificilTest {
    @Test
    void testMediaPreguntaMasDificil() {
        double[][] notas = {
            {4, 10, 0, 8},
            {8, 2, 9.5, 3}, 
            {2, 1, 0, 0},
            {9, 6, 0, 0},
            {10, 3, 10, 9.5}
        };

        assertEquals(3.9, PreguntaMasDificil.mediaPreguntaMasDificil(notas), 0.1);
    }

    @Test
    void testMatrizHorizontal() {
        double[][] notas = {
            {4, 10, 0, 8},
            {8, 2, 9, 3}
        };

        assertEquals(4.5, PreguntaMasDificil.mediaPreguntaMasDificil(notas), 0.1);
    } 

    @Test
    void testMatrizVacia() {
        assertEquals(-1, PreguntaMasDificil.mediaPreguntaMasDificil(new double[0][0]));
    }
    
    @Test
    void testMatrizNull() {
        assertEquals(-1, PreguntaMasDificil.mediaPreguntaMasDificil(null));
    }    
}
