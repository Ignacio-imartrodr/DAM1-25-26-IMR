package UD5.Examen.Entregado;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * @author Ignacio Martínez Rodríguez
 * 
 */
public class Playa {
    int id;
    String nombre;
    String concello;
    String provincia;
    double lat;
    double lon;

    public static void playasConcello(List<Playa> l, String concello) {
        if (concello == null || l == null || l.size() == 0) {
            throw new IllegalArgumentException("Valores no utilizables");
        }
        Iterator<Playa> it = l.iterator();
        while (it.hasNext()) {
            Playa p = it.next();
            if (!p.concello.equals(concello)) {
                it.remove();
            }
        }
    }

    public static List<Playa> playasOrdenadas(List<Playa> l, Comparator<Playa> comp1, Comparator<Playa> comp2) {
        if (l == null || l.size() == 0 || comp1 == null || comp2 == null) {
            return null;
        }
        List<Playa> res = l;
        Collections.sort(res, comp1.thenComparing(comp2));
        return res;
    }

    public static Map<String, List<Playa>> playasPorProvincia(List<Playa> l) {
        if (l == null || l.size() == 0) {
            return null;
        }
        Map<String, List<Playa>> res = new HashMap<>();
        Set<String> provs = new LinkedHashSet<>();
        for (Playa playa : l) {
            provs.add(playa.provincia);
        }
        for (String provincia : provs) {
            List<Playa> playasProv = new ArrayList<>();
            for (Playa playa : l) {
                if (playa.provincia.equals(provincia)) {
                    playasProv.add(playa);
                }
            }
            res.put(provincia, playasProv);
        }

        return res;
    }

    public static Map<Playa, Integer> playasPorProvincia(Map<String, List<Playa>> m) {
        if (m == null || m.size() == 0) {
            return null;
        }

        Map<Playa, Integer> res = new HashMap<>();

        Set<Entry<String, List<Playa>>> entradas = m.entrySet();

        for (Entry<String, List<Playa>> entry : entradas) {
            List<Playa> playasPersona = entry.getValue();
            for (int i = 0; i < 3; i++) {
                if (i < playasPersona.size()) {
                    res.put(playasPersona.get(i), 0);
                }
            }
        }
        for (Entry<String, List<Playa>> entry : entradas) {
            List<Playa> playasPersona = entry.getValue();
            for (int i = 0; i < 3; i++) {
                if (i < playasPersona.size()) {
                    int puntos = 0;
                    switch (i) {
                        case 0:
                            puntos = 10;
                            break;
                        case 1:
                            puntos = 5;
                            break;
                        case 2:
                            puntos = 2;
                            break;
                    }
                    res.put(playasPersona.get(i), res.get(playasPersona.get(i)) + puntos);
                }
            }
        }

        Comparator<Entry<Playa, Integer>> compPuntos = (e1, e2) -> Integer.compare(e1.getValue(), e2.getValue());

        Comparator<Entry<Playa, Integer>> compNombre = (e1, e2) -> (e1.getKey().nombre).compareTo(e2.getKey().nombre);

        List<Entry<Playa, Integer>> playasOrd = new ArrayList<>(res.entrySet());
        
        Collections.sort(playasOrd, compPuntos.thenComparing(compNombre));

        res = new HashMap<>();
        for (Entry<Playa, Integer> entry : playasOrd) {
            res.put(entry.getKey(), entry.getValue());
        }
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Playa other = (Playa) obj;
        if (id != other.id)
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (concello == null) {
            if (other.concello != null)
                return false;
        } else if (!concello.equals(other.concello))
            return false;
        if (provincia == null) {
            if (other.provincia != null)
                return false;
        } else if (!provincia.equals(other.provincia))
            return false;
        if (Double.doubleToLongBits(lat) != Double.doubleToLongBits(other.lat))
            return false;
        if (Double.doubleToLongBits(lon) != Double.doubleToLongBits(other.lon))
            return false;
        return true;
    }
}
