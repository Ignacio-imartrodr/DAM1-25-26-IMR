package UD2;
/**
 * @author Ignacio MR
 */
public class CapicuaLong {
    static long invertirNum(long n) {
        long numInvert=0;
        if (n%10==0) {
            n=n/10;
            invertirNum(n);
        }else{
            while (n % 10 != 0) {
                numInvert = numInvert*10 + n%10;
                n = n/10;
            }
        }
        return numInvert;
    }
    static boolean esCapicua(long n){
        boolean esCapicua = false;
        if (n==invertirNum(n)) {
            esCapicua = true;
        }
        return esCapicua;
    }
    public static void main(String[] args) {
        final long num = 124210;
        System.out.println(esCapicua(num));
    }
}
