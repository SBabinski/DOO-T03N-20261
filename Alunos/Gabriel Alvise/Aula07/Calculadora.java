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
        ArrayList<Gerente> gerentes = new ArrayList<>();
        ArrayList<Double> salariosVendedores = new ArrayList<>();
        ArrayList<Double> salariosGerentes = new ArrayList<>();
        ArrayList<Loja> lojas = new ArrayList<>();
        ArrayList<Item> itens = new ArrayList<>();

        salariosVendedores.add(3200.0);
        salariosVendedores.add(2900.0);
        salariosVendedores.add(1900.0);

        salariosGerentes.add(6000.0);
        salariosGerentes.add(5500.0);
        salariosGerentes.add(5600.0);

        Endereco enderecoV1 = new Endereco("Paraná", "Cascavel", "Centro", "Rua das Palmeiras Douradas", 345, "Casa"); // Endereço Vendedor 1
        Endereco enderecoV2 = new Endereco("Paraná", "Cascavel", "Região do Lago", "Rua Horizonte Azul", 728, "Apartamento"); // Endereço Vendedor 2
        Endereco enderecoV3 = new Endereco("Paraná", "Cascavel", "Maria Luiza", "Rua Flor do Cerrado", 1097, "Sobrado"); // Endereço Vendedor 3
        Endereco enderecoG1 = new Endereco("Paraná", "Cascavel", "Neva", "Rua Monte Sereno", 985, "Sobrado"); // Endereço Gerente 1
        Endereco enderecoG2 = new Endereco("Paraná", "Cascavel", "Santa Felicidade", "Rua Jardim das Acácias", 240, "Apartamento"); // Endereço Gerente 2
        Endereco enderecoL1 = new Endereco("Paraná", "Cascavel", "Centro", "Rua Vale Encantado", 536, "Casa"); // Endereço Loja 1
        Endereco enderecoL2 = new Endereco("Paraná", "Cascavel", "Maria Luiza", "Rua Sol Nascente", 170, "Casa"); // Endereço Loja 2
        Endereco enderecoC1 = new Endereco("Paraná", "Cascavel", "Cancelli", "Rua Pedra Branca", 491, "Casa"); // Endereço Cliente 1
        Endereco enderecoC2 = new Endereco("Paraná", "Cascavel", "Maria Luiza", "Rua Bela Vista do Sul", 371, "Sobrado"); // Endereço Cliente 2

        vendedores.add(new Vendedor("João", 20, "My Plant - Matriz", enderecoV1, 1500.0, salariosVendedores));
        vendedores.add(new Vendedor("Maria", 25, "My Plant - Matriz", enderecoV2, 1800.0, salariosVendedores));
        vendedores.add(new Vendedor("Jorge", 27, "My Plant - Filial", enderecoV3, 2500.0, salariosVendedores));

        gerentes.add(new Gerente("Marcelo", 45, "My Plant - Matriz", enderecoG1, 5000.0, salariosGerentes));
        gerentes.add(new Gerente("Rogério", 38, "My Plant - Matriz", enderecoG2, 4800.0, salariosGerentes));

        clientes.add(new Cliente("Gabriel", 32, enderecoC1));
        clientes.add(new Cliente("Vitória", 18, enderecoC2));

        lojas.add(new Loja("My Plant - Matriz", "PLANTAS LTDA", "12345678000145", enderecoL1, vendedores, clientes));
        lojas.add(new Loja("My Plant - Filial", "PLANTAS LTDA", "12345678000280", enderecoL2, vendedores, clientes));

        itens.add(new Item(1, "Mouse", "Periférico", 80.00));
        itens.add(new Item(2, "Teclado", "Periférico", 150.00));

        Scanner sc = new Scanner(System.in);
        ArrayList<Map<String, Object>> vendas = new ArrayList<>();

        System.out.println("Bem Vindo(a) à Calculadora Loja de Plantas");

        int opcao = 0;
        while (opcao != 10) {
            System.out.println("Selecione a opção desejada: ");
            System.out.println("\nDIGITE APENAS O NÚMERO\n");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Visualizar Histórico");
            System.out.println("[4] - Buscar Vendas por Dia e Mês");
            System.out.println("[5] - VENDEDOR");
            System.out.println("[6] - CLIENTE");
            System.out.println("[7] - LOJA");
            System.out.println("[8] - GERENTE");
            System.out.println("[9] - Criar Pedido");
            System.out.println("[10] - Sair");

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
                                vendedor.apresentarSe();
                            }
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
                                cliente.apresentarSe();
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
                            for (Loja loja:lojas) {
                                loja.apresentarSe();
                            }
                            System.out.println("\n");
                            break;
                        case 2:
                            for (Loja loja:lojas) {
                                loja.contarClientes();
                                break;
                            }
                                System.out.println("\n");
                            break;
                        case 3:
                            for (Loja loja:lojas) {
                                loja.contarVendedores();
                                break;
                            }
                                System.out.println("\n");
                            break;
                        default:
                            System.out.println("Você escolheu um número errado!");
                            break;
                    }
                    break;
                case 8:
                    System.out.println("\nEscolha uma opção para GERENTE: ");
                    System.out.println("[1] - Apresentar-se");
                    System.out.println("[2] - Calcular Média de salários");
                    System.out.println("[3] - Calcular Bônus dos Gerentes");
                    int opcaoGerente = sc.nextInt();

                    switch (opcaoGerente) {
                        case 1:
                            for (Gerente gerente:gerentes) {
                                gerente.apresentarSe();
                            }
                            System.out.println("\n");
                            break;
                        case 2:
                            for (Gerente gerente:gerentes) {
                                gerente.calcularMedia();
                            }
                            System.out.println("\n");
                            break;
                        case 3:
                            for (Gerente gerente:gerentes) {
                                gerente.calcularBonus();
                            }
                            System.out.println("\n");
                            break;
                        default:
                            System.out.println("Você escolheu um número errado!");
                            break;
                    }
                    break;
                case 9:
                    ProcessaPedido processaPedido = new ProcessaPedido();

                    Item[] itensPedido = itens.toArray(new Item[0]);

                    Date dataVencimentoReserva = new Date(System.currentTimeMillis() + 86400000);

                    Pedido pedido = processaPedido.processar(1, clientes.get(0), vendedores.get(0), lojas.get(0), itensPedido, dataVencimentoReserva);

                    for (Item item:itensPedido) {
                        item.gerarDescricao();
                        System.out.println();
                    }

                    pedido.gerarDescricaoVenda();
                    break;
                case 10:
                    System.out.println("Encerrando Programa");
                    opcao = 10;
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