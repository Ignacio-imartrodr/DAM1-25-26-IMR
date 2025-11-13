package UD3;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NumerosPrimosTest {

    @Test
    void testEPrimo() {
        assertFalse(NumerosPrimos.ePrimo(0));
        assertFalse(NumerosPrimos.ePrimo(1));
        assertTrue(NumerosPrimos.ePrimo(2));
        assertTrue(NumerosPrimos.ePrimo(3));
        assertFalse(NumerosPrimos.ePrimo(4));
        assertTrue(NumerosPrimos.ePrimo(5));
    }
}
