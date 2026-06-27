import java.util.Comparator;
import java.util.List;

public class GerenciadorSeries {
    private Usuario usuario;

    public GerenciadorSeries(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Serie> getFavoritas() { return usuario.getFavoritas(); }
    public List<Serie> getAssistidas() { return usuario.getAssistidas(); }
    public List<Serie> getDesejaAssistir() { return usuario.getDesejaAssistir(); }

    public void adicionarFavorita(Serie serie) { usuario.adicionarFavorita(serie); }
    public void adicionarAssistida(Serie serie) { usuario.adicionarAssistida(serie); }
    public void adicionarDesejaAssistir(Serie serie) { usuario.adicionarDesejaAssistir(serie); }

    public void removerFavorita(Serie serie) { usuario.removerFavorita(serie); }
    public void removerAssistida(Serie serie) { usuario.removerAssistida(serie); }
    public void removerDesejaAssistir(Serie serie) { usuario.removerDesejaAssistir(serie); }

    public void ordenarPorNome(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getNome));
    }

    public void ordenarPorNota(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getNota).reversed());
    }

    public void ordenarPorStatus(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getStatus));
    }

    public void ordenarPorEstreia(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getEstreia));
    }
}