import java.util.Scanner;

public class Calculadora {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        do {
            System.out.println("\n===== CALCULADORA DA DONA GABRIELINHA =====");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();

            if (opcao == 1) {
                CalcularPreco();
            }
            if (opcao == 2) {
                CalcularTroco();
            }
        }
        while (opcao != 3);
System.out.println("projeto acabou");

    }


    public static void CalcularPreco() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite a quantidade de plantas: ");
        int quantidade = scanner.nextInt();

        System.out.print("Digite o preço unitário da planta: ");
        double preco = scanner.nextDouble();

        double total = quantidade * preco;

        System.out.println("Preço total da venda: R$ " + total);
    }

public static void CalcularTroco() {
        Scanner scanner = new Scanner(System.in);
    System.out.print("Digite o valor pago: ");
    double valorPago = scanner.nextDouble();

    System.out.print("Digite o valor da compra: ");
    double valorCompra = scanner.nextDouble();

    double troco = valorPago - valorCompra;

    System.out.println("Troco: R$ " + troco);
}
}