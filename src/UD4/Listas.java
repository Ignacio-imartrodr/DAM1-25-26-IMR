package UD4;

import java.util.List;
import java.util.ArrayList;

public class Listas {
    public static void main(String[] args) {
        List<String> lista = new ArrayList<>();
        lista.add(null);
        lista.add("a");
        System.out.println(lista);
        for (String string : lista) {
            System.out.println(string);
        }
    }
}
