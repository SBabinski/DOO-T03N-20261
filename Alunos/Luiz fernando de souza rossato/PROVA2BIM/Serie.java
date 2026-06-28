package tv;


import java.util.ArrayList;
import java.util.List;
public class Serie {
    
    private String nome;
    private List<String> genero;
    private String idioma;
    private  String DatadeEstréia;
    private String dataDeEnceramento;
    private double nota;
    private String nomeDaEmissora;
    private String Status;

    public Serie(){
        genero = new ArrayList<>();
    }

    public Serie(String nome, List<String> genero,
         String idioma, String DatadeEstréia, 
         String Estado, 
         String dataDeEnceramento, double nota, String nomeDaEmissora, String Status) {
        this.nome = nome;
        this.genero = genero;
        this.idioma = idioma;
        this.DatadeEstréia = DatadeEstréia;
        this.dataDeEnceramento = dataDeEnceramento;
        this.nota = nota;
        this.nomeDaEmissora = nomeDaEmissora;
        this.Status = Status;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getGenero() {
        return genero;
    }

    public void setGenero(List<String> genero) {
        this.genero = genero;
    }
    
    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    
    public String getDatadeEstréia() {
        return DatadeEstréia;
    }

    public void setDatadeEstréia(String DatadeEstréia) {
        this.DatadeEstréia = DatadeEstréia;
    }

    public String getDataDeEnceramento() {
        return dataDeEnceramento;
    }

    public void setDataDeEnceramento(String dataDeEnceramento) {
        this.dataDeEnceramento = dataDeEnceramento;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getNomeDaEmissora() {
        return nomeDaEmissora;
    }

    public void setNomeDaEmissora(String nomeDaEmissora) {
        this.nomeDaEmissora = nomeDaEmissora;
    }

    public String getStatus(){
        return Status;
    }

    public void setStatus(String Status){
        this.Status = Status;
    }

    @Override
    public String toString() {
    return getNome();
}
}
