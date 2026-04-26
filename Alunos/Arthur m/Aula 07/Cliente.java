public class Cliente extends Pessoa {

    public Cliente(String nome, int idade, Endereco endereco) {
        super(nome, idade, endereco);
    }

    public void apresentar() {
        System.out.println("Cliente:");
        super.apresentar();
    }
}