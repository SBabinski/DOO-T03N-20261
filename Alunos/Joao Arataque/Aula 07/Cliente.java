public class Cliente extends Pessoa{

    public Cliente(String nome, int idade, Endereco endereco){
        super(nome, idade, endereco);
    }

    @Override
    public void apresentarSe() {
        System.out.println("Cliente: " + nome + " | Idade: "+ idade);
    }
}

