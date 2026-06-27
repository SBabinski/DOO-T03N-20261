import java.util.ArrayList;
import java.util.List;

public class DadosAplicacao {
    private Usuario usuario;
    private List<Serie> favoritos;
    private List<Serie> seriesAssistidas;
    private List<Serie> seriesDesejo;

    public DadosAplicacao() {
        this.usuario = new Usuario();
        this.favoritos = new ArrayList<>();
        this.seriesAssistidas = new ArrayList<>();
        this.seriesDesejo = new ArrayList<>();
    }

    public DadosAplicacao(
            Usuario usuario,
            List<Serie> favoritos,
            List<Serie> seriesAssistidas,
            List<Serie> seriesDesejo
    ) {
        this.usuario = usuario != null ? usuario : new Usuario();
        this.favoritos = favoritos != null ? favoritos : new ArrayList<>();
        this.seriesAssistidas = seriesAssistidas != null ? seriesAssistidas : new ArrayList<>();
        this.seriesDesejo = seriesDesejo != null ? seriesDesejo : new ArrayList<>();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario != null ? usuario : new Usuario();
    }

    public List<Serie> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Serie> favoritos) {
        this.favoritos = favoritos != null ? favoritos : new ArrayList<>();
    }

    public List<Serie> getSeriesAssistidas() {
        return seriesAssistidas;
    }

    public void setSeriesAssistidas(List<Serie> seriesAssistidas) {
        this.seriesAssistidas = seriesAssistidas != null ? seriesAssistidas : new ArrayList<>();
    }

    public List<Serie> getSeriesDesejo() {
        return seriesDesejo;
    }

    public void setSeriesDesejo(List<Serie> seriesDesejo) {
        this.seriesDesejo = seriesDesejo != null ? seriesDesejo : new ArrayList<>();
    }
}