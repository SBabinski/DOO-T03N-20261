public class Cliente extends Pessoa {
    public Cliente(String nome, int idade, Endereco endereco) {
        super(nome, idade, endereco);
    }

    public Cliente(String nome, int idade, String cidade, String bairro, String rua) {
        super(nome, idade, new Endereco("PR", cidade, bairro, "S/N", rua));
    }

    public void apresentarse() {
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Endereco: " + endereco.formatarLogradouro());
    }
}
