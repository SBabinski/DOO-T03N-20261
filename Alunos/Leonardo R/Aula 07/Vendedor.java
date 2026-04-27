import java.util.ArrayList;
import java.util.List;

public class Vendedor extends Pessoa {

    private double salarioBase;
    private List<Double> salariosRecebidos = new ArrayList<>();

    public Vendedor(String nome, String idade, String loja,
            Endereco endereco,
            double salarioBase) {

        super(nome, idade, loja, endereco);
        this.salarioBase = salarioBase;

        salariosRecebidos.add(salarioBase);
        salariosRecebidos.add(salarioBase + 200);
        salariosRecebidos.add(salarioBase + 150);
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void apresentar() {
        System.out.println(nome);
        System.out.println(idade);
        System.out.println(loja);
    }
}