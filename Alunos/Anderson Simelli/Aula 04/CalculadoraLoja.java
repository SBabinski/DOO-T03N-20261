package loja;

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalculadoraLoja {

    // Lista com registro detalhado das vendas
    static ArrayList<String> registroVendas = new ArrayList<>();

    // Controle de quantidade de vendas por dia
    static Map<LocalDate, Integer> vendasPorDia = new HashMap<>();

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static double calcularTroco(double pago, double total) {
        return pago - total;
    }

    public static void registrarVendaDetalhada(int quantidade, double preco, double total, double desconto, LocalDate data) {

        String venda = "Data: " + data.format(formatter) +
                " | Quantidade: " + quantidade +
                " | Preço unitário: " + preco +
                " | Total: " + total +
                " | Desconto: " + desconto;

        registroVendas.add(venda);
    }

    public static void registrarVendaPorData(LocalDate data) {
        vendasPorDia.put(data, vendasPorDia.getOrDefault(data, 0) + 1);
    }

    public static void mostrarVendasDetalhadas() {

        System.out.println("=== Registro de Vendas ===");

        for (String venda : registroVendas) {
            System.out.println(venda);
        }
    }

    public static void consultarPorDia(LocalDate data) {

        int total = vendasPorDia.getOrDefault(data, 0);
        System.out.println("Vendas em " + data.format(formatter) + ": " + total);
    }

    public static void consultarPorMes(int mes) {

        int total = 0;

        for (LocalDate data : vendasPorDia.keySet()) {
            if (data.getMonthValue() == mes) {
                total += vendasPorDia.get(data);
            }
        }

        System.out.println("Total de vendas no mês " + mes + ": " + total);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {

            System.out.println("\n===== Loja da Dona Gabrielinha =====");
            System.out.println("1 - Realizar Venda");
            System.out.println("2 - Calcular Troco");
            System.out.println("3 - Ver Registro de Vendas");
            System.out.println("4 - Consultar Vendas por Dia");
            System.out.println("5 - Consultar Vendas por Mês");
            System.out.println("6 - Sair");
            System.out.print("Escolha: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            // OPÇÃO 1 - VENDA COMPLETA
            if (opcao == 1) {

                System.out.print("Quantidade: ");
                int quantidade = scanner.nextInt();

                System.out.print("Preço unitário: ");
                double preco = scanner.nextDouble();
                scanner.nextLine();

                double total = quantidade * preco;
                double desconto = 0;

                if (quantidade > 10) {
                    desconto = total * 0.05;
                    total -= desconto;
                    System.out.println("Desconto aplicado: R$ " + desconto);
                }

                System.out.println("Total a pagar: R$ " + total);

                System.out.print("Digite a data da venda (dd/MM/yyyy): ");
                String dataTexto = scanner.nextLine();

                LocalDate data = LocalDate.parse(dataTexto, formatter);

                // REGISTRO COMPLETO
                registrarVendaDetalhada(quantidade, preco, total, desconto, data);
                registrarVendaPorData(data);

                System.out.println("Venda registrada com sucesso!");
            }

            // OPÇÃO 2 - TROCO
            else if (opcao == 2) {

                System.out.print("Valor pago: ");
                double pago = scanner.nextDouble();

                System.out.print("Valor da compra: ");
                double total = scanner.nextDouble();

                double troco = calcularTroco(pago, total);

                System.out.println("Troco: R$ " + troco);
            }

            // OPÇÃO 3 - LISTAR VENDAS
            else if (opcao == 3) {
                mostrarVendasDetalhadas();
            }

            // OPÇÃO 4 - CONSULTAR POR DIA
            else if (opcao == 4) {

                System.out.print("Digite a data (dd/MM/yyyy): ");
                String dataTexto = scanner.nextLine();

                LocalDate data = LocalDate.parse(dataTexto, formatter);

                consultarPorDia(data);
            }

            // OPÇÃO 5 - CONSULTAR POR MÊS
            else if (opcao == 5) {

                System.out.print("Digite o mês (1-12): ");
                int mes = scanner.nextInt();

                consultarPorMes(mes);
            }

        } while (opcao != 6);

        scanner.close();
    }
}