
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/** Modelo imutável com os dados relevantes retornados pelo TVmaze. */
public final class Serie {
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final long id;
    private final String nome;
    private final String idioma;
    private final List<String> generos;
    private final double nota;
    private final String statusTvMaze;
    private final EstadoSerie estado;
    private final LocalDate dataEstreia;
    private final LocalDate dataTermino;
    private final String emissora;
    private final String resumo;
    private final String urlImagem;

    public Serie(long id, String nome, String idioma, List<String> generos, double nota,
            String statusTvMaze, LocalDate dataEstreia, LocalDate dataTermino,
            String emissora, String resumo, String urlImagem) {
        if (id <= 0) {
            throw new IllegalArgumentException("A série precisa ter um identificador válido.");
        }
        this.id = id;
        this.nome = textoOuPadrao(nome, "Sem nome");
        this.idioma = textoOuPadrao(idioma, "Não informado");
        this.generos = generos == null ? List.of() : List.copyOf(generos);
        this.nota = nota;
        this.statusTvMaze = textoOuPadrao(statusTvMaze, "Não informado");
        this.estado = EstadoSerie.fromTvMaze(statusTvMaze, dataTermino);
        this.dataEstreia = dataEstreia;
        this.dataTermino = dataTermino;
        this.emissora = textoOuPadrao(emissora, "Não informada");
        this.resumo = textoOuPadrao(resumo, "Resumo não disponível.");
        this.urlImagem = urlImagem == null ? "" : urlImagem.trim();
    }

    private static String textoOuPadrao(String valor, String padrao) {
        return valor == null || valor.isBlank() ? padrao : valor.trim();
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getIdioma() {
        return idioma;
    }

    public List<String> getGeneros() {
        return generos;
    }

    public double getNota() {
        return nota;
    }

    public String getStatusTvMaze() {
        return statusTvMaze;
    }

    public EstadoSerie getEstado() {
        return estado;
    }

    public LocalDate getDataEstreia() {
        return dataEstreia;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public String getEmissora() {
        return emissora;
    }

    public String getResumo() {
        return resumo;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public String getGenerosFormatados() {
        return generos.isEmpty() ? "Não informados" : String.join(", ", generos);
    }

    public String getNotaFormatada() {
        return nota < 0 ? "Sem nota" : String.format(Locale.forLanguageTag("pt-BR"), "%.1f", nota);
    }

    public String getDataEstreiaFormatada() {
        return formatarData(dataEstreia);
    }

    public String getDataTerminoFormatada() {
        return formatarData(dataTermino);
    }

    private static String formatarData(LocalDate data) {
        return data == null ? "Não informada" : FORMATO_DATA.format(data);
    }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public boolean equals(Object outro) {
        if (this == outro) {
            return true;
        }
        if (outro instanceof Serie) {
            Serie serie = (Serie) outro;
            return id == serie.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
