import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Calculadora {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int opcao;

        int totalVendas = 0;
        int totalPlantasVendidas = 0;
        double valorTotalVendido = 0;
        double totalDescontos = 0;

        // Armazena vendas por data
        Map<LocalDate, Integer> vendasPorData = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        do {

            System.out.println("\n=== Calculadora Dona Gabriela ===");
            System.out.println("1 - Calcular Preço Total (com desconto)");
            System.out.println("2 - Calcular Troco");
            System.out.println("3 - Ver Registro de Vendas");
            System.out.println("4 - Registrar vendas por data");
            System.out.println("5 - Buscar vendas por dia");
            System.out.println("6 - Buscar vendas por mês");
            System.out.println("7 - Sair");

            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

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
                    System.out.print("Digite a data (dd/MM/yyyy): ");
                    String dataStr = scanner.nextLine();

                    LocalDate data = LocalDate.parse(dataStr, formatter);

                    System.out.print("Quantidade de vendas no dia: ");
                    int qtdVendasDia = scanner.nextInt();

                    vendasPorData.put(data, vendasPorData.getOrDefault(data, 0) + qtdVendasDia);

                    System.out.println("Vendas registradas com sucesso!");
                    break;

                case 5:
                    System.out.print("Digite a data (dd/MM/yyyy): ");
                    String buscaDataStr = scanner.nextLine();

                    LocalDate buscaData = LocalDate.parse(buscaDataStr, formatter);

                    int vendasDia = vendasPorData.getOrDefault(buscaData, 0);

                    System.out.println("Total de vendas no dia: " + vendasDia);
                    break;

                case 6:
                    System.out.print("Digite o mês e ano (MM/yyyy): ");
                    String mesAnoStr = scanner.nextLine();

                    DateTimeFormatter mesFormatter = DateTimeFormatter.ofPattern("MM/yyyy");

                    int totalMes = 0;

                    for (Map.Entry<LocalDate, Integer> entry : vendasPorData.entrySet()) {
                        String mesAnoEntry = entry.getKey().format(mesFormatter);

                        if (mesAnoEntry.equals(mesAnoStr)) {
                            totalMes += entry.getValue();
                        }
                    }

                    System.out.println("Total de vendas no mês: " + totalMes);
                    break;

                case 7:
                    System.out.println("Programa encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 7);

        scanner.close();
    }
}