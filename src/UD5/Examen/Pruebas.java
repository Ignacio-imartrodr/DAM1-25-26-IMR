package UD5.Examen;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * @author Igncio MR
 */
public class Pruebas {
    public static double calculateShipping(double amount, boolean expressDelivery) {
        if (amount < 0)
            throw new IllegalArgumentException("O importe non pode ser negativo");

        if (amount >= 100)
            return 0.0;

        double shippingCost = expressDelivery ? 10.0 : 5.0;

        if (amount < 20) {
            shippingCost += 2.0;
        }

        return shippingCost;
    }

    @Test
    void test1() {
        assertThrows(IllegalArgumentException.class, () -> calculateShipping(-1, true));
    }

    @Test
    void test2() {
        assertEquals(0.0, calculateShipping(100, true), 0.01);
    }

    @Test
    void test3() {
        assertEquals(10.0, calculateShipping(20, true), 0.01);
    }

    @Test
    void test4() {
        assertEquals(5.0, calculateShipping(20, false), 0.01);
    }

    @Test
    void test5() {
        assertEquals(7.0, calculateShipping(15, false), 0.01);
    }
}
