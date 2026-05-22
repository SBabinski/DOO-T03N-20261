import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Venda> vendas = new ArrayList<>();
        Calculadora calculadora = new Calculadora();

        int opcao;

        do {
            System.out.println("\n=== Loja de Plantas ===");
            System.out.println("1. Calcular preço total");
            System.out.println("2. Calcular troco");
            System.out.println("3. Listar vendas");
            System.out.println("4. Sair");
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

                    double totalSemDesconto = precoUnitario * quantidade;
                    double precoTotal = calculadora.calcularPrecoTotal(precoUnitario, quantidade);

                    double desconto = totalSemDesconto - precoTotal;

                    Venda venda = new Venda(quantidade, precoTotal, desconto);
                    vendas.add(venda);

                    System.out.println("Planta: " + planta.getNome());
                    System.out.println("Preço final: R$ " + precoTotal);

                    if (desconto > 0) {
                        System.out.println("Desconto aplicado: R$ " + desconto);
                    }

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
                    System.out.println("\n=== Registro de Vendas ===");

                    for (Venda v : vendas) {
                        v.exibirVenda();
                    }
                    break;

                case 4:
                    System.out.println("Encerrando o programa...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 4);

        scanner.close();
    }
}