package entities;

public class Cliente {
    private static int contId = 1;
    private int id;
    private String nome;
    private int idade;
    private Endereco endereco;
    
    // Construtor completo
    public Cliente(String nome, int idade, String estado, String cidade,
                   String bairro, String rua, int numero, String complemento) {
        this.id = contId++;
        this.nome = nome;
        this.idade = idade;
        this.endereco = new Endereco(estado, cidade, bairro, rua, numero, complemento);
    }
    
    // Construtor simplificado (sem estado, numero e complemento)
    public Cliente(String nome, int idade, String cidade, String bairro, String rua) {
        this.id = contId++;
        this.nome = nome;
        this.idade = idade;
        this.endereco = new Endereco("N/I", cidade, bairro, rua, 0, "N/I");
    }
    
    // Retorna nome, idade e endereço do cliente
    public String apresentarse() {
        return String.format(id + "  |  " + nome + "  |  " +
                idade + " anos");
    }
}
