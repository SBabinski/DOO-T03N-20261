import java.util.Scanner;

public class CalculadoraGabrielinha {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== Calculadora da Dona Gabrielinha ===");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            if (opcao == 1) {
                System.out.print("Digite a quantidade da planta: ");
                int quantidade = scanner.nextInt();

                System.out.print("Digite o preço unitário da planta: ");
                double precoUnitario = scanner.nextDouble();

                double total = quantidade * precoUnitario;
                System.out.println("Preço total da venda: R$ " + total);

            } else if (opcao == 2) {
                System.out.print("Digite o valor recebido do cliente: ");
                double valorRecebido = scanner.nextDouble();

                System.out.print("Digite o valor total da compra: ");
                double valorTotal = scanner.nextDouble();

                double troco = valorRecebido - valorTotal;
                System.out.println("Troco do cliente: R$ " + troco);

            } else if (opcao == 3) {
                System.out.println("Encerrando a calculadora...");

            } else {
                System.out.println("Opção inválida!");
            }

        } while (opcao != 3);

        scanner.close();
    }
}