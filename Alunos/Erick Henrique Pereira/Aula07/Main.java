import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scan = new Scanner(System.in);
    private static final List<Calculadora> vendas = new ArrayList<>();
    private static List<Vendedor> vendedores = new ArrayList<>();
    private static List<Cliente> clientes;
    private static List<Loja> lojas;
    private static Loja loja1;
    private static Loja loja2;
    private static Loja loja3;

    public static void main(String[] args) {
        Endereco endereco1 = new Endereco("São Paulo", "São Paulo", "Jardins", "Av. Paulista", 1000, "Apartamento");
        Endereco endereco2 = new Endereco("São Paulo", "São Paulo", "Vila Madalena", "Rua Harmonia", 250, "Casa");
        Endereco endereco3 = new Endereco("São Paulo", "São Paulo", "Pinheiros", "Rua dos Pinheiros", 500, "Chácara");
        Endereco endereco4 = new Endereco("São Paulo", "São Paulo", "Moema", "Av. Moema", 300, "Apartamento");
        Endereco endereco5 = new Endereco("São Paulo", "São Paulo", "Itaim Bibi", "Rua Funchal", 200, "Casa");
        Endereco endereco6 = new Endereco("São Paulo", "São Paulo", "Vila Olímpia", "Av. das Nações Unidas", 1500, "Apartamento");
        Endereco endereco7 = new Endereco("São Paulo", "São Paulo", "Bela Vista", "Rua Augusta", 800, "Casa");
        Endereco endereco8 = new Endereco("São Paulo", "São Paulo", "Liberdade", "Rua Galvão Bueno", 400, "Apartamento");
        Endereco endereco9 = new Endereco("São Paulo", "São Paulo", "Brás", "Rua do Brás", 600, "Casa");
       
        Cliente cliente1 = new Cliente("Carlos", 28, endereco1);
        Cliente cliente2 = new Cliente("Mariana", 32, endereco2);
        Cliente cliente3 = new Cliente("João", 24, endereco3);

        clientes = List.of(cliente1, cliente2, cliente3);

        loja1 = new Loja("Loja 1", "Razão Social 1", "12345678000100", endereco1, null, null, null);
        loja2 = new Loja("Loja 2", "Razão Social 2", "22345678000100", endereco2, null, null, null);
        loja3 = new Loja("Loja 3", "Razão Social 3", "32345678000100", endereco3, null, null, null);

        lojas = List.of(loja1, loja2, loja3);

        List<Cliente> clientes1 = new ArrayList<>();
        List<Cliente> clientes2 = new ArrayList<>();
        List<Cliente> clientes3 = new ArrayList<>();

        clientes1.add(cliente1);
        clientes2.add(cliente2);
        clientes3.add(cliente3);

        loja1.setClientes(clientes1);
        loja2.setClientes(clientes2);
        loja3.setClientes(clientes3);

        List<Vendedor> vendedores1 = new ArrayList<>();
        List<Vendedor> vendedores2 = new ArrayList<>();
        List<Vendedor> vendedores3 = new ArrayList<>();

        Vendedor vendedor1 = new Vendedor("Ana", 30, loja1, endereco4, 2500.0,
                List.of(2500.0, 2600.0, 2700.0));

        Vendedor vendedor2 = new Vendedor("Bruno", 27, loja2, endereco5, 2600.0,
                List.of(2600.0, 2700.0, 2800.0));

        Vendedor vendedor3 = new Vendedor("Fernanda", 35, loja3, endereco6, 2700.0,
                List.of(2700.0, 2800.0, 2900.0));

        vendedores1.add(vendedor1);
        vendedores2.add(vendedor2);
        vendedores3.add(vendedor3);

        vendedores.add(vendedor1);
        vendedores.add(vendedor2);
        vendedores.add(vendedor3);

        loja1.setVendedores(vendedores1);
        loja2.setVendedores(vendedores2);
        loja3.setVendedores(vendedores3);

        Gerente gerente1 = new Gerente("Ricardo", 40, endereco7, loja1, 5000.0,
                List.of(5000.0, 5200.0, 5500.0));

        Gerente gerente2 = new Gerente("Sofia", 38, endereco8, loja2, 5200.0,
                List.of(5200.0, 5400.0, 5800.0));

        Gerente gerente3 = new Gerente("Marcos", 45, endereco9, loja3, 5500.0,
                List.of(5500.0, 5700.0, 6000.0));

        loja1.setGerente(gerente1);
        loja2.setGerente(gerente2);
        loja3.setGerente(gerente3);

        System.out.println("Bem-vindo ao sistema de gerenciamento de negócio!");
        int opcao;

        do {
            opcao = mostrarMenu();
            switch (opcao) {
                case 1 -> menuGerente();
                case 2 -> menuVendedores();
                case 3 -> menuClientes();
                case 4 -> menuLojas();
                case 5 -> menuCalculadora();
                case 6 -> criarPedido();
                case 7 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }



        } while (opcao != 7);
    }

    public static void criarPedido() {
        System.out.println("\nCriando pedido de teste...");
        ProcessaPedido processaPedido = new ProcessaPedido();
        Pedido pedido = processaPedido.processar(
                1,
                clientes.get(0),
                loja1.getVendedores().get(0),
                loja1,
                List.of(new Item(1, "Produto A", "Tipo 1", 10.0)),
                LocalDate.now().plusDays(7)
        );
        System.out.println("Valor total do pedido: R$ " + pedido.calcularValorTotal());
        System.out.println("Descrição da venda: " + pedido.gerarDescricaoVenda());
    }

    private static void menuGerente() {
        int opcao;
        do {
            System.out.println("\n--- MENU GERENTES ---");
            System.out.println("1 - Apresentar gerentes");
            System.out.println("2 - Calcular média salarial");
            System.out.println("3 - Calcular bônus");
            System.out.println("4 - Voltar");

            opcao = scan.nextInt();
            switch (opcao) {
                case 1 -> {
                    for (Loja loja : lojas) {
                        System.out.println(loja.getGerente().apresentarse());
                    }
                }
                case 2 -> {
                    for (Loja loja : lojas) {
                        System.out.println("Média salarial de " +
                                loja.getGerente().getName() + ": " +
                                loja.getGerente().calcularMedia());
                    }
                }
                case 3 -> {
                    for (Loja loja : lojas) {
                        System.out.println("Bônus de " +
                                loja.getGerente().getName() + ": " +
                                loja.getGerente().calcularBonus());
                    }
                }
            }
        } while (opcao != 4);
    }

    private static void menuVendedores() {
        int opcao;
        do {
            System.out.println("\n--- MENU VENDEDORES ---");
            System.out.println("1 - Apresentar vendedores");
            System.out.println("2 - Calcular média salarial");
            System.out.println("3 - Calcular bônus");
            System.out.println("4 - Voltar");

            opcao = scan.nextInt();
            switch (opcao) {
                case 1 -> vendedores.forEach(v -> System.out.println(v.apresentarse()));

                case 2 -> vendedores.forEach(v ->
                        System.out.println("Média salarial de " +
                                v.getName() + ": " + v.calcularMedia()));

                case 3 -> vendedores.forEach(v ->
                        System.out.println("Bônus de " +
                                v.getName() + ": " + v.calcularBonus()));
            }




        } while (opcao != 4);
    }

    private static void menuClientes() {
        int opcao;
        do {
            System.out.println("\n--- MENU CLIENTES ---");
            System.out.println("1 - Apresentar clientes");
            System.out.println("2 - Voltar");

            opcao = scan.nextInt();

            if (opcao == 1) {
                clientes.forEach(c -> System.out.println(c.apresentarse()));
            }
        } while (opcao != 2);
    }
    private static void menuLojas() {
        int opcao;
        do {
            System.out.println("\n--- MENU LOJAS ---");
            System.out.println("1 - Apresentar lojas");
            System.out.println("2 - Contar clientes");
            System.out.println("3 - Contar vendedores");
            System.out.println("4 - Voltar");
            opcao = scan.nextInt();
            switch (opcao) { 
                //É professor, lambda e méthod reference é vida kkkk
                case 1 -> lojas.forEach(Loja::apresentarse);

                case 2 -> lojas.forEach(l ->
                        System.out.println(l.getNomeFantasia() +
                                " possui " + l.contarClientes() + " cliente(s)."));

                case 3 -> lojas.forEach(l ->
                        System.out.println(l.getNomeFantasia() +
                                " possui " + l.contarVendedores() + " vendedor(es)."));
            }

        } while (opcao != 4);
    }

    private static void menuCalculadora() {

        int opcao;
        do {
            System.out.println("\n--- MENU CALCULADORA ---");
            System.out.println("1 - Registrar venda");
            System.out.println("2 - Calcular troco");
            System.out.println("3 - Consultar vendas");
            System.out.println("4 - Voltar");

            opcao = scan.nextInt();
            switch (opcao) {
                case 1 -> registrarVenda();
                case 2 -> calcularTroco();
                case 3 -> consultarVendas();
            }

        } while (opcao != 4);
    }

    private static void registrarVenda() {
        try {
            System.out.print("Quantidade: ");
            int quantidade = scan.nextInt();

            System.out.print("Preço unitário: ");
            double preco = scan.nextDouble();

            double total = quantidade * preco;
            double totalFinal = quantidade > 10 ? total * 0.95 : total;

            System.out.print("Data da venda (dd/MM/yyyy): ");
            String texto = scan.next();

            LocalDate dataVenda = LocalDate.parse(
                    texto,
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );

            Calculadora venda = new Calculadora();
            venda.setQuantidade(quantidade);
            venda.setPreco(preco);
            venda.setDescontoAplicado(totalFinal);
            venda.setSaleDate(dataVenda);
            vendas.add(venda);
            System.out.println("Venda registrada!");

        } catch (Exception e) {
            System.out.println("Erro ao registrar venda.");
            scan.nextLine();
        }
    }

    private static void calcularTroco() {
        System.out.print("Valor pago: ");
        double pago = scan.nextDouble();
        System.out.print("Valor total: ");
        double total = scan.nextDouble();
        System.out.println("Troco: R$ " + (pago - total));
    }

    private static void consultarVendas() {
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }
        System.out.println("1 - Consultar por dia");
        System.out.println("2 - Consultar por mês");
        System.out.println("3 - Mostrar todas");
        int op = scan.nextInt();
        scan.nextLine();
        switch (op) {
            case 1 -> consultationDay(vendas);
            case 2 -> consultationMonth(vendas);
            case 3 -> consultationAll(vendas);
        }
    }

    private static void consultationAll(List<Calculadora> vendas) {
        vendas.forEach(v ->
                System.out.println("Qtd: " + v.getQuantidade() +
                        " | Data: " + v.getSaleDate() +
                        " | Total: R$ " + v.getDescontoAplicado()));
    }
    private static void consultationDay(List<Calculadora> vendas) {
        try {
            System.out.print("Digite a data (dd/MM/yyyy): ");
            String texto = scan.nextLine();

            LocalDate data = LocalDate.parse(
                    texto,
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );
            vendas.stream()
                    .filter(v -> v.getSaleDate().isEqual(data))
                    .forEach(v ->
                            System.out.println("Qtd: " + v.getQuantidade() +
                                    " | Total: R$ " + v.getDescontoAplicado()));

        } catch (Exception e) {
            System.out.println("Data inválida.");
        }
    }
    private static void consultationMonth(List<Calculadora> vendas) {
        try {
            System.out.print("Digite mês/ano (MM/yyyy): ");
            String texto = scan.nextLine();
            LocalDate ref = LocalDate.parse(
                    "01/" + texto,
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );
            vendas.stream()
                    .filter(v ->
                            v.getSaleDate().getMonth() == ref.getMonth() &&
                                    v.getSaleDate().getYear() == ref.getYear())
                    .forEach(v ->
                            System.out.println("Dia " +
                                    v.getSaleDate().getDayOfMonth() +
                                    " | Total: R$ " + v.getDescontoAplicado()));
        } catch (Exception e) {
            System.out.println("Formato inválido.");
        }
    }
    private static int mostrarMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1 - Gerentes");
        System.out.println("2 - Vendedores");
        System.out.println("3 - Clientes");
        System.out.println("4 - Lojas");
        System.out.println("5 - Calculadora");
        System.out.println("6 - Criar pedido teste");
        System.out.println("7 - Sair");
        return scan.nextInt();
    }
}