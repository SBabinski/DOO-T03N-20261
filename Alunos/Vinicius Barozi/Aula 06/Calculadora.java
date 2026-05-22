import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Cliente {
    String nome, cidade;
    int idade;

    public Cliente(String nome, int idade, String cidade, String bairro, String rua) {
        this.nome = nome;
        this.idade = idade;
        this.cidade = cidade;
    }

    public void apresentarse() {
        System.out.println("Nome: " + nome + " | Idade: " + idade + " | Cidade: " + cidade);
    }
}

class Vendedor {
    String nome, empresa;
    int idade;
    double salarioBase;
    double[] salarioRecebido;

    public Vendedor(String nome, int idade, String empresa, String cidade, String bairro, String rua, double salarioBase) {
        this.nome = nome;
        this.idade = idade;
        this.empresa = empresa;
        this.salarioBase = salarioBase;
        this.salarioRecebido = new double[]{ salarioBase, salarioBase * 1.1, salarioBase * 1.05 };
    }

    public Vendedor() {
    }

    public void apresentarse() {
        System.out.println("Nome: " + nome + " | Idade: " + idade + " | Empresa: " + empresa);
    }

    public double calcularMedia() {
        double soma = 0;
        for (double s : salarioRecebido) soma += s;
        return soma / salarioRecebido.length;
    }

    public double calcularBonus() {
        return salarioBase * 0.20;
    }
}

class Loja {
    String nomeFantasia, cnpj, cidade, bairro, rua;
    List<Vendedor> vendedores = new ArrayList<>();
    List<Cliente> clientes = new ArrayList<>();

    public Loja(String nomeFantasia, String razaoSocial, String cnpj, String cidade, String bairro, String rua) {
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
    }

    public void apresentarse() {
        System.out.println("Nome Fantasia : " + nomeFantasia);
        System.out.println("CNPJ          : " + cnpj);
        System.out.println("Endereço      : " + rua + ", " + bairro + " - " + cidade);
        System.out.println("Vendedores    : " + vendedores.size());
        System.out.println("Clientes      : " + clientes.size());
    }
}

public class Calculadora {

    static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static final List<LocalDate> datasVendas = new ArrayList<>();
    static final List<String> historico = new ArrayList<>();
    static final Scanner sc = new Scanner(System.in);

    static final Loja loja = new Loja(
            "Gabi Floricultura",
            "Gabi Floricultura Comercio de Plantas LTDA",
            "12.345.678/0001-99",
            "Ubiratã", "Centro", "Av. Nilza de Oliveira, 1750"
    );

    static void inicializarDados() {
        loja.vendedores.add(new Vendedor("Ana Souza",   28, loja.nomeFantasia, "Ubiratã", "Conjunto JK", "Rua das Projetada, 2094", 2000.00));
        loja.vendedores.add(new Vendedor("Carlos Lima", 35, loja.nomeFantasia, "Ubiratã", "Mutirão", "Rua Bahia, 59", 2200.00));

        loja.clientes.add(new Cliente("Maria Oliveira", 45, "Ubiratã", "Jardins","Av. Yolanda Loureiro de Carvalho, 489"));
        loja.clientes.add(new Cliente("João Pereira",   30, "Ubiratã", "Vila Madalena", "Rua Maria das Graças Molina, 36"));
        loja.clientes.add(new Cliente("Lucia Ferreira", 52, "Ubiratã", "Centro", "Rua Maria Aparecida, 80"));
    }

    static double calcularPrecoTotal(int qtd, double precoUnit) {
        double total = qtd * precoUnit;
        double desconto = (qtd > 10) ? total * 0.05 : 0;
        total -= desconto;

        LocalDate hoje = LocalDate.now();
        datasVendas.add(hoje);
        historico.add(String.format("Data: %s | Qtd: %d | Preço: R$ %.2f | Desconto: R$ %.2f | Total: R$ %.2f",
                hoje, qtd, precoUnit, desconto, total));
        return total;
    }

    static double calcularTroco(double pago, double compra) {
        return pago - compra;
    }

    static void buscarPorDia(String dataStr) {
        LocalDate data = LocalDate.parse(dataStr, FMT);
        long count = datasVendas.stream().filter(d -> d.equals(data)).count();
        System.out.println("Total de vendas no dia: " + count);
    }

    static void buscarPorMes(int mes) {
        long count = datasVendas.stream().filter(d -> d.getMonthValue() == mes).count();
        System.out.println("Total de vendas no mês: " + count);
    }

    static void menuVendedores() {
        int op;
        do {
            System.out.println("\n VENDEDORES");
            System.out.println("[1] Listar  [2] Adicionar  [3] Voltar");
            System.out.print("Escolha: ");
            op = sc.nextInt(); sc.nextLine();

            switch (op) {
                case 1 -> {
                    if (loja.vendedores.isEmpty()) { System.out.println("Nenhum vendedor cadastrado."); break; }
                    loja.vendedores.forEach(v -> {
                        v.apresentarse();
                        System.out.printf("  Média: R$ %.2f | Bônus: R$ %.2f%n", v.calcularMedia(), v.calcularBonus());
                    });
                }
                case 2 -> {
                    System.out.print("Nome: ");          String nome   = sc.nextLine();
                    System.out.print("Idade: ");         int    idade  = sc.nextInt(); sc.nextLine();
                    System.out.print("Cidade: ");        String cidade = sc.nextLine();
                    System.out.print("Bairro: ");        String bairro = sc.nextLine();
                    System.out.print("Rua: ");           String rua    = sc.nextLine();
                    System.out.print("Salário Base: ");  double sal    = sc.nextDouble(); sc.nextLine();
                    loja.vendedores.add(new Vendedor(nome, idade, loja.nomeFantasia, cidade, bairro, rua, sal));
                    System.out.println("Vendedor cadastrado!");
                }
            }
        } while (op != 3);
    }

    static void menuClientes() {
        int op;
        do {
            System.out.println("\n CLIENTES ");
            System.out.println("[1] Listar  [2] Adicionar  [3] Voltar");
            System.out.print("Escolha: ");
            op = sc.nextInt(); sc.nextLine();

            switch (op) {
                case 1 -> {
                    if (loja.clientes.isEmpty()) { System.out.println("Nenhum cliente cadastrado."); break; }
                    loja.clientes.forEach(Cliente::apresentarse);
                }
                case 2 -> {
                    System.out.print("Nome: ");   String nome   = sc.nextLine();
                    System.out.print("Idade: ");  int    idade  = sc.nextInt(); sc.nextLine();
                    System.out.print("Cidade: "); String cidade = sc.nextLine();
                    System.out.print("Bairro: "); String bairro = sc.nextLine();
                    System.out.print("Rua: ");    String rua    = sc.nextLine();
                    loja.clientes.add(new Cliente(nome, idade, cidade, bairro, rua));
                    System.out.println("Cliente cadastrado!");
                }
            }
        } while (op != 3);
    }

    static void menuGerenciamento() {
        int op;
        do {
            System.out.println("\n Gerenciamento " + loja.nomeFantasia + " ");
            System.out.println("[1] Informações da Loja  [2] Vendedores  [3] Clientes  [4] Voltar");
            System.out.print("Escolha: ");
            op = sc.nextInt(); sc.nextLine();

            switch (op) {
                case 1 -> loja.apresentarse();
                case 2 -> menuVendedores();
                case 3 -> menuClientes();
            }
        } while (op != 4);
    }


    public static void main(String[] args) {
        inicializarDados();
        try (Scanner scanner = new Scanner(System.in)) {
            int op;

            do {
                System.out.println("\n Sistema " + loja.nomeFantasia + " ");
                System.out.println("[1] Calcular Preço Total    [2] Calcular Troco");
                System.out.println("[3] Ver Registro de Vendas  [4] Buscar por Dia");
                System.out.println("[5] Buscar por Mês          [6] Gerenciamento");
                System.out.println("[7] Sair");
                System.out.print("Escolha: ");
                op = scanner.nextInt(); scanner.nextLine();

                switch (op) {
                    case 1 -> {
                        System.out.print("Quantidade de plantas: "); int    qtd   = scanner.nextInt();
                        System.out.print("Preço unitário: ");        double preco = scanner.nextDouble(); scanner.nextLine();
                        double total = calcularPrecoTotal(qtd, preco);
                        if (qtd > 10) System.out.println("Desconto de 5% aplicado!");
                        System.out.printf("Total: R$ %.2f%n", total);
                    }
                    case 2 -> {
                        System.out.print("Valor pago: ");       double pago   = scanner.nextDouble();
                        System.out.print("Valor da compra: ");  double compra = scanner.nextDouble(); scanner.nextLine();
                        double troco = calcularTroco(pago, compra);
                        if (troco < 0) System.out.printf("Valor insuficiente! Faltam R$ %.2f%n", -troco);
                        else           System.out.printf("Troco: R$ %.2f%n", troco);
                    }
                    case 3 -> {
                        System.out.println("\n Registro de Vendas ");
                        if (historico.isEmpty()) System.out.println("Nenhuma venda registrada.");
                        else historico.forEach(System.out::println);
                    }
                    case 4 -> { System.out.print("Data (dd/MM/yyyy): ");  buscarPorDia(scanner.nextLine()); }
                    case 5 -> { System.out.print("Mês (1-12): "); buscarPorMes(scanner.nextInt()); scanner.nextLine(); }
                    case 6 -> menuGerenciamento();
                    case 7 -> System.out.println("Encerrando... Até logo!");
                    default -> System.out.println("Opção inválida!");
                }
            } while (op != 7);
        }
    }
}