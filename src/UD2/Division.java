package UD2;

import java.util.Scanner;

public class Division {
    public static double Div(double a, double b) {
    double res;
    try { 
        res = a / b;
        return res; 
    } catch (ArithmeticException e) { 
        System.out.println("ERROR: División por cero?"); 
        return 00; 
    } 
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(Div(2, 0)); 
        System.out.println(Div(100, 50));
        System.out.println(Div(26, 39));
        System.out.print("Numerador: ");
        double numerador = sc.nextDouble();
        System.out.print("Denominador: ");
        double denominador = sc.nextDouble();
        System.out.println(Div(numerador, denominador));
        sc.close();
    }
}
