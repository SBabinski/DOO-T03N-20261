import java.util.ArrayList;
import java.util.List;

public class Gerente extends Pessoa {
    // Removido 'endereco' daqui, pois já existe em Pessoa
    String loja;
    double salarioBase;
    List<Double> salarioRecebido = new ArrayList<>();

    public Gerente(String nome, int idade, String loja, Endereco endereco, double salarioBase){
        super(nome, idade, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;
    }

    @Override
    public void apresentarSe(){
        System.out.println("Gerente: " + nome + " | Idade: " + idade + " | Loja: " + loja + ".");
    }

    public double calcularMedia() {
        double soma = 0;
        for (double s : salarioRecebido) {
            soma += s;
        }
        return salarioRecebido.isEmpty() ? 0 : soma / salarioRecebido.size();
    }

    public double calcularBonus(){
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

            gerente.apresentarSe();

            System.out.println("Salário Base: R$ " + String.format("%.2f", gerente.salarioBase));
            System.out.println("Média Salarial: R$ " + String.format("%.2f", gerente.calcularMedia()));
            System.out.println("Bônus (35%): R$ " + String.format("%.2f", gerente.calcularBonus()));

            System.out.print("Endereço: ");
            if (gerente.endereco != null) {
                gerente.endereco.apresentarLogradouro();
            }
            i++;
        }
    }
}