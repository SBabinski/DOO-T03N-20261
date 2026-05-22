public class Cliente extends Pessoa {

    public Cliente() {
    }

    // Construtor completo
    public Cliente(String nome, int idade, Endereco endereco) {
        super(nome, idade, endereco);
    }

    // Método para exibir detalhes do cliente
    public void mostrarCliente() {
        System.out.printf("Cliente: %s | Idade: %d | Endereço: %s%n",
                getNome(), getIdade(), getEndereco());
    }
}
