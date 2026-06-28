package tv;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrdenadorSerie {

    public static void ordenarPorNome(
            List<Serie> lista) {

        Collections.sort(
                lista,
                Comparator.comparing(
                        Serie::getNome,
                        String.CASE_INSENSITIVE_ORDER));

    }

    public static void ordenarPorNota(
            List<Serie> lista) {

        Collections.sort(
                lista,
                Comparator.comparing(
                        Serie::getNota)
                        .reversed());

    }

    public static void ordenarPorStatus(
            List<Serie> lista) {

        Collections.sort(
                lista,
                Comparator.comparing(
                        Serie::getStatus));

    }

    public static void ordenarPorDataDeEstreia(
            List<Serie> lista) {

        Collections.sort(
                lista,
                Comparator.comparing(
                        Serie::getDatadeEstréia));

    }

}