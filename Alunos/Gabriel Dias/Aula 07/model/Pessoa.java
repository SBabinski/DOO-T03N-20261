package myplant.model;

/**
 * Classe abstrata base para todas as pessoas do sistema.
 * Aplica herança para Vendedor, Gerente e Cliente.
 */
public abstract class Pessoa {

    protected String nome;
    protected int idade;
    protected Endereco endereco;

    public Pessoa(String nome, int idade, Endereco endereco) {
        this.nome = nome;
        this.idade = idade;
        this.endereco = endereco;
    }

    public abstract void apresentarse();

    // Getters
    public String getNome()       { return nome; }
    public int getIdade()         { return idade; }
    public Endereco getEndereco() { return endereco; }
}
