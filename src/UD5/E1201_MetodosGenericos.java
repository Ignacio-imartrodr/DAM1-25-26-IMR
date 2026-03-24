package UD5;

import java.util.Arrays;

public class E1201_MetodosGenericos {
    public static <U> U[] add(U e, U[] t) {
        t = Arrays.copyOf(t, t.length + 1);
        t[t.length - 1] = e;
        return t; 
    }

    public static <U> boolean buscar(U e, U[] t) {
        for (int i = 0; i < t.length; i++) {
            if (t[i].equals(e)) {
                return true;
            }
        }
        return false;
    }

     
    public static <U> U[] concatenar(U[] t1, U[] t2) { 
        U[] t = Arrays.copyOf(t1, t1.length);
        for (int i = 0; i < t2.length; i++) {
            t = add(t2[i], t);
        }
        return t;
    }
 
    public static <U, V> Object[] concatenar2(U[] t1, V[] t2) {
        Object[] t = new Object[0];
        t = Arrays.copyOf(t1, t1.length);
        t = concatenar(t, t2);
        return t;
    }
}
