package UD2;
import java.util.Scanner;
public class E0401_Eco {
    public static void eco(int args) {
        for (int i=0; i < args; ++i){
            System.out.println("Eco...");
        }
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Numero de repeticiones? ");
        int n = sc.nextInt();
        sc.close();
        eco(n);
    }
}