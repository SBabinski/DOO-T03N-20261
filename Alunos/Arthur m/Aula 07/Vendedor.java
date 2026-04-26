public class Vendedor extends Pessoa {

    Loja loja;
    double salarioBase;
    double[] salarios = {2000, 2200, 2100};

    public Vendedor(String nome, int idade, Endereco endereco,
                    Loja loja, double salarioBase) {
        super(nome, idade, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;
    }

    public void apresentar() {
        System.out.println("Vendedor: " + nome + " - Loja: " + loja.nome);
    }

    public double calcularMedia() {
        double soma = 0;
        for (double s : salarios) soma += s;
        return soma / salarios.length;
    }

    public double calcularBonus() {
        return salarioBase * 0.2;
    }
}