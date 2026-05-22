import java.util.Scanner;

public class CalculadoraLojaPlantas {

    static Scanner scanner = new Scanner(System.in);

    static int totalPlantasVendidas = 0;
    static double valorTotalVendas = 0;
    static double totalDescontos = 0;

    public static void main(String[] args) {

        int opcao;

        do {

            mostrarMenu();
            opcao = scanner.nextInt();

            switch (opcao) {

                case 1:
                    registrarVenda();
                    break;

                case 2:
                    mostrarRegistroVendas();
                    break;

                case 3:
                    calcularTroco();
                    break;

                case 4:
                    System.out.println("Saindo do sistema...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 4);

        scanner.close();
    }

    public static void mostrarMenu() {
        System.out.println("\n=== Sistema da Loja da Dona Gabrielinha ===");
        System.out.println("[1] Registrar Venda");
        System.out.println("[2] Mostrar Registro de Vendas");
        System.out.println("[3] Calcular Troco");
        System.out.println("[4] Sair");
        System.out.print("Escolha uma opção: ");
    }

    public static void registrarVenda() {

        System.out.print("Digite a quantidade de plantas: ");
        int quantidade = scanner.nextInt();

        System.out.print("Digite o preço da planta: ");
        double preco = scanner.nextDouble();

        double valorTotal = quantidade * preco;
        double desconto = 0;

        if (quantidade > 10) {
            desconto = valorTotal * 0.05;
            valorTotal = valorTotal - desconto;
            System.out.println("Desconto aplicado de 5%!");
        }

        System.out.println("Valor final da venda: R$ " + valorTotal);

        totalPlantasVendidas += quantidade;
        valorTotalVendas += valorTotal;
        totalDescontos += desconto;
    }

    public static void mostrarRegistroVendas() {

        System.out.println("\n=== Registro de Vendas ===");
        System.out.println("Total de plantas vendidas: " + totalPlantasVendidas);
        System.out.println("Valor total das vendas: R$ " + valorTotalVendas);
        System.out.println("Total de descontos aplicados: R$ " + totalDescontos);
    }

    public static void calcularTroco() {

        System.out.print("Digite o valor da compra: ");
        double valorCompra = scanner.nextDouble();

        System.out.print("Digite o valor pago pelo cliente: ");
        double valorPago = scanner.nextDouble();

        double troco = valorPago - valorCompra;

        if (troco < 0) {
            System.out.println("Valor pago insuficiente.");
        } else {
            System.out.println("Troco: R$ " + troco);
        }
    }
}