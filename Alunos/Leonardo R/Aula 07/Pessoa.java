public class Pessoa {

    protected String nome;
    protected String idade;
    protected String loja;
    protected Endereco endereco;

    public Pessoa(String nome, String idade, String loja, Endereco endereco) {
        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public String getIdade() {
        return idade;
    }

    public Endereco getEndereco() {
        return endereco;
    }
}