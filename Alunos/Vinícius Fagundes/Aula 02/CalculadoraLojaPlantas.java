import java.util.Scanner;

public class CalculadoraLojaPlantas {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int opcao;

        do {
            mostrarMenu();
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    calcularPrecoTotal();
                    break;

                case 2:
                    calcularTroco();
                    break;

                case 3:
                    System.out.println("Saindo do sistema...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 3);

        scanner.close();
    }

    public static void mostrarMenu() {
        System.out.println("\n=== Calculadora da Loja da Dona Gabrielinha ===");
        System.out.println("[1] - Calcular Preço Total");
        System.out.println("[2] - Calcular Troco");
        System.out.println("[3] - Sair");
        System.out.print("Escolha uma opção: ");
    }

    public static void calcularPrecoTotal() {
        System.out.print("Digite a quantidade de plantas: ");
        int quantidade = scanner.nextInt();

        System.out.print("Digite o preço unitário da planta: ");
        double preco = scanner.nextDouble();

        double total = quantidade * preco;

        System.out.println("Preço total da compra: R$ " + total);
    }

    public static void calcularTroco() {
        System.out.print("Digite o valor total da compra: ");
        double valorCompra = scanner.nextDouble();

        System.out.print("Digite o valor pago pelo cliente: ");
        double valorPago = scanner.nextDouble();

        double troco = valorPago - valorCompra;

        System.out.println("Troco a ser devolvido: R$ " + troco);
    }
}