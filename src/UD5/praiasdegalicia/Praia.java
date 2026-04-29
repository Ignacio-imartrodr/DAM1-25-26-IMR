package UD5.praiasdegalicia;

import java.util.Arrays;
import java.util.Comparator;

public class Praia implements Comparable<Praia> {
    private int id;
    private String nome;
    private String concello;
    private String provincia;
    private double lat;
    private double lon;

    public Praia() {
    }

    public Praia(int id) {
        this.id = id;
    }

    public Praia(int id, String nome, String concello, String provincia) {
        this.id = id;
        this.nome = nome;
        this.concello = concello;
        this.provincia = provincia;
    }

    public Praia(int id, String nome, String concello, String provincia, double lat, double lon) {
        this.id = id;
        this.nome = nome;
        this.concello = concello;
        this.provincia = provincia;
        this.lat = lat;
        this.lon = lon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getConcello() {
        return concello;
    }

    public void setConcello(String concello) {
        this.concello = concello;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return nome + " (" + concello + ")";
    }

    @Override
    public int compareTo(Praia o) {
        return Integer.compare(id, o.id);
    }

    public static void imprimirLista(Praia[] praias, int i) {
        if (i <= 0) {
            return;
        }
        for (int j = 0; j < i && j < praias.length; j++) {
            praias[j].mostrarDetalles();
        }
    }

    public void mostrarDetalles() {
        System.out.println(id + " - " + nome + " - " + concello + " - " + provincia + " - (" + lat + ", " + lon + ")");
    }

    static Praia[] sortLatitudNorteSur(Praia[] p) {
        Praia[] copy = Arrays.copyOf(p, p.length);
        Comparator<Praia> compNorToSur = (p1, p2) -> Double.compare(p2.lat, p1.lat);
        Arrays.sort(copy, compNorToSur);
        return copy;
    }

    static Praia[] sortProvinciaConcelloNome(Praia[] p) {
        Praia[] copy = Arrays.copyOf(p, p.length);

        Comparator<Praia> compProv = (p1, p2) -> p1.provincia.compareTo(p2.provincia);
        Comparator<Praia> compConcell = (p1, p2) -> p1.concello.compareTo(p2.concello);
        Comparator<Praia> compNome = (p1, p2) -> p1.nome.compareTo(p2.nome);
        Comparator<Praia> compProvConcellNome = compProv.thenComparing(compConcell).thenComparing(compNome);

        Arrays.sort(copy, compProvConcellNome);
        return copy;
    }

    public static Praia[] praiasCercanas(Praia[] praias, double lat, double lon, int n) {
        Praia[] copy = Arrays.copyOf(praias, praias.length);
        Comparator<Praia> compCercania = (p1, p2) -> Double.compare(Math.abs(Util.distancia(p1.lat, p1.lon, lat, lon)), Math.abs(Util.distancia(p2.lat, p2.lon, lat, lon)));
        Arrays.sort(copy, compCercania);
        return Arrays.copyOf(copy, n);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Praia other = (Praia) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
