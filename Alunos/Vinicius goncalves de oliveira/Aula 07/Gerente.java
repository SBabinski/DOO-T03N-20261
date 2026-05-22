import java.util.ArrayList;
import java.util.List;

public class Gerente extends Vendedor {

    public Gerente(String nome, int idade, Loja loja, Endereco endereco, double salarioBase) {
        this(nome, idade, loja, endereco, salarioBase, criarSalariosPadrao(salarioBase));
    }

    public Gerente(String nome, int idade, Loja loja, Endereco endereco, double salarioBase, List<Double> salariosRecebidos) {
        super(nome, idade, loja, endereco, salarioBase, salariosRecebidos);
    }

    private static List<Double> criarSalariosPadrao(double salarioBase) {
        ArrayList<Double> salarios = new ArrayList<>();
        salarios.add(salarioBase);
        salarios.add(salarioBase * 1.1);
        salarios.add(salarioBase * 0.9);
        return salarios;
    }

    @Override
    public void apresentarse() {
        System.out.println("Gerente: " + getNome() + ", idade: " + getIdade() + ", loja: " + getLoja().getNomeFantasia());
    }

    @Override
    public double calcularBonus() {
        return getSalarioBase() * 0.35;
    }
}
