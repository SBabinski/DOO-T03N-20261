public class Gerente extends Pessoa {

    private String loja;
    private double salarioBase;
    private double[] salarioRecebido;

    // Construtor completo
    public Gerente(String nome, int idade, String loja,
            double[] salarioRecebido, double salarioBase, Endereco endereco) {
        super(nome, idade, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;
        this.salarioRecebido = salarioRecebido;
    }

    // Método para exibir detalhes do gerente
    public void mostrarGerente() {
        System.out.println("Gerente: " + getNome() + " | Idade: " + getIdade()
                + " | Loja: " + loja + " | Endereço: " + getEndereco());
    }

    // Cálculo de média salarial e bônus
    public double calcularMedia() {
        double soma = 0;
        for (double s : salarioRecebido)
            soma += s;
        return soma / salarioRecebido.length;
    }

    public double calcularBonus() {
        return salarioBase * 0.35;
    }
}
