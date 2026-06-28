package objetos;

public class Serie {

    private int id;
    private String nome;
    private String status;
    private String estreia;
    private String fim;
    private double nota;
    private String emissora;
    private String generos;
    private String sumario;
    private String idioma;

    
    public Serie() {
    }


    public Serie(int id, String nome, String status, String estreia, String fim, double nota, String emissora,
            String generos, String sumario) {
        this(id, nome, status, estreia, fim, nota, emissora, generos, sumario, "Desconhecido");
    }

    public Serie(int id, String nome, String status, String estreia, String fim, double nota, String emissora,
            String generos, String sumario, String idioma) {
        this.id = id;
        this.nome = nome;
        this.status = status;
        this.estreia = estreia;
        this.fim = fim;
        this.nota = nota;
        this.emissora = emissora;
        this.generos = generos;
        this.sumario = sumario;
        this.idioma = idioma;
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
        this.nome = nome;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getEstreia() {
        return estreia;
    }

    public void setEstreia(String estreia) {
        this.estreia = estreia;
    }


    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }


    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }


    public String getEmissora() {
        return emissora;
    }

    public void setEmissora(String emissora) {
        this.emissora = emissora;
    }


    public String getGeneros() {
        return generos;
    }

    public void setGeneros(String generos) {
        this.generos = generos;
    }


    public String getSumario() {
        return sumario;
    }

    public void setSumario(String sumario) {
        this.sumario = sumario;
    }


    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }


    @Override
    public String toString() {
        return "Serie [nome=" + nome + ", status=" + status + ", nota=" + nota + "]";
    }

    

}
