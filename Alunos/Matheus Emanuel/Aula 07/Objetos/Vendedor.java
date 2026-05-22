package Objetos;

public class Vendedor extends Funcionario {

    public Vendedor(String nome, int idade, Endereco endereco, Loja loja, double salarioBase) {
        super(nome, idade, endereco, loja, salarioBase);
    }

    @Override
    public double calcularBonus() {
        double bonus = getSalarioBase() * 0.2;
        System.out.println("Bônus de " + getNome() + ": R$ " + bonus);
        return bonus;
    }

    @Override
    public void apresentarse() {
        System.out.println("Vendedor: " + getNome()
                + " | Idade: " + getIdade()
                + " | Loja: " + getLoja().getNomeFantasia());
    }
}
