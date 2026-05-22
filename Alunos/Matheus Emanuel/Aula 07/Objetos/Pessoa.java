package Objetos;

public abstract class Pessoa {

    private String nome;
    private int idade;
    private Endereco endereco;

    public Pessoa(String nome, int idade, Endereco endereco) {
        this.nome = nome;
        this.idade = idade;
        this.endereco = endereco;
    }

    public Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome() { return nome; }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            System.out.println("O nome não pode ser vazio.");
            return;
        }
        this.nome = nome;
    }

    public int getIdade() { return idade; }

    public void setIdade(int idade) {
        if (idade < 0) {
            System.out.println("A idade não pode ser negativa.");
            return;
        }
        this.idade = idade;
    }

    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }

    public abstract void apresentarse();
}
