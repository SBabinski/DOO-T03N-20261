package model;

/**
 * Representa uma série de TV obtida da API do TVMaze.
 */
public class Serie {

    private int id;
    private String nome;
    private String idioma;
    private String generos;
    private double nota;
    private String estado;
    private String dataEstreia;
    private String dataTermino;
    private String emissora;
    private String resumo;
    private String urlImagem;

    /**
     * Construtor completo da série.
     */
    public Serie(int id, String nome, String idioma, String generos, double nota,
                 String estado, String dataEstreia, String dataTermino,
                 String emissora, String resumo, String urlImagem) {
        this.id = id;
        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.nota = nota;
        this.estado = estado;
        this.dataEstreia = dataEstreia;
        this.dataTermino = dataTermino;
        this.emissora = emissora;
        this.resumo = resumo;
        this.urlImagem = urlImagem;
    }

    // Getters e setters

    public int obterIdSerie() {
        return id;
    }

    public String obterNome() {
        return nome;
    }

    public void definirNome(String nome) {
        this.nome = nome;
    }

    public String obterIdioma() {
        return idioma;
    }

    public void definirIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String obterGeneros() {
        return generos;
    }

    public void definirGeneros(String generos) {
        this.generos = generos;
    }

    public double obterNota() {
        return nota;
    }

    public void definirNota(double nota) {
        this.nota = nota;
    }

    public String obterEstado() {
        return estado;
    }

    public void definirEstado(String estado) {
        this.estado = estado;
    }

    public String obterDataEstreia() {
        return dataEstreia;
    }

    public void definirDataEstreia(String dataEstreia) {
        this.dataEstreia = dataEstreia;
    }

    public String obterDataTermino() {
        return dataTermino;
    }

    public void definirDataTermino(String dataTermino) {
        this.dataTermino = dataTermino;
    }

    public String obterEmissora() {
        return emissora;
    }

    public void definirEmissora(String emissora) {
        this.emissora = emissora;
    }

    public String obterResumo() {
        return resumo;
    }

    public void definirResumo(String resumo) {
        this.resumo = resumo;
    }

    public String obterUrlImagem() {
        return urlImagem;
    }

    public void definirUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    @Override
    public boolean equals(Object outro) {
        if (this == outro) return true;
        if (outro == null || getClass() != outro.getClass()) return false;
        Serie serie = (Serie) outro;
        return this.id == serie.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return nome + " (" + dataEstreia + ")";
    }
}
