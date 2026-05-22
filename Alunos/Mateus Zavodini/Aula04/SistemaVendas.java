import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SistemaVendas {

    static Map<LocalDate, Integer> vendasPorDia = new HashMap<>();
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n Sistema de Vendas");
            System.out.println("1 - Registrar vendas do dia");
            System.out.println("2 - Consultar vendas por dia");
            System.out.println("3 - Consultar total de vendas por mês");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    registrarVenda(sc);
                    break;
                case 2:
                    consultarPorDia(sc);
                    break;
                case 3:
                    consultarPorMes(sc);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        sc.close();
    }

    public static void registrarVenda(Scanner sc) {
        try {
            System.out.print("Digite a data (dd/MM/yyyy): ");
            String dataStr = sc.nextLine();

            LocalDate data = LocalDate.parse(dataStr, formatter);

            System.out.print("Digite a quantidade de vendas: ");
            int quantidade = sc.nextInt();
            sc.nextLine();

            vendasPorDia.put(data, quantidade);

            System.out.println("Vendas registradas com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao registrar venda. Verifique o formato da data.");
        }
    }

    public static void consultarPorDia(Scanner sc) {
        try {
            System.out.print("Digite a data (dd/MM/yyyy): ");
            String dataStr = sc.nextLine();

            LocalDate data = LocalDate.parse(dataStr, formatter);

            Integer vendas = vendasPorDia.get(data);

            if (vendas != null) {
                System.out.println("Total de vendas no dia: " + vendas);
            } else {
                System.out.println("Nenhuma venda registrada para este dia.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao consultar. Verifique o formato da data.");
        }
    }

    public static void consultarPorMes(Scanner sc) {
        try {
            System.out.print("Digite o mês (1-12): ");
            int mes = sc.nextInt();

            System.out.print("Digite o ano: ");
            int ano = sc.nextInt();
            sc.nextLine();

            int total = 0;

            for (Map.Entry<LocalDate, Integer> entry : vendasPorDia.entrySet()) {
                LocalDate data = entry.getKey();

                if (data.getMonthValue() == mes && data.getYear() == ano) {
                    total += entry.getValue();
                }
            }

            System.out.println("Total de vendas no mês: " + total);

        } catch (Exception e) {
            System.out.println("Erro ao consultar mês.");
        }
    }
}