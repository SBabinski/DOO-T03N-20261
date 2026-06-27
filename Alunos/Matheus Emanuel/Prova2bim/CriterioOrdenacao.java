import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * Criterios de ordenacao disponiveis para as listas de series.
 * Cada criterio fornece o proprio Comparator.
 */
public enum CriterioOrdenacao {

    NOME("Ordem alfabetica (A-Z)") {
        @Override
        public Comparator<Serie> comparator() {
            Collator collator = Collator.getInstance(new Locale("pt", "BR"));
            collator.setStrength(Collator.PRIMARY);
            return (a, b) -> collator.compare(a.getNome(), b.getNome());
        }
    },

    NOTA("Nota geral (maior primeiro)") {
        @Override
        public Comparator<Serie> comparator() {
            return Comparator.comparingDouble(Serie::getNotaOrdenacao).reversed()
                    .thenComparing(Serie::getNome);
        }
    },

    ESTADO("Estado da serie") {
        @Override
        public Comparator<Serie> comparator() {
            return Comparator.comparingInt(Serie::getEstadoOrdem)
                    .thenComparing(Serie::getNome);
        }
    },

    DATA_ESTREIA("Data de estreia (mais recente)") {
        @Override
        public Comparator<Serie> comparator() {
            // Datas no formato AAAA-MM-DD ordenam corretamente como texto.
            // Series sem data vao para o fim.
            return (a, b) -> {
                String da = a.getDataEstreia();
                String db = b.getDataEstreia();
                boolean va = !da.isBlank();
                boolean vb = !db.isBlank();
                if (!va && !vb) return a.getNome().compareToIgnoreCase(b.getNome());
                if (!va) return 1;
                if (!vb) return -1;
                return db.compareTo(da); // mais recente primeiro
            };
        }
    };

    private final String rotulo;

    CriterioOrdenacao(String rotulo) {
        this.rotulo = rotulo;
    }

    public abstract Comparator<Serie> comparator();

    @Override
    public String toString() {
        return rotulo;
    }
}
