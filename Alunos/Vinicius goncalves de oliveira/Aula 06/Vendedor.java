import java.util.ArrayList;

public class Vendedor {
    private String nome;
    private int idade;
    private Loja loja;
    private String cidade;
    private String bairro;
    private String rua;
    private double salarioBase;
    private ArrayList<Double> salarioRecebido;

    public Vendedor(String nome, int idade, Loja loja, String cidade, String bairro, String rua, double salarioBase) {
        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.salarioBase = salarioBase;
        this.salarioRecebido = new ArrayList<>();
    }

    public void adicionarSalarioRecebido(double salario) {
        salarioRecebido.add(salario);
    }

    public void apresentarse() {
        System.out.println("Vendedor: " + nome + ", idade: " + idade + ", loja: " + loja.getNomeLoja());
    }

    public double calcularMedia() {
        if (salarioRecebido.isEmpty()) {
            return 0;
        }

        double soma = 0;
        for (double salario : salarioRecebido) {
            soma += salario;
        }
        return soma / salarioRecebido.size();
    }

    public double calcularBonus() {

        return salarioBase * 0.2;
    }

    public String getNome() {

        return nome;
    }

    public int getIdade() {

        return idade;
    }

    public Loja getLoja() {

        return loja;
    }

    public String getCidade() {

        return cidade;
    }

    public String getBairro() {

        return bairro;
    }

    public String getRua() {

        return rua;
    }

    public double getSalarioBase() {

        return salarioBase;
    }

    public ArrayList<Double> getSalarioRecebido() {

        return salarioRecebido;
    }
}
