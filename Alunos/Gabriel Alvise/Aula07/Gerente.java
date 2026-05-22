import java.util.ArrayList;

public class Gerente extends Funcionario {

    public Gerente(String nome, int idade, String Loja, Endereco endereco, Double salarioBase, ArrayList<Double> salarios) {
        super(nome, idade, Loja, endereco, salarioBase, salarios);
    }

    public void calcularBonus() {
        double bonus = getSalarioBase() * 0.35;
        double salarioBonus = getSalarioBase() + bonus;
        System.out.printf("Gerente: %s | Salário Base: %.2f | Bônus: %.2f| Salário Com Bônus: %.2f\n", getNome(), getSalarioBase(), bonus, salarioBonus);
    }
}
