public class Vendedor extends Pessoa {
    private String cpf;
    private double salario;
    private Endereco endereco;

    public Vendedor(String nome, int idade, String cpf, double salario, Endereco endereco) {
        super(nome, idade);
        this.cpf = cpf;
        this.salario = salario;
        this.endereco = endereco;
    }

    @Override
    public void apresentarse() {
        System.out.println("=== VENDEDOR ===");
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("CPF: " + cpf);
        endereco.apresentarLogradouro();
    }

    public String getCpf() { return cpf; }
    public double getSalario() { return salario; }
    public Endereco getEndereco() { return endereco; }
}