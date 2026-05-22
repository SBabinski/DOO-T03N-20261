package fag;

public class Vendedor extends Funcionario {

    public Vendedor(String nome, int idade, Endereco endereco,
                    Loja loja, double salarioBase) {
        super(nome, idade, endereco, loja, salarioBase);
    }

    @Override
    public void apresentarSe() {
        System.out.println("Vendedor: " + nome);
        System.out.println("Loja: " + loja.nomeFantasia);
    }

    @Override
    public double calcularBonus() {
        return salarioBase * 0.2;
    }
}