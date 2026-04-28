import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Loja loja = new Loja();
        loja.nomeFantasia = "My Plant";
        loja.razaoSocial = "My Plant LTDA";
        loja.cnpj = "12.345.678/0001-99";
        loja.cidade = "Cascavel";
        loja.bairro = "Centro";
        loja.rua = "Rua Principal";

        int opcao;

        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Cadastrar Vendedor");
            System.out.println("2 - Cadastrar Cliente");
            System.out.println("3 - Mostrar Loja");
            System.out.println("4 - Contar Clientes");
            System.out.println("5 - Contar Vendedores");
            System.out.println("6 - Demonstração automática");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {

                case 1:
                    Vendedor v = new Vendedor();

                    System.out.print("Nome: ");
                    v.nome = sc.nextLine();

                    System.out.print("Idade: ");
                    v.idade = sc.nextInt();
                    sc.nextLine();

                    v.loja = loja;
                    v.cidade = "Cascavel";

                    System.out.print("Bairro: ");
                    v.bairro = sc.nextLine();

                    System.out.print("Rua: ");
                    v.rua = sc.nextLine();

                    System.out.print("Salário base: ");
                    v.salarioBase = sc.nextDouble();

                    v.salarioRecebido = new double[3];
                    for (int i = 0; i < 3; i++) {
                        System.out.print("Salário recebido " + (i + 1) + ": ");
                        v.salarioRecebido[i] = sc.nextDouble();
                    }

                    loja.adicionarVendedor(v);
                    System.out.println("Vendedor cadastrado!");
                    break;

                case 2:
                    Cliente c = new Cliente();

                    System.out.print("Nome: ");
                    c.nome = sc.nextLine();

                    System.out.print("Idade: ");
                    c.idade = sc.nextInt();
                    sc.nextLine();

                    c.cidade = "Cascavel";

                    System.out.print("Bairro: ");
                    c.bairro = sc.nextLine();

                    System.out.print("Rua: ");
                    c.rua = sc.nextLine();

                    loja.adicionarCliente(c);
                    System.out.println("Cliente cadastrado!");
                    break;

                case 3:
                    loja.apresentarse();
                    break;

                case 4:
                    loja.contarClientes();
                    break;

                case 5:
                    loja.contarVendedores();
                    break;

                case 6:
                    System.out.println("\n=== DEMONSTRAÇÃO AUTOMÁTICA ===");

                    // limpar dados antes (evita duplicar)
                    loja.qtdClientes = 0;
                    loja.qtdVendedores = 0;

                    // Vendedores
                    Vendedor v1 = new Vendedor();
                    v1.nome = "Carlos";
                    v1.idade = 30;
                    v1.loja = loja;
                    v1.cidade = "Cascavel";
                    v1.bairro = "Centro";
                    v1.rua = "Rua A";
                    v1.salarioBase = 2000;
                    v1.salarioRecebido = new double[]{2000, 2100, 2200};

                    Vendedor v2 = new Vendedor();
                    v2.nome = "Mariana";
                    v2.idade = 25;
                    v2.loja = loja;
                    v2.cidade = "Cascavel";
                    v2.bairro = "Batel";
                    v2.rua = "Rua B";
                    v2.salarioBase = 2500;
                    v2.salarioRecebido = new double[]{2500, 2600, 2550};

                    Vendedor v3 = new Vendedor();
                    v3.nome = "João";
                    v3.idade = 40;
                    v3.loja = loja;
                    v3.cidade = "Cascavel";
                    v3.bairro = "Pioneiros";
                    v3.rua = "Rua C";
                    v3.salarioBase = 3000;
                    v3.salarioRecebido = new double[]{3000, 3100, 3200};

                    loja.adicionarVendedor(v1);
                    loja.adicionarVendedor(v2);
                    loja.adicionarVendedor(v3);

                    // Clientes
                    Cliente c1 = new Cliente();
                    c1.nome = "Ana";
                    c1.idade = 22;
                    c1.cidade = "Cascavel";
                    c1.bairro = "Centro";
                    c1.rua = "Rua D";

                    Cliente c2 = new Cliente();
                    c2.nome = "Pedro";
                    c2.idade = 35;
                    c2.cidade = "Cascavel";
                    c2.bairro = "Alto Alegre";
                    c2.rua = "Rua E";

                    Cliente c3 = new Cliente();
                    c3.nome = "Julia";
                    c3.idade = 28;
                    c3.cidade = "Cascavel";
                    c3.bairro = "Cancelli";
                    c3.rua = "Rua F";

                    loja.adicionarCliente(c1);
                    loja.adicionarCliente(c2);
                    loja.adicionarCliente(c3);

                    // Mostrar tudo
                    loja.apresentarse();

                    System.out.println("\n--- VENDEDORES ---");
                    for (int i = 0; i < loja.qtdVendedores; i++) {
                        loja.vendedores[i].apresentarse();
                        System.out.println("Média: " + loja.vendedores[i].calcularMedia());
                        System.out.println("Bônus: " + loja.vendedores[i].calcularBonus());
                        System.out.println();
                    }

                    System.out.println("\n--- CLIENTES ---");
                    for (int i = 0; i < loja.qtdClientes; i++) {
                        loja.clientes[i].apresentarse();
                    }

                    loja.contarClientes();
                    loja.contarVendedores();

                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        sc.close();
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

    void apresentarse() {
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Loja: " + loja.nomeFantasia);
    }

    double calcularMedia() {
        double soma = 0;
        for (double s : salarioRecebido) {
            soma += s;
        }
        return soma / salarioRecebido.length;
    }

    double calcularBonus() {
        return salarioBase * 0.2;
    }
}

class Cliente {
    String nome;
    int idade;
    String cidade;
    String bairro;
    String rua;

    void apresentarse() {
        System.out.println("Nome: " + nome);
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

    Vendedor[] vendedores = new Vendedor[100];
    Cliente[] clientes = new Cliente[100];

    int qtdVendedores = 0;
    int qtdClientes = 0;

    void adicionarVendedor(Vendedor v) {
        vendedores[qtdVendedores++] = v;
    }

    void adicionarCliente(Cliente c) {
        clientes[qtdClientes++] = c;
    }

    void contarClientes() {
        System.out.println("Clientes: " + qtdClientes);
    }

    void contarVendedores() {
        System.out.println("Vendedores: " + qtdVendedores);
    }

    void apresentarse() {
        System.out.println("Loja: " + nomeFantasia);
        System.out.println("CNPJ: " + cnpj);
        System.out.println("Endereço: " + rua + ", " + bairro + ", " + cidade);
    }
}