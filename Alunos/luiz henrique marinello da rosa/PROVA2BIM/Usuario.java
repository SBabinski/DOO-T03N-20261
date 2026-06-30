import java.util.List;
import java.util.ArrayList;

// O usuario LOCAL do programa (sem login/servidor).
// Guarda o nome/apelido e as 3 listas do trabalho: favoritos, ja assistidas
// e deseja assistir. Os metodos de adicionar nao deixam repetir serie,
// e os de remover nao quebram se a serie nao estiver na lista.

public class Usuario {

    private String nome;

    private List<Serie> favoritos;
    private List<Serie> assistidas;
    private List<Serie> desejaAssistir;

    public Usuario(String nome) {
        // se vier vazio, uso "luiz" como padrao
        this.nome = (nome == null || nome.trim().isEmpty()) ? "luiz" : nome.trim();
        this.favoritos = new ArrayList<Serie>();
        this.assistidas = new ArrayList<Serie>();
        this.desejaAssistir = new ArrayList<Serie>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        // so troca se o novo nome nao for vazio
        if (nome != null && !nome.trim().isEmpty()) {
            this.nome = nome.trim();
        }
    }

    public List<Serie> getFavoritos()      { return favoritos; }
    public List<Serie> getAssistidas()     { return assistidas; }
    public List<Serie> getDesejaAssistir() { return desejaAssistir; }

    // usados quando carrego os dados do arquivo de volta
    public void setFavoritos(List<Serie> lista)      { this.favoritos = (lista == null) ? new ArrayList<Serie>() : lista; }
    public void setAssistidas(List<Serie> lista)     { this.assistidas = (lista == null) ? new ArrayList<Serie>() : lista; }
    public void setDesejaAssistir(List<Serie> lista) { this.desejaAssistir = (lista == null) ? new ArrayList<Serie>() : lista; }

    // favoritos: true = adicionou / false = ja tinha
    public boolean adicionarFavorito(Serie s) {
        return adicionarSemRepetir(favoritos, s);
    }

    public boolean removerFavorito(Serie s) {
        return favoritos.remove(s);
    }

    // ja assistidas
    public boolean adicionarAssistida(Serie s) {
        return adicionarSemRepetir(assistidas, s);
    }

    public boolean removerAssistida(Serie s) {
        return assistidas.remove(s);
    }

    // deseja assistir
    public boolean adicionarDesejaAssistir(Serie s) {
        return adicionarSemRepetir(desejaAssistir, s);
    }

    public boolean removerDesejaAssistir(Serie s) {
        return desejaAssistir.remove(s);
    }

    // o pulo do gato: so adiciona se nao for null e ainda nao estiver na lista.
    // o contains() usa o equals() da Serie (que compara pelo id).
    private boolean adicionarSemRepetir(List<Serie> lista, Serie s) {
        if (s == null || lista.contains(s)) {
            return false;
        }
        lista.add(s);
        return true;
    }
}
