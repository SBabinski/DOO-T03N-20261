public class Gerente extends Vendedor {

    double[] salariosGerente = {5000, 5200, 5100};

    public Gerente(String nome, int idade, Endereco endereco,
                   Loja loja, double salarioBase) {
        super(nome, idade, endereco, loja, salarioBase);
    }

    public void apresentar() {
        System.out.println("Gerente: " + nome + " - Loja: " + loja.nome);
    }

    public double calcularMedia() {
        double soma = 0;
        for (double s : salariosGerente) soma += s;
        return soma / salariosGerente.length;
    }

    public double calcularBonus() {
        return salarioBase * 0.35;
    }
}