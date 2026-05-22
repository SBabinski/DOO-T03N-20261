package loja;

import java.util.Scanner;

public class CalculadoraLojaPlantas {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== CALCULADORA DA LOJA DE PLANTAS DA DONA GABRIELINHA ===");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("Digite a quantidade da planta vendida: ");
                    int quantidade = scanner.nextInt();

                    System.out.print("Digite o preço unitário da planta: ");
                    double precoUnitario = scanner.nextDouble();

                    double precoTotal = calcularPrecoTotal(quantidade, precoUnitario);

                    System.out.printf("O preço total da venda é: R$ %.2f%n", precoTotal);
                    break;

                case 2:
                    System.out.print("Digite o valor recebido do cliente: ");
                    double valorRecebido = scanner.nextDouble();

                    System.out.print("Digite o valor total da compra: ");
                    double valorTotalCompra = scanner.nextDouble();

                    double troco = calcularTroco(valorRecebido, valorTotalCompra);

                    if (troco < 0) {
                        System.out.printf("Valor insuficiente! Faltam: R$ %.2f%n", Math.abs(troco));
                    } else {
                        System.out.printf("O troco a ser dado é: R$ %.2f%n", troco);
                    }
                    break;

                case 3:
                    System.out.println("Encerrando o programa...");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }

        } while (opcao != 3);

        scanner.close();
    }

    public static double calcularPrecoTotal(int quantidade, double precoUnitario) {
        return quantidade * precoUnitario;
    }

    public static double calcularTroco(double valorRecebido, double valorTotalCompra) {
        return valorRecebido - valorTotalCompra;
    }
}
