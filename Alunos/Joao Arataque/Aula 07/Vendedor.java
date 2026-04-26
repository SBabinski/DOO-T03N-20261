import java.util.ArrayList;
import java.util.List;

public class Vendedor extends Pessoa{
    String loja;
    double salarioBase;
    List<Double> salarioRecebido = new ArrayList<>();

    public Vendedor(String nome, int idade, String loja, Endereco endereco, double salarioBase){
        super(nome, idade, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;
    }


    @Override
    public void apresentarSe() {
        System.out.println("Olá, meu nome é " + nome + ", tenho " + idade + " anos e trabalho na loja " + loja + ".");
    }

    public double calcularMedia() {
        double soma = 0;
        for (double s : salarioRecebido) {
            soma += s;
        }
        return salarioRecebido.isEmpty() ? 0 : soma / salarioRecebido.size();
    }

    public double calcularBonus() {
        return salarioBase * 0.2;
    }
}