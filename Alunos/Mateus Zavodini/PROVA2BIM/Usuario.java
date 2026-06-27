

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Essa classe guarda TUDO que precisa ser salvo no arquivo: o nome do usuario
// e as 3 listas de series (favoritas, assistidas e quero assistir).
public class Usuario {

    private String nome = "Usuario";
    private List<Serie> favoritas = new ArrayList<>();
    private List<Serie> assistidas = new ArrayList<>();
    private List<Serie> queroAssistir = new ArrayList<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Serie> getFavoritas() {
        return favoritas;
    }

    public List<Serie> getAssistidas() {
        return assistidas;
    }

    public List<Serie> getQueroAssistir() {
        return queroAssistir;
    }

    public boolean adicionarFavorita(Serie serie) {
        if (favoritas.contains(serie)) return false;
        return favoritas.add(serie);
    }

    public boolean removerFavorita(Serie serie) {
        return favoritas.remove(serie);
    }

    public boolean adicionarAssistida(Serie serie) {
        if (assistidas.contains(serie)) return false;
        return assistidas.add(serie);
    }

    public boolean removerAssistida(Serie serie) {
        return assistidas.remove(serie);
    }

    public boolean adicionarQueroAssistir(Serie serie) {
        if (queroAssistir.contains(serie)) return false;
        return queroAssistir.add(serie);
    }

    public boolean removerQueroAssistir(Serie serie) {
        return queroAssistir.remove(serie);
    }

    public Map<String, Object> paraMapa() {
        Map<String, Object> mapa = new LinkedHashMap<>();
        mapa.put("nome", nome);
        mapa.put("favoritas", listaParaMapas(favoritas));
        mapa.put("assistidas", listaParaMapas(assistidas));
        mapa.put("queroAssistir", listaParaMapas(queroAssistir));
        return mapa;
    }

    private List<Object> listaParaMapas(List<Serie> lista) {
        List<Object> resultado = new ArrayList<>();
        for (Serie serie : lista) {
            resultado.add(serie.paraMapa());
        }
        return resultado;
    }

    @SuppressWarnings("unchecked")
    public static Usuario deMapa(Map<String, Object> mapa) {
        Usuario usuario = new Usuario();

        Object nomeLido = mapa.get("nome");
        usuario.setNome(nomeLido != null ? nomeLido.toString() : "Usuario");

        usuario.favoritas = mapasParaLista((List<Object>) mapa.get("favoritas"));
        usuario.assistidas = mapasParaLista((List<Object>) mapa.get("assistidas"));
        usuario.queroAssistir = mapasParaLista((List<Object>) mapa.get("queroAssistir"));

        return usuario;
    }

    @SuppressWarnings("unchecked")
    private static List<Serie> mapasParaLista(List<Object> listaDeMapas) {
        List<Serie> resultado = new ArrayList<>();
        if (listaDeMapas == null) return resultado;
        for (Object item : listaDeMapas) {
            resultado.add(Serie.deMapa((Map<String, Object>) item));
        }
        return resultado;
    }
}