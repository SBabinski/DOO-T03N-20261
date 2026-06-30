
import java.time.LocalDate;
import java.util.Locale;

/** Estado normalizado de uma série para exibição e ordenação. */
public enum EstadoSerie {
    EM_TRANSMISSAO("Ainda transmitindo", 0),
    CONCLUIDA("Concluída", 1),
    CANCELADA("Cancelada", 2),
    INDEFINIDA("Estado não informado", 3);

    private final String descricao;
    private final int ordem;

    EstadoSerie(String descricao, int ordem) {
        this.descricao = descricao;
        this.ordem = ordem;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getOrdem() {
        return ordem;
    }

    public static EstadoSerie fromTvMaze(String status, LocalDate dataTermino) {
        String valor = status == null ? "" : status.trim().toLowerCase(Locale.ROOT);
        if (valor.contains("cancel")) {
            return CANCELADA;
        }
        if (valor.equals("running")) {
            return EM_TRANSMISSAO;
        }
        if (valor.equals("ended") ||
                (dataTermino != null && !dataTermino.isAfter(LocalDate.now()))) {
            return CONCLUIDA;
        }
        return INDEFINIDA;
    }
}
