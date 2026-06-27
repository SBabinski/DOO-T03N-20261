

import java.util.Comparator;

// Essa classe junta as 4 formas de ordenar uma lista de series.
public class Ordenar {

    public static Comparator<Serie> porNome() {
        return Comparator.comparing(
                serie -> serie.getNome() != null ? serie.getNome().toLowerCase() : ""
        );
    }

    public static Comparator<Serie> porNota() {
        return Comparator.comparing(
                (Serie serie) -> serie.getNota() != null ? serie.getNota() : -1.0
        ).reversed();
    }

    public static Comparator<Serie> porEstado() {
        return Comparator.comparing(
                serie -> serie.getEstado() != null ? serie.getEstado() : ""
        );
    }

    public static Comparator<Serie> porDataEstreia() {
        return Comparator.comparing(
                serie -> serie.getDataEstreia() != null ? serie.getDataEstreia() : ""
        );
    }
}