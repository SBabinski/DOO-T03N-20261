package Objetos;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Loja> lojas = DadosIniciais.carregar();
    private static int proximoIdPedido = 1;

    public static void mostrarMenu() {
        int escolha;
        do {
            System.out.println("\n=== My Plant - Sistema de Gestão ===");
            System.out.println("1. Gerenciar Lojas");
            System.out.println("2. Cadastrar Nova Loja");
            System.out.println("3. Criar Pedido (demo)");
            System.out.println("4. Executar Testes");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();
            switch (escolha) {
                case 1 -> selecionarLoja();
                case 2 -> cadastrarLoja();
                case 3 -> criarPedidoDemo();
                case 4 -> executarTestes();
                case 5 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (escolha != 5);
    }

    private static void selecionarLoja() {
        if (lojas.isEmpty()) {
            System.out.println("Nenhuma loja cadastrada.");
            return;
        }
        System.out.println("\n=== Lojas Cadastradas ===");
        for (int i = 0; i < lojas.size(); i++) {
            System.out.println((i + 1) + ". " + lojas.get(i).getNomeFantasia());
        }
        System.out.println("0. Voltar");
        System.out.print("Selecione uma loja: ");
        int escolha = scanner.nextInt();
        if (escolha == 0 || escolha < 1 || escolha > lojas.size()) return;
        menuLoja(lojas.get(escolha - 1));
    }

    private static void cadastrarLoja() {
        scanner.nextLine();
        System.out.print("Nome Fantasia: ");   String nomeFantasia = scanner.nextLine();
        System.out.print("Razão Social: ");    String razaoSocial = scanner.nextLine();
        System.out.print("CNPJ: ");            String cnpj = scanner.nextLine();
        System.out.print("Estado: ");          String estado = scanner.nextLine();
        System.out.print("Cidade: ");          String cidade = scanner.nextLine();
        System.out.print("Bairro: ");          String bairro = scanner.nextLine();
        System.out.print("Número: ");          String numero = scanner.nextLine();
        System.out.print("Complemento: ");     String complemento = scanner.nextLine();
        lojas.add(new Loja(nomeFantasia, razaoSocial, cnpj,
                new Endereco(estado, cidade, bairro, numero, complemento)));
        System.out.println("Loja \"" + nomeFantasia + "\" cadastrada com sucesso!");
    }

    private static void menuLoja(Loja loja) {
        int escolha;
        do {
            System.out.println("\n=== " + loja.getNomeFantasia() + " ===");
            System.out.println("1. Informações da loja");
            System.out.println("2. Clientes");
            System.out.println("3. Vendedores");
            System.out.println("4. Gerentes");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();
            switch (escolha) {
                case 1 -> loja.apresentarse();
                case 2 -> menuClientes(loja);
                case 3 -> menuFuncionarios(loja, "vendedor");
                case 4 -> menuFuncionarios(loja, "gerente");
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (escolha != 0);
    }

    private static void menuClientes(Loja loja) {
        int escolha;
        do {
            System.out.println("\n=== Clientes - " + loja.getNomeFantasia() + " ===");
            System.out.println("1. Listar clientes");
            System.out.println("2. Cadastrar novo cliente");
            System.out.println("3. Total de clientes");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();
            switch (escolha) {
                case 1 -> loja.getClientes().forEach(Cliente::apresentarse);
                case 2 -> cadastrarCliente(loja);
                case 3 -> loja.contarClientes();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (escolha != 0);
    }

    private static void cadastrarCliente(Loja loja) {
        scanner.nextLine();
        System.out.print("Nome: ");    String nome = scanner.nextLine();
        System.out.print("Idade: ");   int idade = scanner.nextInt(); scanner.nextLine();
        System.out.print("Estado: ");  String estado = scanner.nextLine();
        System.out.print("Cidade: ");  String cidade = scanner.nextLine();
        System.out.print("Bairro: ");  String bairro = scanner.nextLine();
        System.out.print("Número: ");  String numero = scanner.nextLine();
        loja.adicionarCliente(new Cliente(nome, idade, new Endereco(estado, cidade, bairro, numero)));
        System.out.println("Cliente " + nome + " cadastrado com sucesso!");
    }

    private static void menuFuncionarios(Loja loja, String tipo) {
        String titulo = tipo.equals("vendedor") ? "Vendedores" : "Gerentes";
        int escolha;
        do {
            System.out.println("\n=== " + titulo + " - " + loja.getNomeFantasia() + " ===");
            System.out.println("1. Listar");
            System.out.println("2. Cadastrar novo");
            System.out.println("3. Ver média salarial");
            System.out.println("4. Ver bônus");
            System.out.println("5. Total");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();
            switch (escolha) {
                case 1 -> listarFuncionarios(loja, tipo);
                case 2 -> cadastrarFuncionario(loja, tipo);
                case 3 -> buscarFuncionarioEExecutar(loja, tipo, Funcionario::calcularMedia);
                case 4 -> buscarFuncionarioEExecutar(loja, tipo, Funcionario::calcularBonus);
                case 5 -> { if (tipo.equals("vendedor")) loja.contarVendedores(); else loja.contarGerentes(); }
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (escolha != 0);
    }

    private static List<? extends Funcionario> getFuncionarios(Loja loja, String tipo) {
        return tipo.equals("vendedor") ? loja.getVendedores() : loja.getGerentes();
    }

    private static void listarFuncionarios(Loja loja, String tipo) {
        List<? extends Funcionario> lista = getFuncionarios(loja, tipo);
        if (lista.isEmpty()) { System.out.println("Nenhum cadastrado."); return; }
        lista.forEach(Funcionario::apresentarse);
    }

    private static void cadastrarFuncionario(Loja loja, String tipo) {
        scanner.nextLine();
        System.out.print("Nome: ");         String nome = scanner.nextLine();
        System.out.print("Idade: ");        int idade = scanner.nextInt(); scanner.nextLine();
        System.out.print("Estado: ");       String estado = scanner.nextLine();
        System.out.print("Cidade: ");       String cidade = scanner.nextLine();
        System.out.print("Bairro: ");       String bairro = scanner.nextLine();
        System.out.print("Número: ");       String numero = scanner.nextLine();
        System.out.print("Salário base: "); double salario = scanner.nextDouble();
        Endereco endereco = new Endereco(estado, cidade, bairro, numero);
        if (tipo.equals("vendedor")) {
            loja.adicionarVendedor(new Vendedor(nome, idade, endereco, loja, salario));
        } else {
            loja.adicionarGerente(new Gerente(nome, idade, endereco, loja, salario));
        }
        System.out.println("Cadastrado com sucesso!");
    }

    private static void buscarFuncionarioEExecutar(Loja loja, String tipo,
                                                    java.util.function.Consumer<Funcionario> acao) {
        List<? extends Funcionario> lista = getFuncionarios(loja, tipo);
        if (lista.isEmpty()) { System.out.println("Nenhum cadastrado."); return; }
        listarFuncionarios(loja, tipo);
        System.out.print("Digite o nome: ");
        scanner.nextLine();
        String nome = scanner.nextLine();
        lista.stream()
                .filter(f -> f.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .ifPresentOrElse(acao, () -> System.out.println("Não encontrado."));
    }

    private static void criarPedidoDemo() {
        Loja loja = lojas.get(0);
        if (loja.getClientes().isEmpty() || loja.getVendedores().isEmpty()) {
            System.out.println("A loja precisa ter ao menos 1 cliente e 1 vendedor.");
            return;
        }
        Cliente cliente = loja.getClientes().get(0);
        Vendedor vendedor = loja.getVendedores().get(0);
        Date vencimento = new Date(System.currentTimeMillis() + 86_400_000L);
        Item item1 = new Item(proximoIdPedido * 10 + 1, "Samambaia", "Planta", 45.00);
        Item item2 = new Item(proximoIdPedido * 10 + 2, "Vaso Cerâmica", "Acessório", 30.00);
        ProcessaPedido processador = new ProcessaPedido();
        Pedido pedido = processador.processar(proximoIdPedido++, vencimento, cliente, vendedor, loja, item1, item2);
        pedido.gerarDescricaoVenda();
    }

    private static void executarTestes() {
        Loja loja = lojas.get(0);
        Cliente cliente = loja.getClientes().get(0);
        Vendedor vendedor = loja.getVendedores().get(0);
        ProcessaPedido.testar(loja, cliente, vendedor);
    }
}
