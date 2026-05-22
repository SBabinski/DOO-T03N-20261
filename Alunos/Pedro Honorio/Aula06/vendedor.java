package AULA06;

public class Vendedor {
    public String nome;
    public int idade;
    public String loja;
    public String cidade;
    public String bairro;
    public String rua;
    public double salarioBase;
    public double[] salarioRecebido = {2500.0, 2800.0, 3200.0};

    public void apresentarse() {
        System.out.println("Nome: " + nome + " | Idade: " + idade + " | Loja: " + loja);
    }

    public void calcularMedia() {
        double soma = 0;
        for (double s : salarioRecebido) { soma += s; }
        System.out.println("Média salarial: " + (soma / salarioRecebido.length));
    }

    public void calcularBonus() {
        System.out.println("Bônus: " + (salarioBase * 0.2));
    }
}
