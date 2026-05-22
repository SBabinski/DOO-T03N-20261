public abstract class Funcionario extends Pessoa {
    protected String loja;
    protected Endereco endereco;
    protected double salarioBase;
    protected double[] salarioRecebido;

    public Funcionario(String nome, int idade, String loja, Endereco endereco, double salarioBase) {
        super(nome, idade);
        this.loja = loja;
        this.endereco = endereco;
        this.salarioBase = salarioBase;
        this.salarioRecebido = new double[3];
    }

    public abstract double calcularMedia();
    public abstract double calcularBonus();

    protected void exibirSalarios() {
        System.out.println("   Salários recebidos:");
        for (int i = 0; i < salarioRecebido.length; i++) {
            System.out.printf("   Salário %d: R$ %.2f%n", (i + 1), salarioRecebido[i]);
        }
        System.out.printf("   Média: R$ %.2f%n", calcularMedia());
        System.out.printf("   Bônus (sobre salário base): R$ %.2f%n", calcularBonus());
    }

    public String getLoja() { return loja; }
    public void setLoja(String loja) { this.loja = loja; }
    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }
    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }
    public double[] getSalarioRecebido() { return salarioRecebido; }
    public void setSalarioRecebido(double[] salarioRecebido) { this.salarioRecebido = salarioRecebido; }
}