package Aula4;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CalculadoraDonaGabrielinhaV3 {

    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        Map<LocalDate, Double> registroVendas = new HashMap<>();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        int opcao = 0;

        while (opcao != 4) {
            System.out.println("\n--- Sistema Dona Gabrielinha - Aula 04 ---");
            System.out.println("[1] - Registrar Venda (Hoje)");
            System.out.println("[2] - Buscar Total por Data");
            System.out.println("[3] - Ver Todas as Vendas");
            System.out.println("[4] - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = leitor.nextInt();

            switch (opcao) {
                 case 1:
                    System.out.print("Digite o valor da venda: ");
                    double valor = leitor.nextDouble();
                    LocalDate hoje = LocalDate.now();

                    registroVendas.put(hoje, registroVendas.getOrDefault(hoje, 0.0) + valor);
                    System.out.println("Venda de R$ " + valor + " registrada em " + hoje.format(formatador));
                    break;

                case 2:
                    System.out.print("Digite o dia (dd): ");
                    int dia = leitor.nextInt();
                    System.out.print("Digite o mês (MM): ");
                    int mes = leitor.nextInt();


                    LocalDate dataBusca = LocalDate.of(LocalDate.now().getYear(), mes, dia);
                    Double totalNaData = registroVendas.get(dataBusca);

                    if (totalNaData != null) {
                        System.out.println("Total vendido em " + dataBusca.format(formatador) + ": R$ " + totalNaData);
                    } else {
                        System.out.println("Nenhuma venda encontrada para o dia " + dataBusca.format(formatador));
                    }
                    break;

                case 3:
                    System.out.println("\n--- Histórico de Vendas Totais ---");
                 if (registroVendas.isEmpty()) {
                        System.out.println("Nenhuma venda registrada.");
                    } else {
                        registroVendas.forEach((data, total) -> {
                            System.out.println("Data: " + data.format(formatador) + " | Total: R$ " + total);
                        });
                    }
                    break;
            }
        }
        System.out.println("Saindo...");
        leitor.close();
    }
}
