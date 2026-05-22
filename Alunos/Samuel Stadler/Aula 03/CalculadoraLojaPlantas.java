import java.util.Scanner;

public class CalculadoraLojaPlantas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nCalculadora da Loja de Plantas");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();

            if (opcao == 1) {
                System.out.print("Digite a quantidade de plantas: ");
                double quantidade = scanner.nextDouble();
                System.out.print("Digite o preço unitário (R$): ");
                double precoUnitario = scanner.nextDouble();

                double precoTotal = calcularPrecoTotal(quantidade, precoUnitario);
                System.out.printf("Preço Total: R$ %.2f%n", precoTotal);

            } else if (opcao == 2) {
                System.out.print("Digite o valor recebido pelo cliente (R$): ");
                double valorRecebido = scanner.nextDouble();
                System.out.print("Digite o valor total da compra (R$): ");
                double valorTotal = scanner.nextDouble();

                double troco = calcularTroco(valorRecebido, valorTotal);
                System.out.printf("Troco a ser devolvido: R$ %.2f%n", troco);

            } else if (opcao == 3) {
                System.out.println("Saindo... Obrigado por usar a calculadora");
                break;
            } else {
                System.out.println("Opção inválida! Digite 1, 2 ou 3.");
            }
        }

        scanner.close();
    }

    public static double calcularPrecoTotal(double quantidade, double precoUnitario) {
        double precoTotal = quantidade * precoUnitario;
        
        if (quantidade > 10) {
            double desconto = precoTotal * 0.05;
            precoTotal = precoTotal - desconto;
        }
        
        return precoTotal;
    }

    public static double calcularTroco(double valorRecebido, double valorTotal) {
        return valorRecebido - valorTotal;
    }
}