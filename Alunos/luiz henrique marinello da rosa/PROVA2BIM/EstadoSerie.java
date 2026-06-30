// Enum = lista fixa de estados que uma serie pode ter.
// A API manda em ingles ("Running", "Ended"), aqui eu traduzo pra portugues
// e guardo uma "ordem" pra conseguir ordenar a lista por estado depois.

public enum EstadoSerie {

    // cada estado tem um texto bonito e um numero de ordem
    TRANSMITINDO("Ainda transmitindo", 0),
    CONCLUIDA   ("Ja concluida",       1),
    CANCELADA   ("Cancelada",          2),
    DESCONHECIDO("Estado desconhecido", 3);

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

    // pega o texto cru da API ("Ended") e devolve o enum certo.
    // se vier algo estranho, devolve DESCONHECIDO em vez de quebrar.
    public static EstadoSerie apartirDoTexto(String textoApi) {
        if (textoApi == null) {
            return DESCONHECIDO;
        }

        String t = textoApi.trim().toLowerCase();

        switch (t) {
            case "running":
            case "in development":
                return TRANSMITINDO;
            case "ended":
                return CONCLUIDA;
            case "cancelled":
            case "canceled":
                return CANCELADA;
            default:
                return DESCONHECIDO;
        }
    }

    // assim o estado ja aparece com o texto bonito direto
    @Override
    public String toString() {
        return descricao;
    }
}
