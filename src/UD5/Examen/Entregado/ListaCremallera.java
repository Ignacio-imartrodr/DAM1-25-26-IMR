package UD5.Examen.Entregado;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignacio Martínez Rodríguez
 * 
 */
public class ListaCremallera {
    public static <T> List<T> listaCremallera(List<T> a, List<T> b) {
        if (a == null && b == null) {
            return null;
        }
        if (a == null || a.size() == 0) {
            return b;
        }
        if (b == null || b.size() == 0) {
            return a;
        }
        if (a.size() == 0 && b.size() == 0) {
            return a;
        }
        boolean longerA = a.size() > b.size();

        List<T> res = new ArrayList<>(longerA ? a : b);

        int smallSize = (longerA ? b.size() : a.size());

        for (int i = 0, pos = 1; i < smallSize; i++, pos += 2) {
            if (longerA) {
                res.add(pos, b.get(i));
            } else {
                res.add(pos, a.get(i));
            }
        }
        return res;
    }
    public static void main(String[] args) {
        List<String> a = new ArrayList<>();
        a.add("Brais");
        a.add( "Darío");
        a.add(2, "Fer");
        List<String> b = null;/* 
        b.add("Albaro");
        b.add( "Carlos");
        b.add("Eustaquio");*/
        System.out.println(listaCremallera(a, b));
    }
}
