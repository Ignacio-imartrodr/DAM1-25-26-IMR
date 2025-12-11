package Extras;

/**
 * @author Lunna
 */
public class TrianguloInvertidoLunna {
    public static void dibujarTriangulo(int n) {
        for (int i = n; i > 0; i--) {
            for (int e = n - i; e >= 0; e--) {
                System.out.print(" ");
            }
            for (int t = 1; t <= i; t++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        final int num = 3;
        dibujarTriangulo(num);
    }
}
