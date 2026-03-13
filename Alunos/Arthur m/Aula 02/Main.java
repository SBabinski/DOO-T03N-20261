import java.util.Scanner;

public class Main {

    public static double calcularPrecoTotal(int quantidade, double precoUnitario) {
        return quantidade * precoUnitario;
    }

    public static double calcularTroco(double valorRecebido, double valorTotal) {
        return valorRecebido - valorTotal;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {

            System.out.println("\n=== Calculadora da Loja da Dona Gabrielinha ===");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();

            switch (opcao) {

                case 1:
                    System.out.print("Digite a quantidade de plantas: ");
                    int quantidade = scanner.nextInt();

                    System.out.print("Digite o preço unitário da planta: ");
                    double preco = scanner.nextDouble();

                    double total = calcularPrecoTotal(quantidade, preco);

                    System.out.println("Preço total da venda: R$ " + total);
                    break;

                case 2:
                    System.out.print("Digite o valor recebido do cliente: ");
                    double recebido = scanner.nextDouble();

                    System.out.print("Digite o valor total da compra: ");
                    double valorTotal = scanner.nextDouble();

                    double troco = calcularTroco(recebido, valorTotal);

                    System.out.println("Troco a ser devolvido: R$ " + troco);
                    break;

                case 3:
                    System.out.println("Encerrando o programa...");
                    break;

                default:
                    System.out.println("Opção inválida!");

            }

        } while (opcao != 3);

        scanner.close();
    }
}