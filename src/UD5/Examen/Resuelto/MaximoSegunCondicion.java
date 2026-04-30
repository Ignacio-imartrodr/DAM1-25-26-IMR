package UD5.Examen.Resuelto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Ignacio Martínez Rodríguez
 * 
 */
public class MaximoSegunCondicion {
    public static <T> T maximoSegunCondicion(Collection<T> col, Comparator<T> comp, Predicate<T> p){
        List<T> cumplen = new ArrayList<>();
        for (T t : col) {
            if (p.test(t)) {
                cumplen.add(t);
            }
        }
        if (cumplen.isEmpty()) {
            throw new IllegalArgumentException("Ningún elemento cumplió la condición");
        }

        return Collections.max(cumplen,comp);
    }
    public static void main(String[] args) {
        List<LocalDate> l = new ArrayList<>();
        LocalDate hoy = LocalDate.now();
        l.add(hoy);
        l.add(hoy.minusDays(21));
        l.add(hoy.minusDays(-21));
        l.add(hoy.minusDays(-2));
        l.add(hoy.minusDays(1));
        Comparator<LocalDate> compFecha = (d1, d2) -> d1.compareTo(d2);
        Predicate<LocalDate> antesHoy = ld -> ld.isAfter(LocalDate.now());
        System.out.println(maximoSegunCondicion(l, compFecha, antesHoy));
    }
}
