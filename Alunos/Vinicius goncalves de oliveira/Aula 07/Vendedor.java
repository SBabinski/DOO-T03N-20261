import java.util.ArrayList;
import java.util.List;

public class Vendedor extends Pessoa {
    private Loja loja;
    private double salarioBase;
    private ArrayList<Double> salarioRecebido;

    public Vendedor(String nome, int idade, Loja loja, Endereco endereco, double salarioBase) {
        this(nome, idade, loja, endereco, salarioBase, criarSalariosPadrao(salarioBase));
    }

    public Vendedor(String nome, int idade, Loja loja, Endereco endereco, double salarioBase, List<Double> salariosRecebidos) {
        super(nome, idade, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;
        this.salarioRecebido = new ArrayList<>(salariosRecebidos);
    }

    private static List<Double> criarSalariosPadrao(double salarioBase) {
        ArrayList<Double> salarios = new ArrayList<>();
        salarios.add(salarioBase);
        salarios.add(salarioBase * 1.05);
        salarios.add(salarioBase * 0.95);
        return salarios;
    }

    @Override
    public void apresentarse() {
        System.out.println("Vendedor: " + getNome() + ", idade: " + getIdade() + ", loja: " + getLoja().getNomeFantasia());
    }

    public double calcularMedia() {
        if (salarioRecebido.isEmpty()) {
            return 0;
        }

        double soma = 0;
        for (double salario : salarioRecebido) {
            soma += salario;
        }
        return soma / salarioRecebido.size();
    }

    public double calcularBonus() {
        return getSalarioBase() * 0.2;
    }

    public Loja getLoja() {
        return loja;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public List<Double> getSalarioRecebido() {
        return salarioRecebido;
    }
}
