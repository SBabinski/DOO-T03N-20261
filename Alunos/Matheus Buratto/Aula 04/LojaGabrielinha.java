import java.util.Scanner;
import java.util.Map;
import java.util.TreeMap;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LojaGabrielinha {

    Scanner scan = new Scanner(System.in);
    Map<LocalDate, Integer> vendasPorData = new TreeMap<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public LocalDate lerDataValida() {
        while (true) {
            try {
                System.out.print("Digite a data (dd/MM/yyyy): ");
                String dataStr = scan.next();

                return LocalDate.parse(dataStr, formatter);

            } catch (Exception e) {
                System.out.println("Data inválida! Tente novamente.");
            }
        }
    }

    public double calcPreco() {
        System.out.println("Digite a quantidade de plantas:");
        int qnt = scan.nextInt();

        System.out.println("Digite o preço de cada planta:");
        double preco = scan.nextDouble();

        if (qnt <= 0 || preco <= 0) {
            System.out.println("Valores inválidos!");
            return 0;
        }

        double total = qnt * preco;

        if (qnt > 10) {
            total *= 0.95;
        }

        registrarVenda(qnt);

        return total;
    }

    public double troco() {
        System.out.print("Digite o valor recebido: ");
        double recebido = scan.nextDouble();

        System.out.print("Digite o valor da compra: ");
        double compra = scan.nextDouble();

        return recebido - compra;
    }

    public void registrarVenda(int quantidade) {
        LocalDate data = lerDataValida();

        vendasPorData.put(data, vendasPorData.getOrDefault(data, 0) + quantidade);

        System.out.println("Venda registrada com sucesso!");
    }

    public void mostrarHistoricoVendas() {
        System.out.println("\n=== HISTÓRICO DE VENDAS ===");

        if (vendasPorData.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
        } else {
            for (Map.Entry<LocalDate, Integer> entry : vendasPorData.entrySet()) {
                System.out.println(
                        "Data: " + entry.getKey().format(formatter) +
                                " |Total vendido: " + entry.getValue()
                );
            }
        }
    }

    public void buscarVendasPorData() {
        LocalDate dataBusca = lerDataValida();

        int total = vendasPorData.getOrDefault(dataBusca, 0);

        System.out.println("Total vendido no dia: " + total + " unidades.");
    }

    public void buscarVendasPorMes() {
        System.out.print("Digite o mês (MM): ");
        int mes = scan.nextInt();

        int total = 0;

        for (Map.Entry<LocalDate, Integer> entry : vendasPorData.entrySet()) {
            if (entry.getKey().getMonthValue() == mes) {
                total += entry.getValue();
            }
        }

        System.out.println("Total vendido no mês: " + total + " unidades.");
    }

    public static void main(String[] args) {
        LojaGabrielinha sistema = new LojaGabrielinha();

        while (true) {
            System.out.println("\n========== MENU ===========");
            System.out.println("[1] Calcular Preço Total");
            System.out.println("[2] Calcular Troco");
            System.out.println("[3] Histórico de Vendas");
            System.out.println("[4] Buscar Vendas por Data");
            System.out.println("[5] Buscar Vendas por Mês");
            System.out.println("[6] Sair");
            System.out.print("Escolha: ");

            int opcao = sistema.scan.nextInt();

            switch (opcao) {
                case 1:
                    double total = sistema.calcPreco();
                    System.out.printf("Total: R$ %.2f\n", total);
                    break;

                case 2:
                    double troco = sistema.troco();
                    System.out.printf("Troco: R$ %.2f\n", troco);
                    break;

                case 3:
                    sistema.mostrarHistoricoVendas();
                    break;

                case 4:
                    sistema.buscarVendasPorData();
                    break;

                case 5:
                    sistema.buscarVendasPorMes();
                    break;

                case 6:
                    System.out.println("Encerrando sistema...");
                    sistema.scan.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}