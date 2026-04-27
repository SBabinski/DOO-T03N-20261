import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Aula07 {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final ArrayList<Venda> vendas = new ArrayList<Venda>();

    public static void main(String[] args) {
        Loja loja = criarLoja();
        int opcao;

        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opcao: ");

            switch (opcao) {
                case 1:
                    loja.apresentarse();
                    break;
                case 2:
                    apresentarClientes(loja);
                    break;
                case 3:
                    apresentarVendedores(loja);
                    break;
                case 4:
                    apresentarGerentes(loja);
                    break;
                case 5:
                    calcularVendaComDesconto();
                    break;
                case 6:
                    listarVendasRegistradas();
                    break;
                case 7:
                    buscarVendasPorData();
                    break;
                case 8:
                    criarPedidoFake(loja);
                    break;
                case 9:
                    testarConfirmacaoPagamento();
                    break;
                case 0:
                    System.out.println("Encerrando sistema.");
                    break;
                default:
                    System.out.println("Opcao invalida.");
                    break;
            }

            System.out.println();
        } while (opcao != 0);
    }

    private static void exibirMenu() {
        System.out.println("===== MENU AULA 07 =====");
        System.out.println("1 apresentar loja");
        System.out.println("2 apresentar clientes");
        System.out.println("3 apresentar vendedores");
        System.out.println("4 apresentar gerentes");
        System.out.println("5 calcular venda com desconto");
        System.out.println("6 listar vendas registradas");
        System.out.println("7 buscar vendas por data");
        System.out.println("8 criar pedido fake");
        System.out.println("9 testar confirmacao pagamento");
        System.out.println("0 sair");
    }

    private static Loja criarLoja() {
        Endereco enderecoLoja = new Endereco("PR", "Cascavel", "Centro", "123", "Rua das Flores");
        Loja loja = new Loja("My Plant", "My Plant LTDA", "12.345.678/0001-99", enderecoLoja);

        Cliente cliente1 = new Cliente("Joao", 20, new Endereco("PR", "Cascavel", "Centro", "100", "Rua A"));
        Cliente cliente2 = new Cliente("Maria", 25, new Endereco("PR", "Cascavel", "Bairro Alto", "200", "Rua B"));
        loja.clientes = new Cliente[]{cliente1, cliente2};

        double[] salariosVendedor1 = {2000.0, 2100.0, 2200.0};
        double[] salariosVendedor2 = {1800.0, 1850.0, 1900.0};

        Vendedor vendedor1 = new Vendedor(
                "Carlos",
                30,
                new Endereco("PR", "Cascavel", "Centro", "300", "Rua X"),
                loja,
                2000.0,
                salariosVendedor1
        );

        Vendedor vendedor2 = new Vendedor(
                "Ana",
                28,
                new Endereco("PR", "Cascavel", "Cancelli", "400", "Rua Y"),
                loja,
                1800.0,
                salariosVendedor2
        );

        loja.vendedores = new Vendedor[]{vendedor1, vendedor2};

        double[] salariosGerente = {4500.0, 4600.0, 4700.0};
        Gerente gerente1 = new Gerente(
                "Fernanda",
                35,
                new Endereco("PR", "Cascavel", "Centro", "500", "Rua G"),
                loja,
                4500.0,
                salariosGerente
        );

        loja.gerentes = new Gerente[]{gerente1};

        return loja;
    }

    private static void apresentarClientes(Loja loja) {
        if (loja.clientes == null || loja.clientes.length == 0) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        for (int i = 0; i < loja.clientes.length; i++) {
            loja.clientes[i].apresentarse();
            System.out.println();
        }
    }

    private static void apresentarVendedores(Loja loja) {
        if (loja.vendedores == null || loja.vendedores.length == 0) {
            System.out.println("Nenhum vendedor cadastrado.");
            return;
        }

        for (int i = 0; i < loja.vendedores.length; i++) {
            loja.vendedores[i].apresentarse();
            System.out.println("Media dos salarios: R$ " + loja.vendedores[i].calcularMedia());
            System.out.println("Bonus: R$ " + loja.vendedores[i].calcularBonus());
            System.out.println();
        }
    }

    private static void apresentarGerentes(Loja loja) {
        if (loja.gerentes == null || loja.gerentes.length == 0) {
            System.out.println("Nenhum gerente cadastrado.");
            return;
        }

        for (int i = 0; i < loja.gerentes.length; i++) {
            loja.gerentes[i].apresentarse();
            System.out.println("Media dos salarios: R$ " + loja.gerentes[i].calcularMedia());
            System.out.println("Bonus: R$ " + loja.gerentes[i].calcularBonus());
            System.out.println();
        }
    }

    private static void calcularVendaComDesconto() {
        int quantidade = lerInteiro("Quantidade de plantas: ");
        double valorUnitario = lerDouble("Valor unitario: ");
        LocalDate dataVenda = lerData("Data da venda (dd/MM/yyyy) ou enter para hoje: ");

        Venda venda = new Venda(quantidade, valorUnitario, dataVenda);
        vendas.add(venda);

        System.out.println("Venda registrada.");
        venda.apresentarVenda(FORMATTER);
    }

    private static void listarVendasRegistradas() {
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }

        for (int i = 0; i < vendas.size(); i++) {
            System.out.println("Venda " + (i + 1));
            vendas.get(i).apresentarVenda(FORMATTER);
            System.out.println();
        }
    }

    private static void buscarVendasPorData() {
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }

        System.out.println("1 buscar por dia");
        System.out.println("2 buscar por mes");
        int opcao = lerInteiro("Escolha o tipo de busca: ");

        if (opcao == 1) {
            LocalDate data = lerDataObrigatoria("Informe a data (dd/MM/yyyy): ");
            int total = calcularQuantidadePorDia(data);
            System.out.println("Quantidade total vendida no dia " + data.format(FORMATTER) + ": " + total);
        } else if (opcao == 2) {
            int mes = lerInteiro("Informe o mes (1 a 12): ");
            int ano = lerInteiro("Informe o ano: ");
            int total = calcularQuantidadePorMes(mes, ano);
            System.out.println("Quantidade total vendida em " + mes + "/" + ano + ": " + total);
        } else {
            System.out.println("Opcao invalida.");
        }
    }

    private static int calcularQuantidadePorDia(LocalDate data) {
        int total = 0;

        for (int i = 0; i < vendas.size(); i++) {
            if (vendas.get(i).dataVenda.equals(data)) {
                total = total + vendas.get(i).quantidade;
            }
        }

        return total;
    }

    private static int calcularQuantidadePorMes(int mes, int ano) {
        int total = 0;
        YearMonth periodo = YearMonth.of(ano, mes);

        for (int i = 0; i < vendas.size(); i++) {
            YearMonth dataVenda = YearMonth.from(vendas.get(i).dataVenda);

            if (dataVenda.equals(periodo)) {
                total = total + vendas.get(i).quantidade;
            }
        }

        return total;
    }

    private static void criarPedidoFake(Loja loja) {
        Item item1 = new Item(1, "Samambaia", "Planta ornamental", 35.0);
        Item item2 = new Item(2, "Orquidea", "Flor", 50.0);
        Item item3 = new Item(3, "Vaso medio", "Acessorio", 25.0);
        Item[] itens = {item1, item2, item3};

        ProcessaPedido processaPedido = new ProcessaPedido();
        Pedido pedido = processaPedido.processar(1, loja.clientes[0], loja.vendedores[0], loja, itens);

        if (pedido != null) {
            System.out.println(pedido.gerarDescricaoVenda());
        }
    }

    private static void testarConfirmacaoPagamento() {
        ProcessaPedido processaPedido = new ProcessaPedido();
        processaPedido.testarConfirmarPagamento();
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String valor = scanner.nextLine().trim();

            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException erro) {
                System.out.println("Digite um numero inteiro valido.");
            }
        }
    }

    private static double lerDouble(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String valor = scanner.nextLine().trim().replace(",", ".");

            try {
                return Double.parseDouble(valor);
            } catch (NumberFormatException erro) {
                System.out.println("Digite um valor valido.");
            }
        }
    }

    private static LocalDate lerData(String mensagem) {
        System.out.print(mensagem);
        String valor = scanner.nextLine().trim();

        if (valor.isEmpty()) {
            return LocalDate.now();
        }

        try {
            return LocalDate.parse(valor, FORMATTER);
        } catch (DateTimeParseException erro) {
            System.out.println("Data invalida. Usando data atual.");
            return LocalDate.now();
        }
    }

    private static LocalDate lerDataObrigatoria(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String valor = scanner.nextLine().trim();

            try {
                return LocalDate.parse(valor, FORMATTER);
            } catch (DateTimeParseException erro) {
                System.out.println("Data invalida. Use o formato dd/MM/yyyy.");
            }
        }
    }
}
