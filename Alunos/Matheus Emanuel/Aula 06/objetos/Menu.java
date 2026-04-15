package objetos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Loja> lojas = new ArrayList<>();

    // Dados pré-cadastrados
    static {
        Loja loja1 = new Loja("My Plant Centro", "My Plant Ltda", "12.345.678/0001-90", "Maringá", "Centro", "Rua das Flores, 100");
        Vendedor v1 = new Vendedor("Carlos Silva", 35, loja1, "Maringá", "Centro", "Rua das Flores, 100", 2500.00);
        Vendedor v2 = new Vendedor("Ana Souza", 28, loja1, "Maringá", "Zona Norte", "Av. Norte, 200", 2200.00);
        loja1.adicionarVendedor(v1);
        loja1.adicionarVendedor(v2);
        loja1.adicionarCliente(new Cliente("João Pereira", 42, "Maringá", "Centro", "Rua A, 10"));
        loja1.adicionarCliente(new Cliente("Maria Lima", 30, "Maringá", "Zona Sul", "Rua B, 20"));
        lojas.add(loja1);

        Loja loja2 = new Loja("My Plant Zona Sul", "My Plant Sul Ltda", "98.765.432/0001-10", "Maringá", "Zona Sul", "Av. Sul, 300");
        Vendedor v3 = new Vendedor("Bruno Alves", 31, loja2, "Maringá", "Zona Sul", "Av. Sul, 300", 2300.00);
        loja2.adicionarVendedor(v3);
        loja2.adicionarCliente(new Cliente("Pedro Costa", 25, "Maringá", "Zona Sul", "Rua C, 30"));
        lojas.add(loja2);
    }

    // ==================== MENU PRINCIPAL ====================
    public static void mostrarMenu() {
        int escolha;
        do {
            System.out.println("\n=== My Plant - Sistema de Gestão ===");
            System.out.println("1. Gerenciar Lojas");
            System.out.println("2. Cadastrar Nova Loja");
            System.out.println("3. Vendas");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();
            switch (escolha) {
                case 1 -> selecionarLoja();
                case 2 -> cadastrarLoja();
                case 3 -> menuVendas();
                case 4 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (escolha != 4);
    }

    // ==================== SELEÇÃO DE LOJA ====================
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

        if (escolha == 0) return;

        if (escolha < 1 || escolha > lojas.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        Loja lojaSelecionada = lojas.get(escolha - 1);
        menuLoja(lojaSelecionada);
    }

    private static void cadastrarLoja() {
        scanner.nextLine();
        System.out.print("Nome Fantasia: ");
        String nomeFantasia = scanner.nextLine();
        System.out.print("Razão Social: ");
        String razaoSocial = scanner.nextLine();
        System.out.print("CNPJ: ");
        String cnpj = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();
        System.out.print("Rua: ");
        String rua = scanner.nextLine();

        Loja novaLoja = new Loja(nomeFantasia, razaoSocial, cnpj, cidade, bairro, rua);
        lojas.add(novaLoja);
        System.out.println("Loja \"" + nomeFantasia + "\" cadastrada com sucesso!");
    }

    // ==================== MENU DE UMA LOJA ====================
    private static void menuLoja(Loja loja) {
        int escolha;
        do {
            System.out.println("\n=== " + loja.getNomeFantasia() + " ===");
            System.out.println("1. Informações da loja");
            System.out.println("2. Clientes");
            System.out.println("3. Vendedores");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();
            switch (escolha) {
                case 1 -> loja.apresentarse();
                case 2 -> menuClientes(loja);
                case 3 -> menuVendedores(loja);
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (escolha != 0);
    }

    // ==================== CLIENTES ====================
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
                case 1 -> listarClientes(loja);
                case 2 -> cadastrarCliente(loja);
                case 3 -> loja.contarClientes();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (escolha != 0);
    }

    private static void listarClientes(Loja loja) {
        if (loja.getClientes().isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        System.out.println("\n=== Clientes - " + loja.getNomeFantasia() + " ===");
        for (Cliente c : loja.getClientes()) {
            c.apresentarse();
        }
    }

    private static void cadastrarCliente(Loja loja) {
        scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Idade: ");
        int idade = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();
        System.out.print("Rua: ");
        String rua = scanner.nextLine();

        loja.adicionarCliente(new Cliente(nome, idade, cidade, bairro, rua));
        System.out.println("Cliente " + nome + " cadastrado com sucesso!");
    }

    // ==================== VENDEDORES ====================
    private static void menuVendedores(Loja loja) {
        int escolha;
        do {
            System.out.println("\n=== Vendedores - " + loja.getNomeFantasia() + " ===");
            System.out.println("1. Listar vendedores");
            System.out.println("2. Cadastrar novo vendedor");
            System.out.println("3. Ver média salarial de um vendedor");
            System.out.println("4. Ver bônus de um vendedor");
            System.out.println("5. Total de vendedores");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();
            switch (escolha) {
                case 1 -> listarVendedores(loja);
                case 2 -> cadastrarVendedor(loja);
                case 3 -> verMediaVendedor(loja);
                case 4 -> verBonusVendedor(loja);
                case 5 -> loja.contarVendedores();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (escolha != 0);
    }

    private static void listarVendedores(Loja loja) {
        if (loja.getVendedores().isEmpty()) {
            System.out.println("Nenhum vendedor cadastrado.");
            return;
        }
        System.out.println("\n=== Vendedores - " + loja.getNomeFantasia() + " ===");
        for (Vendedor v : loja.getVendedores()) {
            v.apresentarse();
        }
    }

    private static void cadastrarVendedor(Loja loja) {
        scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Idade: ");
        int idade = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();
        System.out.print("Rua: ");
        String rua = scanner.nextLine();
        System.out.print("Salário base: ");
        double salario = scanner.nextDouble();

        loja.adicionarVendedor(new Vendedor(nome, idade, loja, cidade, bairro, rua, salario));
        System.out.println("Vendedor " + nome + " cadastrado com sucesso!");
    }

    private static void verMediaVendedor(Loja loja) {
        listarVendedores(loja);
        if (loja.getVendedores().isEmpty()) return;
        System.out.print("Digite o nome do vendedor: ");
        scanner.nextLine();
        String nome = scanner.nextLine();
        for (Vendedor v : loja.getVendedores()) {
            if (v.getNome().equalsIgnoreCase(nome)) {
                v.calcularMedia();
                return;
            }
        }
        System.out.println("Vendedor não encontrado.");
    }

    private static void verBonusVendedor(Loja loja) {
        listarVendedores(loja);
        if (loja.getVendedores().isEmpty()) return;
        System.out.print("Digite o nome do vendedor: ");
        scanner.nextLine();
        String nome = scanner.nextLine();
        for (Vendedor v : loja.getVendedores()) {
            if (v.getNome().equalsIgnoreCase(nome)) {
                v.calcularBonus();
                return;
            }
        }
        System.out.println("Vendedor não encontrado.");
    }

    // ==================== VENDAS ====================
    private static void menuVendas() {
        int escolha;
        do {
            System.out.println("\n=== Menu de Vendas ===");
            System.out.println("1. Calcular Preço e Registrar Venda");
            System.out.println("2. Calcular Troco");
            System.out.println("3. Ver Registro de Vendas");
            System.out.println("4. Consultar Vendas por Data");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();
            switch (escolha) {
                case 1 -> Venda.calcularPreco();
                case 2 -> Venda.calcularTroco();
                case 3 -> Venda.mostrarRegistroVendas();
                case 4 -> Venda.consultarVendasPorData();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (escolha != 0);
    }
}