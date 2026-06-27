import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private List<Serie> favoritas;
    private List<Serie> assistidas;
    private List<Serie> desejaAssistir;

    public Usuario(String nome) {
        this.nome = nome;
        this.favoritas = new ArrayList<>();
        this.assistidas = new ArrayList<>();
        this.desejaAssistir = new ArrayList<>();
    }

    public String getNome() { return nome; }
    public List<Serie> getFavoritas() { return favoritas; }
    public List<Serie> getAssistidas() { return assistidas; }
    public List<Serie> getDesejaAssistir() { return desejaAssistir; }

    public void adicionarFavorita(Serie serie) {
        if (!favoritas.contains(serie)) favoritas.add(serie);
    }

    public void adicionarAssistida(Serie serie) {
        if (!assistidas.contains(serie)) assistidas.add(serie);
    }

    public void adicionarDesejaAssistir(Serie serie) {
        if (!desejaAssistir.contains(serie)) desejaAssistir.add(serie);
    }

    public void removerFavorita(Serie serie) { favoritas.remove(serie); }
    public void removerAssistida(Serie serie) { assistidas.remove(serie); }
    public void removerDesejaAssistir(Serie serie) { desejaAssistir.remove(serie); }
}