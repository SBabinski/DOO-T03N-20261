2
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    static Loja loja;
    static Vendedor[] vendedores = new Vendedor[10];
    static Cliente[] clientes = new Cliente[10];

    static int quantidadeVendedores = 0;
    static int quantidadeClientes = 0;

    public static void main(String[] args) {
        int opcao;

        do {
            exibirMenu();
            opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    cadastrarLoja();
                    break;

                case 2:
                    cadastrarVendedor();
                    break;

                case 3:
                    cadastrarCliente();
                    break;

                case 4:
                    mostrarDados();
                    break;

                case 5:
                    System.out.println("Sistema encerrado.");
                    break;

                default:
                    System.out.println("Opcao invalida.");
                    break;
            }

            System.out.println();

        } while (opcao != 5);
    }

    public static void exibirMenu() {
        System.out.println("=================================");
        System.out.println(" SISTEMA MY PLANT");
        System.out.println("=================================");
        System.out.println("[1] - Cadastrar Loja");
        System.out.println("[2] - Cadastrar Vendedor");
        System.out.println("[3] - Cadastrar Cliente");
        System.out.println("[4] - Mostrar Dados");
        System.out.println("[5] - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    public static void cadastrarLoja() {
        System.out.println("===== CADASTRO DA LOJA =====");

        System.out.print("Nome fantasia: ");
        String nomeFantasia = scanner.nextLine();

        System.out.print("Razao social: ");
        String razaoSocial = scanner.nextLine();

        System.out.print("CNPJ: ");
        String cnpj = scanner.nextLine();

        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();

        System.out.print("Rua: ");
        String rua = scanner.nextLine();

        loja = new Loja(nomeFantasia, razaoSocial, cnpj, cidade, bairro, rua);

        cadastrarVendedoresExemplo();

        System.out.println("Loja cadastrada com sucesso.");
    }

    public static void cadastrarVendedoresExemplo() {
        if (quantidadeVendedores > 0) {
            return;
        }

        Vendedor vendedor1 = new Vendedor(
                "Carlos",
                28,
                loja,
                "Laranjeiras do Sul",
                "Centro",
                "Rua das Palmeiras",
                2500.00,
                new double[]{2500.00, 2600.00, 2550.00}
        );

        Vendedor vendedor2 = new Vendedor(
                "Ana",
                32,
                loja,
                "Laranjeiras do Sul",
                "Agua Verde",
                "Rua das Orquideas",
                2800.00,
                new double[]{2800.00, 2900.00, 2850.00}
        );

        vendedores[0] = vendedor1;
        vendedores[1] = vendedor2;

        loja.vendedores[0] = vendedor1;
        loja.vendedores[1] = vendedor2;

        quantidadeVendedores = 2;

        System.out.println("Vendedores de exemplo cadastrados automaticamente.");
    }

    public static void cadastrarVendedor() {
        if (loja == null) {
            System.out.println("Cadastre uma loja antes de cadastrar vendedores.");
            return;
        }

        if (quantidadeVendedores >= vendedores.length) {
            System.out.println("Limite de vendedores atingido.");
            return;
        }

        System.out.println("===== CADASTRO DE VENDEDOR =====");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Idade: ");
        int idade = lerInteiro();

        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();

        System.out.print("Rua: ");
        String rua = scanner.nextLine();

        System.out.print("Salario base: R$ ");
        double salarioBase = lerDouble();

        double[] salarioRecebido = new double[3];

        System.out.println("Informe os 3 ultimos salarios recebidos:");

        for (int i = 0; i < salarioRecebido.length; i++) {
            System.out.print("Salario " + (i + 1) + ": R$ ");
            salarioRecebido[i] = lerDouble();
        }

        Vendedor vendedor = new Vendedor(
                nome,
                idade,
                loja,
                cidade,
                bairro,
                rua,
                salarioBase,
                salarioRecebido
        );

        vendedores[quantidadeVendedores] = vendedor;
        loja.vendedores[quantidadeVendedores] = vendedor;
        quantidadeVendedores++;

        System.out.println("Vendedor cadastrado com sucesso.");
    }

    public static void cadastrarCliente() {
        if (quantidadeClientes >= clientes.length) {
            System.out.println("Limite de clientes atingido.");
            return;
        }

        System.out.println("===== CADASTRO DE CLIENTE =====");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Idade: ");
        int idade = lerInteiro();

        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();

        System.out.print("Rua: ");
        String rua = scanner.nextLine();

        Cliente cliente = new Cliente(nome, idade, cidade, bairro, rua);

        clientes[quantidadeClientes] = cliente;

        if (loja != null) {
            loja.clientes[quantidadeClientes] = cliente;
        }

        quantidadeClientes++;

        System.out.println("Cliente cadastrado com sucesso.");
    }

    public static void mostrarDados() {
        if (loja == null) {
            System.out.println("Nenhuma loja cadastrada.");
            return;
        }

        System.out.println("===== DADOS DA LOJA =====");
        loja.apresentarse();
        loja.contarClientes();
        loja.contarVendedores();

        System.out.println();

        System.out.println("===== VENDEDORES =====");

        if (quantidadeVendedores == 0) {
            System.out.println("Nenhum vendedor cadastrado.");
        }

        for (int i = 0; i < quantidadeVendedores; i++) {
            vendedores[i].apresentarse();
            System.out.println("Media salarial: R$ " + vendedores[i].calcularMedia());
            System.out.println("Bonus: R$ " + vendedores[i].calcularBonus());
            System.out.println("-----------------------------");
        }

        System.out.println();

        System.out.println("===== CLIENTES =====");

        if (quantidadeClientes == 0) {
            System.out.println("Nenhum cliente cadastrado.");
        }

        for (int i = 0; i < quantidadeClientes; i++) {
            clientes[i].apresentarse();
            System.out.println("-----------------------------");
        }
    }

    public static int lerInteiro() {
        return Integer.parseInt(scanner.nextLine());
    }

    public static double lerDouble() {
        String texto = scanner.nextLine();
        texto = texto.replace(",", ".");
        return Double.parseDouble(texto);
    }
}

class Vendedor {
    String nome;
    int idade;
    Loja loja;
    String cidade;
    String bairro;
    String rua;
    double salarioBase;
    double[] salarioRecebido;

    public Vendedor(String nome, int idade, Loja loja, String cidade, String bairro, String rua,
                    double salarioBase, double[] salarioRecebido) {
        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.salarioBase = salarioBase;
        this.salarioRecebido = salarioRecebido;
    }

    public void apresentarse() {
        System.out.println("Nome do vendedor: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Loja: " + loja.nomeFantasia);
    }

    public double calcularMedia() {
        double soma = 0;

        for (int i = 0; i < salarioRecebido.length; i++) {
            soma += salarioRecebido[i];
        }

        return soma / salarioRecebido.length;
    }

    public double calcularBonus() {
        return salarioBase * 0.2;
    }
}

class Cliente {
    String nome;
    int idade;
    String cidade;
    String bairro;
    String rua;

    public Cliente(String nome, int idade, String cidade, String bairro, String rua) {
        this.nome = nome;
        this.idade = idade;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
    }

    public void apresentarse() {
        System.out.println("Nome do cliente: " + nome);
        System.out.println("Idade: " + idade);
    }
}

class Loja {
    String nomeFantasia;
    String razaoSocial;
    String cnpj;
    String cidade;
    String bairro;
    String rua;
    Vendedor[] vendedores;
    Cliente[] clientes;

    public Loja(String nomeFantasia, String razaoSocial, String cnpj, String cidade, String bairro, String rua) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.vendedores = new Vendedor[10];
        this.clientes = new Cliente[10];
    }

    public void contarClientes() {
        int quantidadeClientes = 0;

        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i] != null) {
                quantidadeClientes++;
            }
        }

        System.out.println("Quantidade de clientes: " + quantidadeClientes);
    }

    public void contarVendedores() {
        int quantidadeVendedores = 0;

        for (int i = 0; i < vendedores.length; i++) {
            if (vendedores[i] != null) {
                quantidadeVendedores++;
            }
        }

        System.out.println("Quantidade de vendedores: " + quantidadeVendedores);
    }

    public void apresentarse() {
        System.out.println("Nome fantasia: " + nomeFantasia);
        System.out.println("CNPJ: " + cnpj);
        System.out.println("Endereco: " + rua + ", " + bairro + ", " + cidade);
    }
}