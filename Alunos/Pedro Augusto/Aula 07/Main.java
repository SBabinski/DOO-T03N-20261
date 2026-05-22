import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static boolean appRodando = true;
    public static Loja lojaDonaGabrielinha = new Loja("My Plant", "Gabrielinha LTDA", "12.345.678/0001-90",
            new Endereco("PR", "Cascavel", "Centro", "1000", "Perto da praça"));

    public static boolean modoTesteAtivo = true;

    public static void main(String[] args) {
        if (modoTesteAtivo) {
            modoTeste();
        }

        while (appRodando) {
            exibirMenu();
            System.out.print("Ação: ");

            int acaoUsuario = scanner.nextInt();
            scanner.nextLine();

            gerenciarAcoes(acaoUsuario);
        }
        scanner.close();
    }

    public static void exibirMenu() {
        System.out.println("\n----- MENU PRINCIPAL -----");
        System.out.println("1 - Calcular o preço total");
        System.out.println("2 - Calcular troco");
        System.out.println("3 - Realizar Venda");
        System.out.println("4 - Consultar Vendas");
        System.out.println("5 - Gerenciar Loja / Pessoas");
        System.out.println("6 - Gerenciar Pedidos");
        System.out.println("7 - Modo Teste (Carregar Dados)");
        System.out.println("8 - Sair");
        System.out.println("--------------------------");
    }

    public static void gerenciarAcoes(int acaoUsuario) {
        switch (acaoUsuario) {
            case 1:
                calcularPrecoTotal();
                break;
            case 2:
                calcularTroco();
                break;
            case 3:
                realizarVenda();
                break;
            case 4:
                consultarVendas();
                break;
            case 5:
                gerenciarLoja();
                break;
            case 6:
                gerenciarPedidos();
                break;
            case 7:
                modoTeste();
                System.out.println("\n[SISTEMA] Dados de teste carregados com sucesso!");
                break;
            case 8:
                appRodando = false;
                break;
            default:
                System.out.println("Ação inválida.");
                break;
        }
    }

    public static void calcularPrecoTotal() {
        System.out.print("Quantidade de plantas: ");
        int qtd = scanner.nextInt();
        System.out.print("Valor da planta: ");
        float valor = scanner.nextFloat();
        System.out.println("Preço total: R$ " + Calculadora.calcularPreco(qtd, valor));
    }

    public static void calcularTroco() {
        System.out.print("Valor pago: ");
        float pago = scanner.nextFloat();
        System.out.print("Valor total: ");
        float total = scanner.nextFloat();
        System.out.println("Troco: R$ " + Calculadora.calcularTroco(pago, total));
    }

    public static void realizarVenda() {
        System.out.print("Quantidade de plantas: ");
        int qtd = scanner.nextInt();
        System.out.print("Valor das plantas: R$ ");
        float valor = scanner.nextFloat();
        System.out.println("Data da venda: ");

        System.out.print("Dia: ");
        int d = scanner.nextInt();

        System.out.print("Mes: ");
        int m = scanner.nextInt();

        System.out.print("Ano: ");
        int a = scanner.nextInt();

        float total = qtd * valor;
        float desc = (qtd > 10) ? Calculadora.calcularDesconto(total) : 0;
        Database.registrarVenda(new Venda(qtd, total - desc, desc, d, m, a));
        System.out.println("Venda registrada!");
    }

    public static void consultarVendas() {
        System.out.println("---------VENDAS----------");
        ArrayList<Venda> vendas = Database.pegarTotalVendas();
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda.");
            System.out.println("-----------------------");
            return;
        } else {
            for (Venda v : vendas)
                v.exibirRegistro();
        }
        System.out.println("-------------------------");
    }

    public static void gerenciarLoja() {
        int opcao = 0;
        while (opcao != 9) {
            System.out.println("\n--- GERENCIAMENTO ---");
            System.out.println("1 - Listar Vendedores");
            System.out.println("2 - Cadastrar Vendedor");
            System.out.println("3 - Listar Clientes");
            System.out.println("4 - Cadastrar Cliente");
            System.out.println("5 - Listar Gerentes");
            System.out.println("6 - Cadastrar Gerente");
            System.out.println("7 - Exibir Dados da Loja");
            System.out.println("8 - Executar Teste ProcessaPedido");
            System.out.println("9 - Voltar");
            System.out.print("Ação: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    listarVendedores();
                    break;
                case 2:
                    cadastrarVendedor();
                    break;
                case 3:
                    listarClientes();
                    break;
                case 4:
                    cadastrarCliente();
                    break;
                case 5:
                    listarGerentes();
                    break;
                case 6:
                    cadastrarGerente();
                    break;
                case 7:
                    lojaDonaGabrielinha.apresentarSe();
                    break;
                case 8:
                    ProcessaPedidoTest.main(null);
                    break;
            }
        }
    }

    public static void listarVendedores() {
        System.out.println("-------------VENDEDORES--------------");
        for (Vendedor v : lojaDonaGabrielinha.getListaDeVendedores()) {
            System.out.println("---------------------------");
            v.apresentarSe();
            System.out.println("Média Salarial: R$ " + v.calcularMedia());
            System.out.println("Bônus: R$ " + v.calcularBonus());
            System.out.println("----------------------------");
        }
    }

    public static void cadastrarVendedor() {
        System.out.print("Nome: ");
        String n = scanner.nextLine();
        System.out.print("Idade: ");
        int i = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Salário Base: ");
        float s = scanner.nextFloat();
        scanner.nextLine();
        Vendedor v = new Vendedor(n, i, lojaDonaGabrielinha, lojaDonaGabrielinha.getEndereco(), s);
        lojaDonaGabrielinha.cadastrarVendedor(v);
    }

    public static void listarClientes() {
        System.out.println("------------CLIENTES-----------");
        for (Cliente c : lojaDonaGabrielinha.getListaDeClientes()) {
            c.apresentarSe();
        }   
        System.out.println("-------------------------");
    }

    public static void cadastrarCliente() {
        System.out.print("Nome: ");
        String n = scanner.nextLine();
        System.out.print("Idade: ");
        int i = scanner.nextInt();
        scanner.nextLine();
        Cliente c = new Cliente(n, i, lojaDonaGabrielinha.getEndereco());
        lojaDonaGabrielinha.cadastrarCliente(c);
    }

    public static void listarGerentes() {
        System.out.println("----------GERENTES-----------");
        for (Gerente g : lojaDonaGabrielinha.getListaDeGerentes()) {
            System.out.println("---------------------");
            g.apresentarSe();
            System.out.println("Média Salarial: R$ " + g.calcularMedia());
            System.out.println("Bônus: R$ " + g.calcularBonus());
            System.out.println("---------------------");
        }
    }

    public static void cadastrarGerente() {
        System.out.print("Nome: ");
        String n = scanner.nextLine();
        System.out.print("Idade: ");
        int i = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Salário Base: ");
        float s = scanner.nextFloat();
        scanner.nextLine();
        Gerente g = new Gerente(n, i, lojaDonaGabrielinha, lojaDonaGabrielinha.getEndereco(), s);
        lojaDonaGabrielinha.cadastrarGerente(g);
    }

    public static void gerenciarPedidos() {
        int opcao = 0;
        while (opcao != 4) {
            System.out.println("\n--- GERENCIAR PEDIDOS ---");
            System.out.println("1 - Criar Pedido");
            System.out.println("2 - Criar Pedido (Dados Fakes)");
            System.out.println("3 - Listar Pedidos");
            System.out.println("4 - Voltar");
            System.out.print("Ação: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    criarPedido();
                    break;
                case 2:
                    criarPedidoFake();
                    break;
                case 3:
                    listarPedidos();
                    break;
            }
        }
    }

    public static void criarPedido() {
        if (lojaDonaGabrielinha.getListaDeClientes().isEmpty()
                || lojaDonaGabrielinha.getListaDeVendedores().isEmpty()) {
            System.out.println("Erro: É necessário ter pelo menos um cliente e um vendedor cadastrados.");
            return;
        }

        System.out.println("--- Novo Pedido ---");

        // Seleção de Cliente
        System.out.println("Selecione o Cliente:");
        for (int i = 0; i < lojaDonaGabrielinha.getListaDeClientes().size(); i++) {
            System.out.println(i + " - " + lojaDonaGabrielinha.getListaDeClientes().get(i).getNome());
        }
        int indiceCliente = scanner.nextInt();
        Cliente cliente = lojaDonaGabrielinha.getListaDeClientes().get(indiceCliente);

        // Seleção de Vendedor
        System.out.println("Selecione o Vendedor:");
        for (int i = 0; i < lojaDonaGabrielinha.getListaDeVendedores().size(); i++) {
            System.out.println(i + " - " + lojaDonaGabrielinha.getListaDeVendedores().get(i).getNome());
        }
        int indiceVendedor = scanner.nextInt();
        Vendedor vendedor = lojaDonaGabrielinha.getListaDeVendedores().get(indiceVendedor);

        // Adição de Itens
        ArrayList<Item> itens = new ArrayList<>();
        boolean adicionandoItens = true;
        int idItem = 1;

        while (adicionandoItens) {
            scanner.nextLine(); // Limpa buffer
            System.out.print("Nome do Item: ");
            String nomeItem = scanner.nextLine();
            System.out.print("Tipo do Item: ");
            String tipoItem = scanner.nextLine();
            System.out.print("Valor do Item: R$ ");
            float valorItem = scanner.nextFloat();

            itens.add(new Item(idItem++, nomeItem, tipoItem, valorItem));

            System.out.print("Deseja adicionar mais um item? (s/n): ");
            String continuar = scanner.next();
            if (continuar.equalsIgnoreCase("n")) {
                adicionandoItens = false;
            }
        }

        System.out.print("Informe um ID para o pedido: ");
        int idPedido = scanner.nextInt();

        Pedido novoPedido = new Pedido(idPedido, cliente, vendedor, lojaDonaGabrielinha, itens);

        System.out.println("Processando pedido...");
        ProcessaPedido processador = new ProcessaPedido();
        processador.processar(novoPedido);

        Database.registrarPedido(novoPedido);
        System.out.println("Pedido registrado com sucesso!");
    }

    public static void listarPedidos() {
        ArrayList<Pedido> pedidos = Database.pegarTodosPedidos();
        System.out.println("----------PEDIDOS------------");
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido cadastrado.");
            System.out.println("--------------------------");
            return;
        } else {
            for (Pedido p : pedidos) {
                System.out.println("------------------------");
                p.gerarDescricaoVenda();
                System.out.println("------------------------");
            }
        }
    }

    public static void criarPedidoFake() {
        if (lojaDonaGabrielinha.getListaDeClientes().isEmpty()
                || lojaDonaGabrielinha.getListaDeVendedores().isEmpty()) {
            System.out.println("Erro: Cadastre ao menos um cliente e um vendedor primeiro.");
            return;
        }

        Cliente c = lojaDonaGabrielinha.getListaDeClientes().get(0);
        Vendedor v = lojaDonaGabrielinha.getListaDeVendedores().get(0);
        ArrayList<Item> itens = new ArrayList<>();
        itens.add(new Item(1, "Orquídea", "Flor", 45.0f));
        itens.add(new Item(2, "Vaso de Barro", "Acessório", 20.0f));

        Pedido p = new Pedido(101, c, v, lojaDonaGabrielinha, itens);
        p.gerarDescricaoVenda();

        ProcessaPedido processador = new ProcessaPedido();
        processador.processar(p);

        Database.registrarPedido(p);
    }

    public static void modoTeste() {
        // Endereço base
        Endereco end = new Endereco("SC", "Florianópolis", "Centro", "123", "Bloco A");
        lojaDonaGabrielinha.setEndereco(end);

        // Vendedores
        Vendedor v1 = new Vendedor("Sandrine", 28, lojaDonaGabrielinha, end, 2500f);
        v1.pagarSalario(2500f);
        v1.pagarSalario(2700f);
        v1.pagarSalario(2600f);

        Vendedor v2 = new Vendedor("Marcos", 35, lojaDonaGabrielinha,
                new Endereco("SC", "São José", "Kobrasol", "45", null), 2200f);
        v2.pagarSalario(2200f);
        v2.pagarSalario(2300f);
        v2.pagarSalario(2250f);

        Vendedor v3 = new Vendedor("Luana", 22, lojaDonaGabrielinha,
                new Endereco("SC", "Palhoça", "Pagani", "10", "Apto 201"), 2350f);
        v3.pagarSalario(2350f);
        v3.pagarSalario(2400f);
        v3.pagarSalario(2500f);

        lojaDonaGabrielinha.cadastrarVendedor(v1);
        lojaDonaGabrielinha.cadastrarVendedor(v2);
        lojaDonaGabrielinha.cadastrarVendedor(v3);

        // Clientes
        Cliente c1 = new Cliente("Beatriz Oliveira", 30, end);
        Cliente c2 = new Cliente("Fernando Costa", 45, new Endereco("SC", "Florianópolis", "Trindade", "500", null));
        Cliente c3 = new Cliente("Patrícia Santos", 27, new Endereco("SC", "Biguaçu", "Centro", "32", "Casa"));
        Cliente c4 = new Cliente("Roberto Lima", 52, new Endereco("SC", "Florianópolis", "Campeche", "99", null));

        lojaDonaGabrielinha.cadastrarCliente(c1);
        lojaDonaGabrielinha.cadastrarCliente(c2);
        lojaDonaGabrielinha.cadastrarCliente(c3);
        lojaDonaGabrielinha.cadastrarCliente(c4);

        // Gerentes
        Gerente g1 = new Gerente("Dona Gabrielinha", 50, lojaDonaGabrielinha, end, 5000f);
        Gerente g2 = new Gerente("Seu Jorge", 55, lojaDonaGabrielinha,
                new Endereco("SC", "Florianópolis", "Itacorubi", "88", null), 4800f);

        lojaDonaGabrielinha.cadastrarGerente(g1);
        lojaDonaGabrielinha.cadastrarGerente(g2);

        // Vendas Históricas
        Database.registrarVenda(new Venda(5, 150f, 0f, 10, 4, 2026));
        Database.registrarVenda(new Venda(12, 324f, 36f, 12, 4, 2026));
        Database.registrarVenda(new Venda(3, 90f, 0f, 15, 4, 2026));

        // Pedidos Iniciais
        ArrayList<Item> itensPedido1 = new ArrayList<>();
        itensPedido1.add(new Item(1, "Rosa Vermelha", "Flor", 15.0f));
        itensPedido1.add(new Item(2, "Vaso Decorativo", "Acessório", 35.0f));
        Pedido p1 = new Pedido(201, c1, v1, lojaDonaGabrielinha, itensPedido1);
        Database.registrarPedido(p1);

        ArrayList<Item> itensPedido2 = new ArrayList<>();
        itensPedido2.add(new Item(1, "Samambaia", "Planta", 45.0f));
        Pedido p2 = new Pedido(202, c2, v2, lojaDonaGabrielinha, itensPedido2);
        Database.registrarPedido(p2);
    }
}
