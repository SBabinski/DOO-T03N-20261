import java.util.ArrayList;
import java.util.List;


public class Serie {
    private String nome;
    private String idioma;
    private List<String> generos;
    private double nota;
    private String estado;
    private String dataEstreia;
    private String dataTermino;
    private String emissora;


    public Serie() {
        this.generos = new ArrayList<>();
    }

    // Construtor completo
    public Serie(String nome, String idioma, List<String> generos, double nota,
                 String estado, String dataEstreia, String dataTermino, String emissora) {
        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.nota = nota;
        this.estado = estado;
        this.dataEstreia = dataEstreia;
        this.dataTermino = dataTermino;
        this.emissora = emissora;
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

    public String getEstado() {
        return estado;
    }

    public String getDataEstreia() {
        return dataEstreia;
    }

    public String getDataTermino() {
        return dataTermino;
    }

    public String getEmissora() {
        return emissora;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void setGeneros(List<String> generos) {
        this.generos = generos;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setDataEstreia(String dataEstreia) {
        this.dataEstreia = dataEstreia;
    }

    public void setDataTermino(String dataTermino) {
        this.dataTermino = dataTermino;
    }

    public void setEmissora(String emissora) {
        this.emissora = emissora;
    }

    // Transforma a lista de gêneros em texto para mostrar na tela
    public String getGenerosComoTexto() {
        if (generos == null || generos.isEmpty()) {
            return "Não informado";
        }

        return String.join(", ", generos);
    }

    // Texto completo com os detalhes da série quando usamos ver detalhes
    public String getDetalhes() {
        return "Nome: " + nome +
                "\nIdioma: " + idioma +
                "\nGêneros: " + getGenerosComoTexto() +
                "\nNota geral: " + nota +
                "\nEstado: " + estado +
                "\nData de estreia: " + dataEstreia +
                "\nData de término: " + dataTermino +
                "\nEmissora: " + emissora;
    }

    // Usado para comparar séries pelo nome e evitar duplicadas nas listas
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Serie)) {
            return false;
        }

        Serie outraSerie = (Serie) obj;

        if (this.nome == null || outraSerie.nome == null) {
            return false;
        }

        return this.nome.equalsIgnoreCase(outraSerie.nome);
    }

    @Override
    public int hashCode() {
        if (nome == null) {
            return 0;
        }

        return nome.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return nome;
    }
}