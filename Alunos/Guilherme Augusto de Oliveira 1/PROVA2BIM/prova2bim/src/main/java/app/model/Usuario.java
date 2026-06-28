package app.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String nome;
    private List<Serie> favoritos;
    private List<Serie> assistidas;
    private List<Serie> desejoAssistir;

    public Usuario() {
        favoritos = new ArrayList<>();
        assistidas = new ArrayList<>();
        desejoAssistir = new ArrayList<>();
    }

    public Usuario(String nome) {
        this();
        this.nome = nome;
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

    public List<Serie> getAssistidas() {
        return assistidas;
    }

    public List<Serie> getDesejoAssistir() {
        return desejoAssistir;
    }

    public boolean adicionarFavorito(Serie serie) {
        if (!favoritos.contains(serie)) {
            return favoritos.add(serie);
        }
        return false;
    }

    public boolean removerFavorito(Serie serie) {
        return favoritos.remove(serie);
    }

    public boolean adicionarAssistida(Serie serie) {
        if (!assistidas.contains(serie)) {
            return assistidas.add(serie);
        }
        return false;
    }

    public boolean removerAssistida(Serie serie) {
        return assistidas.remove(serie);
    }

    public boolean adicionarDesejo(Serie serie) {
        if (!desejoAssistir.contains(serie)) {
            return desejoAssistir.add(serie);
        }
        return false;
    }

    public boolean removerDesejo(Serie serie) {
        return desejoAssistir.remove(serie);
    }
}