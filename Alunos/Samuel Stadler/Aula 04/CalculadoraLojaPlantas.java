import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalculadoraLojaPlantas {

    static ArrayList<Venda> vendas = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            System.out.println("\nCalculadora da Loja de Plantas");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Relatório de Vendas por Dia");
            System.out.println("[4] - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();

            if (opcao == 1) {
                System.out.print("Digite a quantidade de plantas: ");
                double quantidade = scanner.nextDouble();
                System.out.print("Digite o preço unitário (R$): ");
                double precoUnitario = scanner.nextDouble();

                double precoTotal = calcularPrecoTotal(quantidade, precoUnitario);
                System.out.printf("Preço Total: R$ %.2f%n", precoTotal);

                // Registra a venda automaticamente
                registrarVenda(quantidade, precoTotal);

            } else if (opcao == 2) {
                System.out.print("Digite o valor recebido pelo cliente (R$): ");
                double valorRecebido = scanner.nextDouble();
                System.out.print("Digite o valor total da compra (R$): ");
                double valorTotal = scanner.nextDouble();

                double troco = calcularTroco(valorRecebido, valorTotal);
                System.out.printf("Troco a ser devolvido: R$ %.2f%n", troco);

            } else if (opcao == 3) {
                scanner.nextLine(); // limpa o buffer
                System.out.print("Digite a data (dd/MM/yyyy): ");
                String dataStr = scanner.nextLine();

                try {
                    LocalDate data = LocalDate.parse(dataStr, formatter);
                    double totalPlantas = buscarVendasPorDia(data);
                    System.out.printf("Total de plantas vendidas em %s: %.0f%n", dataStr, totalPlantas);
                } catch (Exception e) {
                    System.out.println("Data inválida! Use o formato dd/MM/yyyy");
                }

            } else if (opcao == 4) {
                System.out.println("Saindo... Obrigado por usar a calculadora");
                break;
            } else {
                System.out.println("Opção inválida! Digite 1, 2, 3 ou 4.");
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

    public static void registrarVenda(double quantidade, double valorTotal) {
        Venda venda = new Venda(quantidade, valorTotal, LocalDate.now());
        vendas.add(venda);
        System.out.println("Venda registrada com sucesso!");
    }

    public static double buscarVendasPorDia(LocalDate data) {
        double total = 0;
        for (Venda v : vendas) {
            if (v.getData().equals(data)) {
                total += v.getQuantidade();
            }
        }
        return total;
    }
}

class Venda {
    private double quantidade;
    private double valorTotal;
    private LocalDate data;

    public Venda(double quantidade, double valorTotal, LocalDate data) {
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.data = data;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public LocalDate getData() {
        return data;
    }
}