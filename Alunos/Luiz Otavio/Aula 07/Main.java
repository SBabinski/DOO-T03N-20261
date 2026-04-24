import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {

    static ArrayList<Pedido> pedidos = new ArrayList<>();

    static Loja loja = new Loja(
            "My Plant",
            "My Plant LTDA",
            "65.226.552/0001-08",
            new Endereco("PR", "Cascavel", "Centro", "Rua Manaus", "3238", "")
    );

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        MenuInicial(scan);
    }

    public static void MenuInicial(Scanner scan) {
        int escolha;
        do {
            System.out.println("\n========= MY PLANT — MENU =========");
            System.out.println("[1]  Calcular preco total");
            System.out.println("[2]  Calcular troco");
            System.out.println("[3]  Registros de pedidos");
            System.out.println("[4]  Buscar pedidos por dia");
            System.out.println("[5]  Buscar pedidos por mes");
            System.out.println("[6]  Cadastrar cliente");
            System.out.println("[7]  Cadastrar vendedor");
            System.out.println("[8]  Listar clientes");
            System.out.println("[9]  Listar vendedores");
            System.out.println("[10] Mostrar dados da loja");
            System.out.println("[11] Cadastrar gerente");
            System.out.println("[12] Listar gerentes");
            System.out.println("[13] Cadastrar pedido");
            System.out.println("[0]  Sair");
            System.out.print("Escolha: ");

            escolha = scan.nextInt();

            switch (escolha) {
                case 1:  CalcularTotal(scan);       break;
                case 2:  CalcularTroco(scan);       break;
                case 3:  MostrarPedidos();          break;
                case 4:  BuscarPorDia(scan);        break;
                case 5:  BuscarPorMes(scan);        break;
                case 6:  CadastrarCliente(scan);    break;
                case 7:  CadastrarVendedor(scan);   break;
                case 8:  ListarClientes();          break;
                case 9:  ListarVendedores();        break;
                case 10: MostrarDadosLoja();        break;
                case 11: CadastrarGerente(scan);    break;
                case 12: ListarGerentes();          break;
                case 13: CadastrarPedido(scan);     break;
            }

        } while (escolha != 0);

        System.out.println("Obrigado(a), volte sempre!");
    }

    public static void CadastrarCliente(Scanner scan) {
        System.out.println("===== CADASTRO DE CLIENTE =====");
        scan.nextLine();

        System.out.print("Nome: ");
        String nome = scan.nextLine();

        System.out.print("Idade: ");
        int idade = scan.nextInt();
        scan.nextLine();

        System.out.print("Estado (UF): ");
        String estado = scan.nextLine();

        System.out.print("Cidade: ");
        String cidade = scan.nextLine();

        System.out.print("Bairro: ");
        String bairro = scan.nextLine();

        System.out.print("Rua: ");
        String rua = scan.nextLine();

        System.out.print("Numero: ");
        String numero = scan.nextLine();

        System.out.print("Complemento: ");
        String complemento = scan.nextLine();

        Endereco endereco = new Endereco(estado, cidade, bairro, rua, numero, complemento);
        loja.clientes.add(new Cliente(nome, idade, endereco));
        System.out.println("Cliente cadastrado!");
    }

    public static void CadastrarVendedor(Scanner scan) {
        System.out.println("===== CADASTRO DE VENDEDOR =====");
        scan.nextLine();

        System.out.print("Nome: ");
        String nome = scan.nextLine();

        System.out.print("Idade: ");
        int idade = scan.nextInt();
        scan.nextLine();

        System.out.print("Estado (UF): ");
        String estado = scan.nextLine();

        System.out.print("Cidade: ");
        String cidade = scan.nextLine();

        System.out.print("Bairro: ");
        String bairro = scan.nextLine();

        System.out.print("Rua: ");
        String rua = scan.nextLine();

        System.out.print("Numero: ");
        String numero = scan.nextLine();

        System.out.print("Complemento: ");
        String complemento = scan.nextLine();

        System.out.print("Salario base: ");
        double salarioBase = scan.nextDouble();

        Endereco endereco = new Endereco(estado, cidade, bairro, rua, numero, complemento);
        loja.vendedores.add(new Vendedor(nome, idade, loja.nomeFantasia, endereco, salarioBase));
        System.out.println("Vendedor cadastrado!");
    }

    public static void CadastrarGerente(Scanner scan) {
        System.out.println("===== CADASTRO DE GERENTE =====");
        scan.nextLine();

        System.out.print("Nome: ");
        String nome = scan.nextLine();

        System.out.print("Idade: ");
        int idade = scan.nextInt();
        scan.nextLine();

        System.out.print("Estado (UF): ");
        String estado = scan.nextLine();

        System.out.print("Cidade: ");
        String cidade = scan.nextLine();

        System.out.print("Bairro: ");
        String bairro = scan.nextLine();

        System.out.print("Rua: ");
        String rua = scan.nextLine();

        System.out.print("Numero: ");
        String numero = scan.nextLine();

        System.out.print("Complemento: ");
        String complemento = scan.nextLine();

        System.out.print("Salario base: ");
        double salarioBase = scan.nextDouble();

        Endereco endereco = new Endereco(estado, cidade, bairro, rua, numero, complemento);
        loja.gerentes.add(new Gerente(nome, idade, loja.nomeFantasia, endereco, salarioBase));
        System.out.println("Gerente cadastrado!");
    }

    public static void ListarClientes() {
        if (loja.clientes.isEmpty()) { System.out.println("Nenhum cliente cadastrado."); return; }
        System.out.println("===== CLIENTES =====");
        for (Pessoa p : loja.clientes) p.apresentarSe();
    }

    public static void ListarVendedores() {
        if (loja.vendedores.isEmpty()) { System.out.println("Nenhum vendedor cadastrado."); return; }
        System.out.println("===== VENDEDORES =====");
        for (Pessoa p : loja.vendedores) p.apresentarSe();
    }

    public static void ListarGerentes() {
        if (loja.gerentes.isEmpty()) { System.out.println("Nenhum gerente cadastrado."); return; }
        System.out.println("===== GERENTES =====");
        for (Pessoa p : loja.gerentes) p.apresentarSe();
    }

    public static void CalcularTotal(Scanner scan) {
        System.out.println("Digite a data (dd/MM/yyyy):");
        String dataTexto = scan.next();
        LocalDate data = LocalDate.parse(dataTexto, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.println("Quantidade:");
        int quantidade = scan.nextInt();

        System.out.println("Valor unitario:");
        double valor = scan.nextDouble();

        double total = quantidade * valor;
        double desconto = 0;

        if (quantidade >= 10) {
            desconto = total * 0.05;
            total -= desconto;
            System.out.println("Desconto de 5% aplicado!");
        }

        System.out.printf("Total: R$ %.2f%n", total);
    }

    public static void CalcularTroco(Scanner scan) {
        System.out.println("Pago:");
        double pago = scan.nextDouble();

        System.out.println("Compra:");
        double compra = scan.nextDouble();

        if (pago < compra) {
            System.out.println("Valor insuficiente");
        } else {
            System.out.printf("Troco: R$ %.2f%n", (pago - compra));
        }
    }

    public static void MostrarPedidos() {
        if (pedidos.isEmpty()) { System.out.println("Nenhum pedido registrado."); return; }
        System.out.println("===== PEDIDOS =====");
        for (Pedido p : pedidos) p.gerarDescricaoVenda();
    }

    public static void BuscarPorDia(Scanner scan) {
        System.out.println("Data (dd/MM/yyyy):");
        LocalDate data = LocalDate.parse(scan.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        long total = pedidos.stream().filter(p -> p.getDataVenda().equals(data)).count();
        System.out.println("Total de pedidos no dia: " + total);
    }

    public static void BuscarPorMes(Scanner scan) {
        System.out.println("Mes (numero):");
        int mes = scan.nextInt();

        long total = pedidos.stream().filter(p -> p.getDataVenda().getMonthValue() == mes).count();
        System.out.println("Total de pedidos no mes: " + total);
    }

    public static void MostrarDadosLoja() {
        System.out.println("\n=== DADOS DA LOJA ===");
        System.out.println("Nome Fantasia : " + loja.nomeFantasia);
        System.out.println("Razao Social  : " + loja.razaoSocial);
        System.out.println("CNPJ          : " + loja.cnpj);
        loja.endereco.apresentarLogradouro();

        System.out.println("\n=== GERENTES ===");
        for (Pessoa p : loja.gerentes) p.apresentarSe();

        System.out.println("\n=== VENDEDORES ===");
        for (Pessoa p : loja.vendedores) p.apresentarSe();

        System.out.println("\n=== CLIENTES ===");
        for (Pessoa p : loja.clientes) p.apresentarSe();
    }

    public static void CadastrarPedido(Scanner scan) {
        System.out.println("===== CADASTRAR PEDIDO =====");

        if (loja.clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado. Cadastre um cliente primeiro.");
            return;
        }
        if (loja.vendedores.isEmpty()) {
            System.out.println("Nenhum vendedor cadastrado. Cadastre um vendedor primeiro.");
            return;
        }

        System.out.println("Selecione o cliente:");
        for (int i = 0; i < loja.clientes.size(); i++) {
            System.out.println("[" + i + "] " + loja.clientes.get(i).getNome());
        }
        int idxCliente = scan.nextInt();
        Cliente cliente = loja.clientes.get(idxCliente);

        System.out.println("Selecione o vendedor:");
        for (int i = 0; i < loja.vendedores.size(); i++) {
            System.out.println("[" + i + "] " + loja.vendedores.get(i).getNome());
        }
        int idxVendedor = scan.nextInt();
        Vendedor vendedor = loja.vendedores.get(idxVendedor);

        System.out.print("ID do pedido: ");
        int idPedido = scan.nextInt();

        System.out.print("Data do pedido (dd/MM/yyyy): ");
        LocalDate dataVenda = LocalDate.parse(scan.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.print("Quantidade de itens: ");
        int quantidade = scan.nextInt();

        System.out.print("Validade da reserva (horas): ");
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

            System.out.print("Valor: ");
            double valor = scan.nextDouble();
            scan.nextLine();

            itens.add(new Item(idItem++, nomeItem, tipo, valor));

            System.out.print("Adicionar outro item? (s/n): ");
            continuar = scan.nextLine();
        }

        double totalItens = 0;
        for (Item item : itens) totalItens += item.getValor();

        double desconto = 0;
        if (quantidade >= 10) {
            desconto = totalItens * 0.05;
            System.out.println("Desconto de 5% aplicado!");
        }

        ProcessaPedido pp = new ProcessaPedido();
        Pedido pedido = pp.processar(idPedido, agora, null, vencimento, cliente, vendedor, loja,
                quantidade, desconto, dataVenda, itens.toArray(new Item[0]));

        pedidos.add(pedido);
    }
}
