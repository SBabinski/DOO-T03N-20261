import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Calculadora {

    public static void calcPrecoTotal(Scanner sc, ArrayList<Map<String, Object>> vendas) {
        System.out.print("Insira a quantidade de plantas vendidas: ");
        int qtdVendidas = sc.nextInt();

        System.out.print("Insira o valor unitário da planta: ");
        float precoPlanta = sc.nextFloat();

        double valorTotal = 0;
        double valorDesconto = 0;

        if (qtdVendidas > 10) {
            double valorOriginal = precoPlanta * qtdVendidas;
            valorDesconto = valorOriginal * (5.0 / 100.0);
            System.out.println("O cliente recebeu um desconto de R$ " + valorDesconto);
            valorTotal = valorOriginal - valorDesconto;
        } else {
            valorTotal = precoPlanta * qtdVendidas;
        }

        LocalDate dataVenda = LocalDate.now();

        Map<String, Object> venda = new HashMap<>();
        venda.put("quantidade", qtdVendidas);
        venda.put("valorTotal", valorTotal);
        venda.put("valorDesconto", valorDesconto);
        venda.put("data", dataVenda);

        vendas.add(venda);

        System.out.printf("O Valor total dessa venda é R$ %.2f\n", valorTotal);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Venda registrada na data: " + dataVenda.format(formatter) + "\n");
    }

    public static void calcTroco(Scanner sc) {
        System.out.print("Insira o valor recebido do cliente: ");
        float valorRecebido = sc.nextFloat();

        System.out.print("Insira o valor total da venda: ");
        float valorVenda = sc.nextFloat();

        float troco = valorRecebido - valorVenda;

        System.out.printf("O troco a dar ao cliente é R$ %.2f\n\n", troco);
    }

    public static void visualizarHistorico(ArrayList<Map<String, Object>> vendas) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Data       | Qtd | Total   | Descontos");

        for (Map<String, Object> venda : vendas) {
            LocalDate data = (LocalDate) venda.get("data");
            int quantidade = (int) venda.get("quantidade");
            double valorTotal = (double) venda.get("valorTotal");
            double valorDesconto = (double) venda.get("valorDesconto");

            System.out.println(
                    data.format(formatter) + " | " +
                            quantidade + " | " +
                            valorTotal + " | " +
                            valorDesconto
            );
        }
        System.out.println();
    }

    public static void buscarVendasDiaMes(Scanner sc, ArrayList<Map<String, Object>> vendas) {
        sc.nextLine();
        System.out.print("Digite a data (dd/MM/yyyy): ");
        String dataInput = sc.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataBusca = LocalDate.parse(dataInput, formatter);

        int totalVendas = 0;

        for (Map<String, Object> venda : vendas) {
            LocalDate data = (LocalDate) venda.get("data");
            int quantidade = (int) venda.get("quantidade");

            if (data.equals(dataBusca)) {
                totalVendas += quantidade;
            }
        }

        System.out.println("Total de plantas vendidas em " + dataInput + ": " + totalVendas + "\n");
    }

    public static int verificarNovaOperacao(Scanner sc) {
        System.out.println("Deseja continuar com uma nova conta?");
        System.out.println("[0] - Não");
        System.out.println("[1] - Sim");
        return sc.nextInt();
    }

    public static void main(String[] args) {


        ArrayList<Vendedor> vendedores = new ArrayList<>();
        ArrayList<Cliente> clientes = new ArrayList<>();
        ArrayList<Double> salarios = new ArrayList<>();
        salarios.add(3200.0);
        salarios.add(2900.0);
        salarios.add(1900.0);

        vendedores.add(new Vendedor("João", 20, "My Plant", "Cascavel", "Centro", "Rua Engenharia", 1500.0, salarios));
        vendedores.add(new Vendedor("Maria", 25, "My Plant", "Cascavel", "Faculdade", "Rua Azenha", 1800.0, salarios));
        vendedores.add(new Vendedor("Jorge", 27, "My Plant", "Foz do Iguaçu", "Centro", "Rua Nereu Ramos", 2500.0, salarios));

        clientes.add(new Cliente("Gabriel", 32, "Cascavel", "Parque Verde", "Avenida das Torres"));
        clientes.add(new Cliente("Vitória", 18, "Blumenau", "Vila Nova", "Catarinenses"));

        Loja loja = new Loja("My Plant", "PLANTAS LTDA", "12345678000145", "Cascavel", "Centro", "Avenida Brasil", vendedores, clientes);


        Scanner sc = new Scanner(System.in);
        ArrayList<Map<String, Object>> vendas = new ArrayList<>();

        System.out.println("Bem Vindo(a) à Calculadora Loja de Plantas");

        int opcao = 0;
        while (opcao != 8) {
            System.out.println("Selecione a opção desejada: ");
            System.out.println("\nDIGITE APENAS O NÚMERO\n");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Visualizar Histórico");
            System.out.println("[4] - Buscar Vendas por Dia e Mês");
            System.out.println("[5] - VENDEDOR");
            System.out.println("[6] - CLIENTE");
            System.out.println("[7] - LOJA");
            System.out.println("[8] - Sair");

            int opcaoEscolhida = sc.nextInt();
            int operacao = 1;

            switch (opcaoEscolhida) {
                case 1:
                    calcPrecoTotal(sc, vendas);
                    operacao = verificarNovaOperacao(sc);
                    break;
                case 2:
                    calcTroco(sc);
                    operacao = verificarNovaOperacao(sc);
                    break;
                case 3:
                    visualizarHistorico(vendas);
                    operacao = verificarNovaOperacao(sc);
                    break;
                case 4:
                    buscarVendasDiaMes(sc, vendas);
                    operacao = verificarNovaOperacao(sc);
                    break;
                case 5:
                    System.out.println("\nEscolha uma opção para VENDEDOR: ");
                    System.out.println("[1] - Apresentar-se");
                    System.out.println("[2] - Calcular Média de salários");
                    System.out.println("[3] - Calcular Bônus dos Vendedores");
                    int opcaoVendedor = sc.nextInt();

                    switch (opcaoVendedor) {
                        case 1:
                            for (Vendedor vendedor:vendedores) {
                                vendedor.apresentarse();
                            }
                            System.out.println("\n");
                            break;
                        case 2:
                            for (Vendedor vendedor:vendedores) {
                                vendedor.calcularMedia();
                            }
                            System.out.println("\n");
                            break;
                        case 3:
                            for (Vendedor vendedor:vendedores) {
                                vendedor.calcularBonus();
                            }
                            System.out.println("\n");
                            break;
                        default:
                            System.out.println("Você escolheu um número errado!");
                            break;
                    }
                    break;
                case 6:
                    System.out.println("\nEscolha uma opção para CLIENTE: ");
                    System.out.println("[1] - Apresentar-se");
                    int opcaoCliente = sc.nextInt();

                    switch (opcaoCliente) {
                        case 1:
                            for (Cliente cliente:clientes) {
                                cliente.apresentarse();
                            }
                            System.out.println("\n");
                            break;
                        default:
                            System.out.println("Você escolheu um número errado!");
                            break;
                    }
                    break;
                case 7:
                    System.out.println("\nEscolha uma opção para LOJA: ");
                    System.out.println("[1] - Apresentar-se");
                    System.out.println("[2] - Quantidade Clientes");
                    System.out.println("[3] - Quantidade Vendedores");
                    int opcaoLoja = sc.nextInt();

                    switch (opcaoLoja) {
                        case 1:
                            loja.apresentarse();
                            System.out.println("\n");
                            break;
                        case 2:
                            loja.contarClientes();
                            System.out.println("\n");
                            break;
                        case 3:
                            loja.contarVendedores();
                            System.out.println("\n");
                            break;
                        default:
                            System.out.println("Você escolheu um número errado!");
                            break;
                    }
                    break;
                case 8:
                    System.out.println("Encerrando Programa");
                    opcao = 8;
                    break;
                default:
                    System.out.println("Opção inválida! Digite apenas os números disponíveis no menu.");
            }

            if (operacao == 0) {
                System.out.println("Encerrando Programa");
                break;
            }

            opcao = opcaoEscolhida;
        }

        sc.close();
    }
}