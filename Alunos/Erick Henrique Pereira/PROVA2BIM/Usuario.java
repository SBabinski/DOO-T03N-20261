package objetos;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private List<Serie> favoritos;
    private List<Serie> assistidos;
    private List<Serie> assistir;

    // Construtor sem argumentos para o Jackson e criação inicial
    public Usuario() {
        this.favoritos = new ArrayList<>();
        this.assistidos = new ArrayList<>();
        this.assistir = new ArrayList<>();
    }

    //construtor se for primeiro acesso sem o nome
    public Usuario(List<Serie> favoritos, List<Serie> assistidos, List<Serie> assistir) {
        this.favoritos = favoritos;
        this.assistidos = assistidos;
        this.assistir = assistir;
    }

    //construtor se já tiver usuário
    public Usuario(String nome, List<Serie> favoritos, List<Serie> assistidos, List<Serie> assistir) {
        this.nome = nome;
        this.favoritos = favoritos;
        this.assistidos = assistidos;
        this.assistir = assistir;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Serie> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Serie> favoritos) {
        this.favoritos = favoritos;
    }

    public List<Serie> getAssistidos() {
        return assistidos;
    }

    public void setAssistidos(List<Serie> assistidos) {
        this.assistidos = assistidos;
    }

    public List<Serie> getAssistir() {
        return assistir;
    }

    public void setAssistir(List<Serie> assistir) {
        this.assistir = assistir;
    }
    

    public void adicionarFavorito(Serie serie){
        if(!favoritos.contains(serie)){
            favoritos.add(serie);
        }
    }
    public void removerFavorito(Serie serie){
        favoritos.remove(serie);
    }
    
    public void adicionarAssistidos(Serie serie){
        if(!assistidos.contains(serie)){
            assistidos.add(serie);
        }
    }
    public void removerAssistidos(Serie serie){
        assistidos.remove(serie);
    }

    public void adicionarAssistir(Serie serie){
        if(!assistir.contains(serie)){
            assistir.add(serie);
        }
    }
    public void removerAssistir(Serie serie){
        assistir.remove(serie);
    }

    
}
