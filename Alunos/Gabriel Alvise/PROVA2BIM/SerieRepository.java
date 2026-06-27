import java.util.List;

public class SerieRepository {
    private DadosAplicacao dadosAplicacao;
    private JsonStorageService jsonStorageService;

    public SerieRepository(DadosAplicacao dadosAplicacao, JsonStorageService jsonStorageService) {
        this.dadosAplicacao = dadosAplicacao;
        this.jsonStorageService = jsonStorageService;
    }

    public Usuario getUsuario() {
        return dadosAplicacao.getUsuario();
    }

    public void atualizarUsuario(String nomeOuApelido) {
        dadosAplicacao.getUsuario().setNomeOuApelido(nomeOuApelido);
        salvar();
    }

    public List<Serie> getFavoritos() {
        return dadosAplicacao.getFavoritos();
    }

    public List<Serie> getSeriesAssistidas() {
        return dadosAplicacao.getSeriesAssistidas();
    }

    public List<Serie> getSeriesDesejo() {
        return dadosAplicacao.getSeriesDesejo();
    }

    public boolean adicionarFavorito(Serie serie) {
        return adicionarSerieNaLista(serie, dadosAplicacao.getFavoritos());
    }

    public boolean removerFavorito(Serie serie) {
        return removerSerieDaLista(serie, dadosAplicacao.getFavoritos());
    }

    public boolean adicionarSerieAssistida(Serie serie) {
        return adicionarSerieNaLista(serie, dadosAplicacao.getSeriesAssistidas());
    }

    public boolean removerSerieAssistida(Serie serie) {
        return removerSerieDaLista(serie, dadosAplicacao.getSeriesAssistidas());
    }

    public boolean adicionarSerieDesejo(Serie serie) {
        return adicionarSerieNaLista(serie, dadosAplicacao.getSeriesDesejo());
    }

    public boolean removerSerieDesejo(Serie serie) {
        return removerSerieDaLista(serie, dadosAplicacao.getSeriesDesejo());
    }

    private boolean adicionarSerieNaLista(Serie serie, List<Serie> lista) {
        if (serie == null || lista == null) {
            return false;
        }

        if (lista.contains(serie)) {
            return false;
        }

        lista.add(serie);
        salvar();
        return true;
    }

    private boolean removerSerieDaLista(Serie serie, List<Serie> lista) {
        if (serie == null || lista == null) {
            return false;
        }

        boolean removeu = lista.remove(serie);

        if (removeu) {
            salvar();
        }

        return removeu;
    }

    private void salvar() {
        jsonStorageService.salvarDados(dadosAplicacao);
    }
}