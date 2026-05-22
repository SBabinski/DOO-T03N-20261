import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Calculadora {

    static ArrayList<Venda> registroDeVendas = new ArrayList<>();
    static Scanner leitor = new Scanner(System.in);

    public static void main(String[] args) {

        // ── Demonstração das novas classes (pedi pro chat fazer os exemplos, CONFERIR SE ELE NAO ALTEROU NADA) ──────────────────────────────

        // Vendedor com três lançamentos de salário
        Vendedor v1 = new Vendedor(
            "Carlos Silva", 30,
            "Floricultura Dona Gabrielinha",
            "Cascavel", "Centro", "Rua das Flores",
            3000.0,
            new double[]{3000.0, 3200.0, 2900.0}
        );
        System.out.println("=== VENDEDOR ===");
        v1.apresentarse();
        System.out.printf("Média salarial : R$ %.2f%n", v1.calcularMedia());
        System.out.printf("Bônus          : R$ %.2f%n", v1.calcularBonus());

        // Cliente
        Cliente c1 = new Cliente("Ana Paula", 25, "Cascavel", "Jardim Universitário", "Rua das Palmeiras");
        System.out.println("\n=== CLIENTE ===");
        c1.apresentarse();

        // Loja
        Loja loja = new Loja(
            "Floricultura Dona Gabrielinha",
            "Gabrielinha Comércio de Plantas LTDA",
            "12.345.678/0001-99",
            "Cascavel", "Centro", "Rua XV de Novembro, 100"
        );
        loja.vendedores.add(v1);
        loja.clientes.add(c1);

        System.out.println("\n=== LOJA ===");
        loja.apresentarse();
        loja.contarVendedores();
        loja.contarClientes();

        
        int opcao = 0;

        while (opcao != 5) {

            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║  FLORICULTURA DONA GABRIELINHA ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.println("1 - Calcular valor total da venda");
            System.out.println("2 - Calcular troco");
            System.out.println("3 - Listar vendas realizadas");
            System.out.println("4 - Buscar vendas por data");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = leitor.nextInt();

            if (opcao == 1) calcularValorTotal();
            if (opcao == 2) calcularTroco();
            if (opcao == 3) listarVendas();
            if (opcao == 4) buscarVendasPorData();
            if (opcao == 5) System.out.println("\nPrograma encerrado. Até logo!");
            if (opcao < 1 || opcao > 5) System.out.println("\nOpção inválida! Tente novamente.");
        }
    }

    // ── metodos 
    public static void calcularValorTotal() {

        System.out.println("\n--- CÁLCULO DE VENDA ---");

        System.out.print("Preço unitário da planta (R$): ");
        double precoPorUnidade = leitor.nextDouble();

        System.out.print("Quantidade de plantas: ");
        int quantidade = leitor.nextInt();

        double valorBruto = precoPorUnidade * quantidade;
        double desconto   = 0;
        double valorFinal;

        if (quantidade > 10) {
            desconto   = valorBruto * 0.05;
            valorFinal = valorBruto - desconto;
            System.out.println("\n✔ Desconto de 5% aplicado por compra acima de 10 unidades!");
        } else {
            valorFinal = valorBruto;
            System.out.println("\n✘ Sem desconto (compra de 10 unidades ou menos).");
        }

        LocalDate dataDeHoje = LocalDate.now();

        System.out.println("\n--- RESUMO ---");
        System.out.printf("Valor bruto  : R$ %.2f%n", valorBruto);
        System.out.printf("Desconto     : R$ %.2f%n", desconto);
        System.out.printf("Valor final  : R$ %.2f%n", valorFinal);

        Venda novaVenda = new Venda(precoPorUnidade, quantidade, valorFinal, desconto, dataDeHoje);
        registroDeVendas.add(novaVenda);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("\n✔ Venda registrada em: " + dataDeHoje.format(formato));
    }

    public static void calcularTroco() {

        System.out.println("\n--- CÁLCULO DE TROCO ---");

        System.out.print("Valor total da compra (R$): ");
        double valorDaCompra = leitor.nextDouble();

        System.out.print("Valor pago pelo cliente (R$): ");
        double valorPago = leitor.nextDouble();

        double troco = valorPago - valorDaCompra;

        if (troco < 0) {
            System.out.printf("%nValor insuficiente! Faltam R$ %.2f%n", Math.abs(troco));
        } else {
            System.out.printf("%nTroco a devolver: R$ %.2f%n", troco);
        }
    }

    public static void listarVendas() {

        System.out.println("\n--- REGISTRO DE VENDAS ---");

        if (registroDeVendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada ainda.");
            return;
        }

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (int i = 0; i < registroDeVendas.size(); i++) {
            Venda venda = registroDeVendas.get(i);
            System.out.println("\nVenda #" + (i + 1));
            System.out.printf("  Data            : %s%n",          venda.data.format(formato));
            System.out.printf("  Preço unitário  : R$ %.2f%n",     venda.precoPorUnidade);
            System.out.printf("  Quantidade      : %d unidades%n", venda.quantidade);
            System.out.printf("  Desconto        : R$ %.2f%n",     venda.desconto);
            System.out.printf("  Valor final     : R$ %.2f%n",     venda.valorFinal);
        }

        System.out.println("\nTotal de vendas: " + registroDeVendas.size());
    }

    public static void buscarVendasPorData() {

        System.out.println("\n--- BUSCA DE VENDAS POR DATA ---");
        System.out.println("1 - Buscar por mês");
        System.out.println("2 - Buscar por dia e mês");
        System.out.print("Escolha: ");
        int opcaoBusca = leitor.nextInt();

        System.out.print("Digite o mês (1-12): ");
        int mesBuscado = leitor.nextInt();

        int diaBuscado = 0;
        if (opcaoBusca == 2) {
            System.out.print("Digite o dia: ");
            diaBuscado = leitor.nextInt();
        }

        int    totalDeVendas   = 0;
        double totalArrecadado = 0;

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (int i = 0; i < registroDeVendas.size(); i++) {

            Venda venda = registroDeVendas.get(i);

            boolean mesIgual = venda.data.getMonthValue()  == mesBuscado;
            boolean diaIgual = venda.data.getDayOfMonth()  == diaBuscado;

            boolean encontrou = (opcaoBusca == 1 && mesIgual)
                             || (opcaoBusca == 2 && mesIgual && diaIgual);

            if (encontrou) {
                totalDeVendas++;
                totalArrecadado += venda.valorFinal;
                System.out.println("\nVenda encontrada — " + venda.data.format(formato));
                System.out.printf("  Quantidade  : %d unidades%n", venda.quantidade);
                System.out.printf("  Desconto    : R$ %.2f%n",     venda.desconto);
                System.out.printf("  Valor final : R$ %.2f%n",     venda.valorFinal);
            }
        }

        if (totalDeVendas == 0) {
            System.out.println("\nNenhuma venda encontrada para essa data.");
        } else {
            System.out.println("\n──────────────────────────────────");
            System.out.println("Total de vendas encontradas : " + totalDeVendas);
            System.out.printf("Total arrecadado            : R$ %.2f%n", totalArrecadado);
        }
    }
}

//  VENDA===========================
class Venda {

    double    precoPorUnidade;
    int       quantidade;
    double    valorFinal;
    double    desconto;
    LocalDate data;

    public Venda(double precoPorUnidade, int quantidade, double valorFinal,
                 double desconto, LocalDate data) {
        this.precoPorUnidade = precoPorUnidade;
        this.quantidade      = quantidade;
        this.valorFinal      = valorFinal;
        this.desconto        = desconto;
        this.data            = data;
    }
}

// CLASSE VENDEDOR====================================================================d
class Vendedor {

    String   nome;
    int      idade;
    String   loja, cidade, bairro, rua;
    double   salarioBase;
    double[] salarioRecebido;   // mínimo 3 lançamentos

    public Vendedor(String nome, int idade, String loja,
                    String cidade, String bairro, String rua,
                    double salarioBase, double[] salarioRecebido) {
        this.nome            = nome;
        this.idade           = idade;
        this.loja            = loja;
        this.cidade          = cidade;
        this.bairro          = bairro;
        this.rua             = rua;
        this.salarioBase     = salarioBase;
        this.salarioRecebido = salarioRecebido;
    }

    public void apresentarse() {
        System.out.println("Nome  : " + nome);
        System.out.println("Idade : " + idade);
        System.out.println("Loja  : " + loja);
    }

    // media de salario dos funcionario
    public double calcularMedia() {
        double soma = 0;
        for (double s : salarioRecebido) {
            soma += s;
        }
        return soma / salarioRecebido.length;
    }

    public double calcularBonus() {
        return salarioBase * 0.2;
    }
}

//  CLIENTE CLASSE
class Cliente {

    String nome;
    int    idade;
    String cidade, bairro, rua;

    public Cliente(String nome, int idade, String cidade, String bairro, String rua) {
        this.nome   = nome;
        this.idade  = idade;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua    = rua;
    }

    public void apresentarse() {
        System.out.println("Nome  : " + nome);
        System.out.println("Idade : " + idade);
    }
}

// aqui é a CLASSE DA LOJA
class Loja {

    String nomeFantasia;
    String razaoSocial;
    String cnpj;
    String cidade, bairro, rua;

    ArrayList<Vendedor> vendedores = new ArrayList<>();
    ArrayList<Cliente>  clientes   = new ArrayList<>();

    public Loja(String nomeFantasia, String razaoSocial, String cnpj,
                String cidade, String bairro, String rua) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial  = razaoSocial;
        this.cnpj         = cnpj;
        this.cidade       = cidade;
        this.bairro       = bairro;
        this.rua          = rua;
    }

    // Mostra a quantidade de clientes
    public void contarClientes() {
        System.out.println("Clientes   : " + clientes.size());
    }

    // Mostra a quantidade de vendedores
    public void contarVendedores() {
        System.out.println("Vendedores : " + vendedores.size());
    }

    // Printa nomeFantasia, cnpj e endereço
    public void apresentarse() {
        System.out.println("Nome fantasia : " + nomeFantasia);
        System.out.println("CNPJ          : " + cnpj);
        System.out.println("Endereço      : " + rua + " — " + bairro + ", " + cidade);
    }
}