
import java.time.LocalDate;
import java.util.Comparator;

/** Critérios disponíveis em todas as listas da aplicação. */
public enum OrdenacaoSerie {
    NOME("Nome (A–Z)", Comparator.comparing(Serie::getNome, String.CASE_INSENSITIVE_ORDER)),
    NOTA("Nota geral (maior primeiro)",
            Comparator.comparingDouble(OrdenacaoSerie::notaParaOrdenar).reversed()
                    .thenComparing(Serie::getNome, String.CASE_INSENSITIVE_ORDER)),
    ESTADO("Estado da série",
            Comparator.comparingInt((Serie serie) -> serie.getEstado().getOrdem())
                    .thenComparing(Serie::getNome, String.CASE_INSENSITIVE_ORDER)),
    ESTREIA("Data de estreia (mais recente)",
            Comparator.comparing(OrdenacaoSerie::dataParaOrdenar).reversed()
                    .thenComparing(Serie::getNome, String.CASE_INSENSITIVE_ORDER));

    private final String descricao;
    private final Comparator<Serie> comparador;

    OrdenacaoSerie(String descricao, Comparator<Serie> comparador) {
        this.descricao = descricao;
        this.comparador = comparador;
    }

    public Comparator<Serie> getComparador() {
        return comparador;
    }

    private static double notaParaOrdenar(Serie serie) {
        return serie.getNota() < 0 ? Double.NEGATIVE_INFINITY : serie.getNota();
    }

    private static LocalDate dataParaOrdenar(Serie serie) {
        return serie.getDataEstreia() == null ? LocalDate.MIN : serie.getDataEstreia();
    }

    @Override
    public String toString() {
        return descricao;
    }
}
