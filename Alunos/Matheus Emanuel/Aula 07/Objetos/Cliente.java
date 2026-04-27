package Objetos;

public class Cliente extends Pessoa {

    public Cliente(String nome, int idade, Endereco endereco) {
        super(nome, idade, endereco);
    }

    public Cliente(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void apresentarse() {
        System.out.print("Cliente: " + getNome() + " | Idade: " + getIdade());
        if (getEndereco() != null) {
            System.out.print(" | ");
            getEndereco().apresentarLogradouro();
        } else {
            System.out.println();
        }
    }
}
