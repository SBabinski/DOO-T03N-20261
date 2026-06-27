import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Representa uma serie de TV com as informacoes exibidas pelo sistema.
 * Imutavel apos a construcao; oferece conversao de/para Map para persistencia
 * em JSON e uma fabrica que interpreta o objeto "show" da API TVmaze.
 */
public final class Serie {

    private final int id;
    private final String nome;
    private final String idioma;
    private final List<String> generos;
    private final Double nota;          // pode ser null quando a API nao informa
    private final String estado;        // estado bruto vindo da API (ex.: "Running")
    private final String dataEstreia;   // formato "AAAA-MM-DD" ou vazio
    private final String dataTermino;   // formato "AAAA-MM-DD" ou vazio
    private final String emissora;
    private final String resumo;
    private final String urlImagem;

    public Serie(int id, String nome, String idioma, List<String> generos, Double nota,
                 String estado, String dataEstreia, String dataTermino,
                 String emissora, String resumo, String urlImagem) {
        this.id = id;
        this.nome = ouVazio(nome);
        this.idioma = ouVazio(idioma);
        this.generos = generos != null ? new ArrayList<>(generos) : new ArrayList<>();
        this.nota = nota;
        this.estado = ouVazio(estado);
        this.dataEstreia = ouVazio(dataEstreia);
        this.dataTermino = ouVazio(dataTermino);
        this.emissora = ouVazio(emissora);
        this.resumo = ouVazio(resumo);
        this.urlImagem = ouVazio(urlImagem);
    }

    // ---- Getters -------------------------------------------------------

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getIdioma() { return idioma; }
    public List<String> getGeneros() { return new ArrayList<>(generos); }
    public Double getNota() { return nota; }
    public String getEstado() { return estado; }
    public String getDataEstreia() { return dataEstreia; }
    public String getDataTermino() { return dataTermino; }
    public String getEmissora() { return emissora; }
    public String getResumo() { return resumo; }
    public String getUrlImagem() { return urlImagem; }

    // ---- Apresentacao amigavel ---------------------------------------

    /** Nota numerica usada na ordenacao; series sem nota vao para o fim. */
    public double getNotaOrdenacao() {
        return nota != null ? nota : -1.0;
    }

    public String getNotaTexto() {
        return nota != null ? String.format("%.1f", nota) : "N/A";
    }

    public String getGenerosTexto() {
        return generos.isEmpty() ? "Nao informado" : String.join(", ", generos);
    }

    public String getIdiomaTexto() {
        return idioma.isBlank() ? "Nao informado" : idioma;
    }

    public String getEmissoraTexto() {
        return emissora.isBlank() ? "Nao informada" : emissora;
    }

    public String getDataEstreiaTexto() {
        return dataEstreia.isBlank() ? "Desconhecida" : dataEstreia;
    }

    public String getDataTerminoTexto() {
        return dataTermino.isBlank() ? "Em aberto" : dataTermino;
    }

    /** Traduz o estado bruto da API para portugues. */
    public String getEstadoTexto() {
        switch (estado.toLowerCase()) {
            case "running":            return "Em transmissao";
            case "ended":              return "Concluida";
            case "to be determined":   return "A definir";
            case "in development":     return "Em desenvolvimento";
            default:                   return estado.isBlank() ? "Desconhecido" : estado;
        }
    }

    /** Prioridade de ordenacao por estado (transmitindo primeiro, concluidas depois). */
    public int getEstadoOrdem() {
        switch (estado.toLowerCase()) {
            case "running":          return 0;
            case "to be determined": return 1;
            case "in development":   return 2;
            case "ended":            return 3;
            default:                 return 4;
        }
    }

    // ---- Persistencia --------------------------------------------------

    public Map<String, Object> toMap() {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("id", id);
        m.put("nome", nome);
        m.put("idioma", idioma);
        m.put("generos", new ArrayList<Object>(generos));
        m.put("nota", nota);
        m.put("estado", estado);
        m.put("dataEstreia", dataEstreia);
        m.put("dataTermino", dataTermino);
        m.put("emissora", emissora);
        m.put("resumo", resumo);
        m.put("urlImagem", urlImagem);
        return m;
    }

    /** Reconstroi uma Serie a partir do Map lido do arquivo de dados. */
    @SuppressWarnings("unchecked")
    public static Serie fromMap(Map<String, Object> m) {
        List<String> generos = new ArrayList<>();
        Object g = m.get("generos");
        if (g instanceof List) {
            for (Object o : (List<Object>) g) {
                if (o != null) generos.add(o.toString());
            }
        }
        return new Serie(
            intDe(m.get("id")),
            texto(m.get("nome")),
            texto(m.get("idioma")),
            generos,
            (m.get("nota") instanceof Number) ? ((Number) m.get("nota")).doubleValue() : null,
            texto(m.get("estado")),
            texto(m.get("dataEstreia")),
            texto(m.get("dataTermino")),
            texto(m.get("emissora")),
            texto(m.get("resumo")),
            texto(m.get("urlImagem"))
        );
    }

    /** Fabrica que interpreta o objeto "show" retornado pela API TVmaze. */
    @SuppressWarnings("unchecked")
    public static Serie fromShow(Map<String, Object> show) {
        int id = intDe(show.get("id"));
        String nome = texto(show.get("name"));
        String idioma = texto(show.get("language"));

        List<String> generos = new ArrayList<>();
        Object g = show.get("genres");
        if (g instanceof List) {
            for (Object o : (List<Object>) g) {
                if (o != null) generos.add(o.toString());
            }
        }

        Double nota = null;
        Object rating = show.get("rating");
        if (rating instanceof Map) {
            Object avg = ((Map<String, Object>) rating).get("average");
            if (avg instanceof Number) nota = ((Number) avg).doubleValue();
        }

        String estado = texto(show.get("status"));
        String estreia = texto(show.get("premiered"));
        String termino = texto(show.get("ended"));

        String emissora = "";
        Object network = show.get("network");
        if (network instanceof Map) {
            emissora = texto(((Map<String, Object>) network).get("name"));
        }
        if (emissora.isBlank()) {
            Object web = show.get("webChannel");
            if (web instanceof Map) emissora = texto(((Map<String, Object>) web).get("name"));
        }

        String resumo = limparHtml(texto(show.get("summary")));

        String imagem = "";
        Object image = show.get("image");
        if (image instanceof Map) {
            Map<String, Object> im = (Map<String, Object>) image;
            imagem = texto(im.get("medium"));
            if (imagem.isBlank()) imagem = texto(im.get("original"));
        }

        return new Serie(id, nome, idioma, generos, nota, estado,
                estreia, termino, emissora, resumo, imagem);
    }

    // ---- Igualdade por id (evita duplicatas nas listas) ----------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Serie)) return false;
        return id == ((Serie) o).id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return nome;
    }

    // ---- Auxiliares ----------------------------------------------------

    private static String ouVazio(String s) {
        return s == null ? "" : s;
    }

    private static String texto(Object o) {
        return o == null ? "" : o.toString();
    }

    private static int intDe(Object o) {
        if (o instanceof Number) return ((Number) o).intValue();
        try { return Integer.parseInt(String.valueOf(o)); } catch (Exception e) { return 0; }
    }

    private static String limparHtml(String html) {
        if (html == null || html.isBlank()) return "";
        return html.replaceAll("<[^>]+>", "")
                   .replace("&amp;", "&")
                   .replace("&quot;", "\"")
                   .replace("&#39;", "'")
                   .replace("&lt;", "<")
                   .replace("&gt;", ">")
                   .trim();
    }
}
