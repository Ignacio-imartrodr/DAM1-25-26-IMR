package UD5.Examen;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * @author Ignacio MR
 */
public class Pruebas2 {
    public static String assignBonus(int employeeNumber, int months, boolean isExecutive) {
        if (employeeNumber < 1 || employeeNumber > 999 || months < 0 || months > 999) {
            throw new IllegalArgumentException("Número de empregado non válido");
        }

        boolean hasSeniority = months >= 12;

        if (isExecutive && hasSeniority) return "P1";
        if (!isExecutive && hasSeniority) return "P2";
        if (isExecutive && !hasSeniority) return "P3";
        return "P4";
    }

    @Test
    void test1() {
        assertEquals("P3", assignBonus(123, 5, true));
    }

    @Test
    void test2() {
        assertEquals("P4", assignBonus(123, 5, false));
    }

    @Test
    void test3() {
        assertEquals("P1", assignBonus(1, 12, true));
    }

    @Test
    void test4() {
        assertEquals("P2", assignBonus(1, 12, false));
    }

    @Test
    void test5() {
        assertThrows(IllegalArgumentException.class, () -> assignBonus(0, 5, false));
    }

    @Test
    void test6() {
        assertThrows(IllegalArgumentException.class, () -> assignBonus(1000, 5, false));
    }

    @Test
    void test7() {
        assertThrows(IllegalArgumentException.class, () -> assignBonus(123, -1, true));
    }

    @Test
    void test8() {
        assertThrows(IllegalArgumentException.class, () -> assignBonus(123, 1000, true));
    }
}

