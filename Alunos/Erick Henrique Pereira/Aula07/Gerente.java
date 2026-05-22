
import java.util.List;

public non-sealed class Gerente extends Pessoa {

    private double salarioBase;
    private List<Double> salarioRecebido;
    private Loja loja;

    public Gerente(String name, int age, Endereco endereco, Loja loja, double salarioBase, List<Double> salarioRecebido) {
        super(name, age, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;
        this.salarioRecebido = salarioRecebido;
    }

    public String apresentarse() {
        return "Gerente: " + getName() + ", Idade: " + getAge() + ", Loja: " + loja.getNomeFantasia();
    }

    public double calcularMedia() {
        if (salarioRecebido == null || salarioRecebido.isEmpty()) {
            return 0.0;
        }
        double soma = 0.0;
        for (Double salario : salarioRecebido) {
            soma += salario;
        }
        return soma / salarioRecebido.size();
    }

    public double calcularBonus() {
        return salarioBase * 0.35;
    }


}
