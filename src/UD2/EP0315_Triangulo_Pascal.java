package UD2;

public class EP0315_Triangulo_Pascal {
    static int factorial (int n){
        int factorial = n;
        if(n==0) {return 1;}
        while (n>=2){
            factorial *= n-1;
            n--;
        }
        return factorial;
    }
    static int numPascal(int n, int m){
        return factorial(n)  /  (factorial(m) * factorial(n - m));
    }
    static void TrianguloPascal(int n){
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < n-i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= i; j++) {
                int cont = 0;
                System.out.print(numPascal(i-1, cont)+" "); 
                cont++;
            } 
            System.out.println();
        }
    }
    public static void main(String[] args) {
        final int num = 4;
        TrianguloPascal(num);
    }
}
