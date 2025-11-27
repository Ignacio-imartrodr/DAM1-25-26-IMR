package UD3;

public class Strings {
    public static void main(String[] args) {
        char[] string = {'N','I','G','H','T'};
        for (int i = 0; i < string.length; i++) {
            System.out.println((char)string[i]);
            if (i==2) {
                for (int j = 0; j < 3; j++) {
                   System.out.println("."); 
                }
                for (int j = 0; j < 5; j++) {
                   System.out.println(" "); 
                }
            }
        }
        System.out.println("---------------------------------");
         for (int i = 0; i < string.length; i++) {
            System.out.print((char)string[i]);
            System.err.print(" ");
         }
    }
}
