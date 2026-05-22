package myplant.menu;

import myplant.model.*;
import myplant.service.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Menu de console do sistema My Plant.
 * Centraliza todas as interações com o usuário.
 */
public class Menu {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Scanner scanner;
    private final Calculadora calculadora;
    private final RegistroVendasService registroService;
    private final ProcessaPedido processaPedido;

    // Dados de exemplo para demonstração
    private final Loja loja;
    private final Vendedor vendedor;
    private final Cliente cliente;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.calculadora = new Calculadora();
        this.registroService = new RegistroVendasService();
        this.processaPedido = new ProcessaPedido();

        // Dados fake para demo
        Endereco endLoja = new Endereco("PR", "Cascavel", "Centro", "Rua das Flores", "100", "Loja 1");
        this.loja = new Loja("My Plant", "My Plant Ltda.", "12.345.678/0001-99", endLoja);

        Endereco endVendedor = new Endereco("PR", "Cascavel", "Universitário", "Rua das Palmeiras", "50", "");
        this.vendedor = new Vendedor("Ana Souza", 28, endVendedor, loja,
                2500.0, new double[]{2500.0, 2600.0, 2450.0});
        loja.adicionarVendedor(vendedor);

        Endereco endCliente = new Endereco("PR", "Cascavel", "Cascavel Velho", "Av. Brasil", "200", "Apto 3");
        this.cliente = new Cliente("João Silva", 35, endCliente);
        loja.adicionarCliente(cliente);
    }

    public void iniciar() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║       🌿  My Plant System 🌿      ║");
        System.out.println("╚══════════════════════════════════╝");

        int opcao = -1;
        while (opcao != 9) {
            exibirMenuPrincipal();
            opcao = lerInt("Escolha: ");
            processarOpcao(opcao);
        }
    }

    private void exibirMenuPrincipal() {
        System.out.println("\n──────────────────────────────────");
        System.out.println("  [1] Calcular Preço Total");
        System.out.println("  [2] Calcular Troco");
        System.out.println("  [3] Registrar Venda");
        System.out.println("  [4] Buscar Vendas por Mês/Dia");
        System.out.println("  [5] Criar Pedido (Demo)");
        System.out.println("  [6] Testar ProcessaPedido");
        System.out.println("  [7] Info da Loja");
        System.out.println("  [8] Info do Vendedor");
        System.out.println("  [9] Sair");
        System.out.println("──────────────────────────────────");
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> menuCalcularPreco();
            case 2 -> menuCalcularTroco();
            case 3 -> menuRegistrarVenda();
            case 4 -> menuBuscarVendas();
            case 5 -> menuCriarPedido();
            case 6 -> ProcessaPedido.testarConfirmarPagamento();
            case 7 -> menuInfoLoja();
            case 8 -> menuInfoVendedor();
            case 9 -> System.out.println("\n🌿 Encerrando My Plant. Até logo!");
            default -> System.out.println("⚠️  Opção inválida!");
        }
    }

    // ── [1] Calcular Preço Total ────────────────────────────
    private void menuCalcularPreco() {
        System.out.println("\n── Calcular Preço Total ──");
        int qtd = lerInt("Quantidade de plantas: ");
        double preco = lerDouble("Preço unitário (R$): ");

        double total = calculadora.calcularPrecoComDesconto(qtd, preco);
        System.out.printf("💰 Valor a pagar: R$ %.2f%n", total);
    }

    // ── [2] Calcular Troco ──────────────────────────────────
    private void menuCalcularTroco() {
        System.out.println("\n── Calcular Troco ──");
        double valorPago = lerDouble("Valor recebido (R$): ");
        double valorCompra = lerDouble("Valor total da compra (R$): ");

        double troco = calculadora.calcularTroco(valorPago, valorCompra);
        if (troco < 0) {
            System.out.printf("❌ Valor insuficiente! Faltam R$ %.2f%n", Math.abs(troco));
        } else {
            System.out.printf("✅ Troco: R$ %.2f%n", troco);
        }
    }

    // ── [3] Registrar Venda ─────────────────────────────────
    private void menuRegistrarVenda() {
        System.out.println("\n── Registrar Venda ──");
        int qtd = lerInt("Quantidade de plantas: ");
        double preco = lerDouble("Preço unitário (R$): ");

        double desconto = calculadora.calcularValorDesconto(qtd, preco);
        double total = calculadora.calcularPrecoComDesconto(qtd, preco);

        RegistroVenda venda = new RegistroVenda(LocalDate.now(), qtd, total, desconto);
        registroService.registrarVenda(venda);
        System.out.printf("📦 Venda registrada — Total: R$ %.2f | Desconto: R$ %.2f%n", total, desconto);
    }

    // ── [4] Buscar Vendas ───────────────────────────────────
    private void menuBuscarVendas() {
        System.out.println("\n── Buscar Vendas ──");
        System.out.println("  [1] Por Mês");
        System.out.println("  [2] Por Dia");
        int sub = lerInt("Escolha: ");

        if (sub == 1) {
            int mes = lerInt("Mês (1-12): ");
            int ano = lerInt("Ano (ex: 2025): ");
            int total = registroService.buscarTotalVendasPorMes(mes, ano);
            List<RegistroVenda> lista = registroService.listarPorMes(mes, ano);

            System.out.printf("📊 Total de plantas vendidas em %02d/%d: %d%n", mes, ano, total);
            lista.forEach(RegistroVenda::exibir);
        } else if (sub == 2) {
            String dataStr = lerString("Data (dd/MM/yyyy): ");
            LocalDate data = LocalDate.parse(dataStr, FORMATTER);
            int total = registroService.buscarTotalVendasPorDia(data);
            System.out.printf("📊 Total de plantas vendidas em %s: %d%n", dataStr, total);
        }
    }

    // ── [5] Criar Pedido (Demo) ─────────────────────────────
    private void menuCriarPedido() {
        System.out.println("\n── Criar Pedido (dados demo) ──");

        Item item1 = new Item(1, "Orquídea Phalaenopsis", "Flor", 89.90);
        Item item2 = new Item(2, "Samambaia", "Folhagem", 35.00);
        item1.gerarDescricao();
        item2.gerarDescricao();

        LocalDate hoje = LocalDate.now();
        LocalDate vencimento = hoje.plusDays(3);

        processaPedido.processar(hoje, hoje, vencimento, cliente, vendedor, loja, item1, item2);
    }

    // ── [7] Info da Loja ────────────────────────────────────
    private void menuInfoLoja() {
        System.out.println();
        loja.apresentarse();
        System.out.println("   Vendedores: " + loja.contarVendedores());
        System.out.println("   Clientes:   " + loja.contarClientes());
    }

    // ── [8] Info do Vendedor ────────────────────────────────
    private void menuInfoVendedor() {
        System.out.println();
        vendedor.apresentarse();
        System.out.printf("   Média salarial: R$ %.2f%n", vendedor.calcularMedia());
        System.out.printf("   Bônus:          R$ %.2f%n", vendedor.calcularBonus());
        System.out.print("   Endereço: ");
        vendedor.getEndereco().apresentarLogradouro();
    }

    // ── Helpers ─────────────────────────────────────────────
    private int lerInt(String msg) {
        System.out.print(msg);
        while (!scanner.hasNextInt()) { scanner.next(); System.out.print(msg); }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }

    private double lerDouble(String msg) {
        System.out.print(msg);
        while (!scanner.hasNextDouble()) { scanner.next(); System.out.print(msg); }
        double val = scanner.nextDouble();
        scanner.nextLine();
        return val;
    }

    private String lerString(String msg) {
        System.out.print(msg);
        return scanner.nextLine().trim();
    }
}
