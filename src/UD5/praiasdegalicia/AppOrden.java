package UD5.praiasdegalicia;

import java.util.Arrays;

public class AppOrden {
    public static void main(String[] args) {
        Praia[] praias = Util.importarPraias("praias.json");

        System.out.println("PRAIAS ORDENADAS POR LATITUD (de norte a sur)");
        System.out.println("=============================================");
        Praia[] praiasNorSur = Arrays.copyOf(Praia.sortLatitudNorteSur(praias), 10);
        for (Praia praia : praiasNorSur) {
            System.out.println(praia.getNome() + " - " + praia.getLat() + " - " + praia.getConcello());
        }

        System.out.println();
        System.out.println("PRAIAS ORDENADAS POR PROVINCIA, CONCELLO E NOME");
        System.out.println("===============================================");
        Praia[] praiasOrder = Praia.sortProvinciaConcelloNome(praias);
        for (int i = 0; i < praiasOrder.length; i++) {
            System.out.println((i + 1) + " - " + praiasOrder[i].getProvincia() + " - " + praiasOrder[i].getConcello() + " - " + praiasOrder[i].getNome());
        }

    }
}
