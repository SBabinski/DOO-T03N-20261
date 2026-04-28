import java.util.ArrayList;
import java.util.List;

public class Gerente extends Pessoa {
    String loja;
    double salarioBase;
    List<Double> salarioRecebido = new ArrayList<>();

    public Gerente(String nome, int idade, String loja, double salarioBase) {
        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.salarioBase = salarioBase;
        this.salarioRecebido.add(salarioBase);
        this.salarioRecebido.add(salarioBase * 1.05);
        this.salarioRecebido.add(salarioBase * 1.10);
    }

    @Override
    public void apresentarse() {
        System.out.println("Gerente: " + nome + " | Loja: " + loja);
    }

    public double calcularMedia() {
        double soma = 0;
        for (double s : salarioRecebido) soma += s;
        return soma / salarioRecebido.size();
    }

    public double calcularBonus() {
        return salarioBase * 0.35;
    }
}
