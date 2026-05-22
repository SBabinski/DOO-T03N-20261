import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Venda> vendas = new ArrayList<>();
        Calculadora calculadora = new Calculadora();

        // NOVO: criando loja
        Loja loja = new Loja("My Plant", "My Plant LTDA", "123456789",
                "Curitiba", "Centro", "Rua das Flores");

        int opcao;

        do {
            System.out.println("\n=== Loja de Plantas ===");
            System.out.println("1. Calcular preço total");
            System.out.println("2. Calcular troco");
            System.out.println("3. Listar vendas");
            System.out.println("4. Buscar vendas por dia");
            System.out.println("5. Buscar vendas por mês");
            System.out.println("6. Cadastrar vendedor");
            System.out.println("7. Cadastrar cliente");
            System.out.println("8. Ver dados da loja");
            System.out.println("9. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {

                case 1:
                    System.out.print("Digite o nome da planta: ");
                    String nomePlanta = scanner.nextLine();

                    System.out.print("Digite o preço unitário da planta: ");
                    double precoUnitario = scanner.nextDouble();

                    System.out.print("Digite a quantidade: ");
                    int quantidade = scanner.nextInt();

                    Planta planta = new Planta(nomePlanta, precoUnitario);

                    double totalSemDesconto = precoUnitario * quantidade;
                    double precoTotal = calculadora.calcularPrecoTotal(precoUnitario, quantidade);

                    double desconto = totalSemDesconto - precoTotal;

                    LocalDate dataAtual = LocalDate.now();
                    Venda venda = new Venda(quantidade, precoTotal, desconto, dataAtual);
                    vendas.add(venda);

                    System.out.println("Planta: " + planta.getNome());
                    System.out.println("Preço final: R$ " + precoTotal);

                    if (desconto > 0) {
                        System.out.println("Desconto aplicado: R$ " + desconto);
                    }

                    break;

                case 2:
                    System.out.print("Digite o valor pago: ");
                    double valorPago = scanner.nextDouble();

                    System.out.print("Digite o preço total da compra: ");
                    double precoTotalCompra = scanner.nextDouble();

                    double troco = calculadora.calcularTroco(valorPago, precoTotalCompra);

                    if (troco < 0) {
                        System.out.println("Valor pago insuficiente. Faltam R$ " + (-troco));
                    } else {
                        System.out.println("Troco: R$ " + troco);
                    }
                    break;

                case 3:
                    System.out.println("\n=== Registro de Vendas ===");

                    for (Venda v : vendas) {
                        v.exibirVenda();
                    }
                    break;

                case 4:

                    System.out.print("Digite a data (AAAA-MM-DD): ");
                    String dataStr = scanner.nextLine();

                    LocalDate dataBusca = LocalDate.parse(dataStr);

                    int totalDia = 0;

                    for (Venda v : vendas) {
                        if (v.getData().equals(dataBusca)) {
                            totalDia++;
                        }
                    }

                    System.out.println("Total de vendas no dia " + dataBusca + ": " + totalDia);
                    break;

                case 5:
                    System.out.print("Digite o mês (1-12):");
                    int mes = scanner.nextInt();

                    int totalMes = 0;

                    for (Venda v : vendas) {
                        if (v.getData().getMonthValue() == mes) {
                            totalMes++;
                        }
                    }
                    System.out.println("Total de vendas no mês " + mes + ": " + totalMes);
                    break;

                case 6:
                    System.out.print("Nome do vendedor: ");
                    String nomeV = scanner.nextLine();

                    System.out.print("Idade: ");
                    int idadeV = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Salário base: ");
                    double salario = scanner.nextDouble();
                    scanner.nextLine();

                    Vendedor vendedor = new Vendedor(nomeV, idadeV, "My Plant",
                            "Curitiba", "Centro", "Rua X", salario);

                    loja.adicionarVendedor(vendedor);

                    System.out.println("Vendedor cadastrado!");
                    break;

                case 7:
                    System.out.print("Nome do cliente: ");
                    String nomeC = scanner.nextLine();

                    System.out.print("Idade: ");
                    int idadeC = scanner.nextInt();
                    scanner.nextLine();

                    Cliente cliente = new Cliente(nomeC, idadeC,
                            "Curitiba", "Centro", "Rua Y");

                    loja.adicionarCliente(cliente);

                    System.out.println("Cliente cadastrado!");
                    break;

                case 8:
                    loja.apresentarSe();
                    System.out.println("Total de clientes: " + loja.contarClientes());
                    System.out.println("Total de vendedores: " + loja.contarVendedores());
                    break;

                case 9:
                    System.out.println("Encerrando o programa...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 9);

        scanner.close();
    }
}
