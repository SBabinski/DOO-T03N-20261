import java.util.ArrayList;

public class Vendedor {
    private String nome;
    private int idade;
    private String loja;
    private String cidade;
    private String bairro;
    private String rua;
    private double salarioBase;
    private ArrayList<Double> salarioRecebido;

    public Vendedor(String nome, int idade, String loja, String cidade, String bairro, String rua,
                    double salarioBase, ArrayList<Double> salarios) {
        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.salarioBase = salarioBase;
        this.salarioRecebido = salarios;
    }

    public void apresentarse() {
        System.out.printf("Vendedor: %s | Idade: %d | Loja: %s\n", nome, idade, loja);
    }

    public void calcularMedia() {
        double total = 0;
        for (Double s : salarioRecebido) total += s;
        double media = total / salarioRecebido.size();
        System.out.printf("Média de salários de %s: R$ %.2f\n", nome, media);
    }

    public void calcularBonus() {
        double bonus = salarioBase * 0.2;
        double comBonus = salarioBase + bonus;
        System.out.printf("Vendedor: %s | Salário Base: R$ %.2f | Bônus: R$ %.2f | Com Bônus: R$ %.2f\n",
                nome, salarioBase, bonus, comBonus);
    }

    public String getNome()
    { return nome; }
    public void setNome(String nome)
    { this.nome = nome; }
    public int getIdade()
    { return idade; }
    public void setIdade(int idade)
    { this.idade = idade; }
    public String getLoja()
    { return loja; }
    public void setLoja(String loja)
    { this.loja = loja; }
    public String getCidade()
    { return cidade; }
    public void setCidade(String cidade)
    { this.cidade = cidade; }
    public String getBairro()
    { return bairro; }
    public void setBairro(String bairro)
    { this.bairro = bairro; }
    public String getRua()
    { return rua; }
    public void setRua(String rua)
    { this.rua = rua; }
    public double getSalarioBase()
    { return salarioBase; }
    public void setSalarioBase(double salarioBase)
    { this.salarioBase = salarioBase; }
    public ArrayList<Double> getSalarioRecebido()
    { return salarioRecebido; }
    public void setSalarioRecebido(ArrayList<Double> salarioRecebido)
    { this.salarioRecebido = salarioRecebido; }
}