import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Calculadora calculadora = new Calculadora();
        ArrayList<Venda> historicoVendas = new ArrayList<>();

        int opcao;

        do {
            System.out.println("\n=== Loja de Plantas da Dona Gabrielinha ===");
            System.out.println("[1] - Realizar Venda");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Ver Histórico de Vendas");
            System.out.println("[4] - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {

                case 1:
                    System.out.print("Nome da planta: ");
                    String nomeProduto = scanner.nextLine();

                    System.out.print("Preço unitário: ");
                    double precoUnitario = scanner.nextDouble();

                    System.out.print("Quantidade: ");
                    int quantidade = scanner.nextInt();

                    Produto produto = new Produto(nomeProduto, precoUnitario);

                    double desconto = calculadora.calcularDesconto(quantidade, precoUnitario);
                    double valorFinal = calculadora.calcularValorTotal(quantidade, precoUnitario);

                    System.out.println("\nProduto: " + produto.getNomeProduto());
                    System.out.println("Desconto: R$ " + desconto);
                    System.out.println("Total a pagar: R$ " + valorFinal);

                    Venda venda = new Venda(nomeProduto, quantidade, valorFinal, desconto);
                    historicoVendas.add(venda);

                    break;

                case 2:
                    System.out.print("Valor pago: ");
                    double valorPago = scanner.nextDouble();

                    System.out.print("Valor da compra: ");
                    double valorCompra = scanner.nextDouble();

                    double troco = calculadora.calcularTroco(valorPago, valorCompra);

                    if (troco < 0) {
                        System.out.println("Valor insuficiente! Faltam R$ " + (-troco));
                    } else {
                        System.out.println("Troco: R$ " + troco);
                    }
                    break;

                case 3:
                    System.out.println("\n=== Histórico de Vendas ===");
                    for (Venda v : historicoVendas) {
                        v.exibirResumo();
                    }
                    break;

                case 4:
                    System.out.println("Sistema encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 4);

        scanner.close();
    }
}