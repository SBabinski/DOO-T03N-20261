import java.util.ArrayList;

public class Gerente extends Pessoa {
    String loja;
    double salarioBase;
    double[] salarioRecebido;

    public Gerente(String nome, int idade, Endereco endereco,
                   String loja, double salarioBase, double[] salarioRecebido) {
        super(nome, idade, endereco);
        this.loja            = loja;
        this.salarioBase     = salarioBase;
        this.salarioRecebido = salarioRecebido;
    }

    @Override
    public void apresentarSe() {
        super.apresentarSe();
        System.out.println("Loja: " + loja);
    }

    public double calcularMedia() {
        double soma = 0;
        for (double s : salarioRecebido) soma += s;
        return soma / salarioRecebido.length;
    }

    public double calcularBonus() {
        return salarioBase * 0.35;
    }

    public static void consultarGerentes(ArrayList<Gerente> gerentes) {
        if (gerentes.isEmpty()) {
            System.out.println("Nenhum gerente cadastrado.");
            return;
        }
        System.out.println("\n===== GERENTES CADASTRADOS =====");
        int i = 1;
        for (Gerente gerente : gerentes) {
            System.out.println("\n[Gerente " + i + "]");
            System.out.println("Nome: " + gerente.nome);
            System.out.println("Idade: " + gerente.idade);
            System.out.println("Loja: " + gerente.loja);
            System.out.println("Salário Base: R$ " + String.format("%.2f", gerente.salarioBase));
            System.out.println("Média Salarial: R$ " + String.format("%.2f", gerente.calcularMedia()));
            System.out.println("Bônus (35%): R$ " + String.format("%.2f", gerente.calcularBonus()));
            System.out.print("Endereço: ");
            gerente.endereco.apresentarLogradouro();
            i++;
        }
    }
}