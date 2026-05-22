public class Cliente extends Pessoa {
    private String cpf;
    private Endereco endereco;

    public Cliente(String nome, int idade, String cpf, Endereco endereco) {
        super(nome, idade);
        this.cpf = cpf;
        this.endereco = endereco;
    }

    @Override
    public void apresentarse() {
        System.out.println("=== CLIENTE ===");
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("CPF: " + cpf);
        endereco.apresentarLogradouro();
    }

    public String getCpf() { return cpf; }
    public Endereco getEndereco() { return endereco; }
}