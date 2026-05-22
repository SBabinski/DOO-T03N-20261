import java.util.ArrayList;

public class Vendedor extends Funcionario {

    public Vendedor(String nome, int idade, String Loja, Endereco endereco, double salarioBase, ArrayList<Double> salarios) {
        super(nome, idade, Loja, endereco, salarioBase, salarios);
    }

    public void calcularBonus() {
        double bonus = getSalarioBase() * 0.2;
        double salarioBonus = getSalarioBase() + bonus;
        System.out.printf("Vendedor: %s | Salário Base: %.2f | Bônus: %.2f| Salário Com Bônus: %.2f\n", getNome(), getSalarioBase(), bonus, salarioBonus);

    }
}
