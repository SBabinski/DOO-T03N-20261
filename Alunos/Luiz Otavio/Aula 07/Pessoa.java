public abstract class Pessoa {
    protected String nome;
    protected int idade;

    public Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public abstract void apresentarSe();

    public String getNome() { return nome; }
    public int getIdade() { return idade; }
}
