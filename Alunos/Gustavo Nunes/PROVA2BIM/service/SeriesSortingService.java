package service;

import model.entities.Serie;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SeriesSortingService {

    /// Sort by name
    public List<Serie> sortByName(List<Serie> series) {

        List<Serie> sorted =
                new ArrayList<>(series);

        sorted.sort(
                Comparator.comparing(
                        Serie::getName,
                        String.CASE_INSENSITIVE_ORDER
                )
        );

        return sorted;
    }

    /// Sort by rating
    public List<Serie> sortByRating(List<Serie> series) {

        List<Serie> sorted =
                new ArrayList<>(series);

        sorted.sort(
                Comparator.comparing(
                        Serie::getAverage
                ).reversed()
        );

        return sorted;
    }

    /// Sort by premiered
    public List<Serie> sortByPremiered(List<Serie> series) {

        List<Serie> sorted =
                new ArrayList<>(series);

        sorted.sort(
                Comparator.comparing(
                        Serie::getPremiered,
                        Comparator.nullsLast(String::compareTo)
                )
        );

        return sorted;
    }

    /// Sort by status
    public List<Serie> sortByStatus(List<Serie> series) {

        List<Serie> sorted =
                new ArrayList<>(series);

        sorted.sort(
                Comparator.comparing(
                        Serie::getStatus
                )
        );

        return sorted;
    }
}
