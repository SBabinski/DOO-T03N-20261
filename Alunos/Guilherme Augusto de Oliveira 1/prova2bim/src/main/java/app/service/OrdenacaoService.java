package app.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import app.model.Serie;

public class OrdenacaoService {

    public static void ordenarPorNome(List<Serie> lista) {

        Collections.sort(lista,
                Comparator.comparing(Serie::getNome));

    }

    public static void ordenarPorNota(List<Serie> lista) {

        Collections.sort(lista,
                Comparator.comparingDouble(Serie::getNota).reversed());

    }

    public static void ordenarPorStatus(List<Serie> lista) {

        Collections.sort(lista,
                Comparator.comparing(Serie::getStatus));

    }

    public static void ordenarPorEstreia(List<Serie> lista) {

        Collections.sort(lista,
                Comparator.comparing(Serie::getDataEstreia));

    }

}