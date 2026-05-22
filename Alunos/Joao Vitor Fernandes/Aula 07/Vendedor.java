public class Vendedor extends Pessoa {
    private String loja;
    private double salarioBase;
    private double[] comissoes;

    public Vendedor(String nome, int idade, Endereco endereco, String loja, double salarioBase) {
        super(nome, idade, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;
        this.comissoes = new double[0];
    }

    @Override
    public void apresentarse() {
        System.out.println("👨‍💼 Vendedor: " + nome + ", " + idade + " anos | Loja: " + loja);
        if (endereco != null) {
            endereco.apresentarLogradouro();
        }
    }

    public void adicionarComissao(double comissao) {
        double[] novasComissoes = new double[comissoes.length + 1];
        System.arraycopy(comissoes, 0, novasComissoes, 0, comissoes.length);
        novasComissoes[comissoes.length] = comissao;
        comissoes = novasComissoes;
    }

    public double calcularMedia() {
        if (comissoes.length == 0) return 0;
        double soma = 0;
        for (double comissao : comissoes) {
            soma += comissao;
        }
        return soma / comissoes.length;
    }

    public String getLoja() {
        return loja;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public double[] getComissoes() {
        return comissoes;
    }
}
