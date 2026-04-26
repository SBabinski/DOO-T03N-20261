public class Gerente extends Funcionario {

    public Gerente(String nome, int idade, Endereco endereco,
                   Loja loja, double salarioBase) {
        super(nome, idade, endereco, loja, salarioBase);
    }

    @Override
    public void apresentarSe() {
        System.out.println("Gerente: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Loja: " + loja.nomeFantasia);
    }

    @Override
    public double calcularBonus() {
        return salarioBase * 0.35;
    }
}