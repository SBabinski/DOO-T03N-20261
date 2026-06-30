import java.util.Comparator;
import java.util.List;

// Classe responsável por controlar as ações feitas com as séries
public class SerieController {

    private Usuario usuario;

    public SerieController(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    // ADICIONAR SÉRIES NAS LISTAS

    public void adicionarFavorito(Serie serie) {
        usuario.adicionarFavorito(serie);
    }

    public void adicionarAssistida(Serie serie) {
        usuario.adicionarAssistida(serie);
    }

    public void adicionarDesejaAssistir(Serie serie) {
        usuario.adicionarDesejaAssistir(serie);
    }

    // REMOVER SÉRIES DAS LISTAS

    public void removerFavorito(Serie serie) {
        usuario.removerFavorito(serie);
    }

    public void removerAssistida(Serie serie) {
        usuario.removerAssistida(serie);
    }

    public void removerDesejaAssistir(Serie serie) {
        usuario.removerDesejaAssistir(serie);
    }

    // PEGAR AS LISTAS

    public List<Serie> getFavoritos() {
        return usuario.getFavoritos();
    }

    public List<Serie> getAssistidas() {
        return usuario.getAssistidas();
    }

    public List<Serie> getDesejaAssistir() {
        return usuario.getDesejaAssistir();
    }

    // ORDENAR LISTAS

    public void ordenarPorNome(List<Serie> lista) {
        lista.sort(Comparator.comparing(
                serie -> tratarTexto(serie.getNome()),
                String.CASE_INSENSITIVE_ORDER
        ));
    }

    public void ordenarPorNota(List<Serie> lista) {
        lista.sort(Comparator.comparingDouble(Serie::getNota).reversed());
    }

    public void ordenarPorEstado(List<Serie> lista) {
        lista.sort(Comparator.comparing(
                serie -> tratarTexto(serie.getEstado()),
                String.CASE_INSENSITIVE_ORDER
        ));
    }

    public void ordenarPorDataEstreia(List<Serie> lista) {
        lista.sort(Comparator.comparing(
                serie -> tratarData(serie.getDataEstreia()),
                String.CASE_INSENSITIVE_ORDER
        ));
    }

    // Evita erro se algum texto vier nulo
    private String tratarTexto(String texto) {
        if (texto == null || texto.isBlank()) {
            return "";
        }

        return texto;
    }

    // Datas não informadas ficam no final da ordenação
    private String tratarData(String data) {
        if (data == null || data.isBlank() || data.equals("Não informado")) {
            return "9999-99-99";
        }

        return data;
    }
}
