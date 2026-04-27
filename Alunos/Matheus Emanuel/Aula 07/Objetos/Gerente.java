package Objetos;

public class Gerente extends Funcionario {

    public Gerente(String nome, int idade, Endereco endereco, Loja loja, double salarioBase) {
        super(nome, idade, endereco, loja, salarioBase);
    }

    @Override
    public double calcularBonus() {
        double bonus = getSalarioBase() * 0.35;
        System.out.println("Bônus de " + getNome() + ": R$ " + bonus);
        return bonus;
    }

    @Override
    public void apresentarse() {
        System.out.println("Gerente: " + getNome()
                + " | Idade: " + getIdade()
                + " | Loja: " + getLoja().getNomeFantasia());
    }
}
