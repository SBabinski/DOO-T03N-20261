import java.util.ArrayList;
import java.util.List;

public class Vendedor{
    String nome;
    int idade;
    String loja;
    String cidade;
    String bairro;
    String rua;
    double salarioBase;

    List<Double> salarioRecebido = new ArrayList<>();

    public void apresentarse() {
        System.out.println("Olá, meu nome é " + nome + ", tenho " + idade + " anos e trabalho na loja " + loja + ".");
    }

    public double calcularMedia() {
        double soma = 0;
        for (double salario : salarioRecebido) {
            soma += salario;
        }
        return salarioRecebido.isEmpty() ? 0 : soma / salarioRecebido.size();
    }

    public double calcularBonus() {
        return salarioBase * 0.2;
    }
}