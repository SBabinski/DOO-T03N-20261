import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int opcao = 0;

        Map<LocalDate, Double> vendas = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (opcao != 5) {
            System.out.println("\n--- Menu Loja da Dona Gabrielinha ---");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Registrar Venda com Data");
            System.out.println("[4] - Consultar Vendas");
            System.out.println("[5] - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = input.nextInt();

            if (opcao == 1) {
                System.out.print("Digite a quantidade da planta: ");
                int quantidade = input.nextInt();
                System.out.print("Digite o valor unitário da planta: ");
                double precoUnitario = input.nextDouble();

                double precoTotal = quantidade * precoUnitario;
                System.out.printf("O preço total da venda é: R$ %.2f%n", precoTotal);

            } else if (opcao == 2) {
                System.out.print("Digite o valor recebido pelo cliente: ");
                double valorRecebido = input.nextDouble();
                System.out.print("Digite o valor total da compra: ");
                double valorTotal = input.nextDouble();

                double troco = valorRecebido - valorTotal;
                System.out.printf("O valor do troco é: R$ %.2f%n", troco);

            } else if (opcao == 3) {
                input.nextLine(); 

                System.out.print("Digite a quantidade da planta: ");
                int quantidade = input.nextInt();
                System.out.print("Digite o valor unitário da planta: ");
                double precoUnitario = input.nextDouble();
                input.nextLine();

                double total = quantidade * precoUnitario;

                System.out.print("Digite a data da venda (dd/MM/yyyy): ");
                String dataStr = input.nextLine();
                LocalDate data = LocalDate.parse(dataStr, formatter);

                vendas.put(data, vendas.getOrDefault(data, 0.0) + total);

                System.out.println("Venda registrada com sucesso! Total: R$ " + total);

            } else if (opcao == 4) {
                System.out.println("[1] Consultar por dia");
                System.out.println("[2] Consultar por mês");
                int escolha = input.nextInt();
                input.nextLine();

                if (escolha == 1) {
                    System.out.print("Digite a data (dd/MM/yyyy): ");
                    String dataStr = input.nextLine();
                    LocalDate data = LocalDate.parse(dataStr, formatter);

                    double total = vendas.getOrDefault(data, 0.0);
                    System.out.println("Total vendido no dia: R$ " + total);

                } else if (escolha == 2) {
                    System.out.print("Digite o mês (1-12): ");
                    int mes = input.nextInt();
                    System.out.print("Digite o ano: ");
                    int ano = input.nextInt();

                    double totalMes = 0;

                    for (Map.Entry<LocalDate, Double> entry : vendas.entrySet()) {
                        if (entry.getKey().getMonthValue() == mes &&
                            entry.getKey().getYear() == ano) {
                            totalMes += entry.getValue();
                        }
                    }

                    System.out.println("Total vendido no mês: R$ " + totalMes);
                }

            } else if (opcao == 5) {
                System.out.println("Encerrando o sistema...");
            } else {
                System.out.println("Opção inválida! Tente novamente.");
            }
        }

        input.close();
    }
}