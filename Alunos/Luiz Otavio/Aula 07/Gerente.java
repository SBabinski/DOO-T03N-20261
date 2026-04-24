public class Gerente extends Funcionario {

    public Gerente(String nome, int idade, String loja, Endereco endereco, double salarioBase) {
        super(nome, idade, loja, endereco, salarioBase);
        salarioRecebido[0] = 4200.00;
        salarioRecebido[1] = 4500.00;
        salarioRecebido[2] = 4800.00;
    }

    @Override
    public void apresentarSe() {
        System.out.println("Gerente: " + nome);
        System.out.println("   Idade: " + idade);
        System.out.println("   Loja: " + loja);
        endereco.apresentarLogradouro();
        exibirSalarios();
        System.out.println("-------------------");
    }

    @Override
    public double calcularMedia() {
        double soma = 0;
        for (double s : salarioRecebido) soma += s;
        return soma / salarioRecebido.length;
    }

    @Override
    public double calcularBonus() {
        return salarioBase * 0.35;
    }
}
