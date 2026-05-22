import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static ArrayList<Venda> vendas = new ArrayList<>();
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static Loja lojaCadastrada;
    private static Vendedor vendedorCadastrado;
    private static Gerente gerenteCadastrado;
    private static final ArrayList<Pedido> pedidos = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        do {
            System.out.println("\n|===== CALCULADORA DA DONA GABRIELINHA =====|");
            System.out.println("|[1] - Cadastrar Loja/Clientes/Vendedor/Gerente |");
            System.out.println("|[2] - Calcular Preco Total                      |");
            System.out.println("|[3] - Calcular Troco                            |");
            System.out.println("|[4] - Listar Vendas de Plantas                  |");
            System.out.println("|[5] - Buscar Total de Vendas por Dia            |");
            System.out.println("|[6] - Buscar Total de Vendas por Mes            |");
            System.out.println("|[7] - Criar Pedido Fake                         |");
            System.out.println("|[8] - Executar Teste de Pedido                  |");
            System.out.println("|[9] - Sair                                      |");
            System.out.print("Escolha uma opcao: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                demonstrarClasses(scanner);
            }
            if (opcao == 2) {
                calcularPreco(scanner);
            }
            if (opcao == 3) {
                calcularTroco(scanner);
            }
            if (opcao == 4) {
                listarVendas();
            }
            if (opcao == 5) {
                buscarVendasPorDia(scanner);
            }
            if (opcao == 6) {
                buscarVendasPorMes(scanner);
            }
            if (opcao == 7) {
                criarPedidoFake();
            }
            if (opcao == 8) {
                ProcessaPedidoTest.executar();
            }

        } while (opcao != 9);

        System.out.println("Sistema encerrado. Ate logo, Dona Gabrielinha!");
        scanner.close();
    }

    public static void demonstrarClasses(Scanner scanner) {
        System.out.println("Cadastro da loja");
        System.out.print("Nome fantasia da loja: ");
        String nomeFantasia = scanner.nextLine();
        System.out.print("Razao social: ");
        String razaoSocial = scanner.nextLine();
        System.out.print("CNPJ: ");
        String cnpj = scanner.nextLine();
        Endereco enderecoLoja = lerEndereco(scanner);

        Loja loja = new Loja(nomeFantasia, razaoSocial, cnpj, enderecoLoja);

        System.out.print("Quantidade de clientes: ");
        int quantidadeClientes = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= quantidadeClientes; i++) {
            System.out.println("Cliente " + i + ":");
            System.out.print("Nome: ");
            String nomeCliente = scanner.nextLine();
            System.out.print("Idade: ");
            int idadeCliente = scanner.nextInt();
            scanner.nextLine();
            Endereco enderecoCliente = lerEndereco(scanner);

            Cliente cliente = new Cliente(nomeCliente, idadeCliente, enderecoCliente);
            loja.adicionarCliente(cliente);
        }

        System.out.println("Dados do vendedor:");
        Vendedor vendedor = criarVendedor(scanner, loja);
        loja.adicionarVendedor(vendedor);

        System.out.println("Dados do gerente:");
        Gerente gerente = criarGerente(scanner, loja);

        lojaCadastrada = loja;
        vendedorCadastrado = vendedor;
        gerenteCadastrado = gerente;

        loja.apresentarse();
        System.out.println("Quantidade de clientes: " + loja.contarClientes());
        System.out.println("Quantidade de vendedores: " + loja.contarVendedores());
        vendedor.apresentarse();
        System.out.printf("Media dos salarios do vendedor: R$ %.2f%n", vendedor.calcularMedia());
        System.out.printf("Bonus do vendedor: R$ %.2f%n", vendedor.calcularBonus());
        gerente.apresentarse();
        System.out.printf("Media dos salarios do gerente: R$ %.2f%n", gerente.calcularMedia());
        System.out.printf("Bonus do gerente: R$ %.2f%n", gerente.calcularBonus());
        System.out.println();
    }

    private static Vendedor criarVendedor(Scanner scanner, Loja loja) {
        System.out.print("Nome: ");
        String nomeVendedor = scanner.nextLine();
        System.out.print("Idade: ");
        int idadeVendedor = scanner.nextInt();
        scanner.nextLine();
        Endereco enderecoVendedor = lerEndereco(scanner);
        System.out.print("Salario base: ");
        double salarioBase = scanner.nextDouble();
        scanner.nextLine();

        ArrayList<Double> salariosRecebidos = lerSalarios(scanner);
        return new Vendedor(nomeVendedor, idadeVendedor, loja, enderecoVendedor, salarioBase, salariosRecebidos);
    }

    private static Gerente criarGerente(Scanner scanner, Loja loja) {
        System.out.print("Nome: ");
        String nomeGerente = scanner.nextLine();
        System.out.print("Idade: ");
        int idadeGerente = scanner.nextInt();
        scanner.nextLine();
        Endereco enderecoGerente = lerEndereco(scanner);
        System.out.print("Salario base: ");
        double salarioBase = scanner.nextDouble();
        scanner.nextLine();

        ArrayList<Double> salariosRecebidos = lerSalarios(scanner);
        return new Gerente(nomeGerente, idadeGerente, loja, enderecoGerente, salarioBase, salariosRecebidos);
    }

    private static ArrayList<Double> lerSalarios(Scanner scanner) {
        ArrayList<Double> salariosRecebidos = new ArrayList<>();
        System.out.println("Digite 3 salarios recebidos:");
        for (int i = 1; i <= 3; i++) {
            System.out.print("Salario " + i + ": ");
            salariosRecebidos.add(scanner.nextDouble());
            scanner.nextLine();
        }
        return salariosRecebidos;
    }

    private static Endereco lerEndereco(Scanner scanner) {
        System.out.print("Estado: ");
        String estado = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();
        System.out.print("Rua: ");
        String rua = scanner.nextLine();
        System.out.print("Numero: ");
        String numero = scanner.nextLine();
        System.out.print("Complemento: ");
        String complemento = scanner.nextLine();

        return new Endereco(estado, cidade, bairro, rua, numero, complemento);
    }

    public static void calcularPreco(Scanner scanner) {
        System.out.print("Digite a data da venda (dd/MM/yyyy): ");
        LocalDate dataVenda = lerData(scanner);

        System.out.print("Digite a quantidade de plantas: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o preco unitario da planta: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();

        double desconto = 0;
        double total;
        if (quantidade >= 10) {
            System.out.println("Parabens, voce ganhou um desconto de 5%");
            desconto = preco * 0.05;
            total = quantidade * (preco - desconto);
        } else {
            total = quantidade * preco;
        }

        Venda venda = new Venda(quantidade, preco, total, desconto, dataVenda);
        vendas.add(venda);

        System.out.printf("Preco total da venda: R$ %.2f%n", total);
        System.out.println("Venda registrada automaticamente em: " + dataVenda.format(FORMATTER));
    }

    public static void calcularTroco(Scanner scanner) {
        System.out.print("Digite o valor pago: ");
        double valorPago = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Digite o valor da compra: ");
        double valorCompra = scanner.nextDouble();
        scanner.nextLine();

        double troco = valorPago - valorCompra;
        System.out.printf("Troco: R$ %.2f%n", troco);
    }

    private static void listarVendas() {
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda de plantas cadastrada.");
            return;
        }

        for (Venda p : vendas) {
            System.out.println(p);
        }
    }

    public static void buscarVendasPorDia(Scanner scanner) {
        System.out.print("Digite o dia que deseja buscar (dd/MM/yyyy): ");
        LocalDate dataBusca = lerData(scanner);

        int totalVendas = 0;
        for (Venda p : vendas) {
            if (p.getDataVenda() != null && p.getDataVenda().equals(dataBusca)) {
                totalVendas++;
            }
        }

        System.out.println("Data: " + dataBusca.format(FORMATTER));
        System.out.println("Total de vendas no dia: " + totalVendas);
    }

    public static void buscarVendasPorMes(Scanner scanner) {
        System.out.print("Digite o mes que deseja buscar (MM/yyyy): ");
        String entrada = scanner.nextLine().trim();

        int mes;
        int ano;
        try {
            String[] partes = entrada.split("/");
            if (partes.length != 2) {
                throw new IllegalArgumentException("Entrada invalida");
            }
            mes = Integer.parseInt(partes[0]);
            ano = Integer.parseInt(partes[1]);
        } catch (Exception e) {
            System.out.println("Formato invalido! Use MM/yyyy.");
            return;
        }

        int totalVendas = 0;
        for (Venda p : vendas) {
            if (p.getDataVenda() != null
                    && p.getDataVenda().getMonthValue() == mes
                    && p.getDataVenda().getYear() == ano) {
                totalVendas++;
            }
        }

        System.out.println("Mes: " + entrada);
        System.out.println("Total de vendas no mes: " + totalVendas);
    }

    private static LocalDate lerData(Scanner scanner) {
        LocalDate data = null;
        while (data == null) {
            String entrada = scanner.nextLine().trim();
            try {
                data = LocalDate.parse(entrada, FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.print("Data invalida! Use o formato dd/MM/yyyy: ");
            }
        }
        return data;
    }

    private static void criarPedidoFake() {
        if (lojaCadastrada == null || vendedorCadastrado == null) {
            System.out.println("Cadastre a loja, clientes e vendedor antes de criar um pedido.");
            return;
        }
        if (lojaCadastrada.getClientes() == null || lojaCadastrada.getClientes().isEmpty()) {
            System.out.println("Cadastre pelo menos um cliente.");
            return;
        }

        Cliente cliente = lojaCadastrada.getClientes().get(0);
        List<Item> itens = new ArrayList<>();
        itens.add(new Item(1, "Samambaia", "Planta", 35.0));
        itens.add(new Item(2, "Vaso Ceramica", "Acessorio", 40.0));
        itens.add(new Item(3, "Terra Adubada", "Insumo", 18.5));

        ProcessaPedido processaPedido = new ProcessaPedido();
        Pedido pedido = processaPedido.processar(
                1001,
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now().plusDays(3),
                cliente,
                vendedorCadastrado,
                lojaCadastrada,
                itens
        );

        pedidos.add(pedido);
        System.out.println("Pedido criado com dados fakes:");
        System.out.println(pedido.gerarDescricaoVenda());
    }
}
