package excs;

import java.util.Scanner;

public class Excloja {

    static Scanner scanner = new Scanner(System.in);

    // Variáveis simples para registro
    static int totalVendas = 0;

    public static void main(String[] args) {
        int opcao;

        do {
            System.out.println("\n=== CALCULADORA DA LOJA DE PLANTAS ===");
            System.out.println("[1] Calcular valor da venda");
            System.out.println("[2] Calcular troco");
            System.out.println("[3] Ver total de vendas");
            System.out.println("[4] Sair");
            System.out.print("Escolha: ");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    realizarVenda();
                    break;

                case 2:
                    calcularTroco();
                    break;

                case 3:
                    System.out.println("Total de vendas realizadas: " + totalVendas);
                    break;

                case 4:
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 4);
    }

    public static void realizarVenda() {
        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();

        System.out.print("Preço unitário: ");
        double preco = scanner.nextDouble();

        double total = quantidade * preco;

        // desconto simples
        if (quantidade > 10) {
            double desconto = total * 0.05;
            total = total - desconto;
            System.out.println("Desconto aplicado!");
        }

        System.out.printf("Valor final: R$ %.2f%n", total);

        totalVendas++; // registra venda
    }

    public static void calcularTroco() {
        System.out.print("Valor da compra: ");
        double valorCompra = scanner.nextDouble();

        System.out.print("Valor recebido: ");
        double recebido = scanner.nextDouble();

        double troco = recebido - valorCompra;

        if (troco < 0) {
            System.out.println("Valor insuficiente!");
        } else {
            System.out.printf("Troco: R$ %.2f%n", troco);
        }
    }
}
