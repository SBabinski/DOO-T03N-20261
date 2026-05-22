import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {

    static Loja loja = new Loja(
            "My Plant",
            "Plantas LTDA",
            "55.861.684/0001-68",
            new Endereco("PR", "Cascavel", "Centro", "Avenida Brasil", "1000", "")
    );

    static ArrayList<Pedido> pedidos = new ArrayList<>();
    static Calculadora calc = new Calculadora();
    static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n================ ============");
            System.out.println("Bem-vindo à Loja My Plant!");
            System.out.println("=========== MENU ============");
            System.out.println("[1]  Calcular preço total");
            System.out.println("[2]  Calcular troco");
            System.out.println("[3]  Histórico de vendas");
            System.out.println("[4]  Buscar vendas por data");
            System.out.println("[5]  Buscar vendas por mês");
            System.out.println("[6]  Cadastrar cliente");
            System.out.println("[7]  Cadastrar vendedor");
            System.out.println("[8]  Cadastrar gerente");
            System.out.println("[9]  Listar clientes");
            System.out.println("[10] Listar vendedores");
            System.out.println("[11] Listar gerentes");
            System.out.println("[12] Cadastrar pedido");
            System.out.println("[13] Listar pedidos");
            System.out.println("[14] Buscar pedidos por dia");
            System.out.println("[15] Buscar pedidos por mês");
            System.out.println("[16] Dados da loja");
            System.out.println("[0]  Sair");
            System.out.print("Escolha: ");
            opcao = scan.nextInt();

            switch (opcao) {
                case 1:
                    calc.calcPreco(scan);
                    break;

                case 2:
                    calc.troco(scan);
                    break;

                case 3:
                    calc.mostrarHistoricoVendas();
                    break;

                case 4:
                    calc.buscarVendasPorData(scan);
                    break;

                case 5:
                    calc.buscarVendasPorMes(scan);
                    break;

                case 6:
                    cadastrarCliente(scan);
                    break;

                case 7:
                    cadastrarVendedor(scan);
                    break;

                case 8:
                    cadastrarGerente(scan);
                    break;

                case 9:
                    listarClientes();
                    break;

                case 10:
                    listarVendedores();
                    break;

                case 11:
                    listarGerentes();
                    break;

                case 12:
                    cadastrarPedido(scan);
                    break;

                case 13:
                    listarPedidos();
                    break;

                case 14:
                    buscarPedidosPorDia(scan);
                    break;

                case 15:
                    buscarPedidosPorMes(scan);
                    break;

                case 16:
                    loja.apresentarSe();
                    break;

                case 0:
                    System.out.println("Obrigado(a), volte sempre!");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }

        scan.close();
    }

    public static void cadastrarCliente(Scanner scan) {
        System.out.println("\n===== CADASTRO DE CLIENTE =====");
        scan.nextLine();

        System.out.print("Nome: ");
        String nome = scan.nextLine();

        System.out.print("Idade: ");
        int idade = Integer.parseInt(scan.nextLine());

        Endereco endereco = lerEndereco(scan);

        loja.getClientes().add(new Cliente(nome, idade, endereco));
        System.out.println("Cliente cadastrado com sucesso!");
    }

    public static void cadastrarVendedor(Scanner scan) {
        System.out.println("\n===== CADASTRO DE VENDEDOR =====");
        scan.nextLine();

        System.out.print("Nome: ");
        String nome = scan.nextLine();

        System.out.print("Idade: ");
        int idade = Integer.parseInt(scan.nextLine());

        Endereco endereco = lerEndereco(scan);

        System.out.print("Salário base: R$ ");
        double salarioBase = Double.parseDouble(scan.nextLine());

        loja.getVendedores().add(new Vendedor(nome, idade, loja.getNomeFantasia(), endereco, salarioBase));
        System.out.println("Vendedor cadastrado com sucesso!");
    }

    public static void cadastrarGerente(Scanner scan) {
        System.out.println("\n===== CADASTRO DE GERENTE =====");
        scan.nextLine();

        System.out.print("Nome: ");
        String nome = scan.nextLine();

        System.out.print("Idade: ");
        int idade = Integer.parseInt(scan.nextLine());

        Endereco endereco = lerEndereco(scan);

        System.out.print("Salário base: R$ ");
        double salarioBase = Double.parseDouble(scan.nextLine());

        loja.getGerentes().add(new Gerente(nome, idade, loja.getNomeFantasia(), endereco, salarioBase));
        System.out.println("Gerente cadastrado com sucesso!");
    }

    public static void listarClientes() {
        if (loja.getClientes().isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        System.out.println("\n===== CLIENTES =====");
        for (Cliente c : loja.getClientes()) c.apresentarSe();
    }

    public static void listarVendedores() {
        if (loja.getVendedores().isEmpty()) {
            System.out.println("Nenhum vendedor cadastrado.");
            return;
        }
        System.out.println("\n===== VENDEDORES =====");
        for (Vendedor v : loja.getVendedores()) v.apresentarSe();
    }

    public static void listarGerentes() {
        if (loja.getGerentes().isEmpty()) {
            System.out.println("Nenhum gerente cadastrado.");
            return;
        }
        System.out.println("\n===== GERENTES =====");
        for (Gerente g : loja.getGerentes()) g.apresentarSe();
    }

    public static void cadastrarPedido(Scanner scan) {
        System.out.println("\n===== CADASTRAR PEDIDO =====");

        if (loja.getClientes().isEmpty()) {
            System.out.println("Nenhum cliente cadastrado. Cadastre um cliente primeiro.");
            return;
        }
        if (loja.getVendedores().isEmpty()) {
            System.out.println("Nenhum vendedor cadastrado. Cadastre um vendedor primeiro.");
            return;
        }

        System.out.println("Selecione o cliente:");
        for (int i = 0; i < loja.getClientes().size(); i++) {
            System.out.println("  [" + i + "] " + loja.getClientes().get(i).getNome());
        }
        System.out.print("Escolha: ");
        int idxCliente = scan.nextInt();
        Cliente cliente = loja.getClientes().get(idxCliente);

        System.out.println("Selecione o vendedor:");
        for (int i = 0; i < loja.getVendedores().size(); i++) {
            System.out.println("  [" + i + "] " + loja.getVendedores().get(i).getNome());
        }
        System.out.print("Escolha: ");
        int idxVendedor = scan.nextInt();
        Vendedor vendedor = loja.getVendedores().get(idxVendedor);

        System.out.print("ID do pedido: ");
        int idPedido = scan.nextInt();

        System.out.print("Data do pedido (dd/MM/yyyy): ");
        LocalDate dataVenda = LocalDate.parse(scan.next(), fmt);

        System.out.print("Quantidade de plantas: ");
        int quantidade = scan.nextInt();

        System.out.print("Validade da reserva (em horas): ");
        int horas = scan.nextInt();
        scan.nextLine();

        Date agora = new Date();
        Date vencimento = new Date(agora.getTime() + (long) horas * 3600 * 1000);

        ArrayList<Item> itens = new ArrayList<>();
        int idItem = 1;
        String continuar = "s";
        while (continuar.equalsIgnoreCase("s")) {
            System.out.println("--- Item " + idItem + " ---");

            System.out.print("Nome do item: ");
            String nomeItem = scan.nextLine();

            System.out.print("Tipo: ");
            String tipo = scan.nextLine();

            System.out.print("Valor: R$ ");
            double valor = Double.parseDouble(scan.nextLine());

            itens.add(new Item(idItem++, nomeItem, tipo, valor));

            System.out.print("Adicionar outro item? (s/n): ");
            continuar = scan.nextLine();
        }

        double totalItens = 0;
        for (Item item : itens) totalItens += item.getValor();

        double desconto = 0;
        if (quantidade >= 10) {
            desconto = totalItens * 0.05;
            System.out.printf("Desconto de 5%% aplicado: -R$ %.2f%n", desconto);
        }

        ProcessaPedido pp = new ProcessaPedido();
        Pedido pedido = pp.processar(idPedido, agora, null, vencimento,
                cliente, vendedor, loja, quantidade, desconto, dataVenda,
                itens.toArray(new Item[0]));

        pedidos.add(pedido);

        calc.registrarVenda(new Scanner(dataVenda.format(fmt)), quantidade);
    }

    public static void listarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido registrado.");
            return;
        }
        System.out.println("\n===== PEDIDOS =====");
        for (Pedido p : pedidos) p.gerarDescricaoVenda();
    }

    public static void buscarPedidosPorDia(Scanner scan) {
        System.out.print("Data (dd/MM/yyyy): ");
        LocalDate data = LocalDate.parse(scan.next(), fmt);
        long total = pedidos.stream().filter(p -> p.getDataVenda().equals(data)).count();
        System.out.println("Total de pedidos no dia " + data.format(fmt) + ": " + total);
    }

    public static void buscarPedidosPorMes(Scanner scan) {
        System.out.print("Mês (número): ");
        int mes = scan.nextInt();
        long total = pedidos.stream().filter(p -> p.getDataVenda().getMonthValue() == mes).count();
        System.out.println("Total de pedidos no mês " + mes + ": " + total);
    }

    public static Endereco lerEndereco(Scanner scan) {
        System.out.print("Estado (UF): ");
        String estado = scan.nextLine();

        System.out.print("Cidade: ");
        String cidade = scan.nextLine();

        System.out.print("Bairro: ");
        String bairro = scan.nextLine();

        System.out.print("Rua: ");
        String rua = scan.nextLine();

        System.out.print("Número: ");
        String numero = scan.nextLine();

        System.out.print("Complemento (deixe vazio se não houver): ");
        String complemento = scan.nextLine();

        return new Endereco(estado, cidade, bairro, rua, numero, complemento);
    }

}