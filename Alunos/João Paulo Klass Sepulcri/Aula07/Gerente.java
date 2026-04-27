public class Gerente extends Pessoa {
    Loja loja;
    double salarioBase;
    double[] salarioRecebido;

    public Gerente(String nome, int idade, Endereco endereco, Loja loja, double salarioBase, double[] salarioRecebido) {
        super(nome, idade, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;
        this.salarioRecebido = garantirTresSalarios(salarioRecebido, salarioBase);
    }

    private double[] garantirTresSalarios(double[] salarios, double salarioBase) {
        if (salarios == null || salarios.length < 3) {
            return new double[]{salarioBase, salarioBase, salarioBase};
        }
        return salarios;
    }

    public void apresentarse() {
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Loja: " + loja.nomeFantasia);
        System.out.println("Endereco: " + endereco.formatarLogradouro());
    }

    public double calcularMedia() {
        double soma = 0;

        for (int i = 0; i < salarioRecebido.length; i++) {
            soma = soma + salarioRecebido[i];
        }

        return soma / salarioRecebido.length;
    }

    public double calcularBonus() {
        return salarioBase * 0.35;
    }
}
