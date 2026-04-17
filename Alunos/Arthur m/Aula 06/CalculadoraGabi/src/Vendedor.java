import java.util.ArrayList;

public class Vendedor {

    String nome;
    int idade;
    String loja;
    String cidade;
    String bairro;
    String rua;
    double salarioBase;
    ArrayList<Double> salariosRecebidos = new ArrayList<>();

    public Vendedor(String nome, int idade, String loja, String cidade,
                     String bairro, String rua, double salarioBase) {

        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.salarioBase = salarioBase;
    }

    public void adicionarSalario(double valor) {
        salariosRecebidos.add(valor);
    }

    public void apresentarSe() {
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Loja: " + loja);
    }

    public double calcularMedia() {
        double soma = 0;

        for (double s : salariosRecebidos) {
            soma += s;
        }

        return soma / salariosRecebidos.size();
    }

    public double calcularBonus() {
        return salarioBase * 0.2;
    }
}