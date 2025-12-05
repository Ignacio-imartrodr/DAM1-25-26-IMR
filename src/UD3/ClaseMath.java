package UD3;

/**
 * @author Ignacio MR
*/

public class ClaseMath {
    
    static double random (double min, double max) {
        double numRnd = Math.random();
        numRnd *= max - min;
        numRnd += min;
        return numRnd;
    }
    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
           System.out.println(random(-360,360)); 
        }
    }
}
