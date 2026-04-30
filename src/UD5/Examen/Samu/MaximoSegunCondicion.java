package UD5.Examen.Samu;

//import java.time.LocalDate;
//import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
//import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class MaximoSegunCondicion {
    public static <T> T maximoSegunCondicion(Collection<T> colleccion, Comparator<T> comparador,
            Predicate<T> condicion) {

        T elementoMaximo = null;

        Set<T> elementosValidos = new LinkedHashSet<>();
        for (T elemento : colleccion) {
            if (condicion.test(elemento)) {
                elementosValidos.add(elemento);
                elementoMaximo = elemento;
            }
        }

        for (T elementoValido : elementosValidos) {
            if (comparador.compare(elementoValido, elementoMaximo) > 0) {
                elementoMaximo = elementoValido;
            }
        }

        if (elementoMaximo == null) {
            throw new IllegalArgumentException("Ningun valor cumple la condición");
        }

        return elementoMaximo;

    }
    /*
    public static void main(String[] args) {
        
        Comparator<LocalDate> comparador1 = 

        List<LocalDate> lista = new ArrayList<>();
        
    }*/
}
