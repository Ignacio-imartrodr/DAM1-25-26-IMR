package UD5.Examen.Samu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Playa {
    int id;
    String nombre;
    String concello;
    String provincia;
    double lat;
    double lon;

    static void playasConcello(List<Playa> playas, String concello) {
        if (playas == null || concello == null) {
            throw new IllegalArgumentException("No se aceptan valores nulos");
        }
        for (Playa playa : playas) {
            if (playa.concello == concello) {
                playas.remove(playas.indexOf(playa));
            }
        }

    }

    static List<Playa> playasOrdenadas(List<Playa> listaPlayas, Comparator<Playa> comparador1,
            Comparator<Playa> comparador2) {
        if (listaPlayas == null) {
            throw new IllegalArgumentException("No se acepta valor nulo");
        }

        listaPlayas.sort(comparador1.thenComparing(comparador2));

        return listaPlayas;

    }

    static Map<String, List<Playa>> playasPorProvincia(List<Playa> lista) {

        if (lista == null) {
            throw new IllegalArgumentException("No se acepta valor null");
        }

        Map<String, List<Playa>> mapa = new LinkedHashMap<>();
        Set<String> claves = new LinkedHashSet<>();

        for (Playa playa : lista) {
            claves.add(playa.provincia);
        }

        for (String provincia : claves) {
            List<Playa> playaProvincia = new ArrayList<>();
            for (Playa playa2 : lista) {
                if (provincia == playa2.provincia) {
                    playaProvincia.add(playa2);
                }
            }
            mapa.put(provincia, playaProvincia);
        }
        return mapa;
    }

    static Map<Playa, Integer> playasFavoritas(Map<String, List<Playa>> mapa) {
        if (mapa == null) {
            throw new IllegalArgumentException("No se acepta el valor null");
        }

        Set<Entry<String, List<Playa>>> entradas = mapa.entrySet();
        Set<Playa> playas = new LinkedHashSet<>();
        Map<Playa, Integer> mapaFavoritas = new LinkedHashMap<>();

        /*
         * dentro de cada entrada del mapa cojo cada playa de las listas y saco cada
         * playa de las listas aportadas
         */
        for (Entry<String, List<Playa>> entry : entradas) {
            for (Playa playa : entry.getValue()) {
                playas.add(playa);
            }
        }

        for (Playa playa : playas) {
            int puntos = 0;
            for (Entry<String, List<Playa>> entrada : entradas) {
                // las entradas se tratan de listas asi que tomo el tamaño para poder recorrerla
                for (int i = 0; i < entrada.getValue().size(); i++) {
                    if (entrada.getValue().get(i).equals(playa)) {
                        switch (i) {
                            case 1:
                                puntos += 10;
                                break;
                            case 2:
                                puntos += 5;
                                break;
                            case 3:
                                puntos += 2;
                                break;
                        }
                    }
                }
            }
            mapaFavoritas.put(playa, puntos);
        }

        Set<Entry<Playa, Integer>> favoritas = mapaFavoritas.entrySet();
        List<Integer> listaOrdenada = new ArrayList<>();
        for (Entry<Playa, Integer> entry : favoritas) {
            listaOrdenada.add(entry.getValue());
        }
        Comparator<Integer> comparador = (i1, i2) -> i1 - i2;
        listaOrdenada.sort(comparador);

        Map<Playa, Integer> mapaFavoritasOrdenado = new LinkedHashMap<>();
        for (Integer integer : listaOrdenada) {
            for (Entry<Playa, Integer> valor : favoritas) {
                if (integer == valor.getValue()) {
                    mapaFavoritasOrdenado.put(valor.getKey(), integer);
                }
            }
        }

        return mapaFavoritasOrdenado;

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((concello == null) ? 0 : concello.hashCode());
        result = prime * result + ((provincia == null) ? 0 : provincia.hashCode());
        long temp;
        temp = Double.doubleToLongBits(lat);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lon);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
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