package OTROS;

public class DireccionArray {
 public static void main(String[] args) {
    char[] string = {'t','i','p'};
        String direccion = string.toString();
        for (int i = 3; i < direccion.length(); i++) {
            System.out.print(direccion.charAt(i));
        }
    
        System.out.println();
        System.out.println(direccion);
 }
}
