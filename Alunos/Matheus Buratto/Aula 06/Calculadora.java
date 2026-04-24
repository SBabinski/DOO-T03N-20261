import java.util.Scanner;
import java.util.Map;
import java.util.TreeMap;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Calculadora {

    private Map<LocalDate, Integer> vendasPorData = new TreeMap<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public LocalDate lerDataValida(Scanner scan) {
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

    public void calcPreco(Scanner scan) {
        System.out.println("Digite a quantidade de plantas:");
        int qnt = scan.nextInt();
        System.out.println("Digite o preço de cada planta:");
        double preco = scan.nextDouble();

        if (qnt <= 0 || preco <= 0) {
            System.out.println("Valores inválidos!");
            return;
        }

        double total = qnt * preco;
        if (qnt > 10) {
            double desconto = total * 0.05;
            System.out.printf("O cliente recebeu um desconto de R$ %.2f\n", desconto);
            total *= 0.95;
        }

        System.out.printf("Total: R$ %.2f\n", total);
        registrarVenda(scan, qnt);
    }

    public void troco(Scanner scan) {
        System.out.print("Digite o valor recebido: ");
        double recebido = scan.nextDouble();
        System.out.print("Digite o valor da compra: ");
        double compra = scan.nextDouble();
        System.out.printf("Troco: R$ %.2f\n", recebido - compra);
    }

    public void registrarVenda(Scanner scan, int quantidade) {
        LocalDate data = lerDataValida(scan);
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
                                " | Total vendido: " + entry.getValue() + " unidades"
                );
            }
        }
    }

    public void buscarVendasPorData(Scanner scan) {
        LocalDate dataBusca = lerDataValida(scan);
        int total = vendasPorData.getOrDefault(dataBusca, 0);
        System.out.println("Total vendido no dia: " + total + " unidades.");
    }

    public void buscarVendasPorMes(Scanner scan) {
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
}