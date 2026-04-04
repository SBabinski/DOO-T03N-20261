import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static ArrayList<Plantas> plantas = new ArrayList<>();
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        do {
            System.out.println("\n|===== CALCULADORA DA DONA GABRIELINHA =====|");
            System.out.println("|[1] - Calcular Preço Total                   |");
            System.out.println("|[2] - Calcular Troco                         |");
            System.out.println("|[3] - Listar Vendas                          |");
            System.out.println("|[4] - Salvar Quantidade de Vendas por Dia    |");
            System.out.println("|[5] - Buscar Total de Vendas por Mês e Dia   |");
            System.out.println("|[6] - Sair                                   |");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();

            if (opcao == 1) {
                CalcularPreco(scanner);
            }
            if (opcao == 2) {
                CalcularTroco(scanner);
            }
            if (opcao == 3) {
                for (Plantas p : plantas) {
                    System.out.println(p);
                }
            }
            if (opcao == 5) {
                buscarVendasPorDia(scanner);
            }

        } while (opcao != 6);

        System.out.println("Sistema encerrado. Até logo, Dona Gabrielinha!");
    }

    public static void CalcularPreco(Scanner scanner) {
        System.out.print("Digite a data da venda (dd/MM/yyyy): ");
        LocalDate dataVenda = lerData(scanner);

        System.out.print("Digite a quantidade de plantas: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o preço unitário da planta: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();

        double desconto = 0;
        double total = 0;
        if (quantidade >= 10) {
            System.out.println("parabens voce ganhou um desconto de 5%");
            desconto = preco;
            desconto = desconto-(desconto * 5)/100;
            total = quantidade * desconto;
        }
        else {
            total = quantidade * preco;
        }

        Plantas venda = new Plantas(quantidade, preco, total, desconto, dataVenda);
        plantas.add(venda);

        System.out.printf("Preço total da venda: R$ %.2f%n", total);
        System.out.println("Venda registrada automaticamente em: " + dataVenda.format(FORMATTER));
    }

    public static void CalcularTroco(Scanner scanner) {
        System.out.print("Digite o valor pago: ");
        double valorPago = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Digite o valor da compra: ");
        double valorCompra = scanner.nextDouble();
        scanner.nextLine();

        double troco = valorPago - valorCompra;
        System.out.printf("Troco: R$", troco);
    }

    public static void buscarVendasPorDia(Scanner scanner) {
        System.out.print("Digite o dia que deseja buscar (dd/MM/yyyy): ");
        LocalDate dataBusca = lerData(scanner);

        int totalVendas = 0;
        for (Plantas p : plantas) {
            if (p.getDataVenda() != null && p.getDataVenda().equals(dataBusca)) {
                totalVendas++;
            }
        }

        System.out.println("Data: " + dataBusca.format(FORMATTER));
        System.out.println("Total de vendas no dia: " + totalVendas);

    }

    public static void buscarVendasPorMes(Scanner scanner) {
        DateTimeFormatter formatoMes = DateTimeFormatter.ofPattern("MM/yyyy");

        System.out.print("Digite o mês que deseja buscar (MM/yyyy): ");
        String entrada = scanner.nextLine().trim();

        int mes, ano;
        try {
            String[] partes = entrada.split("/");
            mes = Integer.parseInt(partes[0]);
            ano = Integer.parseInt(partes[1]);
        } catch (Exception e) {
            System.out.println("Formato inválido! Use MM/yyyy.");
            return;
        }

        int totalVendas = 0;
        for (Plantas p : plantas) {
            if (p.getDataVenda() != null
                    && p.getDataVenda().getMonthValue() == mes
                    && p.getDataVenda().getYear() == ano) {
                totalVendas++;
            }
        }

        System.out.println("Mês: " + entrada);
        System.out.println("Total de vendas no mês: " + totalVendas);

    }

    private static LocalDate lerData(Scanner scanner) {
        LocalDate data = null;
        while (data == null) {
            String entrada = scanner.nextLine().trim();
            try {
                data = LocalDate.parse(entrada, FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.print("Data inválida! Use o formato dd/MM/yyyy: ");
            }
        }
        return data;
    }
}