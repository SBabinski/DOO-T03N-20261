import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CalculadoraDonaGabrielinha {

    static final int LIMITE_VENDAS = 100;
    static final double PERCENTUAL_DESCONTO = 0.05;

    static int[] quantidadesVendidas = new int[LIMITE_VENDAS];
    static double[] valoresVendas = new double[LIMITE_VENDAS];
    static double[] descontosAplicados = new double[LIMITE_VENDAS];
    static LocalDate[] datasVendas = new LocalDate[LIMITE_VENDAS];

    static int totalVendasRegistradas = 0;

    static DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int opcao;

        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    calcularPrecoTotal(scanner);
                    break;

                case 2:
                    listarVendas();
                    break;

                case 3:
                    calcularTroco(scanner);
                    break;

                case 4:
                    buscarVendasPorDia(scanner);
                    break;

                case 5:
                    buscarVendasPorMes(scanner);
                    break;

                case 6:
                    System.out.println("Sistema encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }

            System.out.println();

        } while (opcao != 6);

        scanner.close();
    }

    public static void exibirMenu() {
        System.out.println("=================================");
        System.out.println(" CALCULADORA DONA GABRIELINHA");
        System.out.println("=================================");
        System.out.println("[1] - Calcular Preço Total");
        System.out.println("[2] - Listar Vendas Registradas");
        System.out.println("[3] - Calcular Troco");
        System.out.println("[4] - Buscar Quantidade de Vendas por Dia");
        System.out.println("[5] - Buscar Quantidade de Vendas por Mês");
        System.out.println("[6] - Sair");
        System.out.print("Escolha uma opção: ");
    }

    public static void calcularPrecoTotal(Scanner scanner) {
        System.out.print("Digite a quantidade de plantas: ");
        int quantidade = scanner.nextInt();

        System.out.print("Digite o preço unitário da planta: R$ ");
        double precoUnitario = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Digite a data da venda (dd/MM/yyyy): ");
        String dataTexto = scanner.nextLine();

        LocalDate dataVenda = LocalDate.parse(dataTexto, formatadorData);

        double valorBruto = quantidade * precoUnitario;
        double desconto = calcularDesconto(quantidade, valorBruto);
        double valorFinal = valorBruto - desconto;

        System.out.println("Valor bruto da venda: R$ " + valorBruto);
        System.out.println("Desconto aplicado: R$ " + desconto);
        System.out.println("Valor final da venda: R$ " + valorFinal);

        registrarVenda(quantidade, valorFinal, desconto, dataVenda);
    }

    public static double calcularDesconto(int quantidade, double valorBruto) {
        if (quantidade > 10) {
            return valorBruto * PERCENTUAL_DESCONTO;
        }

        return 0;
    }

    public static void registrarVenda(int quantidade, double valorVenda, double desconto, LocalDate dataVenda) {
        if (totalVendasRegistradas < LIMITE_VENDAS) {
            quantidadesVendidas[totalVendasRegistradas] = quantidade;
            valoresVendas[totalVendasRegistradas] = valorVenda;
            descontosAplicados[totalVendasRegistradas] = desconto;
            datasVendas[totalVendasRegistradas] = dataVenda;

            totalVendasRegistradas++;

            System.out.println("Venda registrada com sucesso!");
        } else {
            System.out.println("Limite de vendas registradas atingido.");
        }
    }

    public static void listarVendas() {
        if (totalVendasRegistradas == 0) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }

        System.out.println("===== VENDAS REGISTRADAS =====");

        for (int i = 0; i < totalVendasRegistradas; i++) {
            System.out.println("Venda " + (i + 1));
            System.out.println("Data da venda: " + datasVendas[i].format(formatadorData));
            System.out.println("Quantidade vendida: " + quantidadesVendidas[i]);
            System.out.println("Valor da venda: R$ " + valoresVendas[i]);
            System.out.println("Desconto aplicado: R$ " + descontosAplicados[i]);
            System.out.println("-----------------------------");
        }
    }

    public static void calcularTroco(Scanner scanner) {
        System.out.print("Digite o valor recebido do cliente: R$ ");
        double valorRecebido = scanner.nextDouble();

        System.out.print("Digite o valor total da compra: R$ ");
        double valorTotalCompra = scanner.nextDouble();

        double troco = valorRecebido - valorTotalCompra;

        if (troco < 0) {
            System.out.println("Valor recebido insuficiente. Faltam R$ " + Math.abs(troco));
        } else {
            System.out.println("Troco a ser devolvido: R$ " + troco);
        }
    }

    public static void buscarVendasPorDia(Scanner scanner) {
        System.out.print("Digite a data para buscar as vendas (dd/MM/yyyy): ");
        String dataTexto = scanner.nextLine();

        LocalDate dataBusca = LocalDate.parse(dataTexto, formatadorData);

        int quantidadeTotal = 0;

        for (int i = 0; i < totalVendasRegistradas; i++) {
            if (datasVendas[i].equals(dataBusca)) {
                quantidadeTotal += quantidadesVendidas[i];
            }
        }

        System.out.println("Quantidade total de plantas vendidas no dia "
                + dataBusca.format(formatadorData) + ": " + quantidadeTotal);
    }

    public static void buscarVendasPorMes(Scanner scanner) {
        System.out.print("Digite o mês para buscar as vendas: ");
        int mesBusca = scanner.nextInt();

        System.out.print("Digite o ano para buscar as vendas: ");
        int anoBusca = scanner.nextInt();

        int quantidadeTotal = 0;

        for (int i = 0; i < totalVendasRegistradas; i++) {
            int mesVenda = datasVendas[i].getMonthValue();
            int anoVenda = datasVendas[i].getYear();

            if (mesVenda == mesBusca && anoVenda == anoBusca) {
                quantidadeTotal += quantidadesVendidas[i];
            }
        }

        System.out.println("Quantidade total de plantas vendidas no mês "
                + mesBusca + "/" + anoBusca + ": " + quantidadeTotal);
    }
}