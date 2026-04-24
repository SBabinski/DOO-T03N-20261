public class Vendedor extends Funcionario {

    public Vendedor(String nome, int idade, String loja, Endereco endereco, double salarioBase) {
        super(nome, idade, loja, endereco, salarioBase);
        salarioRecebido[0] = 1500.00;
        salarioRecebido[1] = 1600.00;
        salarioRecebido[2] = 1700.00;
    }

    @Override
    public void apresentarSe() {
        System.out.println("Vendedor: " + nome);
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
        return salarioBase * 0.20;
    }
}
