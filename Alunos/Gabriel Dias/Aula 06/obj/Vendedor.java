package obj;

public class Vendedor {

    private String nome;
    private int idade;
    private String loja;
    private String cidade;
    private String bairro;
    private String rua;
    private double salarioBase;
    private double[] salarioRecebido;

    public Vendedor(String nome, int idade, String loja, String cidade,
                    String bairro, String rua, double salarioBase) {
        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.salarioBase = salarioBase;
        this.salarioRecebido = new double[]{3200.00, 3750.50, 4100.00};
    }

    public void apresentarse() {
        System.out.println("=== Vendedor ===");
        System.out.println("Nome  : " + nome);
        System.out.println("Idade : " + idade + " anos");
        System.out.println("Loja  : " + loja);
    }

    public double calcularMedia() {
        double soma = 0;
        for (double salario : salarioRecebido) {
            soma += salario;
        }
        return soma / salarioRecebido.length;
    }

    public double calcularBonus() {
        return salarioBase * 0.2;
    }

    public String getNome() { return nome; }
    public int getIdade() { return idade; }
    public String getLoja() { return loja; }
    public String getCidade() { return cidade; }
    public String getBairro() { return bairro; }
    public String getRua() { return rua; }
    public double getSalarioBase() { return salarioBase; }
    public double[] getSalarioRecebido() { return salarioRecebido; }

    public void setNome(String nome) { this.nome = nome; }
    public void setIdade(int idade) { this.idade = idade; }
    public void setLoja(String loja) { this.loja = loja; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public void setRua(String rua) { this.rua = rua; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }
    public void setSalarioRecebido(double[] salarioRecebido) { this.salarioRecebido = salarioRecebido; }
}