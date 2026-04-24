
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        List<Calculadora> vendas = new ArrayList<>();
        System.out.println("Bem vindo!");
        apresentaCafe();
        int escolha = -1;

        do {
            escolha = apresentaMenu(escolha);
            switch (escolha) {
                case 1:
                    Calculadora novaVenda = new Calculadora();
                    calculaTotal(novaVenda);
                    vendas.add(novaVenda);
                    break;
                case 2:
                    calculaTroco();
                    break;
                case 3:
                    registroVendas(vendas);
                    break;
                case 4:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida, tente novamente.");

            }
        } while (escolha != 4);

    }

    public static void apresentaCafe() {
        System.out.println("Lembre-se que dar um café sobre a conversão da venda para 80%");
    }

    public static int apresentaMenu(int escolha) {
        System.out.println("1. Calcular venda");
        System.out.println("2. Calcular troco");
        System.out.println("3. Funcionalidades");
        System.out.println("4. Sair");

        escolha = scan.nextInt();
        scan.nextLine(); // Limpa o buffer do scanner
        return escolha;
    }

    public static void calculaTotal(Calculadora calc) {
        System.out.println("Digite a quantidade do produto:");
        int quantidade = scan.nextInt();
        double totalcomdesc;
        System.out.println("Digite o preço do produto:");
        double preco = scan.nextDouble();
        double total = preco * quantidade;
        if (quantidade > 10) {
            totalcomdesc = total * 0.95;
        } else {
            totalcomdesc = total;
        }
        calc.setQuantidade(quantidade);
        calc.setPreco(total);
        calc.setDescontoAplicado(totalcomdesc);
        System.out.printf("O preço total é: %.2f\n", totalcomdesc);
        System.out.println("Digite a data da venda (dd/MM/yyyy):");
        String dataTexto = scan.next();
        LocalDate data = LocalDate.parse(dataTexto, formatter);
        calc.setSaleDate(data);

    }

    public static void calculaTroco() {
        System.out.println("Digite o valor pago:");
        double valorPago = scan.nextDouble();
        System.out.println("Digite o preço total da compra:");
        double precoTotal = scan.nextDouble();
        double troco = valorPago - precoTotal;
        System.out.println("O troco é: " + troco);
        return;
    }

    public static void registroVendas(List<Calculadora> vendas) {
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }
        System.out.println("Digite a operação: ");
        System.out.println("1 - Exibir vendas de um dia específico");
        System.out.println("2 - Exibir todas as vendas de um mês específico");
        System.out.println("3 - Exibir todas as vendas");

        int option = scan.nextInt();
        scan.nextLine();
        switch (option) {
            case 1 -> consultationDay(vendas);
            case 2 -> consultationMonth(vendas);
            case 3 -> consultationAll(vendas);
            default -> System.out.println("Opção inválida.");
        }
    }
    public static void consultationDay(List<Calculadora> vendas) {
        System.out.println("Digite a data da venda (dd/MM/yyyy):");
        String dataConsulta = scan.nextLine();
        LocalDate data = LocalDate.parse(dataConsulta, formatter);
        System.out.println("Vendas do dia " + dataConsulta + ":");
        boolean encontrou = false;
        for (Calculadora calculadora : vendas) {
            if (calculadora.getSaleDate().equals(data)) {
                System.out.printf(
                        "Quantidade: %d, Preço sem Desconto: %.2f, Total: %.2f\n",
                        calculadora.getQuantidade(),
                        calculadora.getPreco(),
                        calculadora.getDescontoAplicado()
                );
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhuma venda encontrada para essa data.");
        }
    }
    public static void consultationMonth(List<Calculadora> vendas) {
        System.out.println("Digite o mês e ano (MM/yyyy):");
        String entrada = scan.nextLine();
        DateTimeFormatter mesFormatter = DateTimeFormatter.ofPattern("MM/yyyy");
        YearMonth anoMes = YearMonth.parse(entrada, mesFormatter);
        LocalDate inicio = anoMes.atDay(1);
        LocalDate fim = anoMes.atEndOfMonth();

        System.out.println("Vendas do mês " + entrada + ":");

        boolean encontrou = false;

        for (Calculadora calculadora : vendas) {
            LocalDate dataVenda = calculadora.getSaleDate();
            if (!dataVenda.isBefore(inicio) && !dataVenda.isAfter(fim)) {
                System.out.printf(
                        "Quantidade: %d, Preço sem Desconto: %.2f, Total: %.2f\n",
                        calculadora.getQuantidade(),
                        calculadora.getPreco(),
                        calculadora.getDescontoAplicado()
                );
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhuma venda encontrada para esse mês.");
        }
    }
    public static void consultationAll(List<Calculadora> vendas) {
        System.out.println("Todas as vendas:");
        for (Calculadora calculadora : vendas) {
            System.out.printf(
                    "Quantidade: %d, Preço sem Desconto: %.2f, Total: %.2f\n",
                    calculadora.getQuantidade(),
                    calculadora.getPreco(),
                    calculadora.getDescontoAplicado(),
                    calculadora.getSaleDate()
            );
        }
    }
}
