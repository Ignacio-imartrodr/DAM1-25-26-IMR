package UD2;
/**
 * @author Ignacio MR
 */
public class LongitudNum {
    static int longNum(long num) {
        int cont = 0;
        long n = num;
        while (n % 10 != 0 && num != 0) {
            cont++;
            n=n/10;
            System.out.println(cont);
        }
        if(num==0){
            cont = 1;
        }
        return cont;
    }
    public static void main(String[] args) {
        final long num = 0;
        System.out.println(longNum(num));
    }
}
