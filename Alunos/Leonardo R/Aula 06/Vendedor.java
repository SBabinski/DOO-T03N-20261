import java.util.ArrayList;
import java.util.List;

public class Vendedor {

    private String nome;
    private String idade;
    private String loja;
    private String cidade;
    private String bairro;
    private String rua;
    private double salarioBase;

    private List<Double> salariosRecebidos = new ArrayList<>();

    public Vendedor(String nome, String idade, String loja,
            String cidade, String bairro, String rua,
            double salarioBase) {

        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.salarioBase = salarioBase;

        salariosRecebidos.add(salarioBase);
        salariosRecebidos.add(salarioBase + 200);
        salariosRecebidos.add(salarioBase + 150);
    }

    public String getNome() {
        return nome;
    }

    public String getIdade() {
        return idade;
    }

    public String getCidade() {
        return cidade;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void apresentar() {
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Loja: " + loja);
        System.out.println("Salário Base: " + salarioBase);
        System.out.println();
    }
}