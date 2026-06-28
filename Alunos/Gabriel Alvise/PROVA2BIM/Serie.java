import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Serie {
    private int id;
    private String nome;
    private String idioma;
    private List<String> generos;
    private double notaGeral;
    private String estado;
    private String dataEstreia;
    private String dataTermino;
    private String emissora;
    private String imagemUrl;

    public Serie() {
        this.generos = new ArrayList<>();
        this.imagemUrl = "";
    }

    public Serie(
            int id,
            String nome,
            String idioma,
            List<String> generos,
            double notaGeral,
            String estado,
            String dataEstreia,
            String dataTermino,
            String emissora
    ) {
        this(
                id,
                nome,
                idioma,
                generos,
                notaGeral,
                estado,
                dataEstreia,
                dataTermino,
                emissora,
                ""
        );
    }

    public Serie(
            int id,
            String nome,
            String idioma,
            List<String> generos,
            double notaGeral,
            String estado,
            String dataEstreia,
            String dataTermino,
            String emissora,
            String imagemUrl
    ) {
        this.id = id;
        this.nome = tratarTexto(nome);
        this.idioma = tratarTexto(idioma);
        this.generos = generos != null ? generos : new ArrayList<>();
        this.notaGeral = notaGeral;
        this.estado = tratarTexto(estado);
        this.dataEstreia = tratarTexto(dataEstreia);
        this.dataTermino = tratarTexto(dataTermino);
        this.emissora = tratarTexto(emissora);
        this.imagemUrl = tratarImagemUrl(imagemUrl);
    }

    private String tratarTexto(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return "Não informado";
        }

        return texto;
    }

    private String tratarImagemUrl(String imagemUrl) {
        if (imagemUrl == null || imagemUrl.trim().isEmpty() || imagemUrl.equals("Não informado")) {
            return "";
        }

        return imagemUrl.trim();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = tratarTexto(nome);
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = tratarTexto(idioma);
    }

    public List<String> getGeneros() {
        return generos;
    }

    public void setGeneros(List<String> generos) {
        this.generos = generos != null ? generos : new ArrayList<>();
    }

    public double getNotaGeral() {
        return notaGeral;
    }

    public void setNotaGeral(double notaGeral) {
        this.notaGeral = notaGeral;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = tratarTexto(estado);
    }

    public String getDataEstreia() {
        return dataEstreia;
    }

    public void setDataEstreia(String dataEstreia) {
        this.dataEstreia = tratarTexto(dataEstreia);
    }

    public String getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(String dataTermino) {
        this.dataTermino = tratarTexto(dataTermino);
    }

    public String getEmissora() {
        return emissora;
    }

    public void setEmissora(String emissora) {
        this.emissora = tratarTexto(emissora);
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = tratarImagemUrl(imagemUrl);
    }

    public boolean temImagem() {
        return imagemUrl != null && !imagemUrl.trim().isEmpty();
    }

    public String getGenerosFormatados() {
        if (generos == null || generos.isEmpty()) {
            return "Não informado";
        }

        return String.join(", ", generos);
    }

    public String getNotaFormatada() {
        if (notaGeral <= 0) {
            return "Não informado";
        }

        return String.valueOf(notaGeral);
    }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof Serie)) {
            return false;
        }

        Serie serie = (Serie) objeto;

        if (id > 0 && serie.id > 0) {
            return id == serie.id;
        }

        return Objects.equals(nome, serie.nome);
    }

    @Override
    public int hashCode() {
        if (id > 0) {
            return Objects.hash(id);
        }

        return Objects.hash(nome);
    }
}