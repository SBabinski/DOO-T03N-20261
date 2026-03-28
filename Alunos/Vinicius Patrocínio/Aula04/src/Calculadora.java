import java.util.Scanner;

public class Calculadora {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int opcao;

        int totalVendas = 0;
        int totalPlantasVendidas = 0;
        double valorTotalVendido = 0;
        double totalDescontos = 0;

        do {

            System.out.println("\n=== Calculadora Dona Gabriela===");
            System.out.println("1 - Calcular Preço Total (com desconto)");
            System.out.println("2 - Calcular Troco");
            System.out.println("3 - Ver Registro de Vendas");
            System.out.println("4 - Sair");

            System.out.print("Escolha: ");
            opcao = scanner.nextInt();

            switch (opcao) {

                case 1:

                    System.out.print("Quantidade da planta: ");
                    int quantidade = scanner.nextInt();

                    System.out.print("Preço da planta: ");
                    double preco = scanner.nextDouble();

                    double total = quantidade * preco;
                    double desconto = 0;


                    if (quantidade > 10) {
                        desconto = total * 0.05;
                        total = total - desconto;
                        System.out.println("Desconto aplicado: " + desconto);
                    }

                    System.out.println("Preço final: " + total);


                    totalVendas++;
                    totalPlantasVendidas += quantidade;
                    valorTotalVendido += total;
                    totalDescontos += desconto;

                    break;

                case 2:

                    System.out.print("Valor da compra: ");
                    double valorCompra = scanner.nextDouble();

                    System.out.print("Valor pago: ");
                    double valorPago = scanner.nextDouble();

                    if (valorPago < valorCompra) {
                        System.out.println("Valor insuficiente!");
                    } else {
                        double troco = valorPago - valorCompra;
                        System.out.println("Troco: " + troco);
                    }

                    break;

                case 3:

                    System.out.println("\n=== Registro de Vendas ===");
                    System.out.println("Total de vendas: " + totalVendas);
                    System.out.println("Total de plantas vendidas: " + totalPlantasVendidas);
                    System.out.println("Valor total vendido: " + valorTotalVendido);
                    System.out.println("Total de descontos dados: " + totalDescontos);

                    break;

                case 4:
                    System.out.println("Programa encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 4);

        scanner.close();
    }
}