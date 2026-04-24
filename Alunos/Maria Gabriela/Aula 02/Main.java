import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Calculadora calculadora = new Calculadora();

        int opcao;

        do {
            System.out.println("\n=== Loja de Plantas ===");
            System.out.println("1. Calcular preço total");
            System.out.println("2. Calcular troco");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {

                case 1:
                    System.out.print("Digite o nome da planta: ");
                    String nomePlanta = scanner.nextLine();

                    System.out.print("Digite o preço unitário da planta: ");
                    double precoUnitario = scanner.nextDouble();

                    System.out.print("Digite a quantidade: ");
                    int quantidade = scanner.nextInt();

                    Planta planta = new Planta(nomePlanta, precoUnitario);
                    double precoTotal = calculadora.calcularPrecoTotal(precoUnitario, quantidade);

                    System.out.println("Planta: " + planta.getNome());
                    System.out.println("Preço total: R$ " + precoTotal);
                    break;

                case 2:
                    System.out.print("Digite o valor pago: ");
                    double valorPago = scanner.nextDouble();

                    System.out.print("Digite o preço total da compra: ");
                    double precoTotalCompra = scanner.nextDouble();

                    double troco = calculadora.calcularTroco(valorPago, precoTotalCompra);

                    if (troco < 0) {
                        System.out.println("Valor pago insuficiente. Faltam R$ " + (-troco));
                    } else {
                        System.out.println("Troco: R$ " + troco);
                    }
                    break;

                case 3:
                    System.out.println("Encerrando o programa. Obrigado por visitar a loja de plantas!");
                    break;

                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }

        } while (opcao != 3);

        scanner.close();
    }
}