package UD3;

/**
 * @author Ignacio MR
 */

public class NumerosPrimos {
        public static boolean ePrimo(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= n / 2; i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }
    public static void main(String[] args) {
        final int n = 456;
        System.out.println(ePrimo(n));
        
    }
}
