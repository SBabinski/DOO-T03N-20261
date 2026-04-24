import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {

    static ArrayList<Venda> vendas = new ArrayList<>();
    static Loja loja = new Loja("My Plant", "My Plant LTDA", "65.226.552/0001.08",
            "Cascavel", "Centro", "Rua Manaus 3238");

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        MenuInicial(scan);
    }

    public static void MenuInicial(Scanner scan) {
        int escolha;
        do {
            System.out.println("========MENU==========");
            System.out.println("[1] - Calcular preco total");
            System.out.println("[2] - Calcular troco");
            System.out.println("[3] - Registros de vendas");
            System.out.println("[4] - Buscar vendas por dia");
            System.out.println("[5] - Buscar vendas por mes");
            System.out.println("[6] - Cadastrar cliente");
            System.out.println("[7] - Cadastrar vendedor");
            System.out.println("[8] - Listar clientes");
            System.out.println("[9] - Listar vendedores");
            System.out.println("[10] - Mostrar dados da loja"); // ✅ NOVO
            System.out.println("[0] - Sair");

            escolha = scan.nextInt();

            switch (escolha) {
                case 1: CalcularTotal(scan); break;
                case 2: CalcularTroco(scan); break;
                case 3: MostrarVendas(); break;
                case 4: BuscarPorDia(scan); break;
                case 5: BuscarPorMes(scan); break;
                case 6: CadastrarCliente(scan); break;
                case 7: CadastrarVendedor(scan); break;
                case 8: ListarClientes(); break;
                case 9: ListarVendedores(); break;
                case 10: MostrarDadosLoja(); break; // ✅ NOVO
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

        System.out.print("Cidade: ");
        String cidade = scan.nextLine();

        System.out.print("Bairro: ");
        String bairro = scan.nextLine();

        System.out.print("Rua: ");
        String rua = scan.nextLine();

        loja.clientes.add(new Cliente(nome, idade, cidade, bairro, rua));
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

        System.out.print("Cidade: ");
        String cidade = scan.nextLine();

        System.out.print("Bairro: ");
        String bairro = scan.nextLine();

        System.out.print("Rua: ");
        String rua = scan.nextLine();

        System.out.print("Salário base: ");
        double salarioBase = scan.nextDouble();

        loja.vendedores.add(new Vendedor(nome, idade, loja.nomeFantasia, cidade, bairro, rua, salarioBase));
        System.out.println("Vendedor cadastrado!");
    }

    public static void ListarClientes() {
        for (Cliente c : loja.clientes) {
            c.apresentarSe();
        }
    }

    public static void ListarVendedores() {
        for (Vendedor v : loja.vendedores) {
            v.apresentarSe();
        }
    }

    public static void CalcularTotal(Scanner scan) {
        System.out.println("Digite a data (dd/MM/yyyy):");
        String dataTexto = scan.next();

        LocalDate data = LocalDate.parse(dataTexto, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.println("Quantidade:");
        int quantidade = scan.nextInt();

        System.out.println("Valor unitário:");
        double valor = scan.nextDouble();

        double total = quantidade * valor;
        double desconto = 0;

        if (quantidade >= 10) {
            desconto = total * 0.05;
            total -= desconto;
        }

        vendas.add(new Venda(quantidade, total, desconto, data));
        System.out.println("Total: R$ " + total);
    }

    public static void CalcularTroco(Scanner scan) {
        System.out.println("Pago:");
        double pago = scan.nextDouble();

        System.out.println("Compra:");
        double compra = scan.nextDouble();

        if (pago < compra) {
            System.out.println("Valor insuficiente");
        } else {
            System.out.println("Troco: " + (pago - compra));
        }
    }

    public static void MostrarVendas() {
        for (Venda v : vendas) {
            v.mostrarVenda();
        }
    }

    public static void BuscarPorDia(Scanner scan) {
        System.out.println("Data:");
        LocalDate data = LocalDate.parse(scan.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        int total = 0;
        for (Venda v : vendas) {
            if (v.data.equals(data)) total++;
        }

        System.out.println("Total: " + total);
    }

    public static void BuscarPorMes(Scanner scan) {
        System.out.println("Mês:");
        int mes = scan.nextInt();

        int total = 0;
        for (Venda v : vendas) {
            if (v.data.getMonthValue() == mes) total++;
        }

        System.out.println("Total: " + total);
    }


    public static void MostrarDadosLoja() {

        System.out.println("=== DADOS DA LOJA ===");
        System.out.println("Nome Fantasia: " + loja.nomeFantasia);
        System.out.println("Razão Social: " + loja.razaoSocial);
        System.out.println("CNPJ: " + loja.cnpj);
        System.out.println("Cidade: " + loja.cidade);
        System.out.println("Bairro: " + loja.bairro);
        System.out.println("Rua: " + loja.rua);

        System.out.println("\n=== VENDEDORES ===");
        for (Vendedor v : loja.vendedores) {
            v.apresentarSe();
            System.out.println("-------------------");
        }

        System.out.println("\n=== CLIENTES ===");
        for (Cliente c : loja.clientes) {
            System.out.println("Nome: " + c.nome);
            System.out.println("Cidade: " + c.cidade);
            System.out.println("-------------------");
        }
    }
}