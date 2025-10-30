package UD2;
/**
 * E0411_MCD. Escribir una función que calcule de forma recursiva el máximo común divisor de dos 
 * números.
 * 
 * @author Ignacio MR
 */
public class E0411_MCD {
    public static int mcd(int a, int b) {
        if (a==0||b==0) {
            return a==0? b : a;
        }else if (a >= b) {
            return mcd(a - b, b);
        }else if (b > a){
            return mcd(a, b - a);
        }else{
            return 1;
        }
    }
    public static void main(String[] args) {
        System.out.println(mcd(10, 25)); //5
        System.out.println(mcd(100, 60)); //20
        System.out.println(mcd(26, 39)); //13
    }
}
