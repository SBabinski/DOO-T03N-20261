import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//informacoes pedidas no enunciado

public class Serie {
    private final int id;
    private final String nome;
    private final String idioma;
    private final List<String> generos;
    private final Double nota;
    private final String estado;
    private final String estreia;
    private final String termino;
    private final String emissora;

    public Serie(int id, String nome, String idioma, List<String> generos, Double nota,
                 String estado, String estreia, String termino, String emissora) {
        this.id = id;
        this.nome = texto(nome, "Sem nome");
        this.idioma = texto(idioma, "N/A");
        this.generos = generos == null ? new ArrayList<String>() : new ArrayList<String>(generos);
        this.nota = nota;
        this.estado = texto(estado, "N/A");
        this.estreia = texto(estreia, "N/A");
        this.termino = texto(termino, "N/A");
        this.emissora = texto(emissora, "N/A");
    }

    // Cria uma serie usando o Map que veio da API TVmaze.
    public static Serie criarAPartirDaTVMaze(Map<String, Object> mapa) {
        int id = inteiro(mapa.get("id"));
        String nome = texto(mapa.get("name"), "Sem nome");
        String idioma = texto(mapa.get("language"), "N/A");
        List<String> generos = listaDeTextos(mapa.get("genres"));
        Double nota = lerNota(mapa.get("rating"));
        String estado = texto(mapa.get("status"), "N/A");
        String estreia = texto(mapa.get("premiered"), "N/A");
        String termino = texto(mapa.get("ended"), "N/A");

        String emissora = lerNomeCanal(mapa.get("network"));
        if ("N/A".equals(emissora)) {
            emissora = lerNomeCanal(mapa.get("webChannel"));
        }

        return new Serie(id, nome, idioma, generos, nota, estado, estreia, termino, emissora);
    }

    // Cria uma serie usando o Map lido do JSON local.
    public static Serie criarAPartirDoMapa(Map<String, Object> mapa) {
        return new Serie(
                inteiro(mapa.get("id")),
                texto(mapa.get("nome"), "Sem nome"),
                texto(mapa.get("idioma"), "N/A"),
                listaDeTextos(mapa.get("generos")),
                numeroDecimal(mapa.get("nota")),
                texto(mapa.get("estado"), "N/A"),
                texto(mapa.get("estreia"), "N/A"),
                texto(mapa.get("termino"), "N/A"),
                texto(mapa.get("emissora"), "N/A")
        );
    }

    // Transforma a serie em Map para poder salvar depois como JSON.
    public Map<String, Object> transformarEmMapa() {
        Map<String, Object> mapa = new LinkedHashMap<String, Object>();
        mapa.put("id", id);
        mapa.put("nome", nome);
        mapa.put("idioma", idioma);
        mapa.put("generos", new ArrayList<String>(generos));
        mapa.put("nota", nota);
        mapa.put("estado", estado);
        mapa.put("estreia", estreia);
        mapa.put("termino", termino);
        mapa.put("emissora", emissora);
        return mapa;
    }

    // Texto completo mostrado na area de detalhes da tela.
    public String detalhes() {
        StringBuilder texto = new StringBuilder();
        texto.append("Nome: ").append(nome).append('\n');
        texto.append("Idioma: ").append(idioma).append('\n');
        texto.append("Generos: ").append(getGenerosTexto()).append('\n');
        texto.append("Nota geral: ").append(getNotaTexto()).append('\n');
        texto.append("Estado: ").append(getEstadoTexto()).append('\n');
        texto.append("Data de estreia: ").append(estreia).append('\n');
        texto.append("Data de termino: ").append(termino).append('\n');
        texto.append("Emissora: ").append(emissora).append('\n');
        return texto.toString();
    }

    private static String texto(Object valor, String padrao) {
        if (valor == null || String.valueOf(valor).trim().isEmpty() || "null".equalsIgnoreCase(String.valueOf(valor))) {
            return padrao;
        }
        return String.valueOf(valor).trim();
    }

    private static int inteiro(Object valor) {
        if (valor instanceof Number) {
            return ((Number) valor).intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(valor));
        } catch (Exception exception) {
            return 0;
        }
    }

    private static Double numeroDecimal(Object valor) {
        if (valor == null) {
            return null;
        }
        if (valor instanceof Number) {
            return ((Number) valor).doubleValue();
        }
        try {
            return Double.parseDouble(String.valueOf(valor));
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private static Double lerNota(Object valor) {
        if (valor instanceof Map) {
            Map<String, Object> mapaNota = (Map<String, Object>) valor;
            return numeroDecimal(mapaNota.get("average"));
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static String lerNomeCanal(Object valor) {
        if (valor instanceof Map) {
            Map<String, Object> canal = (Map<String, Object>) valor;
            return texto(canal.get("name"), "N/A");
        }
        return "N/A";
    }

    private static List<String> listaDeTextos(Object valor) {
        List<String> textos = new ArrayList<String>();
        if (valor instanceof List) {
            for (Object item : (List<?>) valor) {
                String texto = texto(item, "");
                if (!texto.isEmpty()) {
                    textos.add(texto);
                }
            }
        }
        return textos;
    }

    public String getNome() {
        return nome;
    }

    public String getEstadoTexto() {
        if ("Running".equalsIgnoreCase(estado)) {
            return "Ainda transmitindo";
        }
        if ("Ended".equalsIgnoreCase(estado)) {
            return "Concluida";
        }
        if ("Canceled".equalsIgnoreCase(estado) || "Cancelled".equalsIgnoreCase(estado)) {
            return "Cancelada";
        }
        return estado;
    }

    public String getGenerosTexto() {
        return generos.isEmpty() ? "N/A" : String.join(", ", generos);
    }

    public String getNotaTexto() {
        return nota == null ? "N/A" : String.format("%.1f", nota);
    }

    public double getNotaParaOrdenar() {
        return nota == null ? -1.0 : nota.doubleValue();
    }

    public LocalDate getEstreiaParaOrdenar() {
        try {
            return LocalDate.parse(estreia);
        } catch (DateTimeParseException exception) {
            return LocalDate.MAX;
        }
    }

    @Override
    public String toString() {
        return nome + " | Nota: " + getNotaTexto() + " | " + getEstadoTexto();
    }

    @Override
    public boolean equals(Object outro) {
        if (this == outro) {
            return true;
        }
        if (!(outro instanceof Serie)) {
            return false;
        }
        Serie serie = (Serie) outro;
        return id == serie.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
