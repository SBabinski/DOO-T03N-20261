package myplant.model;

public class Vendedor extends Funcionario {

    public Vendedor(String nome, int idade, Endereco endereco, Loja loja,
                    double salarioBase, double[] salarioRecebido) {
        super(nome, idade, endereco, loja, salarioBase, salarioRecebido);
    }

    @Override
    public void apresentarse() {
        System.out.println("👤 Vendedor: " + nome + " | Idade: " + idade +
                " | Loja: " + (loja != null ? loja.getNomeFantasia() : "N/A"));
    }

    /** Bônus = salarioBase * 0.20 */
    @Override
    public double calcularBonus() {
        return salarioBase * 0.20;
    }
}
