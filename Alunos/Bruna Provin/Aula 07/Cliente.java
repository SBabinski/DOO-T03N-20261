import java.util.ArrayList;

public class Cliente extends Pessoa {

    public Cliente(String nome, int idade, Endereco endereco) {
        super(nome, idade, endereco);
    }

    @Override
    public void apresentarSe() {
        super.apresentarSe(); 
    }

    public static void consultarClientes(ArrayList<Cliente> clientes) {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        System.out.println("\n===== CLIENTES CADASTRADOS =====");
        int i = 1;
        for (Cliente cliente : clientes) {
            System.out.println("\n[Cliente " + i + "]");
            System.out.println("Nome: " + cliente.nome);
            System.out.println("Idade: " + cliente.idade);
            System.out.print("Endereço: ");
            cliente.endereco.apresentarLogradouro();
            i++;
        }
    }
}