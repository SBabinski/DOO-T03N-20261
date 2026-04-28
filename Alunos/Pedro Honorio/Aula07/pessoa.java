public abstract class Pessoa {
    String nome;
    int idade;
    Endereco endereco;

    public void apresentarse() {
        System.out.println("Nome: " + nome + " | Idade: " + idade);
    }
}
