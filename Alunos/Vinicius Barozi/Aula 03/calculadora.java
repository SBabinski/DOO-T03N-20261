import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Calculadora {

    // Lista para registrar vendas
    static List<String> historico = new ArrayList<>();

    public static double calcularPrecoTotal(int quantidade, double precoUnitario) {
        double total = quantidade * precoUnitario;
        double desconto = 0;

        // DESCONTO
        if (quantidade > 10) {
            desconto = total * 0.05;
            total -= desconto;
        }

        // REGISTRO DA VENDA
        historico.add("Qtd: " + quantidade +
                " | Preço Unit: R$ " + precoUnitario +
                " | Desconto: R$ " + desconto +
                " | Total: R$ " + total);

        return total;
    }

    public static double calcularTroco(double valorPago, double valorCompra) {
        return valorPago - valorCompra;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 4) {

            System.out.println("\n=== Calculadora da Dona Gabrielinha ===");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Ver Registro de Vendas");
            System.out.println("[4] - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();

            switch (opcao) {

                case 1:
                    System.out.print("Digite a quantidade de plantas: ");
                    int quantidade = scanner.nextInt();

                    System.out.print("Digite o preço unitário da planta: ");
                    double preco = scanner.nextDouble();

                    double total = calcularPrecoTotal(quantidade, preco);

                    if (quantidade > 10) {
                        System.out.println("Desconto de 5% aplicado!");
                    }

                    System.out.println("Preço total da venda: R$ " + total);
                    break;

                case 2:
                    System.out.print("Digite o valor pago pelo cliente: ");
                    double valorPago = scanner.nextDouble();

                    System.out.print("Digite o valor total da compra: ");
                    double valorCompra = scanner.nextDouble();

                    double troco = calcularTroco(valorPago, valorCompra);

                    if (troco < 0) {
                        System.out.println("Valor insuficiente! Faltam R$ " + (-troco));
                    } else {
                        System.out.println("Troco a ser dado: R$ " + troco);
                    }
                    break;

                case 3:
                    System.out.println("\n=== Registro de Vendas ===");
                    if (historico.isEmpty()) {
                        System.out.println("Nenhuma venda registrada.");
                    } else {
                        for (String venda : historico) {
                            System.out.println(venda);
                        }
                    }
                    break;

                case 4:
                    System.out.println("Encerrando o sistema... Até logo!");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }

        scanner.close();
    }
}