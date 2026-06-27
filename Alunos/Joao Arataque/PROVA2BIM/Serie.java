public class Serie {
    private String nome;
    private String idioma;
    private String generos;
    private double nota;
    private String status;
    private String estreia;
    private String termino;
    private String emissora;

    public Serie(String nome, String idioma, String generos, double nota, String status,
                 String estreia, String termino, String emissora) {
        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.nota = nota;
        this.status = status;
        this.estreia = estreia;
        this.termino = termino;
        this.emissora = emissora;
    }

    public String getNome() { return nome; }
    public String getIdioma() { return idioma; }
    public String getGeneros() { return generos; }
    public double getNota() { return nota; }
    public String getStatus() { return status; }
    public String getEstreia() { return estreia; }
    public String getTermino() { return termino; }
    public String getEmissora() { return emissora; }

    @Override
    public String toString() {
        return nome;
    }

    public String detalhes() {
        return "Nome: " + nome +
                "\nIdioma: " + idioma +
                "\nGêneros: " + generos +
                "\nNota: " + nota +
                "\nStatus: " + status +
                "\nEstreia: " + estreia +
                "\nTérmino: " + termino +
                "\nEmissora: " + emissora;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Serie)) return false;
        Serie outra = (Serie) obj;
        return nome.equalsIgnoreCase(outra.getNome());
    }
}