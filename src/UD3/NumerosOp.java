package UD3;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class NumerosOp {
    static int sumaDigitos(int n) {
        int suma = 0;

        while (n>0) {
            int digito = n % 10;
            suma += digito;
            n /= 10;
        }

        return suma;
    }

    @Test
    void testSumaDigitos() {
        assertEquals(6, NumerosOp.sumaDigitos(123));
        assertEquals(1, NumerosOp.sumaDigitos(1));
        assertEquals(10, NumerosOp.sumaDigitos(190));
    }
}