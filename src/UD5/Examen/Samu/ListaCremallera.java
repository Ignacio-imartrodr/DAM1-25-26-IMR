package UD5.Examen.Samu;

import java.util.ArrayList;
import java.util.List;

public class ListaCremallera {
    public static <T> List<T> listaCremallera(List<T> lista1, List<T> lista2) {

        if (lista1 == null && lista2 == null) {
            return null;
        } else if (lista1 == null || lista2 == null || lista1.isEmpty() || lista2.isEmpty()) {
            throw new IllegalArgumentException("Una lista está vacía");
        }

        boolean listaActual = true;
        List<T> listaCremallera = new ArrayList<>();

        do {
            if (listaActual && !lista1.isEmpty()) {
                listaCremallera.add(lista1.getFirst());
                lista1.removeFirst();
                listaActual = false;
            }
            if (!listaActual && !lista2.isEmpty()) {
                listaCremallera.add(lista2.getFirst());
                lista2.removeFirst();
                listaActual = true;
            }
            if (lista2.isEmpty()) {
                listaActual = true;
            }
            if (lista1.isEmpty()) {
                listaActual = false;
            }
        } while (!lista1.isEmpty() || !lista2.isEmpty());

        return listaCremallera;
    }

    public static void main(String[] args) {
        List<String> lista = new ArrayList<>();
        lista.add("Pepe");
        lista.add("Marta");
        lista.add("Paco");
        lista.add("Cesar");

        List<String> lista2 = new ArrayList<>();
        lista2.add("Juan");
        lista2.add("Ramirez");

        List<String> listaGeneral = listaCremallera(lista, lista2);

        System.out.println(listaGeneral);
    }
}
