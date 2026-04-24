
import java.util.Arrays;
import java.util.List;

public non-sealed class Vendedor extends Pessoa {
    private Loja loja;
    private double salarioBase;
    private List<Double> salarioRecebido;

    public Vendedor(String name, int age, Loja loja, Endereco endereco, double salarioBase) {
        super(name, age, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;
        this.salarioRecebido = Arrays.asList(1500.0, 1600.0, 1700.0);
    }

    public void apresentarse() {
        System.out.println(this.toString());
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vendedor{");
        sb.append("name=").append(getName());
        sb.append(", age=").append(getAge());
        sb.append(", loja=").append(loja);
        sb.append('}');
        return sb.toString();
    }
}
