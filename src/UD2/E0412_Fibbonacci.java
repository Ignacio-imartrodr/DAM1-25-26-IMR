package UD2;
/**
 * E0412_Fibbonacci. Diseñar una función recursiva que calcule el enésimo término de la serie de 
 * Fibonacci. En esta serie el enésimo valor se calcula sumando los dos valores anteriores de la 
 * serie 
 * 
 * @author Ignacio MR
 */

public class E0412_Fibbonacci {
    public static int fibonacci (int n) { 
        return n <= 1 ? 1 : fibonacci(n-1) + fibonacci(n-2);
    }
    public static void main(String[] args) {
        int n = 9;
        System.out.println(fibonacci(n));
    }
}
