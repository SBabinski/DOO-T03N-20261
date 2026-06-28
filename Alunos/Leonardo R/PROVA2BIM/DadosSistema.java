import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Esta classe guarda todos os dados do programa.
// Tambem e responsavel por salvar e carregar esses dados em JSON.
public class DadosSistema {
    private static final Path ARQUIVO_JSON = Paths.get("dados_series_tv.json");

    private String usuario;
    private final List<Serie> favoritos;
    private final List<Serie> assistidas;
    private final List<Serie> desejoAssistir;

    public DadosSistema(String usuario, List<Serie> favoritos, List<Serie> assistidas, List<Serie> desejoAssistir) {
        this.usuario = usuario == null || usuario.trim().isEmpty() ? "dona gabrielinha" : usuario.trim();
        this.favoritos = favoritos == null ? new ArrayList<Serie>() : favoritos;
        this.assistidas = assistidas == null ? new ArrayList<Serie>() : assistidas;
        this.desejoAssistir = desejoAssistir == null ? new ArrayList<Serie>() : desejoAssistir;
    }

    // Dados pre-carregados, como o enunciado pede.
    public static DadosSistema criarComDadosPreCarregados() {
        List<Serie> favoritos = new ArrayList<Serie>();
        List<Serie> assistidas = new ArrayList<Serie>();
        List<Serie> desejoAssistir = new ArrayList<Serie>();

        favoritos.add(new Serie(169, "Breaking Bad", "English",
                Arrays.asList("Drama", "Crime", "Thriller"), 9.2,
                "Ended", "2008-01-20", "2013-09-29", "AMC"));

        assistidas.add(new Serie(82, "Game of Thrones", "English",
                Arrays.asList("Drama", "Adventure", "Fantasy"), 8.9,
                "Ended", "2011-04-17", "2019-05-19", "HBO"));

        desejoAssistir.add(new Serie(618, "Better Call Saul", "English",
                Arrays.asList("Drama", "Crime", "Legal"), 8.8,
                "Ended", "2015-02-08", "2022-08-15", "AMC"));

        return new DadosSistema("dona gabrielinha", favoritos, assistidas, desejoAssistir);
    }

    // Carrega o arquivo JSON. Se ele nao existir, comeca com dados pre-carregados.
    public static DadosSistema carregar() throws IOException {
        if (!Files.exists(ARQUIVO_JSON)) {
            return criarComDadosPreCarregados();
        }

        try {
            String json = new String(Files.readAllBytes(ARQUIVO_JSON), StandardCharsets.UTF_8);
            Object objeto = JsonParser.parse(json);
            if (!(objeto instanceof Map)) {
                throw new IOException("O JSON salvo nao esta no formato esperado.");
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> mapa = (Map<String, Object>) objeto;
            return criarAPartirDoMapa(mapa);
        } catch (IllegalArgumentException exception) {
            throw new IOException("Erro ao ler JSON: " + exception.getMessage(), exception);
        }
    }

    // Salva o estado atual do sistema em formato JSON.
    public void salvar() throws IOException {
        String json = JsonParser.stringify(transformarEmMapa());
        Files.write(ARQUIVO_JSON, json.getBytes(StandardCharsets.UTF_8));
    }

    @SuppressWarnings("unchecked")
    private static DadosSistema criarAPartirDoMapa(Map<String, Object> mapa) {
        String usuario = texto(mapa.get("usuario"), "dona gabrielinha");
        List<Serie> favoritos = lerListaSeries(mapa.get("favoritos"));
        List<Serie> assistidas = lerListaSeries(mapa.get("assistidas"));
        List<Serie> desejoAssistir = lerListaSeries(mapa.get("desejoAssistir"));
        return new DadosSistema(usuario, favoritos, assistidas, desejoAssistir);
    }

    private Map<String, Object> transformarEmMapa() {
        Map<String, Object> mapa = new LinkedHashMap<String, Object>();
        mapa.put("usuario", usuario);
        mapa.put("favoritos", escreverListaSeries(favoritos));
        mapa.put("assistidas", escreverListaSeries(assistidas));
        mapa.put("desejoAssistir", escreverListaSeries(desejoAssistir));
        return mapa;
    }

    private static List<Map<String, Object>> escreverListaSeries(List<Serie> series) {
        List<Map<String, Object>> lista = new ArrayList<Map<String, Object>>();
        for (Serie serie : series) {
            lista.add(serie.transformarEmMapa());
        }
        return lista;
    }

    @SuppressWarnings("unchecked")
    private static List<Serie> lerListaSeries(Object valor) {
        List<Serie> series = new ArrayList<Serie>();
        if (valor instanceof List) {
            for (Object item : (List<?>) valor) {
                if (item instanceof Map) {
                    series.add(Serie.criarAPartirDoMapa((Map<String, Object>) item));
                }
            }
        }
        return series;
    }

    private static String texto(Object valor, String padrao) {
        if (valor == null || String.valueOf(valor).trim().isEmpty()) {
            return padrao;
        }
        return String.valueOf(valor).trim();
    }

    public boolean adicionarNaLista(List<Serie> lista, Serie serie) {
        if (serie == null || lista.contains(serie)) {
            return false;
        }
        lista.add(serie);
        return true;
    }

    public boolean removerDaLista(List<Serie> lista, Serie serie) {
        return serie != null && lista.remove(serie);
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario == null || usuario.trim().isEmpty() ? "dona gabrielinha" : usuario.trim();
    }

    public List<Serie> getFavoritos() {
        return favoritos;
    }

    public List<Serie> getAssistidas() {
        return assistidas;
    }

    public List<Serie> getDesejoAssistir() {
        return desejoAssistir;
    }
}
