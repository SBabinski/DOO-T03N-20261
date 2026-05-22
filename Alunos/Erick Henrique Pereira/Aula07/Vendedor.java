import java.util.Arrays;
import java.util.List;

public non-sealed class Vendedor extends Pessoa {
    private Loja loja;
    private double salarioBase;
    private List<Double> salarioRecebido;

    public Vendedor(String name, int age, Loja loja, Endereco endereco, double salarioBase, List<Double> salarioRecebido) {
        super(name, age, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;
        this.salarioRecebido = salarioRecebido;
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
        return salarioBase * 0.2;
    }

    public Loja getLoja() {
        return loja;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public List<Double> getSalarioRecebido() {
        return salarioRecebido;
    }

    public void setSalarioRecebido(List<Double> salarioRecebido) {
        this.salarioRecebido = salarioRecebido;
    }

    
    public String apresentarse() {
        return "Vendedor: " + getName() + ", Idade: " + getAge() + ", Loja: " + loja.getNomeFantasia();
    }
}
