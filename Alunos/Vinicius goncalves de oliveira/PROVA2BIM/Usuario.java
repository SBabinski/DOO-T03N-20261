package com.seriestv;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Representa o usuário do sistema.
 * Guarda o nome e as 3 listas de séries.
 * Essa classe é salva/carregada do arquivo JSON.
 */
public class Usuario {

    private String nome;
    private List<Serie> favoritos = new ArrayList<>();
    private List<Serie> jaAssistidas = new ArrayList<>();
    private List<Serie> queroAssistir = new ArrayList<>();

    public Usuario(String nome) {

        this.nome = nome;
    }

    // Getter/Setter

    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {

        this.nome = nome;
    }

    // Favoritos

    public List<Serie> getFavoritos() {

       return favoritos;
    }

    public void adicionarFavorito(Serie serie) {
        if (!contemSerie(favoritos, serie)) {
            favoritos.add(serie);
        }
    }

    public void removerFavorito(Serie serie) {

        favoritos.removeIf(s -> s.getId() == serie.getId());
    }

    // ja Assistidas

    public List<Serie> getJaAssistidas() {

        return jaAssistidas;
    }

    public void adicionarJaAssistida(Serie serie) {
        if (!contemSerie(jaAssistidas, serie)) {
            jaAssistidas.add(serie);
        }
    }

    public void removerJaAssistida(Serie serie) {

        jaAssistidas.removeIf(s -> s.getId() == serie.getId());
    }

    // Quero Assistir

    public List<Serie> getQueroAssistir() {

        return queroAssistir;
    }

    public void adicionarQueroAssistir(Serie serie) {
        if (!contemSerie(queroAssistir, serie)) {
            queroAssistir.add(serie);
        }
    }

    public void removerQueroAssistir(Serie serie) {

        queroAssistir.removeIf(s -> s.getId() == serie.getId());
    }

    // Ordenação

    public void ordenarPorNome(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getName, String.CASE_INSENSITIVE_ORDER));
    }

    public void ordenarPorNota(List<Serie> lista) {
        lista.sort(Comparator.comparingDouble(Serie::getRating).reversed());
    }

    public void ordenarPorStatus(List<Serie> lista) {

        lista.sort(Comparator.comparing(Serie::getStatus));
    }

    public void ordenarPorEstreia(List<Serie> lista) {

        lista.sort(Comparator.comparing(Serie::getPremiered));
    }

    // Utilitário

    private boolean contemSerie(List<Serie> lista, Serie serie) {
        return lista.stream().anyMatch(s -> s.getId() == serie.getId());
    }
}
