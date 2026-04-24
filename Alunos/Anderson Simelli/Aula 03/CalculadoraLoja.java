package loja;

import java.util.ArrayList;
import java.util.Scanner;

public class CalculadoraLoja {

    static ArrayList<String> registroVendas = new ArrayList<>();

    public static double calcularPrecoTotal(int quantidade, double preco) {

        double total = quantidade * preco;
        double desconto = 0;

        if (quantidade > 10) {
            desconto = total * 0.05;
            total = total - desconto;
            System.out.println("Desconto aplicado: R$ " + desconto);
        }

        registrarVenda(quantidade, preco, total, desconto);

        return total;
    }

    public static double calcularTroco(double valorPago, double valorTotal) {
        return valorPago - valorTotal;
    }

    public static void registrarVenda(int quantidade, double preco, double total, double desconto) {

        String venda = "Quantidade: " + quantidade +
                       " | Preço unitário: " + preco +
                       " | Total da venda: " + total +
                       " | Desconto aplicado: " + desconto;

        registroVendas.add(venda);
    }

    public static void mostrarVendas() {

        System.out.println("=== Registro de Vendas ===");

        for (String venda : registroVendas) {
            System.out.println(venda);
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {

            System.out.println("\n===== Loja da Dona Gabrielinha =====");
            System.out.println("1 - Calcular Preço Total");
            System.out.println("2 - Calcular Troco");
            System.out.println("3 - Ver Registro de Vendas");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();

            if (opcao == 1) {

                System.out.print("Quantidade de plantas: ");
                int quantidade = scanner.nextInt();

                System.out.print("Preço unitário: ");
                double preco = scanner.nextDouble();

                double total = calcularPrecoTotal(quantidade, preco);

                System.out.println("Total a pagar: R$ " + total);
            }

            else if (opcao == 2) {

                System.out.print("Valor pago pelo cliente: ");
                double pago = scanner.nextDouble();

                System.out.print("Valor da compra: ");
                double total = scanner.nextDouble();

                double troco = calcularTroco(pago, total);

                System.out.println("Troco: R$ " + troco);
            }

            else if (opcao == 3) {
                mostrarVendas();
            }

        } while (opcao != 4);

        scanner.close();
    }
}