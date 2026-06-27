
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Essa classe representa as serie de TV.
// Ela so guarda informacoes e tem metodos simples para ler e alterar eles.
public class Serie {

    private int id;
    private String nome;
    private String idioma;
    private List<String> generos = new ArrayList<>();
    private Double nota;
    private String estado;
    private String dataEstreia;
    private String dataTermino;
    private String emissora;

    public Serie() {
        // construtor vazio, usado quando vamos preencher os dados depois
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

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public List<String> getGeneros() {
        return generos;
    }

    public void setGeneros(List<String> generos) {
        this.generos = generos;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDataEstreia() {
        return dataEstreia;
    }

    public void setDataEstreia(String dataEstreia) {
        this.dataEstreia = dataEstreia;
    }

    public String getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(String dataTermino) {
        this.dataTermino = dataTermino;
    }

    public String getEmissora() {
        return emissora;
    }

    public void setEmissora(String emissora) {
        this.emissora = emissora;
    }

    public String getGenerosTexto() {
        if (generos == null || generos.isEmpty()) {
            return "Nao informado";
        }
        return String.join(", ", generos);
    }

    public Map<String, Object> paraMapa() {
        Map<String, Object> mapa = new LinkedHashMap<>();
        mapa.put("id", id);
        mapa.put("nome", nome);
        mapa.put("idioma", idioma);
        mapa.put("generos", generos);
        mapa.put("nota", nota);
        mapa.put("estado", estado);
        mapa.put("dataEstreia", dataEstreia);
        mapa.put("dataTermino", dataTermino);
        mapa.put("emissora", emissora);
        return mapa;
    }

    @SuppressWarnings("unchecked")
    public static Serie deMapa(Map<String, Object> mapa) {
        Serie serie = new Serie();

        Object idLido = mapa.get("id");
        serie.setId(idLido != null ? ((Number) idLido).intValue() : 0);

        serie.setNome((String) mapa.get("nome"));
        serie.setIdioma((String) mapa.get("idioma"));

        Object generosLidos = mapa.get("generos");
        if (generosLidos instanceof List) {
            List<String> listaGeneros = new ArrayList<>();
            for (Object g : (List<Object>) generosLidos) {
                listaGeneros.add(String.valueOf(g));
            }
            serie.setGeneros(listaGeneros);
        }

        Object notaLida = mapa.get("nota");
        serie.setNota(notaLida != null ? ((Number) notaLida).doubleValue() : null);

        serie.setEstado((String) mapa.get("estado"));
        serie.setDataEstreia((String) mapa.get("dataEstreia"));
        serie.setDataTermino((String) mapa.get("dataTermino"));
        serie.setEmissora((String) mapa.get("emissora"));

        return serie;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Serie)) return false;
        Serie outra = (Serie) obj;
        return this.id == outra.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return nome + " [" + (estado != null ? estado : "?") + "]";
    }
}