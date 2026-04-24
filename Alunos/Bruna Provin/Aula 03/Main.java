import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Calculadora calculadora = new Calculadora();
        List<Venda> listaVendas = new ArrayList<>();

        int opcao;

        do {
            System.out.println("\n=== Loja de Plantas da Dona Gabrielinha ===");
            System.out.println("[1] - Realizar Venda");
            System.out.println("[2] - Listar Vendas");
            System.out.println("[3] - Calcular Troco");
            System.out.println("[4] - Buscar total de vendas por dia");
            System.out.println("[5] - Buscar total de vendas por mês");
            System.out.println("[6] - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {

                case 1:

                    System.out.print("Digite o nome da planta: ");
                    String nome = scanner.nextLine();

                    System.out.print("Digite o preço unitário: ");
                    double preco = scanner.nextDouble();

                    System.out.print("Digite a quantidade: ");
                    int quantidade = scanner.nextInt();
                    scanner.nextLine();

                    Planta planta = new Planta(nome, preco);

                    double valorTotal = calculadora.calcularPrecoTotal(
                            quantidade,
                            planta.getPrecoUnitario());

                    double desconto = calculadora.calcularDesconto(quantidade, valorTotal);
                    double valorFinal = calculadora.calcularValorFinal(valorTotal, desconto);

                    // Data com fuso horário fixo
                    LocalDate dataVenda = LocalDate.now(
                            ZoneId.of("America/Sao_Paulo"));

                    Venda venda = new Venda(
                            planta.getNome(),
                            quantidade,
                            valorFinal,
                            desconto,
                            dataVenda);

                    listaVendas.add(venda);

                    System.out.println("\n--- Resumo da Venda ---");
                    System.out.println("Planta: " + planta.getNome());
                    System.out.println("Quantidade: " + quantidade);
                    System.out.println("Valor total: R$ " + valorTotal);
                    System.out.println("Desconto: R$ " + desconto);
                    System.out.println("Valor final: R$ " + valorFinal);
                    System.out.println("Data: " + dataVenda);

                    break;

                case 2:

                    System.out.println("\n=== Registro de Vendas ===");

                    if (listaVendas.isEmpty()) {
                        System.out.println("Nenhuma venda registrada.");
                    } else {
                        for (Venda v : listaVendas) {
                            System.out.println("------------------------");
                            System.out.println("Planta: " + v.getNomePlanta());
                            System.out.println("Quantidade: " + v.getQuantidade());
                            System.out.println("Desconto: R$ " + v.getDescontoAplicado());
                            System.out.println("Valor Final: R$ " + v.getValorTotal());
                            System.out.println("Data: " + v.getDataVenda());
                        }
                    }
                    break;

                case 3:

                    System.out.print("Digite o valor pago: ");
                    double valorPago = scanner.nextDouble();

                    System.out.print("Digite o valor final da compra: ");
                    double valorCompra = scanner.nextDouble();
                    scanner.nextLine();

                    double troco = calculadora.calcularTroco(valorPago, valorCompra);

                    if (troco < 0) {
                        System.out.println("Valor insuficiente! Faltam R$ " + (-troco));
                    } else {
                        System.out.println("Troco: R$ " + troco);
                    }
                    break;

                case 4:

                    if (listaVendas.isEmpty()) {
                        System.out.println("Nenhuma venda registrada ainda.");
                        break;
                    }

                    try {
                        System.out.print("Digite a data (dd/MM/yyyy): ");
                        String dataTexto = scanner.nextLine();

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate dataBusca = LocalDate.parse(dataTexto, formatter);

                        int totalDia = 0;

                        for (Venda v : listaVendas) {
                            if (v.getDataVenda().equals(dataBusca)) {
                                totalDia += v.getQuantidade();
                            }
                        }

                        if (totalDia == 0) {
                            System.out.println("Nenhuma venda encontrada nessa data.");
                        } else {
                            System.out.println("Total de plantas vendidas nesse dia: " + totalDia);
                        }

                    } catch (Exception e) {
                        System.out.println("Data inválida! Use o formato dd/MM/yyyy.");
                    }

                    break;

                case 5:

                    System.out.print("Digite o mês (1-12): ");
                    int mes = scanner.nextInt();

                    System.out.print("Digite o ano: ");
                    int ano = scanner.nextInt();
                    scanner.nextLine();

                    int totalMes = 0;

                    for (Venda v : listaVendas) {
                        if (v.getDataVenda().getMonthValue() == mes &&
                                v.getDataVenda().getYear() == ano) {

                            totalMes += v.getQuantidade();
                        }
                    }

                    System.out.println("Total de plantas vendidas no mês: " + totalMes);
                    break;

                case 6:
                    System.out.println("Encerrando o sistema...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 64);

        scanner.close();
    }
}