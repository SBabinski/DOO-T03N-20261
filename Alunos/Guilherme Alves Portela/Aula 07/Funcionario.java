import java.util.ArrayList;

public abstract class Funcionario {
    protected String nome;
    protected int idade;
    protected Loja loja;
    protected Endereco endereco;
    protected double salarioBase;
    protected ArrayList<Double> salariosRecebidos;

    public Funcionario(String nome, int idade, Loja loja, Endereco endereco, double salarioBase) {
        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.endereco = endereco;
        this.salarioBase = salarioBase;
        this.salariosRecebidos = new ArrayList<Double>();
    }

    // Getters
    public String getNome() {
        return this.nome;
    }

    public int getIdade() {
        return this.idade;
    }

    public Loja getLoja() {
        return this.loja;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public double getSalarioBase() {
        return this.salarioBase;
    }

    public ArrayList<Double> getSalariosRecebidos() {
        return this.salariosRecebidos;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setLoja(Loja novaLoja) {
        this.loja = novaLoja;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    // Método para adicionar salário recebido
    public void pagarSalario(double salarioRecebido) {
        this.salariosRecebidos.add(salarioRecebido);
    }

    // Método abstrato para calcular bônus
    public abstract double calcularBonus();

    // Método para calcular média de salários
    public double calcularMedia() {
        if (salariosRecebidos == null || salariosRecebidos.isEmpty()) {
            return 0.0;
        }

        double somaSalarios = 0.0;
        for (var salario : salariosRecebidos) {
            somaSalarios += salario;
        }

        double mediaSalarial = somaSalarios / salariosRecebidos.size();

        return mediaSalarial;
    }

    // Método para apresentação
    public void apresentarSe() {
        System.out.println(String.format("Nome: %s | Idade: %d | Loja: %s", this.nome, this.idade,
                (this.loja != null ? this.loja.getNomeFantasia() : "Nenhuma")));
    }
}
