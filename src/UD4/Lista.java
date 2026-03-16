package UD4;

import java.util.Arrays;

public class Lista {
    Integer[] elementos;

    public boolean add(int e) {
        elementos = Arrays.copyOf(elementos, elementos.length + 1);
        elementos[elementos.length - 1] = e;
        return true;
    }

    public void clear() {
        elementos = new Integer[0];
    }

    @Override
    public String toString() {
        return Arrays.toString(elementos);
    }

    public Integer get(int index) {
        return elementos[index];
    }

    public Object size() {
        return elementos.length;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Lista other = (Lista) obj;
        if (!Arrays.equals(elementos, other.elementos))
            return false;
        return true;
    }
    
    

    public static void main(String[] args) {
        Lista lista = new Lista();
        lista.add(5);
        lista.add(7);
        lista.add(3);
        System.out.println(lista);
        int i = 1;
        System.out.println("Elemento en la posición " + i + ": " + lista.get(i));
        lista.clear();
        System.out.println(lista);
        System.out.println("Elemento en la posición " + i + ": ");
        System.out.println(lista.get(i));
        

    }
}
