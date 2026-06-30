
/** Listas pessoais mantidas pelo usuário. */
public enum TipoLista {
    FAVORITOS("Favoritos", "favoritos"),
    ASSISTIDAS("Já assistidas", "assistidas"),
    DESEJO_ASSISTIR("Quero assistir", "queroAssistir");

    private final String descricao;
    private final String chaveJson;

    TipoLista(String descricao, String chaveJson) {
        this.descricao = descricao;
        this.chaveJson = chaveJson;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getChaveJson() {
        return chaveJson;
    }
}
