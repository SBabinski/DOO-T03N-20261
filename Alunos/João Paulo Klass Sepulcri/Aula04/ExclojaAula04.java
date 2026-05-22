package Aula04;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExclojaAula04 {

    static Scanner scanner = new Scanner(System.in);

    static int totalVendas = 0;

    static LocalDate[] datas = new LocalDate[100];
    static int[] vendas = new int[100];
    static int tamanho = 0;

    static DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        int opcao;

        do {
            System.out.println("\n=== CALCULADORA DA LOJA DE PLANTAS ===");
            System.out.println("[1] Calcular valor da venda");
            System.out.println("[2] Calcular troco");
            System.out.println("[3] Ver total geral de vendas");
            System.out.println("[4] Ver vendas por dia");
            System.out.println("[5] Ver vendas por mês");
            System.out.println("[6] Sair");
            System.out.print("Escolha: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    realizarVenda();
                    break;

                case 2:
                    calcularTroco();
                    break;

                case 3:
                    System.out.println("Total de vendas: " + totalVendas);
                    break;

                case 4:
                    vendasPorDia();
                    break;

                case 5:
                    vendasPorMes();
                    break;

                case 6:
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 6);
    }

    public static void realizarVenda() {
        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();

        System.out.print("Preço unitário: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();

        double total = quantidade * preco;

        if (quantidade > 10) {
            total = total - (total * 0.05);
            System.out.println("Desconto aplicado!");
        }

        System.out.print("Digite a data (dd/MM/yyyy): ");
        String textoData = scanner.nextLine();

        LocalDate data = LocalDate.parse(textoData, formato);

        boolean encontrou = false;

        for (int i = 0; i < tamanho; i++) {
            if (datas[i].equals(data)) {
                vendas[i]++;
                encontrou = true;
            }
        }

        if (!encontrou) {
            datas[tamanho] = data;
            vendas[tamanho] = 1;
            tamanho++;
        }

        System.out.printf("Valor final: R$ %.2f%n", total);

        totalVendas++;
    }

    public static void calcularTroco() {
        System.out.print("Valor da compra: ");
        double valorCompra = scanner.nextDouble();

        System.out.print("Valor recebido: ");
        double recebido = scanner.nextDouble();

        double troco = recebido - valorCompra;

        if (troco < 0) {
            System.out.println("Valor insuficiente!");
        } else {
            System.out.printf("Troco: R$ %.2f%n", troco);
        }
    }

    public static void vendasPorDia() {
        System.out.print("Digite a data (dd/MM/yyyy): ");
        String textoData = scanner.nextLine();

        LocalDate data = LocalDate.parse(textoData, formato);

        boolean encontrou = false;

        for (int i = 0; i < tamanho; i++) {
            if (datas[i].equals(data)) {
                System.out.println("Vendas no dia: " + vendas[i]);
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhuma venda nesse dia.");
        }
    }

    public static void vendasPorMes() {
        System.out.print("Digite o mês e ano (MM/yyyy): ");
        String texto = scanner.nextLine();

        int mes = Integer.parseInt(texto.substring(0, 2));
        int ano = Integer.parseInt(texto.substring(3, 7));

        int total = 0;

        for (int i = 0; i < tamanho; i++) {
            if (datas[i].getMonthValue() == mes && datas[i].getYear() == ano) {
                total += vendas[i];
            }
        }

        System.out.println("Total de vendas no mês: " + total);
    }
}