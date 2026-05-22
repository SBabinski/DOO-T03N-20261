import java.util.ArrayList;

public class Funcionario {
    private String nome;
    private int idade;
    private String Loja;
    private Endereco endereco;
    private double salarioBase;
    private ArrayList<Double> salarioRecebido;

    public Funcionario(String nome, int idade, String Loja, Endereco endereco, double salarioBase, ArrayList<Double> salarios) {
        this.nome = nome;
        this.idade = idade;
        this.Loja = Loja;
        this.endereco = endereco;
        this.salarioBase = salarioBase;
        this.salarioRecebido = salarios;
    }

    public void apresentarSe() {
        System.out.printf("Nome: %s | Idade: %d | Loja: %s\n", nome, idade, Loja);
    }

    public void calcularMedia() {

        double totalSalarios = 0;
        for (Double salario:salarioRecebido) {
            totalSalarios+=salario;
        }

        int qtdSalarios = salarioRecebido.size();
        double mediaSalarios = totalSalarios / qtdSalarios;

        System.out.printf("Média de salários do colaborador %s: R$ %.2f\n", nome, mediaSalarios);
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Double> getSalarioRecebido() {
        return salarioRecebido;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public String getLoja() {
        return Loja;
    }

    public int getIdade() {
        return idade;
    }
}
