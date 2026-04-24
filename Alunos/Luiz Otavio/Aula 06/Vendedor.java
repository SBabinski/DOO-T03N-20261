public class Vendedor {

    String nome;
    int idade;
    String loja;
    String cidade;
    String bairro;
    String rua;
    double salarioBase;

    double[] salarioRecebido = new double[3];

    public Vendedor(String nome, int idade, String loja, String cidade, String bairro, String rua, double salarioBase) {
        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.salarioBase = salarioBase;

        salarioRecebido[0] = 1500;
        salarioRecebido[1] = 1600;
        salarioRecebido[2] = 1700;
    }

    public void apresentarSe() {
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Loja: " + loja);

        System.out.println("Salários:");
        for (int i = 0; i < salarioRecebido.length; i++) {
            System.out.println("Salário " + (i + 1) + ": " + salarioRecebido[i]);
        }

        System.out.println("Bônus: " + calcularBonus());
    }

    public double calcularMedia() {
        double soma = 0;
        for (int i = 0; i < salarioRecebido.length; i++) {
            soma += salarioRecebido[i];
        }
        return soma / salarioRecebido.length;
    }

    public double calcularBonus() {
        return salarioBase * 0.2;
    }
}