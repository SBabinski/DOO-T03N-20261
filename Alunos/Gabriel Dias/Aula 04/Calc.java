import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import obj.Calculadora;
import obj.Produto;
import obj.Venda;

public class Calc {

    static Scanner scanner = new Scanner(System.in);
    static Calculadora calculadora = new Calculadora();
    static ArrayList<Venda> historicoVendas = new ArrayList<>();
    public static void main(String[] args) {



        int opcao;

        do {
            System.out.println("\n=== Loja de Plantas da Dona Gabrielinha ===");
            System.out.println("[1] - Realizar venda");
            System.out.println("[2] - Calcular troco");
            System.out.println("[3] - Mostrar histórico de Vendas");
            System.out.println("[4] - Buscar vendas por dia");
            System.out.println("[5] - Buscar Vendas por mês");
            System.out.println("[0] - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {

                case 1:
                    realizaVenda();
                    break;

                case 2:
                    calcularTroco();
                    break;

                case 3:
                    historicoVendas();
                    break;

                case 4:
                    vendaDia();
                    break;

                case 5:
                    vendaMes();
                    break;

                case 0:
                    System.out.println("Sistema encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static void realizaVenda() {
        System.out.print("Nome da planta: ");
        String nomeProduto = scanner.nextLine();

        System.out.print("Preço unitário: ");
        double precoUnitario = scanner.nextDouble();

        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();

        Produto produto = new Produto(nomeProduto, precoUnitario);

        double desconto = calculadora.calcularDesconto(quantidade, precoUnitario);
        double valorFinal = calculadora.calcularValorTotal(quantidade, precoUnitario);

        System.out.println("\nProduto: " + produto.getNomeProduto());
        System.out.println("Desconto: R$ " + desconto);
        System.out.println("Total a pagar: R$ " + valorFinal);

        Venda venda = new Venda(nomeProduto, quantidade, valorFinal, desconto);
        historicoVendas.add(venda);

    }

    private static void calcularTroco(){
        System.out.print("Valor pago: ");
        double valorPago = scanner.nextDouble();

        System.out.print("Valor da compra: ");
        double valorCompra = scanner.nextDouble();

        double troco = calculadora.calcularTroco(valorPago, valorCompra);

        if (troco < 0) {
            System.out.println("Valor insuficiente! Faltam R$ " + (-troco));
        } else {
            System.out.println("Troco: R$ " + troco);
        }
    }

    private static void historicoVendas() {
        System.out.println("\n=== Histórico de Vendas ===");
        for (Venda v : historicoVendas) {
            v.exibirResumo();
        }
    }

    private static void vendaDia(){
        System.out.print("Digite a data (dd/MM/yyyy): ");
        String dataTexto = scanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataBusca = LocalDate.parse(dataTexto, formatter);

        int totalDia = 0;

        for (Venda v : historicoVendas) {
            if (v.getDataVenda().equals(dataBusca)) {
                totalDia++;
            }
        }

        System.out.println("Total de vendas no dia: " + totalDia);

    }

    private static void vendaMes(){
        System.out.print("Digite o mês (1-12): ");
        int mes = scanner.nextInt();

        System.out.print("Digite o ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine();

        int totalMes = 0;

        for (Venda v : historicoVendas) {
            if (v.getDataVenda().getMonthValue() == mes &&
                    v.getDataVenda().getYear() == ano) {
                totalMes++;
            }
        }

        System.out.println("Total de vendas no mês: " + totalMes);
    }
}