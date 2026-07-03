package model;

import java.util.ArrayList;

public class Usuario {

    private String nome;

    private ArrayList<Serie> favoritos =
            new ArrayList<>();

    private ArrayList<Serie> assistidas =
            new ArrayList<>();

    private ArrayList<Serie> desejaAssistir =
            new ArrayList<>();

    public Usuario(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return nome;
    }

    public ArrayList<Serie> getFavoritos(){
        return favoritos;
    }

    public ArrayList<Serie> getAssistidas(){
        return assistidas;
    }

    public ArrayList<Serie> getDesejaAssistir(){
        return desejaAssistir;
    }
}