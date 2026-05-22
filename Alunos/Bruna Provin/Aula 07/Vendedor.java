import java.util.ArrayList;

public class Vendedor extends Pessoa {
    String loja;
    double salarioBase;
    double[] salarioRecebido;

    public Vendedor(String nome, int idade, Endereco endereco,
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
        return salarioBase * 0.20;
    }

    public static void consultarVendedores(ArrayList<Vendedor> vendedores) {
        if (vendedores.isEmpty()) {
            System.out.println("Nenhum vendedor cadastrado.");
            return;
        }
        System.out.println("\n===== VENDEDORES CADASTRADOS =====");
        int i = 1;
        for (Vendedor vendedor : vendedores) {
            System.out.println("\n[Vendedor " + i + "]");
            System.out.println("Nome: " + vendedor.nome);
            System.out.println("Idade: " + vendedor.idade);
            System.out.println("Loja: " + vendedor.loja);
            System.out.println("Salário Base: R$ " + String.format("%.2f", vendedor.salarioBase));
            System.out.println("Média Salarial: R$ " + String.format("%.2f", vendedor.calcularMedia()));
            System.out.println("Bônus (20%): R$ " + String.format("%.2f", vendedor.calcularBonus()));
            System.out.print("Endereço: ");
            vendedor.endereco.apresentarLogradouro();
            i++;
        }
    }
}