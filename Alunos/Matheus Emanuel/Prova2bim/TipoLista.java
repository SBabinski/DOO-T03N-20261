/**
 * As tres listas que o usuario pode manter, ao estilo Netflix.
 */
public enum TipoLista {
    FAVORITOS("Favoritos"),
    ASSISTIDAS("Series ja assistidas"),
    DESEJA_ASSISTIR("Series que deseja assistir");

    private final String rotulo;

    TipoLista(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }

    @Override
    public String toString() {
        return rotulo;
    }
}
