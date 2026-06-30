import java.util.List;
import java.util.Comparator;

// Aqui ficam as 4 formas de ordenar as listas (o que o trabalho pede):
//   - nome (A-Z)
//   - nota
//   - estado
//   - data de estreia
// cada criterio tem seu Comparator. a tela so escolhe e chama ordenar().

public class OrdenadorSeries {

    // os criterios viram um enum. o texto do toString aparece no combo da tela
    public enum Criterio {
        NOME_ALFABETICO("Nome (A-Z)"),
        NOTA_GERAL("Nota geral (maior primeiro)"),
        ESTADO("Estado da serie"),
        DATA_ESTREIA("Data de estreia (mais recente primeiro)");

        private final String rotulo;

        Criterio(String rotulo) {
            this.rotulo = rotulo;
        }

        @Override
        public String toString() {
            return rotulo;
        }
    }

    // ordena a propria lista, conforme o criterio escolhido
    public static void ordenar(List<Serie> series, Criterio criterio) {
        if (series == null || criterio == null) {
            return;
        }

        switch (criterio) {
            case NOME_ALFABETICO:
                series.sort(porNome());
                break;
            case NOTA_GERAL:
                series.sort(porNota());
                break;
            case ESTADO:
                series.sort(porEstado());
                break;
            case DATA_ESTREIA:
                series.sort(porDataEstreia());
                break;
            default:
                // nada
        }
    }

    // A-Z, sem ligar pra maiuscula/minuscula
    private static Comparator<Serie> porNome() {
        return new Comparator<Serie>() {
            @Override
            public int compare(Serie a, Serie b) {
                return a.getNome().compareToIgnoreCase(b.getNome());
            }
        };
    }

    // maior nota primeiro. empatou? desempata pelo nome
    private static Comparator<Serie> porNota() {
        return new Comparator<Serie>() {
            @Override
            public int compare(Serie a, Serie b) {
                int cmp = Double.compare(b.getNotaGeral(), a.getNotaGeral());
                if (cmp != 0) {
                    return cmp;
                }
                return a.getNome().compareToIgnoreCase(b.getNome());
            }
        };
    }

    // usa a "ordem" que defini no enum EstadoSerie. empatou? pelo nome
    private static Comparator<Serie> porEstado() {
        return new Comparator<Serie>() {
            @Override
            public int compare(Serie a, Serie b) {
                int cmp = Integer.compare(a.getEstado().getOrdem(), b.getEstado().getOrdem());
                if (cmp != 0) {
                    return cmp;
                }
                return a.getNome().compareToIgnoreCase(b.getNome());
            }
        };
    }

    // mais recente primeiro. como a data e "yyyy-MM-dd", da pra comparar como texto.
    // serie sem data vai pro fim.
    private static Comparator<Serie> porDataEstreia() {
        return new Comparator<Serie>() {
            @Override
            public int compare(Serie a, Serie b) {
                String da = a.getDataEstreia();
                String db = b.getDataEstreia();

                boolean aVazia = (da == null || da.isEmpty());
                boolean bVazia = (db == null || db.isEmpty());

                if (aVazia && bVazia) {
                    return a.getNome().compareToIgnoreCase(b.getNome());
                }
                if (aVazia) {
                    return 1; // a vai depois
                }
                if (bVazia) {
                    return -1; // b vai depois
                }
                // invertido (db, da) = mais recente primeiro
                return db.compareTo(da);
            }
        };
    }
}
