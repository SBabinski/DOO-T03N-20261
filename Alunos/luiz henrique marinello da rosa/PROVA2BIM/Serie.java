import java.util.List;
import java.util.ArrayList;

// "Molde" de uma serie de TV. So guarda os dados que o trabalho pede:
// nome, idioma, generos, nota, estado, datas e emissora.
// Tudo private + getter/setter = encapsulamento (POO).

public class Serie {

    private int id;                 // id la da API. uso pra nao repetir serie na lista
    private String nome;
    private String idioma;
    private List<String> generos;   // pode ter varios (Drama, Crime...)
    private double notaGeral;       // 0 a 10. 0 = sem nota
    private EstadoSerie estado;
    private String dataEstreia;     // "yyyy-MM-dd" (formato que a API manda)
    private String dataTermino;     // vazio se a serie ainda ta no ar
    private String emissora;        // nome do canal/emissora

    // construtor: monta a serie. o ?: troca null por valor vazio (evita erro depois)
    public Serie(int id, String nome, String idioma, List<String> generos,
                 double notaGeral, EstadoSerie estado,
                 String dataEstreia, String dataTermino, String emissora) {
        this.id = id;
        this.nome = (nome == null) ? "" : nome;
        this.idioma = (idioma == null) ? "" : idioma;
        this.generos = (generos == null) ? new ArrayList<String>() : generos;
        this.notaGeral = notaGeral;
        this.estado = (estado == null) ? EstadoSerie.DESCONHECIDO : estado;
        this.dataEstreia = (dataEstreia == null) ? "" : dataEstreia;
        this.dataTermino = (dataTermino == null) ? "" : dataTermino;
        this.emissora = (emissora == null) ? "" : emissora;
    }

    // getters = ler os dados (so devolvem o valor)
    public int getId()               { return id; }
    public String getNome()          { return nome; }
    public String getIdioma()        { return idioma; }
    public List<String> getGeneros() { return generos; }
    public double getNotaGeral()     { return notaGeral; }
    public EstadoSerie getEstado()   { return estado; }
    public String getDataEstreia()   { return dataEstreia; }
    public String getDataTermino()   { return dataTermino; }
    public String getEmissora()      { return emissora; }

    // setters = mudar os dados
    public void setId(int id)                         { this.id = id; }
    public void setNome(String nome)                  { this.nome = nome; }
    public void setIdioma(String idioma)              { this.idioma = idioma; }
    public void setGeneros(List<String> generos)      { this.generos = generos; }
    public void setNotaGeral(double notaGeral)        { this.notaGeral = notaGeral; }
    public void setEstado(EstadoSerie estado)         { this.estado = estado; }
    public void setDataEstreia(String dataEstreia)    { this.dataEstreia = dataEstreia; }
    public void setDataTermino(String dataTermino)    { this.dataTermino = dataTermino; }
    public void setEmissora(String emissora)          { this.emissora = emissora; }

    // junta os generos num texto so. ex: "Drama, Crime, Suspense"
    public String getGenerosTexto() {
        if (generos == null || generos.isEmpty()) {
            return "Nao informado";
        }
        return String.join(", ", generos);
    }

    // texto curtinho que aparece na lista da tela
    @Override
    public String toString() {
        String nota = (notaGeral > 0) ? String.format("%.1f", notaGeral) : "sem nota";
        return nome + "  (nota: " + nota + ")";
    }

    // 2 series sao "iguais" se tem o mesmo id. e isso que segura a duplicata na lista
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Serie)) {
            return false;
        }
        Serie outra = (Serie) obj;
        return this.id == outra.id;
    }

    // anda junto com o equals (regra do java)
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
