import java.util.ArrayList;
import java.util.List;

public class Gerente extends Pessoa {

    private double salarioBase;
    private List<Double> salarioRecebido = new ArrayList<>();

    public Gerente(String nome, String idade, String loja,
            Endereco endereco,
            double salarioBase) {

        super(nome, idade, loja, endereco);

        this.salarioBase = salarioBase;

        salarioRecebido.add(salarioBase);
        salarioRecebido.add(salarioBase + 500);
        salarioRecebido.add(salarioBase + 300);
    }

    public void apresentarse() {
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Loja: " + loja);
    }

    public double calcularMedia() {
        double soma = 0;

        for (double s : salarioRecebido) {
            soma += s;
        }

        return soma / salarioRecebido.size();
    }

    public double calcularBonus() {
        return salarioBase * 0.35;
    }
}
