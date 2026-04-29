package UD5.praiasdegalicia;

public class AppDistancia {
    public static void main(String[] args) {
        final double LAT = 42.38974;
        final double LON = -8.71052;
        Praia[] praias = Util.importarPraias("praias.json");

        System.out.println("PRAIAS MÁS CERCANAS AO IES CHAN DO MONTE (Lat: 42.38974, Lon: -8.71052)");
        System.out.println("===============================================");
        Praia[] praiasOrder = Praia.praiasCercanas(praias, LAT, LON, 10);
        for (int i = 0; i < praiasOrder.length; i++) {
            Integer dist = Math.round(Float.valueOf(String.valueOf(Math.abs(Util.distancia(praiasOrder[i].getLat(), praiasOrder[i].getLon(), LAT, LON)))));
            System.out.println((i + 1) + ". " + praiasOrder[i].getNome() + " - " + praiasOrder[i].getConcello() + " - " + dist + " m");
        }

    }
}
