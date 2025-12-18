package Extras;

public class ArrayToString {
    static String stringArray(int[] array) {
        String arrayStr = "";
        if (array.length == 0) {
            arrayStr += "[]";
            return arrayStr;
        }
        for (int i = 0; i < array.length; i++) {
            arrayStr = "[ ";
            if (i > 0 && i < array.length) {
                arrayStr += " | ";
            }
            arrayStr += array[i];
            if (i == array.length - 1) {
                arrayStr += " ]";
            }
        }
        return arrayStr;
    }
    public static void main(String[] args) {
        int[] array = new int[0];
        System.out.println(stringArray(array));
    }
}
