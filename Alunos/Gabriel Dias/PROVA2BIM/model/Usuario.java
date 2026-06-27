package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa o usuário do sistema com suas listas de séries.
 */
public class Usuario {

    private String nomeOuApelido;
    private List<Serie> favoritos;
    private List<Serie> jaAssistidas;
    private List<Serie> desejaAssistir;

    /**
     * Cria um novo usuário com as listas vazias.
     *
     * @param nomeOuApelido nome ou apelido do usuário
     */
    public Usuario(String nomeOuApelido) {
        this.nomeOuApelido = nomeOuApelido;
        this.favoritos = new ArrayList<>();
        this.jaAssistidas = new ArrayList<>();
        this.desejaAssistir = new ArrayList<>();
    }

    public String obterNomeOuApelido() {
        return nomeOuApelido;
    }

    public void definirNomeOuApelido(String nomeOuApelido) {
        this.nomeOuApelido = nomeOuApelido;
    }

    public List<Serie> obterFavoritos() {
        return favoritos;
    }

    public List<Serie> obterJaAssistidas() {
        return jaAssistidas;
    }

    public List<Serie> obterDesejaAssistir() {
        return desejaAssistir;
    }
}
