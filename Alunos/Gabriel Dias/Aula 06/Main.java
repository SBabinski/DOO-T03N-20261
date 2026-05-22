import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import obj.Calculadora;
import obj.Produto;
import obj.Venda;
import obj.Vendedor;
import obj.Cliente;
import obj.Loja;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static Calculadora calculadora = new Calculadora();
    static ArrayList<Venda> historicoVendas = new ArrayList<>();

    // === Dados fixos de exemplo ===
    static Vendedor[] vendedores = {
            new Vendedor("Carlos Silva",  30, "My plant", "São Paulo", "Centro",       "Rua das Flores, 123",  3000.00),
            new Vendedor("Fernanda Lima", 27, "My plant", "São Paulo", "Vila Madalena","Rua Harmonia, 456",     2800.00),
            new Vendedor("João Pereira",  35, "My plant", "São Paulo", "Pinheiros",    "Rua dos Pinheiros, 789",3200.00)
    };

    static Cliente[] clientes = {
            new Cliente("Ana Souza",        25, "São Paulo", "Vila Madalena", "Rua Harmonia, 456"),
            new Cliente("Bruno Costa",      32, "São Paulo", "Pinheiros",     "Rua dos Pinheiros, 789"),
            new Cliente("Mariana Oliveira", 28, "São Paulo", "Itaim Bibi",    "Av. Faria Lima, 100")
    };

    static Loja loja = new Loja(
            "My plant",
            "Gabriela Comércio de Plantas Ltda.",
            "12.345.678/0001-99",
            "São Paulo", "Centro", "Rua das Flores, 123",
            vendedores, clientes
    );

    public static void main(String[] args) {
        int opcao;

        do {
            System.out.println("\n=== Loja de Plantas da Dona Gabrielinha ===");
            System.out.println("[1]  - Realizar venda");
            System.out.println("[2]  - Calcular troco");
            System.out.println("[3]  - Mostrar histórico de Vendas");
            System.out.println("[4]  - Buscar vendas por dia");
            System.out.println("[5]  - Buscar Vendas por mês");
            System.out.println("[6]  - Mostrar vendedor");
            System.out.println("[7]  - Calcular salário por vendedor");
            System.out.println("[8]  - Calcular bônus por vendedor");
            System.out.println("[9]  - Mostrar cliente");
            System.out.println("[10] - Contar clientes");
            System.out.println("[11] - Contar vendedores");
            System.out.println("[12] - Mostrar loja");
            System.out.println("[0]  - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:  realizaVenda();           break;
                case 2:  calcularTroco();          break;
                case 3:  historicoVendas();        break;
                case 4:  vendaDia();               break;
                case 5:  vendaMes();               break;
                case 6:  mostrarVendedor();        break;
                case 7:  calcularSalarioVendedor();break;
                case 8:  calcularBonusVendedor();  break;
                case 9:  mostrarCliente();         break;
                case 10: contarClientes();         break;
                case 11: contarVendedores();       break;
                case 12: mostrarLoja();            break;
                case 0:  System.out.println("Sistema encerrado."); break;
                default: System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        scanner.close();
    }

    // =========================================================
    // Métodos originais
    // =========================================================

    private static void realizaVenda() {
        System.out.print("Nome da planta: ");
        String nomeProduto = scanner.nextLine();

        System.out.print("Preço unitário: ");
        double precoUnitario = scanner.nextDouble();

        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();

        Produto produto = new Produto(nomeProduto, precoUnitario);

        double desconto   = calculadora.calcularDesconto(quantidade, precoUnitario);
        double valorFinal = calculadora.calcularValorTotal(quantidade, precoUnitario);

        System.out.println("\nProduto: "       + produto.getNomeProduto());
        System.out.println("Desconto: R$ "    + desconto);
        System.out.println("Total a pagar: R$ " + valorFinal);

        historicoVendas.add(new Venda(nomeProduto, quantidade, valorFinal, desconto));
    }

    private static void calcularTroco() {
        System.out.print("Valor pago: ");
        double valorPago = scanner.nextDouble();

        System.out.print("Valor da compra: ");
        double valorCompra = scanner.nextDouble();

        double troco = calculadora.calcularTroco(valorPago, valorCompra);

        if (troco < 0) {
            System.out.println("Valor insuficiente! Faltam R$ " + (-troco));
        } else {
            System.out.println("Troco: R$ " + troco);
        }
    }

    private static void historicoVendas() {
        System.out.println("\n=== Histórico de Vendas ===");
        for (Venda v : historicoVendas) {
            v.exibirResumo();
        }
    }

    private static void vendaDia() {
        System.out.print("Digite a data (dd/MM/yyyy): ");
        String dataTexto = scanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataBusca = LocalDate.parse(dataTexto, formatter);

        int totalDia = 0;
        for (Venda v : historicoVendas) {
            if (v.getDataVenda().equals(dataBusca)) totalDia++;
        }

        System.out.println("Total de vendas no dia: " + totalDia);
    }

    private static void vendaMes() {
        System.out.print("Digite o mês (1-12): ");
        int mes = scanner.nextInt();

        System.out.print("Digite o ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine();

        int totalMes = 0;
        for (Venda v : historicoVendas) {
            if (v.getDataVenda().getMonthValue() == mes &&
                    v.getDataVenda().getYear() == ano) {
                totalMes++;
            }
        }

        System.out.println("Total de vendas no mês: " + totalMes);
    }

    // =========================================================
    // Novos métodos — Vendedor
    // =========================================================

    private static void mostrarVendedor() {
        System.out.println("\n=== Vendedores cadastrados ===");
        for (int i = 0; i < vendedores.length; i++) {
            System.out.println("[" + (i + 1) + "] " + vendedores[i].getNome());
        }
        System.out.print("Escolha o número do vendedor: ");
        int idx = scanner.nextInt() - 1;
        scanner.nextLine();

        if (idx < 0 || idx >= vendedores.length) {
            System.out.println("Vendedor não encontrado.");
            return;
        }
        vendedores[idx].apresentarse();
    }

    private static void calcularSalarioVendedor() {
        System.out.println("\n=== Vendedores cadastrados ===");
        for (int i = 0; i < vendedores.length; i++) {
            System.out.println("[" + (i + 1) + "] " + vendedores[i].getNome());
        }
        System.out.print("Escolha o número do vendedor: ");
        int idx = scanner.nextInt() - 1;
        scanner.nextLine();

        if (idx < 0 || idx >= vendedores.length) {
            System.out.println("Vendedor não encontrado.");
            return;
        }
        double media = vendedores[idx].calcularMedia();
        System.out.println("Média salarial de " + vendedores[idx].getNome() + ": R$ " + String.format("%.2f", media));
    }

    private static void calcularBonusVendedor() {
        System.out.println("\n=== Vendedores cadastrados ===");
        for (int i = 0; i < vendedores.length; i++) {
            System.out.println("[" + (i + 1) + "] " + vendedores[i].getNome());
        }
        System.out.print("Escolha o número do vendedor: ");
        int idx = scanner.nextInt() - 1;
        scanner.nextLine();

        if (idx < 0 || idx >= vendedores.length) {
            System.out.println("Vendedor não encontrado.");
            return;
        }
        double bonus = vendedores[idx].calcularBonus();
        System.out.println("Bônus de " + vendedores[idx].getNome() + ": R$ " + String.format("%.2f", bonus));
    }

    // =========================================================
    // Novos métodos — Cliente
    // =========================================================

    private static void mostrarCliente() {
        System.out.println("\n=== Clientes cadastrados ===");
        for (int i = 0; i < clientes.length; i++) {
            System.out.println("[" + (i + 1) + "] " + clientes[i].getNome());
        }
        System.out.print("Escolha o número do cliente: ");
        int idx = scanner.nextInt() - 1;
        scanner.nextLine();

        if (idx < 0 || idx >= clientes.length) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        clientes[idx].apresentarse();
    }

    private static void contarClientes() {
        int total = loja.contarClientes();
        System.out.println("Total de clientes: " + total);
    }

    // =========================================================
    // Novos métodos — Loja / Vendedor count
    // =========================================================

    private static void contarVendedores() {
        int total = loja.contarVendedores();
        System.out.println("Total de vendedores: " + total);
    }

    private static void mostrarLoja() {
        loja.apresentarse();
    }
}