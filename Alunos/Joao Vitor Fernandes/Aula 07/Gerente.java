public class Gerente extends Pessoa {
    private String loja;
    private double salarioBase;
    private double[] salarioRecebido;

    public Gerente(String nome, int idade, Endereco endereco, String loja, double salarioBase) {
        super(nome, idade, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;
        // Inicializar com 3 valores de exemplo
        this.salarioRecebido = new double[3];
        this.salarioRecebido[0] = salarioBase;
        this.salarioRecebido[1] = salarioBase;
        this.salarioRecebido[2] = salarioBase;
    }

    public Gerente(String nome, int idade, Endereco endereco, String loja, double salarioBase, double[] salarios) {
        super(nome, idade, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;
        this.salarioRecebido = salarios;
    }

    @Override
    public void apresentarse() {
        System.out.println("👔 Gerente: " + nome + ", " + idade + " anos | Loja: " + loja);
        if (endereco != null) {
            endereco.apresentarLogradouro();
        }
    }

    public double calcularMedia() {
        if (salarioRecebido.length == 0) return 0;
        double soma = 0;
        for (double salario : salarioRecebido) {
            soma += salario;
        }
        return soma / salarioRecebido.length;
    }

    public double calcularBonus() {
        return salarioBase * 0.35;
    }

    public void adicionarSalario(double salario) {
        double[] novosSalarios = new double[salarioRecebido.length + 1];
        System.arraycopy(salarioRecebido, 0, novosSalarios, 0, salarioRecebido.length);
        novosSalarios[salarioRecebido.length] = salario;
        salarioRecebido = novosSalarios;
    }

    public String getLoja() {
        return loja;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public double[] getSalarioRecebido() {
        return salarioRecebido;
    }
}
