public class Gerente extends Pessoa {
    private String loja;
    private Endereco endereco;
    private double salarioBase;
    private double[] salarioRecebido;

    public Gerente(String nome, int idade, String loja, Endereco endereco, double salarioBase) {
        super(nome, idade);
        this.loja = loja;
        this.endereco = endereco;
        this.salarioBase = salarioBase;
        // Três lançamentos de salário fixos
        this.salarioRecebido = new double[]{salarioBase, salarioBase * 1.1, salarioBase * 0.95};
    }

    @Override
    public void apresentarse() {
        System.out.println("=== GERENTE ===");
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Loja: " + loja);
    }

    public double calcularMedia() {
        double soma = 0;
        for (double s : salarioRecebido) {
            soma += s;
        }
        return soma / salarioRecebido.length;
    }

    public double calcularBonus() {
        return salarioBase * 0.35;
    }

    public String getLoja() { return loja; }
    public double getSalarioBase() { return salarioBase; }
    public Endereco getEndereco() { return endereco; }
}